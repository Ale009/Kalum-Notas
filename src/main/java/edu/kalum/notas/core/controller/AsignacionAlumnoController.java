package edu.kalum.notas.core.controller;

import edu.kalum.notas.core.models.dao.services.IAlumnoService;
import edu.kalum.notas.core.models.dao.services.IAsignacionAlumnoService;
import edu.kalum.notas.core.models.dao.services.IClaseService;
import edu.kalum.notas.core.models.entities.Alumno;
import edu.kalum.notas.core.models.entities.AsignacionAlumno;
import edu.kalum.notas.core.models.entities.Clase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kalum-notas/v1")
public class AsignacionAlumnoController {
    @Value("${edu.kalum.configuration.page.registros}")
    private Integer registros;

    private Logger logger = LoggerFactory.getLogger(AsignacionAlumnoController.class);

    @Autowired
    private IAsignacionAlumnoService asignacionAlumnoService;

    @Autowired
    private IAlumnoService alumnoService;

    @Autowired
    private IClaseService claseService;

    @GetMapping("/asignaciones/page/{page}")
    public ResponseEntity<?> index(@PathVariable Integer page){
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page,registros);
        try {
            Page<AsignacionAlumno> asignaciones = asignacionAlumnoService.findAll(pageable);

            if (asignaciones == null || asignaciones.getSize() == 0){
                return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<Page<AsignacionAlumno>>(asignaciones,  HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e) {
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (DataAccessException e) {
            logger.error("Error al momento de consultar la informaci??n a la base de datos");
            response.put("Mensaje", "Error al momento de consultar la informaci??n a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @GetMapping("/asignaciones/{id}")
    public ResponseEntity<?> show(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        try {
            AsignacionAlumno asignacion = asignacionAlumnoService.findById(id);
            if(asignacion == null){
                response.put("Mensaje", "No existe la asignaci??n con el id".concat(id));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<AsignacionAlumno>(asignacion, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e) {
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (DataAccessException e) {
            logger.error("Error al momento de consultar la informaci??n a la base de datos");
            response.put("Mensaje", "Error al momento de consultar la informaci??n a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @GetMapping("/asignaciones")
    public ResponseEntity<?> listaAsignaicones() {
        Map<String, Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de las asignaciones en la base de datos");

        try {
            logger.debug("Iniciando el proceso de la consulta a la base de datos");
            List<AsignacionAlumno> listaAsignaciones = asignacionAlumnoService.findAll();
            if (listaAsignaciones == null || listaAsignaciones.size() == 0) {
                logger.warn("No existen registros en la tabla de asignaciones");
                response.put("Mensaje", "No existen registros en la tabla de asignaciones");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            } else {
                logger.info("Obteniendo listado de asignaciones");
                return new ResponseEntity<List<AsignacionAlumno>>(listaAsignaciones, HttpStatus.OK);

            }
        } catch (CannotCreateTransactionException e) {
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (DataAccessException e) {
            logger.error("Error al momento de consultar la informaci??n a la base de datos");
            response.put("Mensaje", "Error al momento de consultar la informaci??n a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
    @PostMapping("/asignaciones")
    public ResponseEntity<?> create(@Valid @RequestBody AsignacionAlumno registro, BindingResult result){
        AsignacionAlumno asignacionAlumno = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errorres = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errorres);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            Alumno alumno = alumnoService.findByCarne(registro.getAlumno().getCarne());

            if(alumno == null){
                response.put("Mensaje", "No existe el alumno con el carn?? ".concat(registro.getAlumno().getCarne()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            Clase clase = claseService.findById(registro.getClase().getClaseId());
            if (clase == null){
                response.put("Mensaje", "No existe la clase con el id ".concat(registro.getClase().getClaseId()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

            }
            registro.setAsignacionId(UUID.randomUUID().toString());
            asignacionAlumno = this.asignacionAlumnoService.save(registro);

        }catch (CannotCreateTransactionException e) {
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (DataAccessException e) {
            logger.error("Error al momento de insertar la informaci??n a la base de datos");
            response.put("Mensaje", "Error al momento de insertar la informaci??n a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","La asignaci??n fue creada con ??xito");
        response.put("Asignaci??n", asignacionAlumno);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/asignaciones/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody AsignacionAlumno update, BindingResult result,@PathVariable String id){
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream().map((error -> error.getDefaultMessage()))
                    .collect(Collectors.toList());
            response.put("Errores", errores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        AsignacionAlumno asignacion = this.asignacionAlumnoService.findById(id);
        if(asignacion == null){
            response.put("Mensaje","No existe la asignaci??n con el id".concat(id));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            Alumno alumno = alumnoService.findByCarne(update.getAlumno().getCarne());

            if(alumno == null){
                response.put("Mensaje", "No existe el alumno con el carn?? ".concat(update.getAlumno().getCarne()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            Clase clase = claseService.findById(update.getClase().getClaseId());
            if (clase == null){
                response.put("Mensaje", "No existe la clase con el id ".concat(update.getClase().getClaseId()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

            }
            asignacion.setFechaAsignacion(update.getFechaAsignacion());
            asignacion.setAlumno(alumno);
            asignacion.setClase(clase);
            this.asignacionAlumnoService.save(asignacion);

        }catch (CannotCreateTransactionException e) {
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (DataAccessException e) {
            logger.error("Error al momento de actualizar la informaci??n a la base de datos");
            response.put("Mensaje", "Error al momento de actualizar la informaci??n a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","La asignaci??n del alumno a la clase se ha realizado correctamente");
        response.put("Asignaci??n",asignacion);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @DeleteMapping("/asignaciones/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        AsignacionAlumno asignacion = asignacionAlumnoService.findById(id);

        try {
            if (asignacion == null) {
                response.put("Mensaje", "No existe ninguna asignaci??n con el id".concat(id));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                asignacionAlumnoService.delete(asignacion);
            }
        } catch(CannotCreateTransactionException e){
                logger.error("Error al momento de conectarse a la base de datos");
                response.put("Mensaje", "Error al momento de conectarse a la base de datos");
                response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch(DataAccessException e){
                logger.error("Error al momento de eliminar la informaci??n a la base de datos");
                response.put("Mensaje", "Error al momento de eliminar la informaci??n a la base de datos");
                response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","La asignaci??n fue eliminada correctamente");
        response.put("Asignaci??n",asignacion);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
