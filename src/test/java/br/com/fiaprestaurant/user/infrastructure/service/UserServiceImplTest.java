package br.com.fiaprestaurant.user.infrastructure.service;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUserSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.exception.NoResultException;
import br.com.fiaprestaurant.user.infrastructure.repository.UserRepository;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 1;

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService userService;

  @Test
  void shouldSaveUserWhenAllUserAttributesAreCorrect() {
    var user = createUser();
    var userSchema = createNewUserSchema(user);
    when(userRepository.save(userSchema)).then(returnsFirstArg());

    var userSaved = userService.save(userSchema);

    assertThat(userSaved).isNotNull();
    assertThat(userSaved.getName()).isEqualTo(user.getName());
    assertThat(userSaved.getEmail()).isEqualTo(user.getEmail().address());
    verify(userRepository).save(userSchema);
  }

  @Test
  void shouldGetAllUsersPaginatedWhenUsersExits() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    var users = List.of(userSchema);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = users.size();
    var page = new PageImpl<>(users, pageable, size);

    when(userRepository.findAll(pageable)).thenReturn(page);
    var usersFound = userService.getAllUsersPaginated(pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldReturnEmptyPageWhenDoesNotExistAnyUserSaved() {
    var users = new ArrayList<UserSchema>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(users, pageable, size);
    when(userRepository.findAll(pageable)).thenReturn(page);

    var usersFound = userService.getAllUsersPaginated(pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldFindUserByEmailWhenUserExists() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userRepository.findByEmail(userSchema.getEmail())).thenReturn(Optional.of(userSchema));

    var userFoundOptional = userService.findByEmail(userSchema.getEmail());
    var userFound = userFoundOptional.orElse(null);

    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(userSchema.getName());
    assertThat(userFound.getEmail()).isEqualTo(userSchema.getEmail());
  }

  @Test
  void shouldFindUserByCpfWhenUserExists() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userRepository.findByCpf(userSchema.getCpf())).thenReturn(Optional.of(userSchema));

    var userFoundOptional = userService.findByCpf(userSchema.getCpf());
    var userFound = userFoundOptional.orElse(null);

    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(userSchema.getName());
    assertThat(userFound.getCpf()).isEqualTo(userSchema.getCpf());
  }

  @Test
  void shouldReturnEmptyWhenFindUserByEmailDoesNotExist() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userRepository.findByEmail(userSchema.getEmail())).thenReturn(Optional.empty());

    var userFoundOptional = userService.findByEmail(userSchema.getEmail());
    var userFound = userFoundOptional.orElse(null);

    assertThat(userFound).isNull();
  }

  @Test
  void shouldFindUserByNameWhenUserExists() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    var name = userSchema.getName();
    var users = List.of(userSchema);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = users.size();
    var page = new PageImpl<>(users, pageable, size);
    when(userRepository.findByNameLikeIgnoreCase(name, pageable)).thenReturn(page);

    var usersPageFound = userService.findByNamePageable(name, pageable);

    assertThat(usersPageFound).isNotNull();
    assertThat(usersPageFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersPageFound.getTotalPages()).isEqualTo(size);
    assertThat(usersPageFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldReturnEmptyPageWhenFindByNameAndDoesNotExistAnyUserSaved() {
    var name = "Smith";
    var users = new ArrayList<UserSchema>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(users, pageable, size);
    when(userRepository.findByNameLikeIgnoreCase(name, pageable)).thenReturn(page);

    var usersFound = userService.findByNamePageable(name, pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldFindUserByIdWhenUserExists() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userRepository.findById(userSchema.getId())).thenReturn(Optional.of(userSchema));

    var userFoundOptional = userService.findById(userSchema.getId());
    var userFound = userFoundOptional.orElse(null);

    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(userSchema.getName());
    assertThat(userFound.getEmail()).isEqualTo(userSchema.getEmail());
  }

  @Test
  void shouldReturnEmptyWhenFindUserByIdDoesNotExist() {
    var uuid = UUID.randomUUID();
    when(userRepository.findById(uuid)).thenReturn(Optional.empty());

    var userFound = userService.findById(uuid);

    assertThat(userFound.isPresent()).isEqualTo(Boolean.FALSE);
  }

  @Test
  void shouldFindUserByEmailRequiredWhenUserExists() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userRepository.findByEmail(userSchema.getEmail())).thenReturn(Optional.of(userSchema));

    var userFound = userService.findByEmailRequired(userSchema.getEmail());

    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(userSchema.getName());
    assertThat(userFound.getEmail()).isEqualTo(userSchema.getEmail());
  }

  @Test
  void shouldFindUserByIdRequiredWhenUserExists() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userRepository.findById(userSchema.getId())).thenReturn(Optional.of(userSchema));

    var userFound = userService.findUserByIdRequired(userSchema.getId());

    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(userSchema.getName());
    assertThat(userFound.getCpf()).isEqualTo(userSchema.getCpf());
  }

  @Test
  void shouldReturnEmptyWhenFindUserByEmailRequiredDoesNotExist() {
    when(userRepository.findByEmail(DEFAULT_USER_EMAIL)).thenReturn(Optional.empty());

    assertThrows(NoResultException.class,
        () -> userService.findByEmailRequired(DEFAULT_USER_EMAIL));
  }

  @Test
  void shouldReturnEmptyWhenFindUserByIdRequiredDoesNotExist() {
    when(userRepository.findById(DEFAULT_USER_UUID)).thenReturn(Optional.empty());

    assertThrows(NoResultException.class,
        () -> userService.findUserByIdRequired(DEFAULT_USER_UUID));
  }

}
