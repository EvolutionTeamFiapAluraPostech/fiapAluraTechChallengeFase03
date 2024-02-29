package br.com.fiaprestaurant.restaurant.domain.valueobject;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class TypeOfCuisine {

  private static final String TYPE_OF_CUISINE = "type of cuisine";
  private static final String ENTER_THE_TYPE_OF_CUISINE = "Enter the type of cuisine.";
  private static final String TYPE_OF_CUISINE_LENGTH = "Type of cuisine must have between 3 and 50 characters. It has %s.";
  private final String typeOfCuisineDescription;

  public TypeOfCuisine(String typeOfCuisineDescription) {
    validateTypeOfCuisineIsNullOrEmpty(typeOfCuisineDescription);
    validateTypeOfCuisineLength(typeOfCuisineDescription);
    this.typeOfCuisineDescription = typeOfCuisineDescription;
  }

  private void validateTypeOfCuisineLength(String typeOfCuisineDescription) {
    if (typeOfCuisineDescription.length() < 3 || typeOfCuisineDescription.length() > 50) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), TYPE_OF_CUISINE,
          TYPE_OF_CUISINE_LENGTH.formatted(typeOfCuisineDescription.length())));
    }
  }

  private void validateTypeOfCuisineIsNullOrEmpty(String typeOfCuisineDescription) {
    if (typeOfCuisineDescription == null || typeOfCuisineDescription.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), TYPE_OF_CUISINE,
          ENTER_THE_TYPE_OF_CUISINE));
    }
  }

  public String getTypeOfCuisineDescription() {
    return typeOfCuisineDescription;
  }
}
