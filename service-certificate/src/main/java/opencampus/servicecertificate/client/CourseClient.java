package opencampus.servicecertificate.client;

import opencampus.servicecertificate.model.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "service-course")
@RequestMapping("/courses")
public interface CourseClient {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable("id") Long id);
}
