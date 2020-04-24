package net.hyerin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebSecurity // 웹 보안을 활성화
@SpringBootApplication
public class LitebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(LitebookApplication.class, args);
	}

	@Bean
	public InternalResourceViewResolver setupViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();

		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

}
