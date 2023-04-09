package com.bilge.cgaccountdemo.configs;

import com.bilge.cgaccountdemo.entities.Customer;
import com.bilge.cgaccountdemo.repo.CustomerRepository;
import com.bilge.cgaccountdemo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadSampleData {
	private static final Logger log = LoggerFactory.getLogger(LoadSampleData.class);

	@Bean
	CommandLineRunner initSampleData(CustomerService cRepo){
		return args -> {
			log.info("Sample customer loading -> " + cRepo.save(new Customer("Bilge","Güç")));
			log.info("Sample customer loading -> " + cRepo.save(new Customer("Alexander","Wielemaker")));
			log.info("Sample customer loading -> " + cRepo.save(new Customer("Özlem Işıl","Güç")));
			log.info("Sample customer loading -> " + cRepo.save(new Customer("Tante","The Cat")));
		};
	}
}
