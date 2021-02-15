package opencampus.student.repository;

import opencampus.student.repository.entity.Language;
import opencampus.student.repository.entity.Region;
import opencampus.student.repository.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student findByCedula(String cedula);
    public List<Student> findByLastName(String lastName);
    public List<Student> findByRegion(Region region);
    public List<Student> findByLanguage(Language language);
}
