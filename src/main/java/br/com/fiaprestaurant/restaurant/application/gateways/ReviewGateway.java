package br.com.fiaprestaurant.restaurant.application.gateways;

import br.com.fiaprestaurant.restaurant.domain.entity.Review;

public interface ReviewGateway {

  Review save(Review review);

}
