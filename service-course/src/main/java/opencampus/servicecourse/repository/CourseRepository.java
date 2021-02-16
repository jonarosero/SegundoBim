package opencampus.servicecourse.repository;

import opencampus.servicecourse.repository.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    public Course findByCode(String code);
    public Course findByName(String name);
    public List<Course> findByEdition(String edition);
}
