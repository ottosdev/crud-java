package br.com.otto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication()
public class FernandaDevApplication {

	public static void main(String[] args) {
		SpringApplication.run(FernandaDevApplication.class, args);
	}

}
