package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_SCORE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createRestaurantReview;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.ReviewGateway;
import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantReviewInteractorTest {

  @Mock
  private RestaurantGateway restaurantGateway;
  @Mock
  private UserGateway userGateway;
  @Mock
  private ReviewGateway reviewGateway;
  @InjectMocks
  private CreateRestaurantReviewInteractor createRestaurantReviewInteractor;

  @Test
  void shouldCreateReview() {
    var restaurant = RestaurantTestData.createRestaurant();
    var user = createUser();
    var review = createRestaurantReview(restaurant.getId(), DEFAULT_RESTAURANT_REVIEW_DESCRIPTION,
        DEFAULT_RESTAURANT_REVIEW_SCORE, user.getId());
    when(restaurantGateway.findByIdRequired(restaurant.getId())).thenReturn(restaurant);
    when(userGateway.findUserByIdRequired(user.getId())).thenReturn(user);
    when(reviewGateway.save(review)).then(returnsFirstArg());

    var reviewSaved = createRestaurantReviewInteractor.execute(review);

    assertThat(reviewSaved).isNotNull();
    assertThat(reviewSaved.getId()).isNotNull().isEqualTo(review.getId());
    assertThat(reviewSaved.getRestaurantId()).isNotNull().isEqualTo(review.getRestaurantId());
    assertThat(reviewSaved.getUserId()).isNotNull().isEqualTo(review.getUserId());
    assertThat(reviewSaved.getDescription()).isNotBlank().isEqualTo(review.getDescription());
    assertThat(reviewSaved.getScore()).isNotNull().isNotNegative().isLessThan(11);
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantReviewWithInvalidRestaurant() {
    var restaurantId = UUID.randomUUID();
    var user = createUser();
    var review = createRestaurantReview(restaurantId, DEFAULT_RESTAURANT_REVIEW_DESCRIPTION,
        DEFAULT_RESTAURANT_REVIEW_SCORE, user.getId());
    when(restaurantGateway.findByIdRequired(restaurantId)).thenThrow(NoResultException.class);

    assertThatThrownBy(() -> createRestaurantReviewInteractor.execute(review))
        .isInstanceOf(NoResultException.class);
    verify(userGateway, never()).findUserByIdRequired(review.getUserId());
    verify(reviewGateway, never()).save(review);
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantReviewWithInvalidUser() {
    var restaurant = RestaurantTestData.createRestaurant();
    var userId = UUID.randomUUID();
    var review = createRestaurantReview(restaurant.getId(), DEFAULT_RESTAURANT_REVIEW_DESCRIPTION,
        DEFAULT_RESTAURANT_REVIEW_SCORE, userId);
    when(restaurantGateway.findByIdRequired(restaurant.getId())).thenReturn(restaurant);
    when(userGateway.findUserByIdRequired(userId)).thenThrow(NoResultException.class);

    assertThatThrownBy(() -> createRestaurantReviewInteractor.execute(review))
        .isInstanceOf(NoResultException.class);
    verify(reviewGateway, never()).save(review);
  }

}