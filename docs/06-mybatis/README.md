# Chapter 6: MyBatis 基礎

## 🎯 学習目標

- MyBatisの仕組みとJPAとの違いを理解する
- アノテーションとXMLマッパーを使ってCRUDを実装できる
- `ResultMap` で複雑なマッピングを扱える
- H2インメモリDBと `schema.sql` / `data.sql` でローカル開発できる

---

## 📚 学習トピック

### 6.1 MyBatis とは

**ORMとSQLマッパーの違い:**
| | JPA (Hibernate) | MyBatis |
|---|---|---|
| SQLの書き方 | 自動生成（JPQL） | 開発者が直接SQL記述 |
| 複雑なSQL | 苦手 | 得意 |
| 学習コスト | 高め | 低め |
| パフォーマンス制御 | 難しい | やりやすい |

**MyBatisのメリット:**
- 複雑な結合クエリや集計クエリをそのまま書ける
- SQLを完全に制御できる
- DBエンジン固有の機能を活用しやすい

### 6.2 pom.xml の設定

```xml
<dependencies>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>3.0.3</version>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### 6.3 application.yml の設定

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true  # http://localhost:8080/h2-console でSQL確認可能
  sql:
    init:
      schema-locations: classpath:schema.sql
      data-locations: classpath:data.sql

mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true   # user_name → userName 自動変換
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # SQLログ出力
```

### 6.4 スキーマとテストデータ

**schema.sql:**
```sql
CREATE TABLE IF NOT EXISTS users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**data.sql:**
```sql
INSERT INTO users (name, email) VALUES ('田中 太郎', 'tanaka@example.com');
INSERT INTO users (name, email) VALUES ('佐藤 花子', 'sato@example.com');
```

### 6.5 Mapper インターフェース（アノテーション方式）

```java
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{id}")
    Optional<User> findById(Long id);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Insert("INSERT INTO users (name, email) VALUES (#{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
    int update(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Long id);
}
```

### 6.6 XMLマッパー（複雑なSQLに推奨）

**UserMapper.java（インターフェース）:**
```java
@Mapper
public interface UserMapper {
    Optional<User> findById(Long id);
    List<User> findAll();
    int insert(User user);
    int update(User user);
    int deleteById(Long id);
}
```

**resources/mapper/UserMapper.xml:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.mapper.UserMapper">

    <resultMap id="userResultMap" type="com.example.entity.User">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="email" column="email"/>
        <result property="createdAt" column="created_at"/>
    </resultMap>

    <select id="findById" resultMap="userResultMap">
        SELECT id, name, email, created_at
        FROM users
        WHERE id = #{id}
    </select>

    <select id="findAll" resultMap="userResultMap">
        SELECT id, name, email, created_at
        FROM users
        ORDER BY id
    </select>

    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO users (name, email)
        VALUES (#{name}, #{email})
    </insert>

    <update id="update">
        UPDATE users
        SET name = #{name}, email = #{email}
        WHERE id = #{id}
    </update>

    <delete id="deleteById">
        DELETE FROM users WHERE id = #{id}
    </delete>

</mapper>
```

### 6.7 Entity クラス

```java
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    // コンストラクタ / getter / setter
}
```

### 6.8 Repository 層 での利用

```java
@Repository
public class UserRepository {

    private final UserMapper userMapper;

    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User save(User user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
        return user;
    }

    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }
}
```

---

## 🔗 関連リソース

- [examples/06-mybatis/](../../examples/06-mybatis/) — 動くサンプルコード（H2 + MyBatis CRUD）
- [exercises/06-mybatis/problem/](../../exercises/06-mybatis/problem/) — 演習問題
- [exercises/06-mybatis/solution/](../../exercises/06-mybatis/solution/) — 解答例

---

## ⏱️ 推奨学習時間

1週間（10〜15時間）

---

## ✅ チェックリスト

- [ ] `@Mapper` アノテーションを使ってMapperを定義できる
- [ ] XMLマッパーで `<resultMap>` を使ったマッピングができる
- [ ] `useGeneratedKeys` で自動採番IDを取得できる
- [ ] `map-underscore-to-camel-case` 設定の役割を説明できる
- [ ] H2コンソールでSQLを実行してデータ確認できる
- [ ] `schema.sql` / `data.sql` でテストデータを投入できる

---

## 📝 次の章

[Chapter 7: MyBatis応用（動的SQL・関連付け）](../07-mybatis-advanced/README.md)
