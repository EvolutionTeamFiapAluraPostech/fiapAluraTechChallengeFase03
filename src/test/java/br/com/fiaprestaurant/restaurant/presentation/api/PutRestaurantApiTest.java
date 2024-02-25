package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_CITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NEIGHBORHOOD;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NUMBER;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_POSTAL_CODE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_STATE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_STREET;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_VALID_CNPJ;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_CITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_HOUR_CLOSE_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_HOUR_OPEN_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NEIGHBORHOOD;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NUMBER;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_PEOPLE_CAPACITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_POSTAL_CODE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_STATE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_STREET;
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

  private static final String URL_RESTAURANTS = "/restaurants/{id}";
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
      String closeAt, Integer peopleCapacity, String street, String number,
      String neighborhood, String city, String state, String postalCode) {
    var restaurantSchema = RestaurantTestData.createNewRestaurantSchema(defaultRestaurantName,
        defaultRestaurantValidCnpj, defaultRestaurantTypeOfCuisine, defaultRestaurantLatitude,
        defaultRestaurantLongitude, openAt, closeAt, peopleCapacity, street, number, neighborhood,
        city, state, postalCode);
    return entityManager.merge(restaurantSchema);
  }

  @Test
  void shouldUpdateRestaurant() throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE, ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT,
        ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        ALTERNATIVE_RESTAURANT_STREET, ALTERNATIVE_RESTAURANT_NUMBER,
        ALTERNATIVE_RESTAURANT_NEIGHBORHOOD, ALTERNATIVE_RESTAURANT_CITY,
        ALTERNATIVE_RESTAURANT_STATE, ALTERNATIVE_RESTAURANT_POSTAL_CODE);
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
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

  @ParameterizedTest
  @ValueSource(strings = {"a", "1", "ABC"})
  void shouldThrowExceptionWhenIDisInvalid(String id) throws Exception {
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE, ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT,
        ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        ALTERNATIVE_RESTAURANT_STREET, ALTERNATIVE_RESTAURANT_NUMBER,
        ALTERNATIVE_RESTAURANT_NEIGHBORHOOD, ALTERNATIVE_RESTAURANT_CITY,
        ALTERNATIVE_RESTAURANT_STATE, ALTERNATIVE_RESTAURANT_POSTAL_CODE);
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenIdWasNotFound() throws Exception {
    var id = UUID.randomUUID();
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE, ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT,
        ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        ALTERNATIVE_RESTAURANT_STREET, ALTERNATIVE_RESTAURANT_NUMBER,
        ALTERNATIVE_RESTAURANT_NEIGHBORHOOD, ALTERNATIVE_RESTAURANT_CITY,
        ALTERNATIVE_RESTAURANT_STATE, ALTERNATIVE_RESTAURANT_POSTAL_CODE);
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenCnpjAlreadyExistsInOtherRestaurant() throws Exception {
    var cnpjRestauranteBrasileiro = "11167069000120";
    var cnpjRestauranteArgentino = "47072726000101";
    var restaurantSchemaBrasileiro = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        cnpjRestauranteBrasileiro, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantSchemaArgentino = createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME,
        cnpjRestauranteArgentino, ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE,
        ALTERNATIVE_RESTAURANT_LATITUDE, ALTERNATIVE_RESTAURANT_LONGITUDE,
        ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT, ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT,
        ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY, DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchemaArgentino.getName(), restaurantSchemaBrasileiro.getCnpj(),
        restaurantSchemaArgentino.getTypeOfCuisine(), restaurantSchemaArgentino.getLatitude(),
        restaurantSchemaArgentino.getLongitude(), restaurantSchemaArgentino.getOpenAt(),
        restaurantSchemaArgentino.getCloseAt(), restaurantSchemaArgentino.getPeopleCapacity(),
        restaurantSchemaArgentino.getStreet(), restaurantSchemaArgentino.getNumber(),
        restaurantSchemaArgentino.getNeighborhood(), restaurantSchemaArgentino.getCity(),
        restaurantSchemaArgentino.getState(), restaurantSchemaArgentino.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchemaArgentino.getId())
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
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
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
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
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
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
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
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
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
        closeAt, DEFAULT_RESTAURANT_PEOPLE_CAPACITY, DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 0})
  void shouldThrowExceptionWhenRestaurantPeopleCapacityIsInvalid(Integer peopleCapacity)
      throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, peopleCapacity, DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantTypeOfCuisineIsNullOrEmpty(String typeOfCuisine)
      throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, typeOfCuisine,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantTypeOfCuisineLengthIsBiggerThan50Characters()
      throws Exception {
    var typeOfCuisine = StringUtil.generateStringLength(51);
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, typeOfCuisine,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantStreetIsNullOrEmpty(String street) throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        street, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantStreetLengthIsBiggerThan255Characters() throws Exception {
    var street = StringUtil.generateStringLength(256);
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        street, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(), restaurantSchema.getState(),
        restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantNeighborhoodIsNullOrEmpty(String neighborhood)
      throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, neighborhood,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantNeighborhoodLengthIsBiggerThan100Characters()
      throws Exception {
    var neighborhood = StringUtil.generateStringLength(101);
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, neighborhood,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(), restaurantSchema.getState(),
        restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantCityIsNullOrEmpty(String city) throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        city, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantCityLengthIsBiggerThan100Characters() throws Exception {
    var city = StringUtil.generateStringLength(101);
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_CITY,
        city, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(), restaurantSchema.getState(),
        restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantStateIsNullOrEmpty(String state) throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, state, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(),
        restaurantSchema.getState(), restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantStateLengthIsBiggerThan3Characters() throws Exception {
    var state = StringUtil.generateStringLength(3);
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_CITY, state, DEFAULT_RESTAURANT_POSTAL_CODE);
    var restaurantInputDto = RestaurantTestData.updateRestaurantInputDto(
        restaurantSchema.getName(), restaurantSchema.getCnpj(),
        restaurantSchema.getTypeOfCuisine(), restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude(), restaurantSchema.getOpenAt(),
        restaurantSchema.getCloseAt(), restaurantSchema.getPeopleCapacity(),
        restaurantSchema.getStreet(), restaurantSchema.getNumber(),
        restaurantSchema.getNeighborhood(), restaurantSchema.getCity(), restaurantSchema.getState(),
        restaurantSchema.getPostalCode());
    var restaurantInputJson = JsonUtil.toJson(restaurantInputDto);

    var request = put(URL_RESTAURANTS, restaurantSchema.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(restaurantInputJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

}
