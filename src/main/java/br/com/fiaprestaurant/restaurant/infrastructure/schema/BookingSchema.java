package br.com.fiaprestaurant.restaurant.infrastructure.schema;

import br.com.fiaprestaurant.shared.domain.entity.BaseEntity;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
@Table(schema = "restaurant_management", name = "booking")
@SQLRestriction("deleted = false")
public class BookingSchema extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  private RestaurantSchema restaurantSchema;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserSchema userSchema;
  private LocalDateTime bookingDate;


}
