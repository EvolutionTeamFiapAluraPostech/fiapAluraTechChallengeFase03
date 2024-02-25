package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_VALID_CNPJ;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_HOUR_CLOSE_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_HOUR_OPEN_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_PEOPLE_CAPACITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_TYPE_OF_CUISINE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_VALID_CNPJ;
import static br.com.fiaprestaurant.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.api.JsonUtil;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import br.com.fiaprestaurant.shared.util.StringUtil;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PutRestaurantApiTest {

  private static final String URL_RESTAURANTS = "/restaurants/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PutRestaurantApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private RestaurantSchema createAndSaveRestaurant(String defaultRestaurantName,
      String defaultRestaurantValidCnpj, String defaultRestaurantTypeOfCuisine,
      Double defaultRestaurantLatitude, Double defaultRestaurantLongitude, String openAt,
      String closeAt, Integer peopleCapacity) {
    var restaurantSchema = RestaurantTestData.createNewRestaurantSchema(defaultRestaurantName,
        defaultRestaurantValidCnpj, defaultRestaurantTypeOfCuisine, defaultRestaurantLatitude,
        defaultRestaurantLongitude, openAt, closeAt, peopleCapacity);
    return entityManager.merge(restaurantSchema);
  }

  @Test
  void shouldUpdateRestaurant() throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE, ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT,
        ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS + restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isAccepted())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var restaurantSchemaFound = entityManager.find(RestaurantSchema.class, UUID.fromString(id));
    assertThat(restaurantSchemaFound).isNotNull();
    assertThat(restaurantSchemaFound.getId()).isNotNull().isEqualTo(restaurantSchema.getId());
    assertThat(restaurantSchemaFound.getName()).isNotNull().isEqualTo(restaurantInputDto.name());
    assertThat(restaurantSchemaFound.getCnpj()).isNotNull().isEqualTo(restaurantInputDto.cnpj());
    assertThat(restaurantSchemaFound.getTypeOfCuisine()).isNotNull()
        .isEqualTo(restaurantInputDto.typeOfCuisine());
    assertThat(restaurantSchemaFound.getLatitude()).isNotNull()
        .isEqualTo(restaurantInputDto.latitude());
    assertThat(restaurantSchemaFound.getLongitude()).isNotNull()
        .isEqualTo(restaurantInputDto.longitude());
  }

  @Test
  void shouldThrowExceptionWhenCnpjAlreadyExistsInOtherRestaurant() throws Exception {
    var restaurantSchemaBrasileiro = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantSchemaArgentino = createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME,
        ALTERNATIVE_RESTAURANT_VALID_CNPJ, ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE,
        ALTERNATIVE_RESTAURANT_LATITUDE, ALTERNATIVE_RESTAURANT_LONGITUDE,
        ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT, ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT,
        ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchemaArgentino.getName(), restaurantSchemaBrasileiro.getCnpj(),
        restaurantSchemaArgentino.getTypeOfCuisine(), restaurantSchemaArgentino.getLatitude(),
        restaurantSchemaArgentino.getLongitude(), restaurantSchemaArgentino.getOpenAt(),
        restaurantSchemaArgentino.getCloseAt(), restaurantSchemaArgentino.getPeopleCapacity());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS + restaurantSchemaArgentino.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isConflict())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantNameIsNullOrEmpty(String name) throws Exception {
    var restaurantSchema = createAndSaveRestaurant(name,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(),
        restaurantSchema.getPeopleCapacity());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS + restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantNameLengthIsBiggerThan100Characters() throws Exception {
    var restaurantName = StringUtil.generateStringLength(101);
    var restaurantSchema = createAndSaveRestaurant(restaurantName,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(),
        restaurantSchema.getPeopleCapacity());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS + restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "1", "12345678901234", "11111111111111", "22222222222222",
      "33333333333333", "44444444444444", "55555555555555", "66666666666666", "77777777777777",
      "88888888888888", "99999999999999", "00000000000000"})
  void shouldThrowExceptionWhenRestaurantCnpjIsInvalid(String cnpj) throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        cnpj, DEFAULT_RESTAURANT_TYPE_OF_CUISINE, DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(),
        restaurantSchema.getPeopleCapacity());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS + restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "1", "12345", "a0:00", "10:a0"})
  void shouldThrowExceptionWhenRestaurantOpenAtIsInvalid(String openAt) throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, openAt,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(),
        restaurantSchema.getPeopleCapacity());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS + restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "1", "12345", "a0:00", "10:a0"})
  void shouldThrowExceptionWhenRestaurantCloseAtIsInvalid(String closeAt) throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        closeAt, DEFAULT_RESTAURANT_PEOPLE_CAPACITY);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(),
        restaurantSchema.getPeopleCapacity());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS + restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 0})
  void shouldThrowExceptionWhenRestaurantPeopleCapacityIsInvalid(Integer peopleCapacity) throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, peopleCapacity);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(),
        restaurantSchema.getPeopleCapacity());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS + restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

}
