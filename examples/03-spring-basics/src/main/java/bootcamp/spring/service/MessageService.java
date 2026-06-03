package bootcamp.spring.service;

/**
 * メッセージサービスのインターフェース
 *
 * ポイント: Controller は実装クラスではなく、
 * このインターフェースに依存する（依存逆転の原則 - DIP）
 */
public interface MessageService {
    String getMessage(String name);
}
