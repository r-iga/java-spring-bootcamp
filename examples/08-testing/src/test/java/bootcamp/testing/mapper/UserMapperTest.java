package bootcamp.testing.mapper;

import bootcamp.testing.entity.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.*;

/**
 * Mapperテスト（@MybatisTest使用）
 *
 * ポイント:
 *   @MybatisTest — MyBatis + H2 のみをロード（Web/Service層は不要）
 *   @AutoConfigureTestDatabase — テスト専用のインメモリDBを自動設定
 *   @Transactional（@MybatisTestに内包）— 各テスト後にロールバック
 *
 * スキーマ/データは src/test/resources/schema.sql と data.sql から読み込む
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findAll_データが3件返る() {
        // data.sql で2件投入済み
        var users = userMapper.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    void findById_存在するIDの場合_ユーザーを返す() {
        var user = userMapper.findById(1L);
        assertThat(user).isPresent();
        assertThat(user.get().getName()).isEqualTo("田中 太郎");
    }

    @Test
    void findById_存在しないIDの場合_空のOptionalを返す() {
        var user = userMapper.findById(999L);
        assertThat(user).isEmpty();
    }

    @Test
    void insert_新規ユーザーを挿入してIDが設定される() {
        var newUser = new User(null, "山田 次郎", "yamada@example.com", null);
        int rows = userMapper.insert(newUser);

        // 影響行数の検証
        assertThat(rows).isEqualTo(1);
        // useGeneratedKeys=true により id が設定されていることを検証
        assertThat(newUser.getId()).isNotNull().isGreaterThan(0);

        // 実際にDBから取得できることを確認
        var found = userMapper.findById(newUser.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("yamada@example.com");
    }

    @Test
    void deleteById_存在するIDを削除する() {
        int rows = userMapper.deleteById(1L);
        assertThat(rows).isEqualTo(1);
        assertThat(userMapper.findById(1L)).isEmpty();
    }
}
