package br.com.fiaprestaurant.restaurant.infrastructure.repository;

import br.com.fiaprestaurant.restaurant.domain.repository.RestaurantRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantSchemaRepository extends RestaurantRepository,
    JpaRepository<RestaurantSchema, UUID> {

  Optional<RestaurantSchema> findByCnpj(String cnpjValue);

}
