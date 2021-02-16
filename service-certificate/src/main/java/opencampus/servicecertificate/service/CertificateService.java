package opencampus.servicecertificate.service;

import opencampus.servicecertificate.model.Course;
import opencampus.servicecertificate.model.Student;
import opencampus.servicecertificate.repository.entity.Certificate;

import java.util.List;

public interface CertificateService {

    public List<Certificate> findCertificateAll();
    public List<Certificate> findCertificateByCourse(int courseId);
    public List<Certificate> findCertificateByStudent(int studentId);

    public Certificate createCertificate(Certificate certificate, Student student, Course course);
    public Certificate updateCertificate(Certificate certificate, Student student, Course course);
    public Certificate deleteCertificate(Certificate certificate);
    public Certificate getCertificate(Long id);
    public Certificate getCourseByCode(String code);

    public Certificate validateCertificate(Course course, Certificate certificate);
}
