package com.jsw.mes.mdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class MdmService {

	public static void main(String[] args) {
		SpringApplication.run(MdmService.class, args);
	}


}
