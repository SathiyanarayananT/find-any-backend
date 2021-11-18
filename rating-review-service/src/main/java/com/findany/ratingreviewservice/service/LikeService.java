package com.findany.ratingreviewservice.service;

import com.findany.ratingreviewservice.entity.Like;
import com.findany.ratingreviewservice.model.LikesCountResponse;
import com.findany.ratingreviewservice.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public Flux<LikesCountResponse> getProductLikesCount(List<Integer> ids) {
//        likeRepository.count()
        return null;
    }

}
