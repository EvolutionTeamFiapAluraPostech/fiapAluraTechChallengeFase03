package br.com.fiaprestaurant.restaurant.presentation.dto;

import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;

public record RestaurantReviewOutputDto(
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do review do restaurante.")
    String id,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do restaurante.")
    String restaurantId,
    @Schema(example = "Restaurante com boa comida e ambiente agradável", description = "Descrição da avaliação.", minLength = 3, maxLength = 500)
    String description,
    @Schema(example = "Número de 1 a 10", description = "Nota da avaliação.", minimum = "0", maximum = "10")
    Integer score,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do usuário.")
    String userId
) {

  public static RestaurantReviewOutputDto from(Review review) {
    return new RestaurantReviewOutputDto(review.getId().toString(),
        review.getRestaurantId().toString(), review.getDescription(), review.getScore(),
        review.getUserId().toString());
  }
}
