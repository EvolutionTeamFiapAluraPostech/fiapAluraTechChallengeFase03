package br.com.fiaprestaurant.user.presentation.api;

import br.com.fiaprestaurant.user.application.usecase.CreateUserUseCase;
import br.com.fiaprestaurant.user.application.usecase.DeleteUserUseCase;
import br.com.fiaprestaurant.user.application.usecase.GetAllUsersUseCase;
import br.com.fiaprestaurant.user.application.usecase.GetUserByCpfUseCase;
import br.com.fiaprestaurant.user.application.usecase.GetUserByEmailUseCase;
import br.com.fiaprestaurant.user.application.usecase.GetUserByIdUseCase;
import br.com.fiaprestaurant.user.application.usecase.GetUsersByNameUseCase;
import br.com.fiaprestaurant.user.application.usecase.UpdateUserUseCase;
import br.com.fiaprestaurant.user.presentation.dto.PostUserInputDto;
import br.com.fiaprestaurant.user.presentation.dto.PutUserInputDto;
import br.com.fiaprestaurant.user.presentation.dto.UserOutputDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController implements UsersApi {

  private final CreateUserUseCase createUserUseCase;
  private final GetUserByEmailUseCase getUserByEmailUseCase;
  private final GetUserByIdUseCase getUserByIdUseCase;
  private final UpdateUserUseCase updateUserUseCase;
  private final DeleteUserUseCase deleteUserUseCase;
  private final GetUserByCpfUseCase getUserByCpfUseCase;

  public UsersController(
      CreateUserUseCase createUserUseCase,
      GetUserByEmailUseCase getUserByEmailUseCase,
      GetUserByIdUseCase getUserByIdUseCase,
      UpdateUserUseCase updateUserUseCase,
      DeleteUserUseCase deleteUserUseCase, GetUserByCpfUseCase getUserByCpfUseCase) {
    this.createUserUseCase = createUserUseCase;
    this.getUserByEmailUseCase = getUserByEmailUseCase;
    this.getUserByIdUseCase = getUserByIdUseCase;
    this.updateUserUseCase = updateUserUseCase;
    this.deleteUserUseCase = deleteUserUseCase;
    this.getUserByCpfUseCase = getUserByCpfUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserOutputDto postUser(@RequestBody @Valid PostUserInputDto postUserInputDto) {
    var user = PostUserInputDto.toUser(postUserInputDto);
    var userCreated = createUserUseCase.execute(user);
    return UserOutputDto.from(userCreated);
  }

  @GetMapping("/email/{email}")
  @ResponseStatus(HttpStatus.OK)
  public UserOutputDto getUserByEmail(@PathVariable String email) {
    var userFound = getUserByEmailUseCase.execute(email);
    return UserOutputDto.from(userFound);
  }

  @GetMapping("/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public UserOutputDto getUserById(@PathVariable String userId) {
    var user = getUserByIdUseCase.execute(userId);
    return UserOutputDto.from(user);
  }

  @PutMapping("/{userUuid}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public UserOutputDto putUser(@PathVariable String userUuid,
      @RequestBody @Valid PutUserInputDto putUserInputDto) {
    var user = PutUserInputDto.toUser(putUserInputDto);
    var userUpdated = updateUserUseCase.execute(userUuid, user);
    return UserOutputDto.from(userUpdated);
  }

  @DeleteMapping("/{userUuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable String userUuid) {
    deleteUserUseCase.execute(userUuid);
  }

  @GetMapping("/cpf/{cpf}")
  @ResponseStatus(HttpStatus.OK)
  public UserOutputDto getUserByCpf(@PathVariable String cpf) {
    var user = getUserByCpfUseCase.execute(cpf);
    return UserOutputDto.from(user);
  }
}
