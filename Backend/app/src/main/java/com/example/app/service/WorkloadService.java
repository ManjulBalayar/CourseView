package com.example.app.service;

import com.example.app.model.Course;
import com.example.app.model.Review;
import com.example.app.model.Schedule;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.ReviewRepository;
import com.example.app.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkloadService {
    private static final double CONSTANT_VALUE = 1.0;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public Double findAvgRatingByCourseCourseId(Long courseId) {
        List<Review> reviews = reviewRepository.findByCourse_Courseid(courseId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public Double findAvgDifficultyByCourseCourseId(Long courseId) {
        List<Review> reviews = reviewRepository.findByCourse_Courseid(courseId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream()
                .mapToDouble(Review::getDifficulty)
                .average()
                .orElse(0.0);
    }

    public Double findAvgTimeCommitmentByCourseCourseId(Long courseId) {
        List<Review> reviews = reviewRepository.findByCourse_Courseid(courseId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream()
                .mapToDouble(Review::getTime_commitment)
                .average()
                .orElse(0.0);
    }

    public double Workload(Long userId) {
        List<Review> scheduleList = scheduleRepository.findByUserProfileUserid(userId);
        double totalWorkload = 0.0;
        for(Review scheduleItem : scheduleList) {
            for(Course course : scheduleItem.getCourse()) {
                Double avgRating = findAvgRatingByCourseCourseId(course.getCourse_id());
                Double avgDifficulty = findAvgDifficultyByCourseCourseId(course.getCourse_id());
                Double avgTimeCommitment = findAvgTimeCommitmentByCourseCourseId(course.getCourse_id());

                totalWorkload += (avgDifficulty * avgTimeCommitment * avgRating) / CONSTANT_VALUE;
            }
        }
        return totalWorkload;
    }
}
