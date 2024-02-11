package br.com.fiaprestaurant.user.presentation.dto;

import br.com.fiaprestaurant.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserOutputDto", description = "DTO de saída para representação de um usuário")
public record UserOutputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "Valid UUID")
    String id,
    @Schema(example = "Thomas Anderson", description = "Nome do condutor do veículo/usuário do aplicativo")
    String name,
    @Schema(example = "thomas.anderson@matrix.com", description = "email do condutor do veículo/usuário do aplicativo")
    String email,
    @Schema(example = "92477979000", description = "CPF do condutor do veículo/usuário do aplicativo")
    String cpf
) {

  public UserOutputDto(User user) {
    this(user.getId() != null ? user.getId().toString() : null,
        user.getName(),
        user.getEmail().address(),
        user.getCpf().cpf());
  }

  public static UserOutputDto from(User user) {
    return new UserOutputDto(user);
  }
}
