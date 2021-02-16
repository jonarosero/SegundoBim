package opencampus.servicecertificate.model;

import lombok.Data;

@Data
public class Student {
    private Long id;
    private String cedula;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private int regionId;
    private String description;
    private int languageId;
}
