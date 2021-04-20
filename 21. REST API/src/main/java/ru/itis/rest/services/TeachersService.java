package ru.itis.rest.services;

import ru.itis.rest.dto.TeacherDto;

import java.util.List;

/**
 * 24.03.2021
 * 04. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface TeachersService {
    List<TeacherDto> getAllTeachers();

    TeacherDto addTeacher(TeacherDto teacher);

    TeacherDto updateTeacher(Long teacherId, TeacherDto teacher);

    void deleteTeacher(Long teacherId);
}
