package br.com.fiaprestaurant.restaurant.application.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurant;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantInputDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.validator.RestaurantAlreadyRegisteredByCnpjValidator;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

  @Mock
  private RestaurantService restaurantService;
  @Mock
  private RestaurantAlreadyRegisteredByCnpjValidator restaurantAlreadyRegisteredByCnpjValidator;
  @InjectMocks
  private CreateRestaurantUseCase createRestaurantUseCase;

  @Test
  void shouldCreateNewRestaurant() {
    var restaurantInputDto = createRestaurantInputDto();
    var restaurantToSave = createRestaurant();
    when(restaurantService.save(any(Restaurant.class))).thenReturn(restaurantToSave);

    var restaurantOutputDto = createRestaurantUseCase.execute(restaurantInputDto);

    assertThat(restaurantOutputDto).isNotNull();
    assertThat(restaurantOutputDto.id()).isNotNull();
    verify(restaurantAlreadyRegisteredByCnpjValidator).validate(restaurantInputDto.cnpj());
  }

}