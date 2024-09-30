package com.project.scope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.scope.model.Course;


@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
	
	Course findCourseById(Long id);

}
