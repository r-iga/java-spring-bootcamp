package bootcamp.basics;

import java.util.List;
import java.util.Map;

/**
 * Chapter 1: 制御構文のサンプル
 *
 * 実行方法:
 *   javac ControlFlow.java && java bootcamp.basics.ControlFlow
 */
public class ControlFlow {

    public static void main(String[] args) {
        // ─── 1. if / else ────────────────────────────────────
        System.out.println("=== if / else ===");
        int score = 75;
        if (score >= 90) {
            System.out.println("S評価");
        } else if (score >= 70) {
            System.out.println("A評価");  // ここに入る
        } else if (score >= 50) {
            System.out.println("B評価");
        } else {
            System.out.println("C評価");
        }

        // ─── 2. switch 式（Java 14+ Arrow switch）───────────────
        System.out.println("\n=== switch 式（Arrow）===");
        String day = "MONDAY";
        String dayType = switch (day) {
            case "SATURDAY", "SUNDAY" -> "休日";
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "平日";
            default -> "不明";
        };
        System.out.println(day + " は " + dayType);

        // yield で値を返す
        int numLetters = switch (day) {
            case "MONDAY", "FRIDAY", "SUNDAY" -> 6;
            case "TUESDAY" -> 7;
            case "THURSDAY", "SATURDAY" -> 8;
            default -> {
                System.out.println("  (計算中...)");
                yield day.length();
            }
        };
        System.out.println(day + " の文字数: " + numLetters);

        // ─── 3. for / for-each ───────────────────────────────
        System.out.println("\n=== for ループ ===");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("\n=== for-each ===");
        var fruits = List.of("りんご", "バナナ", "みかん");
        for (String fruit : fruits) {
            System.out.println("  - " + fruit);
        }

        // ─── 4. while / do-while ─────────────────────────────
        System.out.println("\n=== while ===");
        int n = 1;
        while (n <= 3) {
            System.out.println("  n = " + n);
            n++;
        }

        System.out.println("\n=== do-while（最低1回実行）===");
        int x = 10;
        do {
            System.out.println("  x = " + x);
            x++;
        } while (x < 10);  // 条件falseでも1回は実行される

        // ─── 5. break / continue ─────────────────────────────
        System.out.println("\n=== break / continue ===");
        for (int i = 0; i < 10; i++) {
            if (i == 3) continue;   // 3はスキップ
            if (i == 7) break;      // 7で終了
            System.out.print(i + " ");
        }
        System.out.println();

        // ─── 6. Nullish 系演算子 ──────────────────────────────
        System.out.println("\n=== Nullish 演算子 ===");
        String user = null;

        // null 合体演算子（??）に相当するのはJavaにはないが、三項演算子で代替
        String displayName = (user != null) ? user : "ゲスト";
        System.out.println("displayName: " + displayName);

        // Map.getOrDefault — null のとき既定値を返す
        Map<String, String> config = Map.of("timeout", "30");
        String retry = config.getOrDefault("retry", "3");
        System.out.println("retry: " + retry);

        // ─── 7. 三項演算子 ────────────────────────────────────
        System.out.println("\n=== 三項演算子 ===");
        int value = 42;
        String result = value > 0 ? "正" : value < 0 ? "負" : "ゼロ";
        System.out.println(value + " → " + result);
    }
}
