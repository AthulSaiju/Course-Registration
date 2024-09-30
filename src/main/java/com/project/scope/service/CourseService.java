package com.project.scope.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.scope.model.Course;
import com.project.scope.repository.CourseRepo;

@Service
public class CourseService {
    @Autowired
    private CourseRepo courseRepo;

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }
    
    public Course findCourseById(Long id) {
        return courseRepo.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course addCourse(Course course) {
        return courseRepo.save(course);
    }
}
