package opencampus.servicecourse.repository;

import opencampus.servicecourse.repository.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    public Course findByCode(String code);
    public Course findByName(String name);
    public List<Course> findByEdition(String edition);
}
