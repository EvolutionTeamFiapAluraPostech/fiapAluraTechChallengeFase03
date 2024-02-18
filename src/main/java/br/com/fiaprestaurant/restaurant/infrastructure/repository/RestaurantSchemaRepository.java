package br.com.fiaprestaurant.restaurant.infrastructure.repository;

import br.com.fiaprestaurant.restaurant.domain.repository.RestaurantRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantSchemaRepository extends RestaurantRepository,
    JpaRepository<RestaurantSchema, UUID> {

  Optional<RestaurantSchema> findByCnpj(String cnpjValue);

  @Query("""
      select r
        from RestaurantSchema r
        where r.deleted = false
        and (:name is null or lower(r.name) like %:name%)
        and (:typeOfCuisine is null or lower(r.typeOfCuisine) like %:typeOfCuisine%)
        and ((:latitude is null and :longitude is null) or (r.latitude = :latitude and r.longitude = :longitude))
        order by r.name
      """)
  List<RestaurantSchema> queryByNameCoordinatesTypeOfCuisine(String name, String typeOfCuisine,
      Double latitude, Double longitude);

}
