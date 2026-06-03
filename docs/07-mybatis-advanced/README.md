# Chapter 7: MyBatis 応用（動的SQL・関連付け）

## 🎯 学習目標

- `<if>` / `<foreach>` / `<choose>` を使った動的SQLを書ける
- `<association>` / `<collection>` で関連エンティティをマッピングできる
- ページネーションとキーワード検索を実装できる

---

## 📚 学習トピック

### 7.1 動的SQL

**`<if>` — 条件付きSQL:**
```xml
<select id="search" resultMap="userResultMap">
    SELECT * FROM users
    <where>
        <if test="name != null and name != ''">
            AND name LIKE CONCAT('%', #{name}, '%')
        </if>
        <if test="email != null and email != ''">
            AND email = #{email}
        </if>
    </where>
    ORDER BY id
</select>
```
> `<where>` タグは条件が1つもない場合に `WHERE` を除去し、先頭の `AND` を自動削除する。

**`<choose>` / `<when>` / `<otherwise>` — if-else:**
```xml
<select id="findSorted" resultMap="userResultMap">
    SELECT * FROM users
    ORDER BY
    <choose>
        <when test="sortBy == 'name'">name</when>
        <when test="sortBy == 'email'">email</when>
        <otherwise>id</otherwise>
    </choose>
    <if test="order == 'desc'">DESC</if>
    <if test="order != 'desc'">ASC</if>
</select>
```

**`<foreach>` — IN句・バルクインサート:**
```xml
<!-- IN句 -->
<select id="findByIds" resultMap="userResultMap">
    SELECT * FROM users
    WHERE id IN
    <foreach collection="ids" item="id" open="(" separator="," close=")">
        #{id}
    </foreach>
</select>

<!-- バルクインサート -->
<insert id="insertBatch">
    INSERT INTO users (name, email) VALUES
    <foreach collection="users" item="user" separator=",">
        (#{user.name}, #{user.email})
    </foreach>
</insert>
```

**`<set>` — UPDATE の動的セット句:**
```xml
<update id="updatePartial">
    UPDATE users
    <set>
        <if test="name != null">name = #{name},</if>
        <if test="email != null">email = #{email},</if>
    </set>
    WHERE id = #{id}
</update>
```

### 7.2 1対多・多対1の関連付け

**テーブル構成（注文と注文明細）:**
```sql
CREATE TABLE orders (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    total      DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id   BIGINT NOT NULL,
    product    VARCHAR(200) NOT NULL,
    quantity   INT NOT NULL,
    price      DECIMAL(10, 2) NOT NULL
);
```

**多対1（OrderItem → Order）— `<association>`:**
```xml
<resultMap id="orderItemResultMap" type="OrderItem">
    <id property="id" column="item_id"/>
    <result property="product" column="product"/>
    <result property="quantity" column="quantity"/>
    <result property="price" column="price"/>
    <association property="order" javaType="Order">
        <id property="id" column="order_id"/>
        <result property="total" column="total"/>
    </association>
</resultMap>
```

**1対多（Order → OrderItem）— `<collection>`:**
```xml
<resultMap id="orderResultMap" type="Order">
    <id property="id" column="id"/>
    <result property="total" column="total"/>
    <result property="createdAt" column="created_at"/>
    <collection property="items" ofType="OrderItem">
        <id property="id" column="item_id"/>
        <result property="product" column="product"/>
        <result property="quantity" column="quantity"/>
        <result property="price" column="price"/>
    </collection>
</resultMap>

<select id="findOrderWithItems" resultMap="orderResultMap">
    SELECT
        o.id, o.total, o.created_at,
        i.id AS item_id, i.product, i.quantity, i.price
    FROM orders o
    LEFT JOIN order_items i ON i.order_id = o.id
    WHERE o.id = #{id}
</select>
```

### 7.3 ページネーション

**Mapper:**
```java
List<User> findPage(@Param("limit") int limit, @Param("offset") int offset);
long countAll();
```

**XML:**
```xml
<select id="findPage" resultMap="userResultMap">
    SELECT * FROM users
    ORDER BY id
    LIMIT #{limit} OFFSET #{offset}
</select>

<select id="countAll" resultType="long">
    SELECT COUNT(*) FROM users
</select>
```

**Service 層でのページング計算:**
```java
public record PageResult<T>(
    List<T> items,
    long totalCount,
    int page,
    int size,
    int totalPages
) {}

public PageResult<UserResponse> findPage(int page, int size) {
    int offset = (page - 1) * size;
    List<User> users = userMapper.findPage(size, offset);
    long total = userMapper.countAll();
    int totalPages = (int) Math.ceil((double) total / size);
    List<UserResponse> responses = users.stream()
        .map(this::toResponse)
        .toList();
    return new PageResult<>(responses, total, page, size, totalPages);
}
```

### 7.4 検索条件オブジェクト（SearchCriteria）

```java
public record UserSearchCriteria(
    String name,
    String email,
    String sortBy,
    String order
) {}
```

```xml
<select id="search" resultMap="userResultMap" parameterType="UserSearchCriteria">
    SELECT * FROM users
    <where>
        <if test="name != null and name != ''">
            AND name LIKE CONCAT('%', #{name}, '%')
        </if>
        <if test="email != null and email != ''">
            AND email LIKE CONCAT('%', #{email}, '%')
        </if>
    </where>
</select>
```

---

## 🔗 関連リソース

- [examples/07-mybatis-advanced/](../../examples/07-mybatis-advanced/) — 動くサンプルコード
- [exercises/07-mybatis-advanced/problem/](../../exercises/07-mybatis-advanced/problem/) — 演習問題
- [exercises/07-mybatis-advanced/solution/](../../exercises/07-mybatis-advanced/solution/) — 解答例

---

## ⏱️ 推奨学習時間

1週間（10〜12時間）

---

## ✅ チェックリスト

- [ ] `<where>` + `<if>` で動的な検索条件を実装できる
- [ ] `<foreach>` でIN句を生成できる
- [ ] `<set>` + `<if>` で部分UPDATEを実装できる
- [ ] `<collection>` で1対多の関連をマッピングできる
- [ ] LIMITとOFFSETでページネーションを実装できる

---

## 📝 次の章

[Chapter 8: テスト（JUnit / Mockito / Spring Boot Test）](../08-testing/README.md)
