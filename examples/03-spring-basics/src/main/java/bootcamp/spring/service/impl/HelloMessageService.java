package bootcamp.spring.service.impl;

import bootcamp.spring.service.MessageService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * MessageService の実装① — 挨拶メッセージ
 *
 * @Primary: MessageService の候補が複数ある場合、
 *           デフォルトでこの実装が選ばれる
 */
@Service
@Primary
public class HelloMessageService implements MessageService {

    @Override
    public String getMessage(String name) {
        return "こんにちは、" + name + "さん！Spring DIデモへようこそ。";
    }
}
