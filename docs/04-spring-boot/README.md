# Chapter 4: Spring Boot 入門

## 🎯 学習目標

- Spring Bootの自動設定の仕組みを理解する
- `application.properties` / `application.yml` を使って設定を管理できる
- プロファイルを使って環境ごとに設定を切り替えられる
- SLF4J + Logbackを使ってログを出力できる

---

## 📚 学習トピック

### 4.1 Spring Boot とは

**Spring Frameworkとの違い:**
- Spring Framework: 柔軟だが設定が多い
- Spring Boot: **「慣習優先」** アプローチで設定を最小化

**Spring Bootの3大特徴:**
1. **自動設定（Auto-configuration）** — classpath上のライブラリを検出して自動設定
2. **スターター（Starter）** — 依存関係のセット (`spring-boot-starter-web` など)
3. **組み込みサーバー** — Tomcatが内蔵されていてjarで起動できる

### 4.2 @SpringBootApplication

```java
@SpringBootApplication  // 以下3つのアノテーションの合成
// = @SpringBootConfiguration
// + @EnableAutoConfiguration
// + @ComponentScan
public class BootcampApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootcampApplication.class, args);
    }
}
```

### 4.3 application.properties / application.yml

**application.properties:**
```properties
# サーバー設定
server.port=8080
server.servlet.context-path=/api

# データソース
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# ログレベル
logging.level.root=INFO
logging.level.com.example=DEBUG
```

**application.yml（同等の内容）:**
```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:

logging:
  level:
    root: INFO
    com.example: DEBUG
```

### 4.4 プロパティの読み込み

**@Value:**
```java
@Value("${app.name:デフォルト値}")
private String appName;
```

**@ConfigurationProperties（推奨）:**
```java
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {
    private String name;
    private int maxRetry;
    // getter / setter
}
```

### 4.5 プロファイル

```
src/main/resources/
├── application.properties          # 共通設定
├── application-dev.properties      # 開発環境
└── application-prod.properties     # 本番環境
```

**起動時にプロファイル指定:**
```bash
# JVMオプション
java -Dspring.profiles.active=dev -jar app.jar

# 環境変数
SPRING_PROFILES_ACTIVE=prod mvn spring-boot:run
```

**コードでの分岐:**
```java
@Profile("dev")
@Bean
public DataSource devDataSource() { ... }
```

### 4.6 ロギング（SLF4J + Logback）

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User findById(Long id) {
        log.debug("findById called: id={}", id);
        // ...
        log.info("User found: {}", user);
        return user;
    }
}
```

**Lombokを使う場合:**
```java
@Slf4j  // @Lombokアノテーション
@Service
public class UserService {
    public User findById(Long id) {
        log.debug("findById called: id={}", id);
        ...
    }
}
```

### 4.7 主要なスターター

| スターター | 用途 |
|---|---|
| `spring-boot-starter-web` | Web / REST API（Tomcat込み） |
| `spring-boot-starter-data-jpa` | JPA / Hibernate |
| `spring-boot-starter-test` | JUnit / Mockito |
| `spring-boot-starter-validation` | Bean Validation |
| `spring-boot-starter-security` | Spring Security |
| `mybatis-spring-boot-starter` | MyBatis連携 |

---

## 🔗 関連リソース

- [examples/04-spring-boot/](../../examples/04-spring-boot/) — サンプルコード
- [exercises/04-spring-boot/problem/](../../exercises/04-spring-boot/problem/) — 演習問題
- [exercises/04-spring-boot/solution/](../../exercises/04-spring-boot/solution/) — 解答例

---

## ⏱️ 推奨学習時間

1週間（10〜12時間）

---

## ✅ チェックリスト

- [ ] `@SpringBootApplication` の役割を説明できる
- [ ] `application.yml` でサーバーポートを変更できる
- [ ] `@ConfigurationProperties` でプロパティを型安全に読み込める
- [ ] `dev` / `prod` プロファイルを切り替えて起動できる
- [ ] SLF4Jで `DEBUG` / `INFO` / `WARN` / `ERROR` レベルでログを出せる

---

## 📝 次の章

[Chapter 5: REST API設計と実装](../05-rest-api/README.md)
