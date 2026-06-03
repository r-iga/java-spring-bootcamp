# Chapter 9: レイヤードアーキテクチャ

## 🎯 学習目標

- Controller / Service / Repository の責務を明確に分離できる
- カスタム例外を設計して一貫したエラーレスポンスを返せる
- `@Transactional` を正しく使えてトランザクションの境界を意識できる
- ディレクトリ構成のベストプラクティスを理解する

---

## 📚 学習トピック

### 9.1 レイヤードアーキテクチャとは

```
[HTTP Request]
      ↓
┌─────────────────────────────┐
│  Controller層                │  HTTPの受け口。DTOに変換してServiceへ委譲
└──────────────┬──────────────┘
               ↓
┌─────────────────────────────┐
│  Service層                   │  ビジネスロジックの中心。トランザクション境界
└──────────────┬──────────────┘
               ↓
┌─────────────────────────────┐
│  Repository層 (Mapper)       │  DBアクセスの抽象化。SQLの実行
└──────────────┬──────────────┘
               ↓
          [Database]
```

**各層の責務:**
| 層 | 責務 | 知ってよいもの |
|---|---|---|
| Controller | リクエスト受け取り・バリデーション・レスポンス変換 | HTTP, DTO |
| Service | ビジネスルール・複数のRepository協調・トランザクション | Entity, DTO, Repository |
| Repository | DBアクセスのみ | Entity, SQL |

### 9.2 ディレクトリ構成

```
src/main/java/com/example/
├── controller/
│   └── UserController.java
├── service/
│   ├── UserService.java          # インターフェース（依存逆転の原則）
│   └── impl/
│       └── UserServiceImpl.java
├── repository/                   # Repositoryクラスを置く場合
│   └── UserRepository.java
├── mapper/
│   └── UserMapper.java           # MyBatis @Mapper
├── entity/
│   └── User.java                 # DBのテーブルに対応するクラス
├── dto/
│   ├── request/
│   │   ├── CreateUserRequest.java
│   │   └── UpdateUserRequest.java
│   └── response/
│       ├── UserResponse.java
│       └── PageResponse.java
├── exception/
│   ├── UserNotFoundException.java
│   ├── DuplicateEmailException.java
│   └── GlobalExceptionHandler.java
└── config/
    └── MyBatisConfig.java

src/main/resources/
├── application.yml
├── application-dev.yml
├── application-prod.yml
├── schema.sql
├── data.sql
└── mapper/
    └── UserMapper.xml
```

### 9.3 Serviceのインターフェースを作る理由

```java
// インターフェース
public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(Long id);
    UserResponse create(CreateUserRequest request);
    UserResponse update(Long id, UpdateUserRequest request);
    void delete(Long id);
}

// 実装クラス
@Service
public class UserServiceImpl implements UserService {
    // ...
}
```

**メリット:**
- テスト時にモックに差し替えやすい
- 将来的に別の実装に切り替えやすい（DIP: 依存逆転の原則）
- Controllerは抽象（インターフェース）に依存するため変更に強い

### 9.4 例外設計

**カスタム例外の階層:**
```
RuntimeException
├── ApplicationException（基底クラス）
│   ├── NotFoundException
│   │   └── UserNotFoundException
│   ├── ConflictException
│   │   └── DuplicateEmailException
│   └── BadRequestException
```

**基底例外クラス:**
```java
public abstract class ApplicationException extends RuntimeException {
    private final String errorCode;

    protected ApplicationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
```

**個別例外クラス:**
```java
public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(Long id) {
        super("USER_NOT_FOUND", "ユーザーが見つかりません: id=" + id);
    }
}

public class DuplicateEmailException extends ApplicationException {
    public DuplicateEmailException(String email) {
        super("DUPLICATE_EMAIL", "このメールアドレスはすでに使用されています: " + email);
    }
}
```

**グローバル例外ハンドラー:**
```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(UserNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(DuplicateEmailException ex) {
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
        return new ErrorResponse("VALIDATION_ERROR", message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex) {
        log.error("Unexpected error", ex);
        return new ErrorResponse("INTERNAL_ERROR", "サーバー内部エラーが発生しました");
    }
}
```

### 9.5 トランザクション管理

```java
@Service
@Transactional(readOnly = true)  // クラスレベルで読み取り専用をデフォルト化
public class UserServiceImpl implements UserService {

    @Override
    public List<UserResponse> findAll() {
        // readOnly=true が適用される
        return userMapper.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional  // 書き込み操作は明示的に上書き（readOnly=false）
    public UserResponse create(CreateUserRequest request) {
        if (userMapper.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }
        var user = new User(null, request.name(), request.email(), null);
        userMapper.insert(user);
        return toResponse(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userMapper.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
        userMapper.deleteById(id);
    }
}
```

**@Transactional のポイント:**
- `readOnly = true` の利用でパフォーマンスの最適化が期待できる
- **Service層に置く** — Repositoryに置くと複数Repository操作でトランザクションが分断される
- 非チェック例外（RuntimeException）でロールバック、チェック例外（Exception）ではロールバックしない（デフォルト）

### 9.6 Mapper → Service → Controller のデータ変換

```java
// Entityは内部表現（DBの構造に近い）
public class User { ... }

// ResponseDTOは外部表現（APIクライアントへの出力）
public record UserResponse(Long id, String name, String email, LocalDateTime createdAt) {}

// Service内でEntityからResponseDTOへ変換する
private UserResponse toResponse(User user) {
    return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
}
```

---

## 🔗 関連リソース

- [examples/09-architecture/](../../examples/09-architecture/) — 総合サンプル
- [projects/final/README.md](../../projects/final/README.md) — 最終プロジェクト

---

## ⏱️ 推奨学習時間

1週間（10〜12時間）

---

## ✅ チェックリスト

- [ ] 各層の責務を明確に説明できる
- [ ] Serviceをインターフェースと実装クラスに分けられる
- [ ] カスタム例外クラスを設計して `@RestControllerAdvice` でハンドリングできる
- [ ] `@Transactional(readOnly = true)` を活用できる
- [ ] EntityとDTOを分けてデータ変換できる
- [ ] 推奨ディレクトリ構成に従ってプロジェクトを組める

---

## 📝 次のステップ

[最終プロジェクト](../../projects/final/README.md)
