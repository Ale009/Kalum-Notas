package edu.kalum.notas.core.controller;

import edu.kalum.notas.core.models.dao.services.IAlumnoService;
import edu.kalum.notas.core.models.entities.Alumno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/kalum-notas/v1")
@CrossOrigin(origins = "*")
public class AlumnoController {

    //para rastrear el paquete de petición
    private Logger logger = LoggerFactory.getLogger(AlumnoController.class);

    //Cuando se necesita se llama para crear la instacia
    @Autowired
    private IAlumnoService alumnoService;

    @GetMapping("/alumnos")
    public ResponseEntity<?> listarAlumnos(){
        //Registra todas las posibles respuestas de la API
        Map<String, Object> response = new HashMap<>();
        //va imprimiendo los mensajes de debug, siempre y cuando sea en desarrollo
        logger.debug("Iniciando el proceso de la consulta de los alumnos en la base de datos");
        try{
            logger.debug("Iniciando la consulta a la base de datos");
            List<Alumno> listaAlumnos = alumnoService.findAll();

            if(listaAlumnos == null || listaAlumnos.size() == 0){
                logger.warn("No existen registros en la tabla alumnos");
                response.put("Mensaje", "No existen registros en la tabla alumnos");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else {
                logger.info("Obteniendo listado de la información de alumnos");
                return new ResponseEntity<List<Alumno>>(listaAlumnos, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje", "Error al momento de consultar la información a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @GetMapping("/alumnos/{carne}")
    public ResponseEntity<?> showAlumno(@PathVariable String carne){
        Map<String, Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de los alumnos en la base de datos");

        try{
            logger.debug("iniciando la consulta de alumno por número de carné ".concat(carne));
            Alumno alumno = alumnoService.findByCarne(carne);
            if (alumno == null){
                logger.warn("No existen registros en la tabla alumnos".concat(carne));
                response.put("Mensaje", "No existe registro en la tabla alumno con el carné ".concat(carne));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else {
                logger.info("Obteniendo la información del alumno con el carné ".concat(carne));
                return new ResponseEntity<Alumno>(alumno,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje", "Error al momento de consultar la información a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
