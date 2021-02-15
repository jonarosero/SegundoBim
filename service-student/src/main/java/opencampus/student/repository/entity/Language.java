package opencampus.student.repository.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_languages")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
