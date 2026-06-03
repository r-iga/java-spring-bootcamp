package bootcamp.testing.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

/**
 * JUnit 5 基礎テスト
 *
 * JUnit 5の主要アノテーション:
 *   @Test                 — テストメソッド
 *   @ParameterizedTest    — パラメータ化テスト
 *   @ValueSource          — 単一パラメータのソース
 *   @CsvSource            — 複数パラメータのソース
 *   @BeforeEach           — 各テスト前に実行
 *   @AfterEach            — 各テスト後に実行
 */
class JUnit5BasicsTest {

    // ─── AssertJ アサーション基礎 ───────────────────────────────

    @Test
    void 文字列のアサーション() {
        String result = "Hello, World!";

        assertThat(result)
            .isNotNull()
            .startsWith("Hello")
            .endsWith("!")
            .contains("World")
            .hasSize(13);
    }

    @Test
    void 数値のアサーション() {
        int value = 42;

        assertThat(value)
            .isEqualTo(42)
            .isGreaterThan(0)
            .isLessThan(100)
            .isBetween(1, 99);
    }

    @Test
    void コレクションのアサーション() {
        var list = java.util.List.of("Alice", "Bob", "Charlie");

        assertThat(list)
            .hasSize(3)
            .contains("Alice", "Bob")
            .doesNotContain("Dave")
            .first().asString().startsWith("A");
    }

    @Test
    void 例外のアサーション() {
        // assertThatThrownBy で例外の型とメッセージを検証する
        assertThatThrownBy(() -> {
            throw new IllegalArgumentException("無効な引数です");
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("無効な引数です");
    }

    // ─── パラメータ化テスト ──────────────────────────────────────

    @ParameterizedTest(name = "入力: {0} → 期待: {1}")
    @CsvSource({
        "1,  1",
        "5,  120",
        "10, 3628800"
    })
    void 階乗のパラメータ化テスト(int input, long expected) {
        assertThat(factorial(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"user@example.com", "foo.bar@test.co.jp"})
    void メールアドレス形式チェック(String email) {
        assertThat(email).matches("[^@]+@[^@]+\\.[^@]+");
    }

    // ─── テスト対象ロジック（簡単な例） ─────────────────────────

    private long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }
}
