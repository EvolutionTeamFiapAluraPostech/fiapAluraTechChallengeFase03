package br.com.fiaprestaurant.restaurant.domain.entity;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_SCORE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createRestaurantReview;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_ID;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import br.com.fiaprestaurant.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ReviewTest {

  @Test
  void shouldCreateNewReview() {
    var review = createRestaurantReview(DEFAULT_RESTAURANT_ID,
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, DEFAULT_USER_UUID);
    assertThat(review).isNotNull();
    assertThat(review.getId()).isNotNull();
    assertThat(review.getRestaurantId()).isNotNull().isEqualTo(DEFAULT_RESTAURANT_ID);
    assertThat(review.getUserId()).isNotNull().isEqualTo(DEFAULT_USER_UUID);
    assertThat(review.getDescription()).isNotBlank()
        .isEqualTo(DEFAULT_RESTAURANT_REVIEW_DESCRIPTION);
    assertThat(review.getScore()).isNotNull().isEqualTo(DEFAULT_RESTAURANT_REVIEW_SCORE);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenReviewDescriptionIsNullOrEmpty(String description) {
    assertThatThrownBy(() -> createRestaurantReview(DEFAULT_RESTAURANT_ID,
        description, DEFAULT_RESTAURANT_REVIEW_SCORE, DEFAULT_USER_UUID))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldThrowExceptionWhenReviewDescriptionLengthIsGreaterThan100Characters() {
    var description = StringUtil.generateStringLength(101);
    assertThatThrownBy(() -> createRestaurantReview(DEFAULT_RESTAURANT_ID,
        description, DEFAULT_RESTAURANT_REVIEW_SCORE, DEFAULT_USER_UUID))
        .isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(ints = {-1, 11})
  void shouldThrowExceptionWhenScoreIsNullOrLessThanZeroOrGreaterThenTen(Integer score) {
    assertThatThrownBy(() -> createRestaurantReview(DEFAULT_RESTAURANT_ID,
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, score, DEFAULT_USER_UUID))
        .isInstanceOf(ValidatorException.class);
  }

}
