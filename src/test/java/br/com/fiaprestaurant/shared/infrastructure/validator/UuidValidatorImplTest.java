package br.com.fiaprestaurant.shared.infrastructure.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UuidValidatorImplTest {

  @Spy
  private UuidValidatorImpl uuidValidator;

  @Test
  void shouldValidateUuid() {
    var uuid = UUID.randomUUID();
    Assertions.assertDoesNotThrow(() -> uuidValidator.validate(uuid.toString()));
  }

  @Test
  void shouldThrowExceptionWhenUuidIsInvalid() {
    var invalidUuid = "abc";
    assertThrows(ValidatorException.class, () -> uuidValidator.validate(invalidUuid));
  }
}
