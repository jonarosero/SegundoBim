package opencampus.student.service;

import lombok.extern.slf4j.Slf4j;
import opencampus.student.repository.StudentRepository;
import opencampus.student.repository.entity.Language;
import opencampus.student.repository.entity.Region;
import opencampus.student.repository.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> findStudentAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findStudentByRegion(Region region) {
        return studentRepository.findByRegion(region);
    }

    @Override
    public List<Student> findStudentByLanguage(Language language) {
        return studentRepository.findByLanguage(language);
    }

    @Override
    public List<Student> findStudentByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    @Override
    public Student createStudent(Student student) {
        Student studentDB = studentRepository.findByCedula(student.getCedula());
        if (studentDB != null){
            return studentDB;
        }

        student.setState("CREATED");
        student = studentRepository.save(student);
        return studentDB;
    }

    @Override
    public Student updateStudent(Student student) {
        Student studentDB = getStudent(student.getId());
        if (studentDB == null) {
            return null;
        }
        studentDB.setCedula(student.getCedula());
        studentDB.setFirstName(student.getFirstName());
        studentDB.setLastName(student.getLastName());
        studentDB.setEmail(student.getEmail());
        studentDB.setPhotoUrl(student.getPhotoUrl());
        studentDB.setRegion(student.getRegion());
        studentDB.setLanguage(student.getLanguage());

        return studentRepository.save(studentDB);
    }

    @Override
    public Student deleteStudent(Student student) {
        Student studentDB = getStudent(student.getId());
        if (studentDB == null) {
            return null;
        }
        student.setState("DELETED");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student getStudentByCedula(String cedula) {
        return studentRepository.findByCedula(cedula);
    }
}
