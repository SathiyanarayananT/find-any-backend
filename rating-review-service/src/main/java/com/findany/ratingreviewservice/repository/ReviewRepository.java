package com.findany.ratingreviewservice.repository;

import com.findany.ratingreviewservice.entity.Review;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ReactiveCrudRepository<Review, Integer> {
}
