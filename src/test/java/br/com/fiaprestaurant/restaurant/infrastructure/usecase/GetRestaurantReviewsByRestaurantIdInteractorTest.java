package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_SCORE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createRestaurantReview;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.ReviewGateway;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetRestaurantReviewsByRestaurantIdInteractorTest {

  @Mock
  private ReviewGateway reviewGateway;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private RestaurantGateway restaurantGateway;
  @InjectMocks
  private GetRestaurantReviewsByRestaurantIdInteractor getRestaurantReviewsByRestaurantIdInteractor;

  @Test
  void shouldGetRestaurantReviewsByRestaurantId() {
    var restaurant = createRestaurant();
    var restaurantReview = createRestaurantReview(restaurant.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, UUID.randomUUID());
    var reviews = List.of(restaurantReview);
    when(restaurantGateway.findByIdRequired(restaurant.getId())).thenReturn(restaurant);
    when(reviewGateway.findReviewsByRestaurantId(restaurant.getId())).thenReturn(reviews);

    var reviewsFounded = getRestaurantReviewsByRestaurantIdInteractor.execute(
        restaurant.getId().toString());

    assertThat(reviewsFounded).isNotEmpty();
    verify(uuidValidator).validate(restaurant.getId().toString());
  }

  @Test
  void shouldThrowExceptionWhenGetRestaurantReviewsByRestaurantIdAndRestaurantWasNotFound() {
    var restaurant = createRestaurant();
    when(restaurantGateway.findByIdRequired(restaurant.getId())).thenThrow(NoResultException.class);

    assertThatThrownBy(() -> getRestaurantReviewsByRestaurantIdInteractor.execute(
        restaurant.getId().toString()))
        .isInstanceOf(NoResultException.class);

    verify(uuidValidator).validate(restaurant.getId().toString());
    verify(reviewGateway, never()).findReviewsByRestaurantId(restaurant.getId());
  }

}