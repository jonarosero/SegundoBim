package opencampus.student.service;

import opencampus.student.repository.entity.Language;
import opencampus.student.repository.entity.Region;
import opencampus.student.repository.entity.Student;

import java.util.List;

public interface StudentService {
    public List<Student> findStudentAll();
    public List<Student> findStudentByRegion(Region region);
    public List<Student> findStudentByLanguage(Language language);
    public List<Student> findStudentByLastName(String lastName);

    public Student createStudent (Student student);
    public Student updateStudent (Student student);
    public Student deleteStudent (Student student);
    public Student getStudent(Long id);
    public Student getStudentByCedula (String cedula);

}
