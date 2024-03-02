package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.ReviewGateway;
import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantReviewUseCase;
import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRestaurantReviewInteractor implements CreateRestaurantReviewUseCase {

  private final RestaurantGateway restaurantGateway;
  private final UserGateway userGateway;
  private final ReviewGateway reviewGateway;

  public CreateRestaurantReviewInteractor(RestaurantGateway restaurantGateway,
      UserGateway userGateway, ReviewGateway reviewGateway) {
    this.restaurantGateway = restaurantGateway;
    this.userGateway = userGateway;
    this.reviewGateway = reviewGateway;
  }

  @Transactional
  @Override
  public Review execute(Review review) {
    restaurantGateway.findByIdRequired(review.getRestaurantId());
    userGateway.findUserByIdRequired(review.getUserId());
    return reviewGateway.save(review);
  }
}
