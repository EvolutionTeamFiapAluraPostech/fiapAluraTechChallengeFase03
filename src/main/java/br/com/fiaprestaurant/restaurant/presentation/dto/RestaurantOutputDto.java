package br.com.fiaprestaurant.restaurant.presentation.dto;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public record RestaurantOutputDto(
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do restaurante.")
    String id,
    @Schema(example = "Sabor Goiâno", description = "Nome do restaurante.", minLength = 3, maxLength = 100)
    String name,
    @Schema(example = "69635854000140", description = "CNPJ do restaurante.", format = "00000000000000", minLength = 14, maxLength = 14)
    String cnpj,
    @Schema(example = "Brasileira", description = "Tipo de cozinha.", minLength = 3, maxLength = 50)
    String typeOfCuisine,
    @Schema(example = "-23.56390", description = "Latitude.", minContains = -90, maxContains = 90)
    Double latitude,
    @Schema(example = "-46.65239", description = "Longitude.", minContains = -90, maxContains = 90)
    Double longitude,
    @Schema(example = "Av. Brasil", description = "Rua.", minLength = 3, maxLength = 255)
    String street,
    @Schema(example = "Av. Brasil", description = "Rua.", minLength = 3, maxLength = 255, nullable = true)
    String number,
    @Schema(example = "Centro", description = "Bairro.", minLength = 3, maxLength = 100)
    String neighborhood,
    @Schema(example = "São Paulo", description = "Cidade.", minLength = 3, maxLength = 100)
    String city,
    @Schema(example = "SP", description = "Estado.", minLength = 2, maxLength = 2)
    String state,
    @Schema(example = "01153-000", description = "CEP.", minLength = 9, maxLength = 9)
    String postalCode,
    @Schema(example = "11:00", description = "Horário de abertura.", minLength = 5, maxLength = 5)
    String openAt,
    @Schema(example = "23:00", description = "Horário de fechamento.", minLength = 5, maxLength = 5)
    String closeAt,
    @Schema(example = "200", description = "Capacidade total de pessoas", format = "Numérico")
    Integer peopleCapacity) {

  public static RestaurantOutputDto toRestaurantOutputDtoFrom(Restaurant restaurant) {
    return new RestaurantOutputDto(restaurant.getId() != null ? restaurant.getId().toString() : "",
        restaurant.getName(),
        restaurant.getCnpj().getCnpjValue(),
        restaurant.getTypeOfCuisine().getTypeOfCuisineDescription(),
        restaurant.getAddress().getCoordinates().getLatitude(),
        restaurant.getAddress().getCoordinates().getLongitude(),
        restaurant.getAddress().getStreet(),
        restaurant.getAddress().getNumber(),
        restaurant.getAddress().getNeighborhood(),
        restaurant.getAddress().getCity(),
        restaurant.getAddress().getState(),
        restaurant.getAddress().getPostalCode(),
        restaurant.getOpenAt(),
        restaurant.getCloseAt(),
        restaurant.getPeopleCapacity());
  }

  public static Page<RestaurantOutputDto> toPage(Page<Restaurant> restaurantesPage) {
    var restaurants = restaurantesPage.map(restaurant ->
        new RestaurantOutputDto(restaurant.getId().toString(),
            restaurant.getName(),
            restaurant.getCnpj().getCnpjValue(),
            restaurant.getTypeOfCuisine().getTypeOfCuisineDescription(),
            restaurant.getAddress().getCoordinates().getLatitude(),
            restaurant.getAddress().getCoordinates().getLongitude(),
            restaurant.getAddress().getStreet(),
            restaurant.getAddress().getNumber(),
            restaurant.getAddress().getNeighborhood(),
            restaurant.getAddress().getCity(),
            restaurant.getAddress().getState(),
            restaurant.getAddress().getPostalCode(),
            restaurant.getOpenAt(),
            restaurant.getCloseAt(),
            restaurant.getPeopleCapacity()
        )).toList();
    return new PageImpl<>(restaurants);
  }

  public static List<RestaurantOutputDto> toRestaurantsOutputDtoFrom(List<Restaurant> restaurants) {
    if (restaurants == null || restaurants.isEmpty()) {
      return new ArrayList<>();
    }
    return restaurants.stream().map(RestaurantOutputDto::toRestaurantOutputDtoFrom).toList();
  }
}
