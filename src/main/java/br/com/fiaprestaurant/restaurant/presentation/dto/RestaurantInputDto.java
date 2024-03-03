package br.com.fiaprestaurant.restaurant.presentation.dto;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Tag(name = "RestaurantInputDto", description = "DTO de entrada de dados do restaurante.")
public record RestaurantInputDto(
    @Schema(example = "Sabor Goiâno", description = "Nome do restaurante.", minLength = 3, maxLength = 100)
    String name,
    @Schema(example = "69635854000140", description = "CNPJ do restaurante.", format = "00000000000000", minLength = 14, maxLength = 14)
    String cnpj,
    @Schema(example = "Brasileira", description = "Tipo de cozinha.", minLength = 3, maxLength = 50)
    String typeOfCuisine,
    @Schema(example = "-23.56391", description = "Latitude.", minLength = -90, maxLength = 90)
    Double latitude,
    @Schema(example = "-46.65239", description = "Longitude.", minLength = -90, maxLength = 90)
    Double longitude,
    @Schema(example = "Av. Brasil", description = "Rua.", minLength = 3, maxLength = 255)
    String street,
    @Schema(example = "Av. Brasil", description = "Rua.", minLength = 3, maxLength = 255, nullable = true)
    String number,
    @Schema(example = "Centro", description = "Bairro.", minLength = 3, maxLength = 100)
    String neighborhood,
    @Schema(example = "São Paulo", description = "Cidade.", minLength = 3, maxLength = 100)
    String city,
    @Schema(example = "SP", description = "Estado.", minLength = 2, maxLength = 2)
    String state,
    @Schema(example = "01153-000", description = "CEP.", minLength = 9, maxLength = 9)
    String postalCode,
    @Schema(example = "11:00", description = "Horário de abertura.", minLength = 5, maxLength = 5)
    String openAt,
    @Schema(example = "23:00", description = "Horário de fechamento.", minLength = 5, maxLength = 5)
    String closeAt,
    @NotNull
    @Positive
    @Schema(example = "200", description = "Capacidade total de pessoas", format = "Numérico")
    Integer peopleCapacity) {

  public Restaurant toRestaurantFrom() {
    return new Restaurant(this.name,
        this.cnpj,
        this.typeOfCuisine,
        this.latitude,
        this.longitude,
        this.street,
        this.number,
        this.neighborhood,
        this.city,
        this.state,
        this.postalCode,
        this.openAt,
        this.closeAt,
        this.peopleCapacity);
  }
}
