package ru.itis.rest.dto;

import lombok.Data;

import java.util.List;

/**
 * 24.03.2021
 * 04. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Data
public class CourseDto {
    private String title;
    private List<String> teachers;
}
