package bootcamp.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Chapter 3: Spring基礎（DI/IoC）のデモアプリケーション
 *
 * 実行方法:
 *   cd examples/03-spring-basics
 *   mvn spring-boot:run
 */
@SpringBootApplication
public class SpringBasicsApplication {

    public static void main(String[] args) {
        // SpringコンテナをRunnerで起動
        var ctx = SpringApplication.run(SpringBasicsApplication.class, args);

        // コンテナからBeanを取得して確認
        System.out.println("\n===== DIデモ開始 =====");

        // GreetingService は @Autowired で注入されているが、
        // ここでは ApplicationContext 経由で取得してデモ
        var service = ctx.getBean(GreetingController.class);
        service.runDemo();

        System.out.println("===== DIデモ終了 =====\n");
        ctx.close();
    }
}
