package gateway.admin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * app endpoint
 */
@SpringBootApplication
public class GatewayAdminApp {

    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.main(GatewayAdminApp.class)
                .sources(new Class<?>[]{GatewayAdminApp.class})
                .listeners(new ApplicationPidFileWriter())
                .build()
                .run(args);
    }
}
