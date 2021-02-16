package opencampus.servicecourse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import opencampus.servicecourse.repository.entity.Course;
import opencampus.servicecourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseRest {

    @Autowired
    CourseService courseService;

    //-------------------------TODOS LOS CURSOS-------------------------
    @GetMapping
    public ResponseEntity<List<Course>> listAllCourses (@RequestParam(name = "edition", required = false) String edition){
        List<Course> courses = new ArrayList<>();
        if (null == edition){
            courses = courseService.findCourseAll();
            if (courses.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }if (edition != null){
            courses = courseService.findCoursesByEdition(edition);
            if (null == courses){
                log.error("Cursos de la {} no encontrados", edition);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(courses);
    }

    //-----------------------ENCONTRAR CURSOS------------------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable("id") Long id){
        log.info("Buscando curso con id {}", id);
        Course course = courseService.getCourse(id);
        if (null == course){
            log.error("Course con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(course);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Course> getCourseBy(@RequestParam(name = "code", required = false) String code,
                                              @RequestParam(name = "name", required = false) String name){
        log.info("Buscando curso especcífico");
        Course course = new Course();
        if (code != null){
            course = courseService.getCourseByCode(code);
            if (null == course){
                log.error("Curso con código {} no encontrado", code);
                return ResponseEntity.notFound().build();
            }
        }if (name != null){
            course = courseService.getCourseByName(name);
            if (null == course){
                log.error("Curso de nombre {} no encontrado", name);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(course);
    }

    //---------------------------CREAR CURSO-----------------------------------
    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course, BindingResult result){
        log.info("Creando curso: {}", course);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Course courseDB = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
    }

    //---------------------------ACTUALIZAR CURSO-------------------------------
    @PutMapping(value = "/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Long id, @RequestBody Course course){
        log.info("Actualizando curso con id {}",id);

        Course currentCourse = courseService.getCourse(id);

        if (null == currentCourse){
            log.error("No se encuentra el curso {}", id);
            return ResponseEntity.notFound().build();
        }
        course.setId(id);
        currentCourse = courseService.updateCourse(course);
        return ResponseEntity.ok(currentCourse);
    }

    //----------------------------ELIMINAR CURSO-------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("id") Long id){
        log.info("Eliminando curso con id {}", id);

        Course course = courseService.getCourse(id);
        if (null == course){
            log.error("No se puede eliminar, curso con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        course = courseService.deleteCourse(course);
        return ResponseEntity.ok(course);
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
