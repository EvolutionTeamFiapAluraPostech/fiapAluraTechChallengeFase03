package br.com.fiaprestaurant.restaurant.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Tag(name = "RestaurantContent", description = "DTO de saída representação de um restaurante")

@Getter
@Setter
public class RestaurantContent {

  @Schema(description = "Lista DTO de restaurantes")
  private List<RestaurantOutputDto> content;

}
