package br.com.fiaprestaurant.restaurant.domain.entity;

import java.util.UUID;

public class RestaurantBuilder {

  private UUID id;
  private String name;
  private String cnpj;
  private String typeOfCuisine;
  private String street;
  private String number;
  private String neighborhood;
  private String city;
  private String state;
  private String postalCode;
  private String openAt;
  private String closeAt;
  private int capacityOfPeople;

  public RestaurantBuilder setId(UUID id) {
    this.id = id;
    return this;
  }

  public RestaurantBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public RestaurantBuilder setCnpj(String cnpj) {
    this.cnpj = cnpj;
    return this;
  }

  public RestaurantBuilder setTypeOfCuisine(String typeOfCuisine) {
    this.typeOfCuisine = typeOfCuisine;
    return this;
  }

  public RestaurantBuilder setStreet(String street) {
    this.street = street;
    return this;
  }

  public RestaurantBuilder setNumber(String number) {
    this.number = number;
    return this;
  }

  public RestaurantBuilder setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
    return this;
  }

  public RestaurantBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public RestaurantBuilder setState(String state) {
    this.state = state;
    return this;
  }

  public RestaurantBuilder setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public RestaurantBuilder setOpenAt(String openAt) {
    this.openAt = openAt;
    return this;
  }

  public RestaurantBuilder setCloseAt(String closeAt) {
    this.closeAt = closeAt;
    return this;
  }

  public RestaurantBuilder setCapacityOfPeople(int capacityOfPeople) {
    this.capacityOfPeople = capacityOfPeople;
    return this;
  }

  public Restaurant createRestaurant() {
    return new Restaurant(id, name, cnpj, typeOfCuisine, street, number, neighborhood, city, state,
        postalCode, openAt, closeAt, capacityOfPeople);
  }

}