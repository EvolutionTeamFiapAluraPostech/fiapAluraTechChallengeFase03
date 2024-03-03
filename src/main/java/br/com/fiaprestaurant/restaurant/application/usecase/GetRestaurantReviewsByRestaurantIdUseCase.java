package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import java.util.List;

public interface GetRestaurantReviewsByRestaurantIdUseCase {

  List<Review> execute(String restaurantId);

}
