package br.com.fiaprestaurant.restaurant.infrastructure.repository;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.ReviewSchema;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewSchemaRepository extends JpaRepository<ReviewSchema, UUID> {

}
