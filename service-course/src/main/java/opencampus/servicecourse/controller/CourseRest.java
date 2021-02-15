package opencampus.servicecourse.controller;

import lombok.extern.slf4j.Slf4j;
import opencampus.servicecourse.repository.entity.Course;
import opencampus.servicecourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

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
    @GetMapping
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

    //---------------------------ACTUALIZAR CURSO-------------------------------

}
