package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.entity.Review;

public interface CreateRestaurantReviewUseCase {

  Review execute(Review review);

}
