package br.com.fiaprestaurant.restaurant.presentation.dto;

import br.com.fiaprestaurant.restaurant.domain.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

@Tag(name = "RestaurantReviewInputDto", description = "DTO de entrada de dados do review.")
public record RestaurantReviewInputDto(
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do restaurante.")
    String restaurantId,
    @Schema(example = "Restaurante com boa comida e ambiente agradável", description = "Descrição da avaliação.", minLength = 3, maxLength = 500)
    String description,
    @Schema(example = "Número de 1 a 10", description = "Nota da avaliação.", minimum = "0", maximum = "10")
    Integer score,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do usuário.")
    String userId
) {

  public Review toReviewFrom() {
    return new Review(UUID.fromString(this.restaurantId), this.description, this.score, UUID.fromString(this.userId));
  }
}
