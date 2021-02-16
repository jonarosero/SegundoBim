package opencampus.servicecertificate.service;

import lombok.RequiredArgsConstructor;
import opencampus.servicecertificate.client.CourseClient;
import opencampus.servicecertificate.client.StudentClient;
import opencampus.servicecertificate.model.Course;
import opencampus.servicecertificate.model.Student;
import opencampus.servicecertificate.repository.CertificateRepository;
import opencampus.servicecertificate.repository.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService{

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    CourseClient courseClient;

    @Autowired
    StudentClient studentClient;

    @Override
    public List<Certificate> findCertificateAll() {
        return certificateRepository.findAll();
    }

    @Override
    public List<Certificate> findCertificateByCourse(Long courseId) {
        return certificateRepository.findByCourseId(courseId);
    }

    @Override
    public List<Certificate> findCertificateByStudent(Long studentId) {
        return certificateRepository.findByStudentId(studentId);
    }

    @Override
    public Certificate createCertificate(Certificate certificate, Student student, Course course) {
        Certificate certificateDB = getCertificate(certificate.getId());
        if (certificateDB != null){
            return certificateDB;
        }
        certificate.setDate(new Date());
        certificateDB.setAchievement(validateCertificate(course, certificate).getAchievement());
        certificateDB.setCode(certificate.getCode());
        certificateDB.setCode(certificate.getCode());
        certificateDB.setDescription(certificate.getDescription());
        certificateDB.setDate(certificate.getDate());
        certificateDB.setCourseId(course.getId());
        certificateDB.setStudentId(student.getId());
        certificateDB.setState("CREATED");

        return certificateRepository.save(certificateDB);
    }

    @Override
    public Certificate updateCertificate(Certificate certificate) {
        Certificate certificateDB = getCertificate(certificate.getId());
        if (certificateDB == null){
            return null;
        }
        certificateDB.setDescription(certificate.getDescription());
        certificateDB.setCode(certificate.getCode());
        return certificateRepository.save(certificateDB);
    }

    @Override
    public Certificate deleteCertificate(Certificate certificate) {
        Certificate certificateDB = getCertificate(certificate.getId());
        if (certificateDB == null){
            return null;
        }
        certificateDB.setState("DELETED");
        return certificateRepository.save(certificateDB);
    }

    @Override
    public Certificate getCertificate(Long id) {
        return certificateRepository.findById(id).orElse(null);
    }

    @Override
    public Certificate getCourseByCode(String code) {
        return certificateRepository.findByCode(code);
    }

    @Override
    public Certificate validateCertificate(Course course, Certificate certificate) {
        int totalHours = (int)(course.getDate().getTime() - certificate.getDate().getTime());
        if (totalHours >= course.getHours()){
            certificate.setAchievement("Aprobado con Honores");
            return certificate;
        }else if (course.getHours() == totalHours){
            certificate.setAchievement("Aprobado");
            return certificate;
        }
        return certificate;
    }
}
