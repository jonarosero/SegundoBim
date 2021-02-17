package opencampus.servicecertificate.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import opencampus.servicecertificate.model.Course;
import opencampus.servicecertificate.model.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_certificates")
public class Certificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;
    @Column(name = "finish_at")
    private Date date;
    private String description;

    private String achievement;

    @Transient
    private Course course;

    @Transient
    private Student student;

    private String state;


}
