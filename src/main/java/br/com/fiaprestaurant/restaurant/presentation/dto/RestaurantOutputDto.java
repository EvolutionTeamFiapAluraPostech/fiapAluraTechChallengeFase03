package br.com.fiaprestaurant.restaurant.presentation.dto;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;

public record RestaurantOutputDto(String id,
                                  String name,
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

  public static RestaurantOutputDto toRestaurantOutputDtoFrom(Restaurant restaurant) {
    return new RestaurantOutputDto(restaurant.getId() != null ? restaurant.getId().toString() : "",
        restaurant.getName(),
        restaurant.getCnpj().getCnpjValue(),
        restaurant.getTypeOfCuisine().getTypeOfCuisineDescription(),
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

}
