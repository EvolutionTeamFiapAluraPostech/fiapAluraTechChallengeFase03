package br.com.fiaprestaurant.restaurant.infrastructure.gateway;

import br.com.fiaprestaurant.restaurant.application.gateways.ReviewGateway;
import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import br.com.fiaprestaurant.restaurant.infrastructure.repository.ReviewSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.ReviewSchema;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ReviewSchemaGateway implements ReviewGateway {

  private final ReviewSchemaRepository reviewSchemaRepository;

  public ReviewSchemaGateway(ReviewSchemaRepository reviewSchemaRepository) {
    this.reviewSchemaRepository = reviewSchemaRepository;
  }

  @Override
  public Review save(Review review) {
    var reviewSchema = convertToReviewSchema(review);
    var reviewSchemaSaved = reviewSchemaRepository.save(reviewSchema);
    return reviewSchemaSaved.toReview();
  }

  @Override
  public List<Review> findReviewsByRestaurantId(UUID restaurantId) {
    var reviews = reviewSchemaRepository.findByRestaurantSchemaIdOrderByRestaurantSchemaCreatedAtDesc(
        restaurantId);
    return reviews.stream().map(ReviewSchema::toReview).toList();
  }

  private ReviewSchema convertToReviewSchema(Review review) {
    return ReviewSchema.builder()
        .restaurantSchema(RestaurantSchema.builder().id(review.getRestaurantId()).build())
        .description(review.getDescription())
        .score(review.getScore())
        .userSchema(UserSchema.builder().id(review.getUserId()).build())
        .build();
  }
}
