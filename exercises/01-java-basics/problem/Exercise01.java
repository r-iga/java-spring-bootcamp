package bootcamp.ex01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 演習 01-1: Java基礎文法
 *
 * 以下の TODO を全て実装してください。
 * 実行して期待する出力が得られたら合格です。
 */
public class Exercise01 {

    public static void main(String[] args) {
        primitiveExercise();
        stringExercise();
        collectionExercise();
        streamExercise();
    }

    /**
     * TODO 1: プリミティブ型と型変換
     *
     * 1. int型の変数 x に 42 を代入してください
     * 2. x を double型の変数 d に暗黙的な型変換で代入してください
     * 3. d を int型に明示的にキャストした結果を変数 y に代入してください
     * 4. x, d, y をそれぞれ System.out.println で出力してください
     *
     * 期待する出力:
     *   x = 42
     *   d = 42.0
     *   y = 42
     */
    static void primitiveExercise() {
        // TODO: ここに実装してください

    }

    /**
     * TODO 2: String操作
     *
     * 変数 name = "Java Spring Bootcamp" に対して:
     * 1. 大文字に変換して出力
     * 2. 小文字に変換して出力
     * 3. "Spring" が含まれるか boolean で出力
     * 4. "Java" を "Kotlin" に置換して出力
     * 5. スペースで分割した配列の長さを出力
     *
     * 期待する出力:
     *   JAVA SPRING BOOTCAMP
     *   java spring bootcamp
     *   true
     *   Kotlin Spring Bootcamp
     *   3
     */
    static void stringExercise() {
        String name = "Java Spring Bootcamp";
        // TODO: ここに実装してください

    }

    /**
     * TODO 3: コレクション操作
     *
     * 1. fruits という ArrayList<String> を作成して、
     *    "Apple", "Banana", "Cherry", "Apple" を追加してください
     * 2. fruits の要素数を出力してください（期待: 4）
     * 3. "Apple" が含まれるか出力してください（期待: true）
     * 4. countMap という HashMap<String, Integer> を作成して、
     *    fruits の各要素の出現回数を集計してください
     * 5. countMap の内容を出力してください
     *    （例: {Apple=2, Banana=1, Cherry=1} ※順序は不定）
     */
    static void collectionExercise() {
        // TODO: ここに実装してください

    }

    /**
     * TODO 4: Stream API
     *
     * numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] に対して:
     * 1. 偶数だけを抽出して出力してください（期待: [2, 4, 6, 8, 10]）
     * 2. 全要素を2乗した結果を出力してください（期待: [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]）
     * 3. 全要素の合計を出力してください（期待: 55）
     * 4. 5より大きい要素が存在するか出力してください（期待: true）
     */
    static void streamExercise() {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // TODO: ここに実装してください

    }
}
