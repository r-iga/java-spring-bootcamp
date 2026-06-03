# Chapter 5: REST API 設計と実装

## 🎯 学習目標

- RESTful APIの設計原則を理解する
- `@RestController` を使ってCRUD APIを実装できる
- DTOパターンでリクエスト / レスポンスを分離できる
- Bean Validationでリクエストを検証できる
- `@ControllerAdvice` で一元的な例外ハンドリングを実装できる

---

## 📚 学習トピック

### 5.1 REST設計の基本

**リソース中心のURL設計:**
| 操作 | HTTP Method | URL | 説明 |
|---|---|---|---|
| 一覧取得 | GET | `/api/users` | ユーザー一覧 |
| 1件取得 | GET | `/api/users/{id}` | 特定ユーザー |
| 新規作成 | POST | `/api/users` | ユーザー作成 |
| 更新 | PUT | `/api/users/{id}` | 全フィールド更新 |
| 部分更新 | PATCH | `/api/users/{id}` | 一部フィールド更新 |
| 削除 | DELETE | `/api/users/{id}` | ユーザー削除 |

**HTTPステータスコード:**
- `200 OK` — 正常
- `201 Created` — リソース作成成功
- `204 No Content` — 削除成功（ボディなし）
- `400 Bad Request` — リクエスト不正
- `404 Not Found` — リソースなし
- `409 Conflict` — 重複など
- `500 Internal Server Error` — サーバーエラー

### 5.2 Controller の実装

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id,
                               @Valid @RequestBody UpdateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
```

### 5.3 DTOパターン

**なぜDTOを使うか:**
- Entityに含まれるすべての情報を外部に公開したくない（パスワードなど）
- 入力の形式（Request）と出力の形式（Response）を分離できる
- バリデーションをリクエストDTO側に閉じ込められる

**レスポンスDTO:**
```java
public record UserResponse(
    Long id,
    String name,
    String email,
    LocalDateTime createdAt
) {}
```

**リクエストDTO + バリデーション:**
```java
public record CreateUserRequest(
    @NotBlank(message = "名前は必須です")
    @Size(max = 100, message = "名前は100文字以内にしてください")
    String name,

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレスの形式が正しくありません")
    String email,

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 8, message = "パスワードは8文字以上にしてください")
    String password
) {}
```

### 5.4 Bean Validation 主要アノテーション

| アノテーション | 説明 |
|---|---|
| `@NotNull` | null 不可 |
| `@NotBlank` | null・空文字・空白不可（文字列用） |
| `@NotEmpty` | null・空コレクション不可 |
| `@Size(min, max)` | 長さ範囲 |
| `@Min(value)` / `@Max(value)` | 数値範囲 |
| `@Email` | メール形式 |
| `@Pattern(regexp)` | 正規表現 |
| `@Positive` | 正の数 |
| `@Future` / `@Past` | 未来日・過去日 |

### 5.5 例外ハンドリング

**カスタム例外:**
```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("ユーザーが見つかりません: id=" + id);
    }
}
```

**グローバル例外ハンドラー:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(UserNotFoundException ex) {
        return new ErrorResponse("NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
        return new ErrorResponse("VALIDATION_ERROR", message);
    }
}
```

**エラーレスポンス:**
```java
public record ErrorResponse(String code, String message) {}
```

### 5.6 レイヤー構成

```
src/main/java/com/example/
├── controller/
│   └── UserController.java
├── service/
│   ├── UserService.java          # インターフェース
│   └── impl/
│       └── UserServiceImpl.java  # 実装クラス
├── dto/
│   ├── CreateUserRequest.java
│   ├── UpdateUserRequest.java
│   └── UserResponse.java
└── exception/
    ├── UserNotFoundException.java
    └── GlobalExceptionHandler.java
```

---

## 🔗 関連リソース

- [examples/05-rest-api/](../../examples/05-rest-api/) — 動くサンプルコード（インメモリCRUD API）
- [exercises/05-rest-api/problem/](../../exercises/05-rest-api/problem/) — 演習問題
- [exercises/05-rest-api/solution/](../../exercises/05-rest-api/solution/) — 解答例

---

## ⏱️ 推奨学習時間

2週間（15〜20時間）

---

## ✅ チェックリスト

- [ ] RESTfulなURL設計ができる
- [ ] HTTPメソッドとステータスコードを正しく使い分けられる
- [ ] DTOパターンでリクエスト / レスポンスを分離できる
- [ ] `@Valid` + Bean Validationでバリデーションを実装できる
- [ ] `@RestControllerAdvice` で例外を一元管理できる
- [ ] `curl` や Postman でAPIをテストできる

---

## 📝 次の章

[Chapter 6: MyBatis基礎](../06-mybatis/README.md)
