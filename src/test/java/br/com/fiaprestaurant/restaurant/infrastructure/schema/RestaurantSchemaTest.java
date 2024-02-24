package br.com.fiaprestaurant.restaurant.infrastructure.schema;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantSchema;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RestaurantSchemaTest {

  @Test
  void shouldCreateRestaurant() {
    var restaurantSchema = createRestaurantSchema();

    assertThat(restaurantSchema).isNotNull();
    assertThat(restaurantSchema.getId()).isNotNull();
  }

}
