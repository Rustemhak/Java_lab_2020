package ru.itis.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.rest.dto.CourseDto;
import ru.itis.rest.dto.TeacherDto;
import ru.itis.rest.models.Course;
import ru.itis.rest.models.Teacher;
import ru.itis.rest.repositories.CoursesRepository;
import ru.itis.rest.repositories.TeachersRepository;

import java.util.List;

/**
 * 24.03.2021
 * 04. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Service
public class CoursesServiceImpl implements CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private TeachersRepository teachersRepository;

    @Override
    public List<Course> getAllCourses() {
        return coursesRepository.findAll();
    }

    @Override
    public Course addCourse(CourseDto course) {
        return coursesRepository.save(Course.builder()
                .title(course.getTitle())
                .build());
    }

    @Override
    public Course addTeacherIntoCourse(Long courseId, TeacherDto teacher) {
        Course course = coursesRepository.findById(courseId)
                .orElseThrow(IllegalArgumentException::new);
        Teacher teacherForCourse = teachersRepository.findById(teacher.getId())
                .orElseThrow(IllegalArgumentException::new);

        course.getTeachers().add(teacherForCourse);
        coursesRepository.save(course);
        return course;
    }
}
