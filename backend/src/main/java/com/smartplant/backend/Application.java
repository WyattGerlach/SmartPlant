package com.smartplant.backend;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
// CommandLineRunner and @Bean imports intentionally removed because the
// verbose bean dumper is commented out; keep them available in history if needed.
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@EventListener(ApplicationReadyEvent.class)
	public void onReady(ApplicationReadyEvent event) {
		ApplicationContext ctx = event.getApplicationContext();
		int port = -1;
		if (ctx instanceof WebServerApplicationContext) {
			WebServerApplicationContext wsc = (WebServerApplicationContext) ctx;
			port = wsc.getWebServer().getPort();
		}
		String[] profiles = ctx.getEnvironment().getActiveProfiles();
		log.info("Application started on port {} with active profiles {} and {} beans", port, Arrays.toString(profiles), ctx.getBeanDefinitionCount());
	}

	/*
	// Bean left for debugging — commented out so logs aren't noisy in normal runs.
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
	*/

}