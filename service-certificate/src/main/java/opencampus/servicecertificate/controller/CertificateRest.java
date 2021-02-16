package opencampus.servicecertificate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import opencampus.servicecertificate.repository.entity.Certificate;
import opencampus.servicecertificate.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/certificates")
public class CertificateRest {
    @Autowired
    CertificateService certificateService;

    //--------------------------TODOS LOS CERTIFICADOS-------------------------------------
    @GetMapping
    public ResponseEntity<List<Certificate>> listAllCertificates (@RequestParam(name = "course", required = false) Long courseID,
                                                                  @RequestParam (name = "student" , required = false) Long studentID){
        List<Certificate> certificates = new ArrayList<>();
        if (studentID == null || courseID == null) {
            certificates = certificateService.findCertificateAll();
            if (certificates.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }if (courseID != null){
            certificates = certificateService.findCertificateByCourse(courseID);
            if (null == certificates){
                log.error("Certificados del curso {} no encontrado", courseID);
                return ResponseEntity.notFound().build();
            }
        }if (studentID != null){
            certificates = certificateService.findCertificateByStudent(studentID);
            if (null == certificates){
                log.error("Certificados del estudiante {} no encontrado", studentID);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(certificates);

    }

    //--------------------ECONTRAR CERTIFICADO---------------------------------
    @GetMapping(value = "/{id}")
    public  ResponseEntity<Certificate> getCertificate(@PathVariable("id") Long id){
        log.info("Buscando certificado con id {}", id);
        Certificate certificate = certificateService.getCertificate(id);
        if (null == certificate){
            log.error("Certificado con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificate);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Certificate> getCertificateBy(@RequestParam(name = "code", required = false)String code){
        log.info("Buscando certificado específico");
        Certificate certificate = new Certificate();
        if (code != null){
            certificate = certificateService.getCourseByCode(code);
            if (null == certificate){
                log.error("Certificado con código {} no encontrado", code);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(certificate);
    }

    //--------------------CREAR CERTIFICADO---------------------------------------------------
    @PostMapping
    public ResponseEntity<Certificate> createCertificate(@Valid @RequestBody Certificate certificate, BindingResult result){
        log.info("Creando certificado {}", certificate);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Certificate certificateDB = certificateService.createCertificate(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(certificateDB);
    }

    //--------------------ACTUALIZAR CERTIFICADO-------------------------------------------------
    @PutMapping(value = "/{id}")
    public ResponseEntity<Certificate> updateCertificate(@PathVariable("id") Long id, @RequestBody Certificate certificate){
        log.info("Actualizando curso con id {}",id);

        Certificate currentCertificate = certificateService.getCertificate(id);

        if (null == currentCertificate){
            log.error("No se encuentra el certificado {}", id);
            return ResponseEntity.notFound().build();
        }
        certificate.setId(id);
        currentCertificate = certificateService.updateCertificate(certificate);
        return ResponseEntity.ok(currentCertificate);
    }

    //--------------------ELIMINAR CERTIFICADO--------------------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Certificate> deleteCertificate(@PathVariable("id") Long id){
        log.info("Eliminando certificado con id  {}", id);

        Certificate certificate = certificateService.getCertificate(id);
        if (null == certificate){
            log.error("No se puede eliminar, curso con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        certificate = certificateService.deleteCertificate(certificate);
        return ResponseEntity.ok(certificate);
    }

    //----------------------ERROR----------------------------------------------------------------
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
