package opencampus.servicecertificate.client;

import opencampus.servicecertificate.model.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "service-student")
@RequestMapping(value = "/students")
public interface StudentClient {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id);

}
