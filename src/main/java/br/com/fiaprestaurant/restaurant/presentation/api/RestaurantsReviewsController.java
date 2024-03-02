package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantReviewUseCase;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantReviewInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantReviewOutputDto;
import org.springframework.http.HttpStatus;
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
  public RestaurantReviewOutputDto postRestaurantReview(@RequestBody
  RestaurantReviewInputDto restaurantReviewInputDto) {
    var review = restaurantReviewInputDto.toReviewFrom();
    var reviewSaved = createRestaurantReviewUseCase.execute(review);
    return RestaurantReviewOutputDto.from(reviewSaved);
  }
}
