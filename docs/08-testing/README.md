# Chapter 8: テスト（JUnit / Mockito / Spring Boot Test）

## 🎯 学習目標

- JUnit 5でユニットテストを書ける
- Mockitoでモックを作成してService層を単体テストできる
- `@WebMvcTest` でControllerをテストできる
- `@MybatisTest` でMapperをテストできる
- テストの種類と使い分けを理解する

---

## 📚 学習トピック

### 8.1 テストの種類と使い分け

| 種類 | アノテーション | Spring起動 | DBアクセス | 速度 |
|---|---|---|---|---|
| ユニットテスト | なし（JUnit only） | なし | なし | 最速 |
| Serviceテスト（モック） | なし | なし | モック | 速い |
| Controllerテスト | `@WebMvcTest` | 一部 | なし | 中 |
| Mapperテスト | `@MybatisTest` | 一部 | インメモリDB | 中 |
| 統合テスト | `@SpringBootTest` | 全部 | 実DB | 遅い |

### 8.2 JUnit 5 の基礎

```java
class UserValidatorTest {

    @Test
    void 有効なメールアドレスの場合はtrueを返す() {
        var validator = new UserValidator();
        assertThat(validator.isValidEmail("test@example.com")).isTrue();
    }

    @Test
    void 無効なメールアドレスの場合はfalseを返す() {
        var validator = new UserValidator();
        assertThat(validator.isValidEmail("invalid-email")).isFalse();
    }

    @Test
    void nullの場合はIllegalArgumentExceptionをスローする() {
        var validator = new UserValidator();
        assertThatThrownBy(() -> validator.isValidEmail(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void 空白文字はfalseを返す(String input) {
        var validator = new UserValidator();
        assertThat(validator.isValidEmail(input)).isFalse();
    }
}
```

> AssertJを使うと `assertThat` で可読性の高いアサーションが書ける。

### 8.3 Mockito — Service のユニットテスト

```java
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findById_存在するIDの場合はUserResponseを返す() {
        // Arrange
        var user = new User(1L, "田中 太郎", "tanaka@example.com", null);
        when(userMapper.findById(1L)).thenReturn(Optional.of(user));

        // Act
        var result = userService.findById(1L);

        // Assert
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("田中 太郎");
        verify(userMapper, times(1)).findById(1L);
    }

    @Test
    void findById_存在しないIDの場合はUserNotFoundExceptionをスローする() {
        // Arrange
        when(userMapper.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.findById(99L))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void create_正常な入力でユーザーを作成できる() {
        // Arrange
        var request = new CreateUserRequest("山田 次郎", "yamada@example.com", "password123");
        doAnswer(inv -> {
            User u = inv.getArgument(0);
            u.setId(1L);
            return null;
        }).when(userMapper).insert(any(User.class));

        // Act
        var result = userService.create(request);

        // Assert
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("山田 次郎");
        verify(userMapper).insert(any(User.class));
    }
}
```

### 8.4 @WebMvcTest — Controller のテスト

```java
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void GET_api_users_id_正常系() throws Exception {
        var response = new UserResponse(1L, "田中 太郎", "tanaka@example.com", null);
        when(userService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("田中 太郎"));
    }

    @Test
    void GET_api_users_id_存在しない場合は404() throws Exception {
        when(userService.findById(99L)).thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(get("/api/users/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void POST_api_users_バリデーションエラーは400() throws Exception {
        var invalidRequest = new CreateUserRequest("", "not-an-email", "short");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }
}
```

### 8.5 @MybatisTest — Mapper のテスト

```java
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findById_存在するIDの場合はUserを返す() {
        var result = userMapper.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("田中 太郎");
    }

    @Test
    void insert_新しいユーザーが登録される() {
        var user = new User(null, "新規 ユーザー", "new@example.com", null);
        userMapper.insert(user);

        assertThat(user.getId()).isNotNull();
        var found = userMapper.findById(user.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("新規 ユーザー");
    }

    @Test
    @Transactional
    void deleteById_削除後は取得できない() {
        userMapper.deleteById(1L);
        var result = userMapper.findById(1L);
        assertThat(result).isEmpty();
    }
}
```

### 8.6 テストのベストプラクティス

- **AAA（Arrange-Act-Assert）パターン**でテストを整理する
- テストメソッド名は「条件_期待結果」を日本語で書くと読みやすい
- 1テスト1アサーション（原則）— 1つの振る舞いを検証する
- `@Transactional` を使ってDB変更をロールバックする
- 本番データに影響する可能性があるテストには `@Sql` でテストデータを用意する

---

## 🔗 関連リソース

- [examples/08-testing/](../../examples/08-testing/) — サンプルコード
- [exercises/08-testing/problem/](../../exercises/08-testing/problem/) — 演習問題
- [exercises/08-testing/solution/](../../exercises/08-testing/solution/) — 解答例

---

## ⏱️ 推奨学習時間

1週間（10〜12時間）

---

## ✅ チェックリスト

- [ ] JUnit 5 で基本的なテストを書ける
- [ ] `@ParameterizedTest` で複数パターンをテストできる
- [ ] Mockitoで `when` / `verify` を使ったモックテストが書ける
- [ ] `@WebMvcTest` + `MockMvc` でAPIのレスポンスを検証できる
- [ ] `@MybatisTest` でMapperの動作を検証できる
- [ ] AAAパターンでテストを構造化できる

---

## 📝 次の章

[Chapter 9: レイヤードアーキテクチャ](../09-architecture/README.md)
