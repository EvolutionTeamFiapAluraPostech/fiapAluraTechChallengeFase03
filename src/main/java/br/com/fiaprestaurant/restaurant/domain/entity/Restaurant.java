package br.com.fiaprestaurant.restaurant.domain.entity;

import java.util.UUID;

public class Restaurant {

  private UUID id;
  private final String name;
  private final Cnpj cnpj;
  private final TypeOfCuisine typeOfCuisine;
  private final Address address;
  private final String openAt;
  private final String closeAt;
  private final int peopleCapacity;

  public Restaurant(String name, String cnpj, String typeOfCuisine, String street, String number,
      String neighborhood, String city, String state, String postalCode, String openAt,
      String closeAt, int peopleCapacity) {
    this.name = name;
    this.cnpj = new Cnpj(cnpj);
    this.typeOfCuisine = new TypeOfCuisine(typeOfCuisine);
    this.address = new Address(street, number, neighborhood, city, state, postalCode);
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

  public Cnpj getCnpj() {
    return cnpj;
  }

  public String getName() {
    return name;
  }

  public TypeOfCuisine getTypeOfCuisine() {
    return typeOfCuisine;
  }

  public Address getAddress() {
    return address;
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
