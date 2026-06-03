package bootcamp.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Chapter 6: MyBatis基礎サンプルアプリケーション
 *
 * 実行方法:
 *   cd examples/06-mybatis
 *   mvn spring-boot:run
 *
 * H2コンソール（SQLを直接実行できる）:
 *   http://localhost:8080/h2-console
 *   JDBC URL: jdbc:h2:mem:testdb
 *   User Name: sa / Password: (空)
 *
 * API確認:
 *   curl http://localhost:8080/api/users
 */
@SpringBootApplication
public class MyBatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisApplication.class, args);
    }
}
