package bootcamp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Chapter 5: REST API サンプルアプリケーション
 *
 * 実行方法:
 *   cd examples/05-rest-api
 *   mvn spring-boot:run
 *
 * 動作確認:
 *   curl http://localhost:8080/api/users
 *   curl http://localhost:8080/api/users/1
 *   curl -X POST http://localhost:8080/api/users \
 *        -H "Content-Type: application/json" \
 *        -d '{"name":"山田 次郎","email":"yamada@example.com","password":"pass1234"}'
 */
@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }
}
