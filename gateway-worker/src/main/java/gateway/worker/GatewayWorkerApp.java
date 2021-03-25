package gateway.worker;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class GatewayWorkerApp {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.main(GatewayWorkerApp.class)
                .sources(new Class<?>[]{GatewayWorkerApp.class})
                .listeners(new ApplicationPidFileWriter())
                .build()
                .run(args);
    }
}
