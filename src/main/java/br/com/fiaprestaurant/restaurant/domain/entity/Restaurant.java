package br.com.fiaprestaurant.restaurant.domain.entity;

import br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields;
import br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages;
import br.com.fiaprestaurant.restaurant.domain.valueobject.Address;
import br.com.fiaprestaurant.restaurant.domain.valueobject.CloseAt;
import br.com.fiaprestaurant.restaurant.domain.valueobject.Cnpj;
import br.com.fiaprestaurant.restaurant.domain.valueobject.OpenAt;
import br.com.fiaprestaurant.restaurant.domain.valueobject.PeopleCapacity;
import br.com.fiaprestaurant.restaurant.domain.valueobject.TypeOfCuisine;
import br.com.fiaprestaurant.shared.exception.ValidatorException;
import java.util.UUID;
import org.springframework.validation.FieldError;

public class Restaurant {

  private UUID id;
  private final String name;
  private final Cnpj cnpj;
  private final TypeOfCuisine typeOfCuisine;
  private final Address address;
  private final OpenAt openAt;
  private final CloseAt closeAt;
  private final PeopleCapacity peopleCapacity;

  public Restaurant(String name, String cnpj, String typeOfCuisine, Double latitude,
      Double longitude, String street, String number, String neighborhood, String city,
      String state, String postalCode, String openAt, String closeAt, int peopleCapacity) {
    validateNameIsNullOrEmpty(name);
    validateNameLength(name);
    this.name = name;
    this.cnpj = new Cnpj(cnpj);
    this.typeOfCuisine = new TypeOfCuisine(typeOfCuisine);
    this.address = new Address(latitude, longitude, street, number, neighborhood, city, state,
        postalCode);
    this.openAt = new OpenAt(openAt);
    this.closeAt = new CloseAt(closeAt);
    this.peopleCapacity = new PeopleCapacity(peopleCapacity);
  }

  public Restaurant(UUID id, String name, String cnpj, String typeOfCuisine, Double latitude,
      Double longitude, String street, String number, String neighborhood, String city,
      String state, String postalCode, String openAt, String closeAt, int peopleCapacity) {
    this(name, cnpj, typeOfCuisine, latitude, longitude, street, number, neighborhood, city, state,
        postalCode, openAt, closeAt, peopleCapacity);
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

  public OpenAt getOpenAt() {
    return openAt;
  }

  public CloseAt getCloseAt() {
    return closeAt;
  }

  public PeopleCapacity getPeopleCapacity() {
    return peopleCapacity;
  }


  private void validateNameIsNullOrEmpty(String name) {
    if (name == null || name.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_NAME_FIELD, RestaurantMessages.ENTER_THE_RESTAURANT_NAME));
    }
  }

  private void validateNameLength(String name) {
    if (name.trim().length() < 3 || name.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_NAME_FIELD,
          RestaurantMessages.RESTAURANT_NAME_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS.formatted(
              name.trim().length())));
    }
  }

}
