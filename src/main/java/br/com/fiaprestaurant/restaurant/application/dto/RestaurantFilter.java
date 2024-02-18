package br.com.fiaprestaurant.restaurant.application.dto;

public record RestaurantFilter(
    String name, Double latitude, Double longitude, String typeOfCuisine) {

}
