package br.com.fiaprestaurant.restaurant.presentation.dto;

public record RestaurantFilter(
    String name, Double latitude, Double longitude, String typeOfCuisine) {

}
