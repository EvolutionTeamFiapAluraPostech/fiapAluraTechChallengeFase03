package br.com.fiaprestaurant.shared.api;

import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class PageUtil {

  public static final int PAGE_NUMBER = 0;
  public static final int PAGE_SIZE = 1;

  public static PageImpl<UserSchema> generatePageOfUser(UserSchema userSchema) {
    var pageNumber = 0;
    var pageSize = 2;
    var totalItems = 3;
    var pageable = PageRequest.of(pageNumber, pageSize);
    return new PageImpl<>(List.of(userSchema), pageable, totalItems);
  }
}
