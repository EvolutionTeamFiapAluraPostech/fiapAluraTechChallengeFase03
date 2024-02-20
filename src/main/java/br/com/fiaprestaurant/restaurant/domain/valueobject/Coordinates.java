package br.com.fiaprestaurant.restaurant.domain.valueobject;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Coordinates {

  private final Double latitude;
  private final Double longitude;

  public Coordinates(Double latitude, Double longitude) {
    validateNullLatitude(latitude);
    validateLatitude(latitude);
    validateNullLongitude(longitude);
    validateLongitude(longitude);
    this.latitude = latitude;
    this.longitude = longitude;
  }

  private void validateLongitude(Double longitude) {
    if (longitude < -90 || longitude > 90) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "longitude",
          "longitude must be greater than -90 and less than 90."));
    }
  }

  private void validateLatitude(Double latitude) {
    if (latitude < -90 || latitude > 90) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "latitude",
          "latitude must be greater than -90 and less than 90."));
    }
  }

  private void validateNullLongitude(Double longitude) {
    if (longitude == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "longitude",
          "longitude must be a number."));
    }
  }

  private void validateNullLatitude(Double latitude) {
    if (latitude == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "latitude",
          "latitude must be a number."));
    }
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }
}
