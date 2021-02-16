package opencampus.servicecertificate.model;

import lombok.Data;

import java.util.Date;

@Data
public class Course {
    private Long id;
    private String code;
    private String name;
    private String institutionName;
    private String description;
    private int hours;
    private Date date;
    private String teacher;
    private String edition;
    private Double cost;
}
