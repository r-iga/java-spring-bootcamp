package bootcamp.basics;

/**
 * Chapter 1: 変数・型・型変換のサンプル
 *
 * 実行方法:
 *   javac Variables.java && java bootcamp.basics.Variables
 */
public class Variables {

    public static void main(String[] args) {
        // ─── 1. プリミティブ型 ───────────────────────────────
        int age = 25;
        long population = 8_000_000_000L;   // アンダースコアで区切り可能
        double price = 1980.50;
        boolean isActive = true;
        char grade = 'A';

        System.out.println("=== プリミティブ型 ===");
        System.out.println("age: " + age);
        System.out.println("population: " + population);
        System.out.println("price: " + price);
        System.out.println("isActive: " + isActive);
        System.out.println("grade: " + grade);

        // ─── 2. 参照型 ───────────────────────────────────────
        String name = "田中 太郎";
        String nullableName = null;

        System.out.println("\n=== 参照型 ===");
        System.out.println("name: " + name);
        System.out.println("name.length(): " + name.length());

        // ─── 3. final（イミュータブル変数）─────────────────────
        final int MAX_RETRY = 3;
        // MAX_RETRY = 5;  // コンパイルエラー: final変数は再代入不可

        System.out.println("\n=== final ===");
        System.out.println("MAX_RETRY: " + MAX_RETRY);

        // ─── 4. var（型推論 Java 10+）────────────────────────
        var message = "Hello, Bootcamp!";   // String と推論される
        var count = 42;                      // int と推論される

        System.out.println("\n=== var（型推論）===");
        System.out.println("message: " + message + "  (型: " + ((Object) message).getClass().getSimpleName() + ")");
        System.out.println("count: " + count);

        // ─── 5. 型変換 ───────────────────────────────────────
        // 自動型変換（widening）: 小さい型 → 大きい型は自動
        int intValue = 100;
        long longValue = intValue;      // int → long は自動
        double doubleValue = intValue;  // int → double は自動

        // 明示的キャスト（narrowing）: 大きい型 → 小さい型はキャストが必要
        double pi = 3.14159;
        int truncated = (int) pi;       // 小数点以下切り捨て

        System.out.println("\n=== 型変換 ===");
        System.out.println("int → double: " + doubleValue);
        System.out.println("double → int (cast): " + truncated);

        // ─── 6. String と数値の相互変換 ────────────────────────
        String numStr = "42";
        int parsed = Integer.parseInt(numStr);
        String backToStr = String.valueOf(parsed);

        System.out.println("\n=== String⇔数値変換 ===");
        System.out.println("parseInt: " + parsed + 1);         // 数値として計算
        System.out.println("String.valueOf: " + backToStr + 1); // 文字列連結

        // ─── 7. プリミティブ型と Wrapper クラス ─────────────────
        // Boxing: int → Integer
        Integer boxed = 42;            // オートボクシング
        int unboxed = boxed;           // アンボクシング

        // nullを扱えるのは Wrapper クラスのみ
        Integer nullable = null;       // OK
        // int cantBeNull = null;       // コンパイルエラー

        System.out.println("\n=== Wrapper クラス ===");
        System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);
        System.out.println("Integer.MIN_VALUE: " + Integer.MIN_VALUE);
        System.out.println("Integer.toBinaryString(255): " + Integer.toBinaryString(255));
    }
}
