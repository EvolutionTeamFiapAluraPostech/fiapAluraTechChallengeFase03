package br.com.fiaprestaurant;


import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@IntegrationTest
@DatabaseTest
class FiapRestaurantApplicationTest {

	private final ApplicationContext applicationContext;

	@Autowired
	FiapRestaurantApplicationTest(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Test
	void contextLoads() {
		var fiapRestaurantApplication = applicationContext.getBean(FiapRestaurantApplication.class);
		Assertions.assertThat(fiapRestaurantApplication).isNotNull();
	}

}
