package bootcamp.ex02;

/**
 * 演習 02-1: オブジェクト指向
 *
 * 以下のクラス設計を実装してください。
 */

// ─── TODO 1: 抽象クラス Animal ──────────────────────────────────────────────
/**
 * 動物を表す抽象クラス Animal を定義してください。
 *
 * フィールド:
 *   - protected String name (名前)
 *   - protected int age  (年齢)
 *
 * メソッド:
 *   - コンストラクタ: name と age を受け取る
 *   - abstract String speak()  : 鳴き声を返す（サブクラスで実装）
 *   - void introduce()         : "私は{name}、{age}歳です。{speak()の結果}" を出力
 */
// TODO: Animal クラスを実装してください
abstract class Animal {
}

// ─── TODO 2: インターフェース Trainable ──────────────────────────────────────
/**
 * 訓練可能な動物を表すインターフェース Trainable を定義してください。
 *
 * メソッド:
 *   - void performTrick(String trickName)  : "{name}が{trickName}をします！" を出力
 *   - default void sit()                   : performTrick("お座り") を呼ぶ
 */
// TODO: Trainable インターフェースを実装してください
interface Trainable {
}

// ─── TODO 3: Dog クラス ───────────────────────────────────────────────────────
/**
 * Dog クラスを定義してください。
 *
 * - Animal を継承する
 * - Trainable を実装する
 * - speak() は "ワンワン！" を返す
 * - performTrick() は "{name}が{trickName}をします！" を出力する
 */
// TODO: Dog クラスを実装してください
class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
    }
}

// ─── TODO 4: Cat クラス ───────────────────────────────────────────────────────
/**
 * Cat クラスを定義してください。
 *
 * - Animal を継承する（Trainableは実装しない）
 * - speak() は "ニャーニャー！" を返す
 */
// TODO: Cat クラスを実装してください
class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
    }
}

// ─── TODO 5: main メソッド ────────────────────────────────────────────────────
public class Exercise02 {

    public static void main(String[] args) {
        // TODO: 以下を実装してください
        //
        // 1. Dog インスタンス dog を作成 (名前: "ポチ", 年齢: 3)
        // 2. Cat インスタンス cat を作成 (名前: "タマ", 年齢: 5)
        // 3. dog.introduce() と cat.introduce() を呼ぶ
        // 4. dog.sit() と dog.performTrick("握手") を呼ぶ
        // 5. Animal の配列 animals に dog と cat を格納し、
        //    ポリモーフィズムを使って各動物の introduce() を呼ぶ
        //
        // 期待する出力:
        //   私はポチ、3歳です。ワンワン！
        //   私はタマ、5歳です。ニャーニャー！
        //   ポチがお座りをします！
        //   ポチが握手をします！
        //   --- 全動物の紹介 ---
        //   私はポチ、3歳です。ワンワン！
        //   私はタマ、5歳です。ニャーニャー！

    }
}
