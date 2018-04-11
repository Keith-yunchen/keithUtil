package com.zhouy.module;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhouy
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan
public class ModuleApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ModuleApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
