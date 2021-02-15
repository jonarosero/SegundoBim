package opencampus.student.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import opencampus.student.repository.entity.Language;
import opencampus.student.repository.entity.Region;
import opencampus.student.repository.entity.Student;
import opencampus.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.ws.rs.PUT;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentRest {
    @Autowired
    StudentService studentService;

    // --------------------------TODOS LOS ESTUDIANTES ----------------
    @GetMapping
    public ResponseEntity<List<Student>> listAllStudents(@RequestParam(name = "regionId", required = false) Long regionId,
                                                        @RequestParam(name = "languageId", required = false) Long languageId,
                                                        @RequestParam(name = "lastName", required = false) String lastName){
        List<Student> students = new ArrayList<>();
        if (null == regionId || null == languageId || null == lastName) {
            students = studentService.findStudentAll();
            if (students.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }if (regionId != null){
            Region region = new Region();
            region.setId(regionId);
            students = studentService.findStudentByRegion(region);
            if (null == students){
                log.error("Estudiantes del país {} no encontrados", regionId);
                return ResponseEntity.notFound().build();
            }
        }if (languageId != null){
            Language language = new Language();
            language.setId(languageId);
            students = studentService.findStudentByLanguage(language);
            if (null == students){
                log.error("Estudiantes del lenguaje {} no encontrados", languageId);
                return ResponseEntity.notFound().build();
            }
        }if (lastName != null){
            students = studentService.findStudentByLastName(lastName);
            if (null == students){
                log.error("Estudiantes del apellido {} no encontrados", lastName);
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(students);
    }

    // ---------------------------ENCONTRAR ESTUDIANTE-----------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id){
        log.info("Buscando estudiante con id {}", id);
        Student student = studentService.getStudent(id);
        if (null == student){
            log.error("Estudiante con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping(value="/search")
    public ResponseEntity<Student> getStudentByCedula(@RequestParam(name = "cedula", required = true) String cedula){
        log.info("Buscando estudiante con cédula {}", cedula);
        Student student = studentService.getStudentByCedula(cedula);
        if (null == student){
            log.error("Estudiante con cédula {} no encontrado", cedula);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    //-------------------------CREAR ESTUDIANTE----------------------------------
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student, BindingResult result){
        log.info("Creando Estudiante: {}", student);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Student studentDB = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDB);
    }

    //----------------------ACTUALIZAR ESTUDIANTE-------------------------------
    @PutMapping(value = "/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student){
        log.info("Acualizando estudiante con id {}", id);

        Student currentStudent = studentService.getStudent(id);

        if (null == currentStudent){
            log.error("No se encuentra el estudiante {}", id);
            return ResponseEntity.notFound().build();
        }
        student.setId(id);
        currentStudent=studentService.updateStudent(student);
        return ResponseEntity.ok(currentStudent);
    }

    //-----------------------ELIMINAR ESTUDIANTE-----------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") Long id){
        log.info("Eliminando estudiante con id {}", id);

        Student student = studentService.getStudent(id);
        if (null == student){
            log.error("No se puede eliminar, estudiante con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        student = studentService.deleteStudent(student);
        return ResponseEntity.ok(student);
    }

    //------------------------ERROR-----------------------------------
    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
