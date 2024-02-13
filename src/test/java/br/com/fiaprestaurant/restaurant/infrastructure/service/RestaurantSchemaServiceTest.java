package br.com.fiaprestaurant.restaurant.infrastructure.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.infrastructure.repository.RestaurantSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantSchemaServiceTest {

  @Mock
  private RestaurantSchemaRepository restaurantSchemaRepository;
  @InjectMocks
  private RestaurantSchemaService restaurantSchemaService;

  @Test
  void shouldSaveRestaurant() {
    var restaurantSchema = RestaurantSchema.builder()
        .id(UUID.randomUUID())
        .name("Comida Boa").cnpj("").typeOfCuisine("Brasileira").street("Av Goiás")
        .number("1000").neighborhood("Centro").city("Goiânia").state("GO")
        .postalCode("74000000").openAt("11:00").closeAt("15:00")
        .peopleCapacity(200).build();
    var restaurant = restaurantSchema.fromRestaurantSchema();
    when(restaurantSchemaRepository.save(any(RestaurantSchema.class))).thenReturn(restaurantSchema);

    var restaurantSaved = restaurantSchemaService.save(restaurant);

    assertThat(restaurantSaved).isNotNull();
  }

}