package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantInputDto;
import static br.com.fiaprestaurant.shared.testData.user.AuthenticateTestData.DEFAULT_USER_AUTHENTICATE_REQUEST_BODY;
import static br.com.fiaprestaurant.shared.testData.user.AuthenticateTestData.URL_AUTHENTICATE;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@IntegrationTest
@DatabaseTest
class PostRestaurantApiTest {

  private static final String URL_RESTAURANTS = "/restaurants";
  private final EntityManager entityManager;
  @LocalServerPort
  private int port;
  private String token;

  @Autowired
  PostRestaurantApiTest(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    var response = given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(DEFAULT_USER_AUTHENTICATE_REQUEST_BODY)
        .when()
        .post(URL_AUTHENTICATE);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().jsonPath()).isNotNull();
    assertThat(response.getBody().jsonPath().getString("token")).isNotNull();
    token = response.body().jsonPath().getString("token");
  }

  @Test
  void shouldCreateRestaurant() {
    var restaurantInputDto = createRestaurantInputDto();

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .header("Content-Type", startsWith("application/json"))
        .body("name", equalTo(restaurantInputDto.name()))
        .body("cnpj", equalTo(restaurantInputDto.cnpj()));

    var restaurantFound = entityManager
        .createQuery("select r from RestaurantSchema r where r.cnpj = :cnpj",
            RestaurantSchema.class)
        .setParameter("cnpj", restaurantInputDto.cnpj())
        .getSingleResult();
    assertThat(restaurantFound).isNotNull();
    assertThat(restaurantFound.getName()).isNotEmpty().isEqualTo(restaurantInputDto.name());
    assertThat(restaurantFound.getCnpj()).isNotEmpty().isEqualTo(restaurantInputDto.cnpj());
  }
}