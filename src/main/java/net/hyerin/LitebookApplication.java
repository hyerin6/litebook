package net.hyerin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity // 웹 보안을 활성화
@SpringBootApplication
public class LitebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(LitebookApplication.class, args);
	}

}
