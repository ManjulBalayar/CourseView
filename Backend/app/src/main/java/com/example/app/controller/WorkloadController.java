package com.example.app.controller;

import com.example.app.miscellaneous.Workload;
import com.example.app.model.Review;
import com.example.app.model.UserProfile;
import com.example.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class WorkloadController {

    @Autowired
    ReviewRepository reviewRepository;


    @GetMapping("/workload/{coure_id}")
    public Workload getStudent(@PathVariable("coure_id") Long coure_id) {

        List<Review> reviewList = reviewRepository.findByCourseCourseid(coure_id);
        Long R = 0L;
        Long D = 0L;
        Long T = 0L;
        Long size = 0L;

        for(Review r: reviewList){
            R += r.getRating();
            D += r.getDifficulty();
            T += r.getTime_commitment();
            size += 1;
        }

        R = R/size;
        D = D/size;
        T = T/size;

        Workload workload = new Workload(R,D,T);
        return workload;
    }
}
