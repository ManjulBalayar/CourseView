package com.example.app.controller;

import com.example.app.miscellaneous.Workload;
import com.example.app.model.Course;
import com.example.app.model.Review;
import com.example.app.model.Schedule;
import com.example.app.model.UserProfile;
import com.example.app.repository.ReviewRepository;
import com.example.app.repository.ScheduleRepository;
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

    @Autowired
    ScheduleRepository scheduleRepository;


    @GetMapping("/workload/byCourseID/{course_id}")
    public Workload getStudent(@PathVariable("course_id") Long course_id) {
        return getCourseWorkload(course_id);
    }

    @GetMapping("/workload/byUserid/{user_id}")
    public Workload getAverageWorkload(@PathVariable("user_id") Long student_id){
        return getSchedulWorkload(student_id);
    }

    public  Workload getSchedulWorkload(Long user_id) {
        Schedule schedule = scheduleRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Course> courses = schedule.getCourses();

        if (courses.isEmpty()) {
            // Handle the case where there are no courses in the schedule
            return null; // or return some default values
        }

        Float totalR = 0F;
        Float totalD = 0F;
        Float totalT = 0F;
        int totalCourses = 0;

        for (Course course : courses) {
            Workload courseWorkload = getCourseWorkload(course.getCourseid());
            System.out.println(courseWorkload);

            if (courseWorkload != null) {
                totalR += courseWorkload.getRating();
                totalD += courseWorkload.getDifficulty();
                totalT += courseWorkload.getTime_commitment();
                totalCourses++;
            }
        }

        if (totalCourses == 0) {
            // Handle the case where there are no valid courses with reviews
            return null; // or return some default values
        }

        Float avgR = totalR / totalCourses;
        Float avgD = totalD / totalCourses;
        Float avgT = totalT / totalCourses;

        return new Workload(avgR, avgD, avgT);
    }


    public Workload getCourseWorkload(Long course_id) {

        List<Review> reviewList = reviewRepository.findByCourseCourseid(course_id);
        Float R = 0F;
        Float D = 0F;
        Float T = 0F;
        Float size = 0F;

        for(Review r: reviewList){
            R += r.getRating();
            D += r.getDifficulty();
            T += r.getTime_commitment();
            size += 1;
        }

        R = R/size;
        D = D/size;
        T = T/size;

        return new Workload(R,D,T);
    }
}
