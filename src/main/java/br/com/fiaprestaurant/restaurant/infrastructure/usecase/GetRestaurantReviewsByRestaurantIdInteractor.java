package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.ReviewGateway;
import br.com.fiaprestaurant.restaurant.application.usecase.GetRestaurantReviewsByRestaurantIdUseCase;
import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetRestaurantReviewsByRestaurantIdInteractor implements
    GetRestaurantReviewsByRestaurantIdUseCase {

  private final ReviewGateway reviewGateway;
  private final UuidValidator uuidValidator;
  private final RestaurantGateway restaurantGateway;

  public GetRestaurantReviewsByRestaurantIdInteractor(ReviewGateway reviewGateway,
      UuidValidator uuidValidator, RestaurantGateway restaurantGateway) {
    this.reviewGateway = reviewGateway;
    this.uuidValidator = uuidValidator;
    this.restaurantGateway = restaurantGateway;
  }

  @Override
  public List<Review> execute(String restaurantId) {
    uuidValidator.validate(restaurantId);
    var restaurant = restaurantGateway.findByIdRequired(UUID.fromString(restaurantId));
    return reviewGateway.findReviewsByRestaurantId(restaurant.getId());
  }
}
