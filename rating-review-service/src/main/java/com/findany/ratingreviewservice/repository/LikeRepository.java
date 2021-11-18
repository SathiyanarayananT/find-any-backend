package com.findany.ratingreviewservice.repository;

import com.findany.ratingreviewservice.entity.Like;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends ReactiveCrudRepository<Like, Integer> {

}
