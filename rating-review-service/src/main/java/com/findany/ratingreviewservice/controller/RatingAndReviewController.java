package com.findany.ratingreviewservice.controller;

import com.findany.ratingreviewservice.model.LikesCountResponse;
import com.findany.ratingreviewservice.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/rating-review")
public class RatingAndReviewController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/{city}")
    public Flux<LikesCountResponse> getProductLikesCountByIdIn(@RequestBody List<Integer> ids) {
        return likeService.getProductLikesCount(null);
    }

}
