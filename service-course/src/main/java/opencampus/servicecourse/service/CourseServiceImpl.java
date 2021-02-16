package opencampus.servicecourse.service;

import lombok.RequiredArgsConstructor;
import opencampus.servicecourse.repository.CourseRepository;
import opencampus.servicecourse.repository.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> findCourseAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findCoursesByEdition(String edition) {
        return courseRepository.findByEdition(edition);
    }

    @Override
    public Course createCourse(Course course) {
        Course courseDB = courseRepository.findByCode(course.getCode());
        if (courseDB != null){
            return courseDB;
        }

        course.setStatus("CREATED");
        course.setEdition("Edición 1");
        course.setInstitutionName("UTPL");
        course.setDate(new Date());
        course = courseRepository.save(course);
        return courseDB;
    }

    @Override
    public Course updateCourse(Course course) {
        Course courseDB = getCourse(course.getId());
        if (courseDB == null){
            return null;
        }
        courseDB.setCode(course.getCode());
        courseDB.setName(course.getName());
        courseDB.setDescription(course.getDescription());
        courseDB.setEdition(course.getEdition());
        courseDB.setDate(course.getDate());
        courseDB.setHours(course.getHours());
        courseDB.setCost(course.getCost());
        courseDB.setTeacher(course.getTeacher());
        courseDB.setInstitutionName(course.getInstitutionName());

        return courseRepository.save(courseDB);
    }

    @Override
    public Course deleteCourse(Course course) {
        Course courseDB = getCourse(course.getId());
        if (courseDB == null){
            return null;
        }
        course.setStatus("DELETED");
        return courseRepository.save(course);
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course getCourseByCode(String code) {
        return courseRepository.findByCode(code);
    }

    @Override
    public Course getCourseByName(String name) {
        return courseRepository.findByName(name);
    }
}
