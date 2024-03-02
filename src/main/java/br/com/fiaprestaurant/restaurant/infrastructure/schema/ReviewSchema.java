package br.com.fiaprestaurant.restaurant.infrastructure.schema;

import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import br.com.fiaprestaurant.shared.domain.entity.BaseEntity;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(schema = "restaurant_management", name = "reviews")
@SQLRestriction("deleted = false")
public class ReviewSchema extends BaseEntity {

  private RestaurantSchema restaurantSchema;
  private String description;
  private Integer score;
  private UserSchema userSchema;

  public Review toReview() {
    return new Review(this.getId(), this.getRestaurantSchema().getId(), this.getDescription(),
        this.getScore(), this.getUserSchema().getId());
  }
}
