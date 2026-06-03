package bootcamp.spring;

import bootcamp.spring.service.MessageService;
import bootcamp.spring.service.impl.FarewellMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * DIのデモコンポーネント
 *
 * ポイント:
 * - コンストラクタインジェクションを使用（推奨方式）
 * - @Primary が付いた HelloMessageService が primaryService に注入される
 * - @Qualifier で FarewellMessageService を明示的に指定
 */
@Component
public class GreetingController {

    // インターフェース型で受け取る（実装クラスに直接依存しない）
    private final MessageService primaryService;
    private final MessageService farewellService;

    /**
     * コンストラクタインジェクション
     *
     * Spring 4.3以降、コンストラクタが1つの場合は @Autowired を省略できる
     */
    public GreetingController(
        MessageService primaryService,                                          // @Primary のものが選ばれる
        @Qualifier("farewellMessageService") MessageService farewellService     // 明示的に指定
    ) {
        this.primaryService = primaryService;
        this.farewellService = farewellService;
    }

    public void runDemo() {
        String name = "田中";

        System.out.println("[primaryService の型]: " + primaryService.getClass().getSimpleName());
        System.out.println("[farewellService の型]: " + farewellService.getClass().getSimpleName());
        System.out.println();

        System.out.println("primaryService.getMessage(): " + primaryService.getMessage(name));
        System.out.println("farewellService.getMessage(): " + farewellService.getMessage(name));
    }
}
