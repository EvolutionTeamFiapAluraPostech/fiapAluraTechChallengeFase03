package br.com.fiaprestaurant.restaurant.domain.entity;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_REVIEW_DESCRIPTION_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_REVIEW_SCORE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_REVIEW_DESCRIPTION;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_REVIEW_DESCRIPTION_LENGTH_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_REVIEW_SCORE_MUST_BE_GREATER_THAN_ZERO;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_REVIEW_SCORE_MUST_BE_GREATER_THAN_ZERO_AND_LESS_THAN_TEN;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.util.UUID;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

public class Review {

  private UUID restaurantId;
  private String description;
  private Integer score;
  private UUID userId;

  public Review(UUID restaurantId, String description, Integer score, UUID userId) {
    validateReviewDescriptionIsNullOrEmpty(description);
    validateReviewDescriptionLengthItMustHaveBetween3And100Characters(description);
    validateReviewScoreIsNullOrLessThanZero(score);
    validateReviewScoreBetweenZeroAndTen(score);
    this.restaurantId = restaurantId;
    this.description = description;
    this.score = score;
    this.userId = userId;
  }

  private void validateReviewScoreBetweenZeroAndTen(Integer score) {
    if (score == null || score < 0) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_REVIEW_SCORE_FIELD,
              RESTAURANT_REVIEW_SCORE_MUST_BE_GREATER_THAN_ZERO_AND_LESS_THAN_TEN));
    }
  }

  private void validateReviewScoreIsNullOrLessThanZero(Integer score) {
    if (score == null || score < 0) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_REVIEW_SCORE_FIELD,
              RESTAURANT_REVIEW_SCORE_MUST_BE_GREATER_THAN_ZERO));
    }
  }

  private void validateReviewDescriptionLengthItMustHaveBetween3And100Characters(String description) {
    var reviewDescriptionLength = description.trim().length();
    if (reviewDescriptionLength > 100) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_REVIEW_DESCRIPTION_FIELD,
              RESTAURANT_REVIEW_DESCRIPTION_LENGTH_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS.formatted(reviewDescriptionLength)));
    }
  }

  private void validateReviewDescriptionIsNullOrEmpty(String description) {
    if (!StringUtils.hasText(description)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_REVIEW_DESCRIPTION_FIELD,
              ENTER_RESTAURANT_REVIEW_DESCRIPTION));
    }
  }

  public UUID getRestaurantId() {
    return restaurantId;
  }

  public String getDescription() {
    return description;
  }

  public Integer getScore() {
    return score;
  }

  public UUID getUserId() {
    return userId;
  }
}
