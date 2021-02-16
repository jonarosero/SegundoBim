package opencampus.servicecertificate.repository;

import opencampus.servicecertificate.repository.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    public List<Certificate> findByStudentId (Long studentId);
    public List<Certificate> findByCourseId (Long courseId);
    public Certificate findByCode(String code);

}
