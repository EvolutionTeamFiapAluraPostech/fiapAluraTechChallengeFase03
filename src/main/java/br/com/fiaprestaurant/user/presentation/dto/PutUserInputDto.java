package br.com.fiaprestaurant.user.presentation.dto;

import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.domain.entity.UserBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Tag(name = "PutUserInputDto", description = "DTO de entrada represetação de um usuário, para atualização de dados")
public record PutUserInputDto(
    @Schema(example = "Thomas Anderson", description = "Nome do usuário do aplicativo")
    @NotBlank(message = "Name is required.")
    @Length(max = 500, message = "Max name length is 500 characters.")
    String name,
    @Schema(example = "thomas.anderson@matrix.com", description = "email do usuário do aplicativo")
    @NotBlank(message = "email is required.")
    @Length(max = 500, message = "Max email length is 500 characters.")
    @Email
    String email,
    @Schema(example = "92477979000", description = "CPF do usuário do aplicativo")
    @NotBlank(message = "cpf is required.")
    @Length(max = 14, message = "Max cpf length is 14 characters.")
    @CPF
    String cpf
) {

  public static User userToUpdate(PutUserInputDto putUserInputDto) {
    return new UserBuilder()
        .setName(putUserInputDto.name)
        .setEmail(putUserInputDto.email)
        .setCpf(putUserInputDto.cpf)
        .updateUser();
  }

}
