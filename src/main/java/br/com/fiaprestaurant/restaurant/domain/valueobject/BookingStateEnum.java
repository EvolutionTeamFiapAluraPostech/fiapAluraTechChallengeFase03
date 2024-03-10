package br.com.fiaprestaurant.restaurant.domain.valueobject;

public enum BookingStateEnum {

  RESERVED("Reserved"), CLOSED("Closed");

  private final String label;

  BookingStateEnum(String value) {
    this.label = value;
  }

  public String getLabel() {
    return label;
  }
}
