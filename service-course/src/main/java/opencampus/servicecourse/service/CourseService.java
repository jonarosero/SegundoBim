package opencampus.servicecourse.service;

import opencampus.servicecourse.repository.entity.Course;

import java.util.List;

public interface CourseService {
    public List<Course> findCourseAll();
    public List<Course> findCoursesByEdition(String edition);

    public Course createCourse (Course course);
    public Course updateCourse (Course course);
    public Course deleteCourse (Course course);
    public Course getCourse (Long id);
    public Course getCourseByCode (String code);
    public Course getCourseByName (String name);

}
