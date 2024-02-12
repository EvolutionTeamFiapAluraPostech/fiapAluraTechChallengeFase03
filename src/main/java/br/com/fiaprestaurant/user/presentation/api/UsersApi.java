package br.com.fiaprestaurant.user.presentation.api;

import br.com.fiaprestaurant.user.presentation.dto.PostUserInputDto;
import br.com.fiaprestaurant.user.presentation.dto.PutUserInputDto;
import br.com.fiaprestaurant.user.presentation.dto.UserOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UsersApi", description = "API de cadastro do usuário do aplicativo")
public interface UsersApi {

  @Operation(summary = "Cadastro de usuários",
      description = "Endpoint para cadastrar novos usuários. O email e CPF devem ser únicos na base de dados",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação de senha, email e cpf",
          content = {@Content(schema = @Schema(hidden = true))})})
  UserOutputDto postUser(
      @Parameter(description = "DTO com atributos para se cadastrar um novo usuário. Requer validação de dados informados, como nome, email, cpf e senha")
      PostUserInputDto postUserInputDto);

  @Operation(summary = "Recupera um usuário",
      description = "Endpoint para recuperar um usuário pelo email cadastrado",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found", content = {
          @Content(schema = @Schema(hidden = true))})})
  UserOutputDto getUserByEmail(@Parameter(description = "email válido") String email);

  @Operation(summary = "Recupera um usuário",
      description = "Endpoint para recuperar um usuário pelo ID cadastrado",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  UserOutputDto getUserById(
      @Parameter(description = "UUID do usuário válido") String userId);

  @Operation(summary = "Atualiza usuários",
      description = "Endpoint para atualizar dados do usuário",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "409", description = "conflic para email/cpf já cadastrado em outro usuário", content = {
          @Content(schema = @Schema(hidden = true))})})
  UserOutputDto putUser(@Parameter(description = "UUID do usuário válido") String userUuid,
      @Parameter(description = "DTO com atributos para se cadastrar um novo usuário. Requer validação de dados informados, como nome, email, cpf e senha") PutUserInputDto putUserInputDto);

  @Operation(summary = "Exclui usuários",
      description = "Endpoint para excluir usuários. A exclusão é feita por soft delete",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "successful operation", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado", content = {
          @Content(schema = @Schema(hidden = true))})})
  void deleteUser(@Parameter(description = "UUID do usuário válido") String userUuid);

  @Operation(summary = "Recupera um usuário",
      description = "Endpoint para recuperar um usuário pelo CPF cadastrado",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado", content = {
          @Content(schema = @Schema(hidden = true))})})
  UserOutputDto getUserByCpf(@Parameter(description = "CPF do usuário válido") String cpf);
}
