package ru.itis.rest.services;

import ru.itis.rest.dto.CourseDto;
import ru.itis.rest.dto.TeacherDto;
import ru.itis.rest.models.Course;

import java.util.List;

/**
 * 24.03.2021
 * 04. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface CoursesService {
    List<Course> getAllCourses();

    Course addCourse(CourseDto course);

    Course addTeacherIntoCourse(Long courseId, TeacherDto teacher);
}
