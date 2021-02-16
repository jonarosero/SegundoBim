package opencampus.servicecertificate.service;

import lombok.RequiredArgsConstructor;
import opencampus.servicecertificate.model.Course;
import opencampus.servicecertificate.model.Student;
import opencampus.servicecertificate.repository.CertificateRepository;
import opencampus.servicecertificate.repository.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService{

    @Autowired
    CertificateRepository certificateRepository;


    @Override
    public List<Certificate> findCertificateAll() {
        return null;
    }

    @Override
    public List<Certificate> findCertificateByCourse(int courseId) {
        return null;
    }

    @Override
    public List<Certificate> findCertificateByStudent(int studentId) {
        return null;
    }

    @Override
    public Certificate createCertificate(Certificate certificate, Student student, Course course) {
        return null;
    }

    @Override
    public Certificate updateCertificate(Certificate certificate, Student student, Course course) {
        return null;
    }

    @Override
    public Certificate deleteCertificate(Certificate certificate) {
        return null;
    }

    @Override
    public Certificate getCertificate(Long id) {
        return null;
    }

    @Override
    public Certificate getCourseByCode(String code) {
        return null;
    }

    @Override
    public Certificate validateCertificate(Course course, Certificate certificate) {
        return null;
    }
}
