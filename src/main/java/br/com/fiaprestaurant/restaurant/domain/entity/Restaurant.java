package br.com.fiaprestaurant.restaurant.domain.entity;

import java.util.UUID;

public class Restaurant {

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
  private int peopleCapacity;

  public Restaurant(String name, String cpnj, String typeOfCuisine, String street, String number,
      String neighborhood, String city, String state, String postalCode, String openAt,
      String closeAt, int peopleCapacity) {
    this.name = name;
    this.cnpj = cnpj;
    this.typeOfCuisine = typeOfCuisine;
    this.street = street;
    this.number = number;
    this.neighborhood = neighborhood;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.openAt = openAt;
    this.closeAt = closeAt;
    this.peopleCapacity = peopleCapacity;
  }

  public Restaurant(UUID id, String name, String cnpj, String typeOfCuisine, String street,
      String number,
      String neighborhood, String city, String state, String postalCode, String openAt,
      String closeAt, int peopleCapacity) {
    this(name, cnpj, typeOfCuisine, street, number, neighborhood, city, state, postalCode, openAt,
        closeAt, peopleCapacity);
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public String getCnpj() {
    return cnpj;
  }

  public String getName() {
    return name;
  }

  public String getTypeOfCuisine() {
    return typeOfCuisine;
  }

  public String getStreet() {
    return street;
  }

  public String getNumber() {
    return number;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getOpenAt() {
    return openAt;
  }

  public String getCloseAt() {
    return closeAt;
  }

  public int getPeopleCapacity() {
    return peopleCapacity;
  }
}
