package br.com.fiaprestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FiapRestaurantApplication {

  public static void main(String[] args) {
    SpringApplication.run(FiapRestaurantApplication.class, args);
  }

}
