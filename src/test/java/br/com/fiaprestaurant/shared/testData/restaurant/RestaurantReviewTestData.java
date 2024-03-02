package br.com.fiaprestaurant.shared.testData.restaurant;

import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.ReviewSchema;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantReviewInputDto;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.UUID;

public final class RestaurantReviewTestData {

  public static final String DEFAULT_RESTAURANT_REVIEW_DESCRIPTION = "Comida saborosa";
  public static final Integer DEFAULT_RESTAURANT_REVIEW_SCORE = 7;

  private RestaurantReviewTestData() {
  }

  public static RestaurantReviewInputDto createRestaurantReviewInputDto(UUID restaurantId,
      String description, Integer score, UUID userId) {
    return new RestaurantReviewInputDto(restaurantId.toString(), description, score,
        userId.toString());
  }

  public static Review createRestaurantReview(UUID restaurantId, String description,
      Integer score, UUID userId) {
    return new Review(UUID.randomUUID(), restaurantId, description, score, userId);
  }

  public static ReviewSchema createNewRestaurantReviewSchema(Review review) {
    return ReviewSchema.builder()
        .restaurantSchema(RestaurantSchema.builder().id(review.getRestaurantId()).build())
        .description(review.getDescription())
        .score(review.getScore())
        .userSchema(UserSchema.builder().id(review.getUserId()).build())
        .build();
  }

  public static ReviewSchema createRestaurantReviewSchema(Review review) {
    var reviewSchema = createNewRestaurantReviewSchema(review);
    reviewSchema.setId(UUID.randomUUID());
    return reviewSchema;
  }

}
