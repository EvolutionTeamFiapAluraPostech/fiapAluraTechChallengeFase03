package br.com.fiaprestaurant.restaurant.infrastructure.gateway;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_SCORE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createNewRestaurantReviewSchema;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createRestaurantReview;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createRestaurantReviewSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.infrastructure.repository.ReviewSchemaRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewSchemaGatewayTest {

  @Mock
  private ReviewSchemaRepository reviewSchemaRepository;
  @InjectMocks
  private ReviewSchemaGateway reviewSchemaGateway;

  @Test
  void shoudSaveReviewSchema() {
    var review = createRestaurantReview(UUID.randomUUID(), DEFAULT_RESTAURANT_REVIEW_DESCRIPTION,
        DEFAULT_RESTAURANT_REVIEW_SCORE, UUID.randomUUID());
    var reviewSchema = createNewRestaurantReviewSchema(review);
    var reviewSchemaSaved = createRestaurantReviewSchema(review);
    when(reviewSchemaRepository.save(reviewSchema)).thenReturn(reviewSchemaSaved);

    var reviewSaved = reviewSchemaGateway.save(review);

    assertThat(reviewSaved).isNotNull();
    assertThat(reviewSaved.getId()).isNotNull().isEqualTo(reviewSchemaSaved.getId());
    assertThat(reviewSaved.getRestaurantId()).isNotNull()
        .isEqualTo(reviewSchemaSaved.getRestaurantSchema().getId());
    assertThat(reviewSaved.getDescription()).isNotBlank()
        .isEqualTo(reviewSchemaSaved.getDescription());
    assertThat(reviewSaved.getScore()).isNotNull().isNotNegative().isLessThan(11)
        .isEqualTo(reviewSchemaSaved.getScore());
    assertThat(reviewSaved.getUserId()).isNotNull()
        .isEqualTo(reviewSchemaSaved.getUserSchema().getId());
  }

  @Test
  void shouldFindReviewsByRestaurantId() {
    var review = createRestaurantReview(UUID.randomUUID(), DEFAULT_RESTAURANT_REVIEW_DESCRIPTION,
        DEFAULT_RESTAURANT_REVIEW_SCORE, UUID.randomUUID());
    var reviewSchema = createNewRestaurantReviewSchema(review);
    var reviews = List.of(reviewSchema);
    when(reviewSchemaRepository.findByRestaurantSchemaIdOrderByRestaurantSchemaCreatedAtDesc(
        reviewSchema.getRestaurantSchema().getId())).thenReturn(reviews);

    var reviewsFound = reviewSchemaGateway.findReviewsByRestaurantId(
        reviewSchema.getRestaurantSchema().getId());

    assertThat(reviewsFound).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldReturnEmptyListWhenFindReviewsByRestaurantIdWasNotFound() {
    var review = createRestaurantReview(UUID.randomUUID(), DEFAULT_RESTAURANT_REVIEW_DESCRIPTION,
        DEFAULT_RESTAURANT_REVIEW_SCORE, UUID.randomUUID());
    when(reviewSchemaRepository.findByRestaurantSchemaIdOrderByRestaurantSchemaCreatedAtDesc(
        review.getRestaurantId())).thenReturn(Collections.emptyList());

    var reviewsFound = reviewSchemaGateway.findReviewsByRestaurantId(review.getRestaurantId());

    assertThat(reviewsFound).isEmpty();
  }

}