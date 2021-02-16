package opencampus.servicecourse;

import opencampus.servicecourse.repository.CourseRepository;
import opencampus.servicecourse.repository.entity.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void whenFindByAll(){
        Course course01 = new Course();
        course01.setName("Youtube");
        course01.setCode("00123");
        course01.setInstitutionName("UTPL");
        course01.setDate(new Date());
        course01.setHours(5);
        course01.setCost(15);

        courseRepository.save(course01);

        List<Course> founds = courseRepository.findAll();
        Assertions.assertThat(founds.size()).isEqualTo(5);
    }
}
