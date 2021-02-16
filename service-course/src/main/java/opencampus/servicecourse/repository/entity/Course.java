package opencampus.servicecourse.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_courses")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El código no puede estar vacío")
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @NotEmpty(message = "El nombre del curso no puede estar vacío")
    @Column(name = "course_name")
    private String name;

    @NotEmpty(message = "El nombre de la institución no puede estar vacío")
    @Column(name = "institution_name")
    private String institutionName;

    private String description;

    @NotEmpty(message = "La cantidad de horas no pueden estar vacias")
    @Positive(message = "La cantidad de horas deben ser mayor que cero")
    private Double hours;

    @Column(name = "begin_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String teacher;

    private String edition;

    @NotEmpty(message = "El costo del curso no puede estar vacío")
    @Positive(message = "La cantidad de horas deben ser mayor que cero")
    private Double cost;

    private String status;

}
