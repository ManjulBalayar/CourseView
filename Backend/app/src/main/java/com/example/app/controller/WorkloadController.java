package com.example.app.controller;

import com.example.app.service.WorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WorkloadController {

    private final WorkloadService workloadService;

    @Autowired
    public WorkloadController(WorkloadService workloadService) {
        this.workloadService = workloadService;
    }

    @GetMapping("/workload/user/{userId}")
    public ResponseEntity<Double> getWorkloadForUser(@PathVariable Long userId) {
        double workload = workloadService.Workload(userId);
        return ResponseEntity.ok(workload);
    }
}


