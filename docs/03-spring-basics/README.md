# Chapter 3: Maven + Spring基礎（DI / IoC）

## 🎯 学習目標

- Mavenによるプロジェクト管理と依存関係の解決を理解する
- DI（Dependency Injection）とIoC（Inversion of Control）の概念を習得する
- Springのアノテーションを使ってBeanを定義・注入できる

---

## 📚 学習トピック

### 3.1 Maven

**基本構成:**
```
my-project/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/       # Javaソースコード
    │   └── resources/  # 設定ファイル
    └── test/
        ├── java/       # テストコード
        └── resources/
```

**pom.xmlの主要要素:**
- `<groupId>`, `<artifactId>`, `<version>` — プロジェクト座標
- `<dependencies>` — 依存ライブラリ
- `<parent>` — spring-boot-starter-parentの継承
- `<build><plugins>` — ビルドプラグイン

**主要コマンド:**
```bash
mvn compile        # コンパイル
mvn test           # テスト実行
mvn package        # JARファイル生成
mvn spring-boot:run  # Spring Boot起動
mvn dependency:tree  # 依存ツリー表示
```

### 3.2 IoC（制御の逆転）とは

従来のコード:
```java
// 利用側がインスタンスを自分で作る
UserService service = new UserServiceImpl();
```

IoC後のコード:
```java
// Springコンテナがインスタンスを作って渡してくれる
@Autowired
private UserService service;
```

**メリット:**
- クラス間の結合度が下がる（テストしやすい）
- 実装を差し替えやすい
- ライフサイクル管理をSpringに任せられる

### 3.3 Bean とは

- Springコンテナが管理するオブジェクト
- スコープ: `singleton`（デフォルト）/ `prototype` / `request` / `session`

### 3.4 主要アノテーション

| アノテーション | 用途 |
|---|---|
| `@Component` | 汎用コンポーネント |
| `@Service` | ビジネスロジック層 |
| `@Repository` | データアクセス層 |
| `@Controller` / `@RestController` | プレゼンテーション層 |
| `@Configuration` + `@Bean` | Java Config でBeanを定義 |
| `@Autowired` | 依存注入（フィールド / コンストラクタ / セッター） |
| `@Qualifier` | 複数のBean候補から指定して注入 |
| `@Primary` | デフォルトで選択されるBeanを指定 |

### 3.5 依存注入の3つの方法

**① フィールドインジェクション（非推奨）:**
```java
@Autowired
private UserService userService;
```

**② コンストラクタインジェクション（推奨）:**
```java
private final UserService userService;

public UserController(UserService userService) {
    this.userService = userService;
}
```

**③ セッターインジェクション:**
```java
@Autowired
public void setUserService(UserService userService) {
    this.userService = userService;
}
```

> コンストラクタインジェクションが推奨される理由:
> - `final` フィールドにできる（イミュータブル）
> - テスト時にSpringなしで注入できる
> - 循環依存をコンパイル時に検出できる

### 3.6 ApplicationContext

- Springの中核となるDIコンテナ
- `@SpringBootApplication` が付いたクラスから起動すると自動的に初期化される
- `@ComponentScan` でBeanの対象パッケージを指定（デフォルトは起動クラスと同パッケージ以下）

---

## 🔗 関連リソース

- [examples/03-spring-basics/](../../examples/03-spring-basics/) — サンプルコード
- [exercises/03-spring-basics/problem/](../../exercises/03-spring-basics/problem/) — 演習問題
- [exercises/03-spring-basics/solution/](../../exercises/03-spring-basics/solution/) — 解答例

---

## ⏱️ 推奨学習時間

1週間（10〜15時間）

---

## ✅ チェックリスト

- [ ] `pom.xml` の基本構造を書ける
- [ ] `mvn spring-boot:run` でアプリケーションを起動できる
- [ ] IoC（制御の逆転）の概念を説明できる
- [ ] `@Service` / `@Repository` の役割を説明できる
- [ ] コンストラクタインジェクションを使ってBeanを注入できる
- [ ] インターフェース経由で注入してテスト容易性を確保できる

---

## 📝 次の章

[Chapter 4: Spring Boot入門](../04-spring-boot/README.md)
