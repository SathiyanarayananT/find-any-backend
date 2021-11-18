package com.findany.productfeedservice.repository;

import com.findany.productfeedservice.entity.ProductFeed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFeedRepository extends JpaRepository<ProductFeed, String> {

    Slice<ProductFeed> findByCity(String city, Pageable pageable);

}
