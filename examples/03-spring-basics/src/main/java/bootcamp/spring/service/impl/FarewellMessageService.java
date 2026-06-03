package bootcamp.spring.service.impl;

import bootcamp.spring.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * MessageService の実装② — 別れの挨拶メッセージ
 *
 * @Qualifier("farewellMessageService") で指定して注入できる
 */
@Service
public class FarewellMessageService implements MessageService {

    @Override
    public String getMessage(String name) {
        return "さようなら、" + name + "さん。また会いましょう！";
    }
}
