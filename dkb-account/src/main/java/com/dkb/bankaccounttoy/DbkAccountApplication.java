/**
 * 
 */
package com.dkb.bankaccounttoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Praneeth
 *
 */
@SpringBootApplication
@EnableJpaAuditing
public class DbkAccountApplication {
	

	public static void main(String[] args) {
	    SpringApplication.run(DbkAccountApplication.class, args);
	  }
	
}
