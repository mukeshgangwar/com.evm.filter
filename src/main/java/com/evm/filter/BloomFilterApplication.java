package com.evm.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class BloomFilterApplication  implements CommandLineRunner {
	@Autowired
	FilterApplicationStarter applicationStarter;
	public static void main(String[] args) {
		SpringApplication.run(BloomFilterApplication.class, args);
		SpringApplication app = new SpringApplication(BloomFilterApplication.class);
		//app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

	}


	@Override
	public void run(String... args) throws Exception {
		applicationStarter.checkVoters();
	}
}
