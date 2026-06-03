# Chapter 2: オブジェクト指向

## 🎯 学習目標

- クラスとインスタンスの仕組みを理解する
- カプセル化・継承・ポリモーフィズムを使いこなす
- 抽象クラスとインターフェースを設計に活かせる
- ジェネリクス・ラムダ式・Stream APIを実務で使える

---

## 📚 学習トピック

### 2.1 クラスとインスタンス

- フィールド・コンストラクタ・メソッド
- `this` キーワード
- アクセス修飾子（`public` / `protected` / `private` / パッケージプライベート）
- ゲッター / セッター（カプセル化）
- `record` クラス（Java 16+）— イミュータブルなデータ保持

### 2.2 継承

- `extends` によるサブクラス化
- メソッドのオーバーライドと `@Override`
- `super` キーワード（親クラスのコンストラクタ・メソッド呼び出し）
- `final` クラス / `final` メソッド（継承・オーバーライドの禁止）
- `instanceof` 演算子とパターンマッチング（Java 16+）

### 2.3 抽象クラスとインターフェース

**抽象クラス (`abstract class`):**
- 共通の実装を持ちつつ一部を抽象化
- コンストラクタを持てる

**インターフェース (`interface`):**
- 複数実装（多重実装）が可能
- `default` メソッドと `static` メソッド（Java 8+）
- 関数型インターフェース（`@FunctionalInterface`）

**使い分けの指針:**
- IS-A関係で共通の状態が必要 → 抽象クラス
- CAPABILITY（〜できる）を表現 → インターフェース

### 2.4 ポリモーフィズム

- アップキャスト / ダウンキャスト
- 実行時の動的ディスパッチ
- コレクションとポリモーフィズムの組み合わせ

### 2.5 ジェネリクス

- `<T>` による型パラメータ
- 境界型パラメータ (`<T extends Comparable<T>>`)
- ワイルドカード (`<?>`, `<? extends T>`, `<? super T>`)

### 2.6 ラムダ式と Stream API

**ラムダ式:**
- `(引数) -> 処理` の構文
- メソッド参照 (`ClassName::method`)

**主要な関数型インターフェース:**
- `Predicate<T>` — 条件判定（boolean返却）
- `Function<T, R>` — 変換
- `Consumer<T>` — 処理（戻り値なし）
- `Supplier<T>` — 供給（引数なし）

**Stream API:**
- 中間操作: `filter`, `map`, `flatMap`, `sorted`, `distinct`, `limit`
- 終端操作: `collect`, `forEach`, `count`, `findFirst`, `anyMatch`
- `Collectors.toList()`, `Collectors.groupingBy()`, `Collectors.toMap()`

### 2.7 Optional

- `null` を安全に扱う
- `Optional.of()` / `Optional.ofNullable()` / `Optional.empty()`
- `map()`, `filter()`, `orElse()`, `orElseThrow()`

---

## 🔗 関連リソース

- [examples/02-oop/](../../examples/02-oop/) — サンプルコード
- [exercises/02-oop/problem/](../../exercises/02-oop/problem/) — 演習問題
- [exercises/02-oop/solution/](../../exercises/02-oop/solution/) — 解答例

---

## ⏱️ 推奨学習時間

1週間（10〜15時間）

---

## ✅ チェックリスト

- [ ] カプセル化の意図を説明できる
- [ ] 抽象クラスとインターフェースを使い分けられる
- [ ] `@Override` を適切に使える
- [ ] ポリモーフィズムを活用してコードの柔軟性を高められる
- [ ] `record` クラスを使える
- [ ] ラムダ式とメソッド参照を書ける
- [ ] Stream APIでフィルタリング・変換・集約ができる
- [ ] `Optional` を使って `NullPointerException` を回避できる

---

## 📝 次の章

[Chapter 3: Maven + Spring基礎（DI/IoC）](../03-spring-basics/README.md)
