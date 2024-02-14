package br.com.fiaprestaurant.restaurant.domain.entity;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import java.util.UUID;
import org.springframework.validation.FieldError;

public class Restaurant {

  public static final String RESTAURANT_NAME_FIELD = "name";
  public static final String ENTER_THE_RESTAURANT_NAME = "Enter the restaurant name.";
  public static final String RESTAURANT_NAME_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS = "Restaurant name must have between 3 and 100 characters. It has %s.";
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
    validateNameIsNullOrEmpty(name);
    validateNameLength(name);
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

  private void validateNameIsNullOrEmpty(String name) {
    if (name == null || name.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RESTAURANT_NAME_FIELD, ENTER_THE_RESTAURANT_NAME));
    }
  }

  private void validateNameLength(String name) {
    if (name.trim().length() < 3 || name.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RESTAURANT_NAME_FIELD,
          RESTAURANT_NAME_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS.formatted(name.trim().length())));
    }
  }

}
