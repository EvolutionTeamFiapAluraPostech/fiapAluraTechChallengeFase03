package br.com.fiaprestaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@EnableAsync
@SpringBootApplication
public class FiapRestaurantApplication {

  public static final Logger logger = LoggerFactory.getLogger(FiapRestaurantApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(FiapRestaurantApplication.class, args);
  }

}
