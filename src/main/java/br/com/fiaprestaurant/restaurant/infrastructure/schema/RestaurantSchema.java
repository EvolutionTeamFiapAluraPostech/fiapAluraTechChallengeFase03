package br.com.fiaprestaurant.restaurant.infrastructure.schema;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.entity.RestaurantBuilder;
import br.com.fiaprestaurant.shared.domain.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "restaurant_management", name = "restaurants")
@SQLRestriction("deleted = false")
public class RestaurantSchema extends BaseEntity {

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

  public Restaurant fromRestaurantSchema() {
    return new RestaurantBuilder()
        .setId(this.getId())
        .setName(this.getName())
        .setCnpj(this.getCnpj())
        .setTypeOfCuisine(this.getTypeOfCuisine())
        .setStreet(this.getStreet())
        .setNumber(this.getNumber())
        .setNeighborhood(this.getNeighborhood())
        .setCity(this.getCity())
        .setState(this.getState())
        .setPostalCode(this.getPostalCode())
        .setOpenAt(this.getOpenAt())
        .setCloseAt(this.getCloseAt())
        .setCapacityOfPeople(this.getPeopleCapacity())
        .createRestaurant();
  }

}
