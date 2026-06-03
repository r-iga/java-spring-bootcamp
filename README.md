# 🚀 Java/Spring Boot Bootcamp

2ヶ月間でJavaの基礎からSpring Boot + MyBatisを使った実務レベルのAPI開発まで習得する研修プログラム

---

## 📚 対象者

- 他のプログラミング言語の経験がある方
- JavaとSpring Bootを体系的に学びたい方
- バックエンドAPI開発のベストプラクティスを習得したい方

---

## 🎯 学習目標

- Javaの基礎文法とオブジェクト指向設計の理解
- Spring FrameworkのDI（依存性の注入）の仕組みの習得
- Spring Bootを使ったREST APIの設計と実装
- MyBatisを使ったデータベースアクセスの実装
- JUnit / Mockito を使ったユニットテスト・統合テストの作成
- レイヤードアーキテクチャと実務的な設計パターンの理解

---

## 📖 カリキュラム構成

### Week 1-2: Java 基礎

- [Chapter 1: Java基礎文法](docs/01-java-basics/README.md)
  - 変数・型・制御構文
  - メソッドの定義とオーバーロード
  - String操作・コレクション（List, Map, Set）

- [Chapter 2: オブジェクト指向](docs/02-oop/README.md)
  - クラス・インスタンス・カプセル化
  - 継承・ポリモーフィズム
  - 抽象クラス・インターフェース
  - ジェネリクス・ラムダ式・Stream API

### Week 3: ビルドツールとSpring入門

- [Chapter 3: Maven + Spring基礎（DI/IoC）](docs/03-spring-basics/README.md)
  - Mavenプロジェクト構成とpom.xml
  - DIコンテナとBean管理
  - アノテーション（@Component, @Service, @Repository）
  - @Autowiredと依存関係の注入

### Week 4-5: Spring Boot

- [Chapter 4: Spring Boot入門](docs/04-spring-boot/README.md)
  - Spring Bootの自動設定の仕組み
  - application.properties / application.yml
  - プロファイル（dev / prod）
  - ロギング（SLF4J + Logback）

### Week 6-7: REST API開発

- [Chapter 5: REST API設計と実装](docs/05-rest-api/README.md)
  - @RestController / @RequestMapping
  - HTTPメソッドとリソース設計
  - DTOパターン（Request / Response分離）
  - バリデーション（@Valid, @NotNull など）
  - 例外ハンドリング（@ControllerAdvice）

### Week 8: MyBatisとDBアクセス

- [Chapter 6: MyBatis基礎](docs/06-mybatis/README.md)
  - MyBatisの仕組みとSQLマッパー
  - @Mapper / XMLマッパー
  - CRUD操作とResultMap
  - H2インメモリDBでの動作確認

- [Chapter 7: MyBatis応用](docs/07-mybatis-advanced/README.md)
  - 動的SQL（`<if>`, `<foreach>`, `<choose>`）
  - 1対多・多対1のマッピング
  - ページネーションと検索

### Week 9: テスト

- [Chapter 8: テスト（JUnit / Mockito / Spring Boot Test）](docs/08-testing/README.md)
  - JUnit 5の基礎
  - Mockitoでのモック作成
  - @SpringBootTest / @WebMvcTest / @MybatisTest
  - テストカバレッジの考え方

### Week 10: アーキテクチャと設計

- [Chapter 9: レイヤードアーキテクチャ](docs/09-architecture/README.md)
  - Controller / Service / Repository の責務分離
  - 例外設計とカスタム例外
  - トランザクション管理（@Transactional）
  - ディレクトリ構成のベストプラクティス

### Week 11-12: 最終プロジェクト

- 学んだ内容を統合した実践的なREST APIの開発
- 詳細は [projects/final/README.md](projects/final/README.md) を参照

---

## 📁 ディレクトリ構成

```
java-spring-bootcamp/
├── docs/           # 📚 各章の学習資料（Markdown）
├── examples/       # 💡 サンプルコード（実際に動く）
├── exercises/      # ✏️ ハンズオン演習
│   ├── problem/    # 演習問題（TODOコメントで穴埋め）
│   └── solution/   # 解答例
└── projects/       # 🚀 週次プロジェクト・最終課題
```

---

## 🚀 学習の進め方

1. **各章のREADMEを読む** — 学習目標とトピックを確認
2. **サンプルコードを動かす** — `examples/` のコードを実際に実行
3. **演習問題に取り組む** — `exercises/problem/` の課題を解く（TODOを埋める）
4. **解答例を確認** — `exercises/solution/` で答え合わせ
5. **週次プロジェクト** — 学んだ内容を統合して小規模APIを作成

---

## 💡 推奨学習時間

- 平日: 2〜3時間/日
- 週末: 4〜6時間/日
- 合計: 約120〜150時間（2ヶ月間）

---

## 🛠️ 環境構築

### 必要なもの

| ツール | バージョン | インストール先 |
|---|---|---|
| JDK | 21 (LTS) | [Adoptium](https://adoptium.net/) |
| Maven | 3.9+ | [maven.apache.org](https://maven.apache.org/) |
| IDE | IntelliJ IDEA (推奨) / VS Code | — |

### バージョン確認

```bash
java -version
# java version "21.x.x" ...

mvn -version
# Apache Maven 3.9.x ...
```

### サンプルプロジェクトの起動

```bash
# 例: Chapter 5 REST API サンプルを起動
cd examples/05-rest-api
mvn spring-boot:run

# アクセス確認
curl http://localhost:8080/api/users
```

---

## 📝 ライセンス

MIT License
