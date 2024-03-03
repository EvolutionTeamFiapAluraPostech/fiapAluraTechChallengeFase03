package br.com.fiaprestaurant.restaurant.application.gateways;

import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import java.util.List;
import java.util.UUID;

public interface ReviewGateway {

  Review save(Review review);

  List<Review> findReviewsByRestaurantId(UUID restaurantId);

}
