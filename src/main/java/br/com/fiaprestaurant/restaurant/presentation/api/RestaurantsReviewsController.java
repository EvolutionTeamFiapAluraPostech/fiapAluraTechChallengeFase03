package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantReviewUseCase;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantReviewInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantReviewOutputDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/{restaurant-id}/review/{user-id}")
public class RestaurantsReviewsController implements RestaurantsReviewsApi {

  private final CreateRestaurantReviewUseCase createRestaurantReviewUseCase;

  public RestaurantsReviewsController(CreateRestaurantReviewUseCase createRestaurantReviewUseCase) {
    this.createRestaurantReviewUseCase = createRestaurantReviewUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public RestaurantReviewOutputDto postRestaurantReview(
      @PathVariable(name = "restaurant-id") String restaurantId,
      @PathVariable(name = "user-id") String userId, @RequestBody @Valid
  RestaurantReviewInputDto restaurantReviewInputDto) {
    validateRestaurantIdParamWithRestaurantIdRequestBodyAttribute(restaurantId, restaurantReviewInputDto);
    validateUserIdParamWithUserIdRequestBodyAttribute(userId, restaurantReviewInputDto);
    var review = restaurantReviewInputDto.toReviewFrom();
    var reviewSaved = createRestaurantReviewUseCase.execute(review);
    return RestaurantReviewOutputDto.from(reviewSaved);
  }

  private void validateRestaurantIdParamWithRestaurantIdRequestBodyAttribute(String restaurantIdParam,
      RestaurantReviewInputDto restaurantReviewInputDto) {
    if (!restaurantReviewInputDto.restaurantId().equals(restaurantIdParam)) {
      throw new IllegalArgumentException("Restaurant ID param is different from Restaurant ID request body attribute.");
    }
  }

  private void validateUserIdParamWithUserIdRequestBodyAttribute(String userIdParam,
      RestaurantReviewInputDto restaurantReviewInputDto) {
    if (!restaurantReviewInputDto.userId().equals(userIdParam)) {
      throw new IllegalArgumentException("User ID param is different from User ID request body attribute.");
    }
  }
}
