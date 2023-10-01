package onuraktas.simplebanking.spring.spring;

import onuraktas.simplebanking.spring.config.CommonConfig;
import onuraktas.simplebanking.spring.config.TransactionManagementConfig;
import onuraktas.simplebanking.spring.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(
		value = {
				CommonConfig.class,
				TransactionManagementConfig.class,
				WebConfig.class
		}
)
public class SimplebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplebankingApplication.class, args);
	}

}
