package br.com.fiaprestaurant.restaurant.presentation.dto;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;

public record RestaurantInputDto(String name,
                                 String cnpj,
                                 String typeOfCuisine,
                                 String street,
                                 String number,
                                 String neighborhood,
                                 String city,
                                 String state,
                                 String postalCode,
                                 String openAt,
                                 String closeAt,
                                 Integer peopleCapacity) {

  public static Restaurant toRestaurantFrom(RestaurantInputDto restaurantInputDto) {
    return new Restaurant(restaurantInputDto.name,
        restaurantInputDto.cnpj,
        restaurantInputDto.typeOfCuisine,
        restaurantInputDto.street,
        restaurantInputDto.number,
        restaurantInputDto.neighborhood,
        restaurantInputDto.city,
        restaurantInputDto.state,
        restaurantInputDto.postalCode,
        restaurantInputDto.openAt,
        restaurantInputDto.closeAt,
        restaurantInputDto.peopleCapacity);
  }
}
