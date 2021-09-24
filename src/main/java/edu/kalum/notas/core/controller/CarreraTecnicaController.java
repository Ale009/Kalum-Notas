package edu.kalum.notas.core.controller;
import edu.kalum.notas.core.models.dao.services.IAlumnoService;
import edu.kalum.notas.core.models.dao.services.ICarreraTecnicaService;
import edu.kalum.notas.core.models.entities.Alumno;
import edu.kalum.notas.core.models.entities.AsignacionAlumno;
import edu.kalum.notas.core.models.entities.CarreraTecnica;
import edu.kalum.notas.core.models.entities.Clase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
@RequestMapping(value = "/kalum-notas/v1")
@CrossOrigin(origins = "*")
public class CarreraTecnicaController {

    private Logger logger = LoggerFactory.getLogger(CarreraTecnicaController.class);

    //Cuando se necesita se llama para crear la instacia
    @Autowired
    private ICarreraTecnicaService carreraTecnicaService;

    @Autowired

    @GetMapping("/carrerasTecnicas")
    public ResponseEntity<?> listarCarrerasTecnicas() {
        //Registra todas las posibles respuestas de la API
        Map<String, Object> response = new HashMap<>();
        //va imprimiendo los mensajes de debug, siempre y cuando sea en desarrollo
        logger.debug("Iniciando el proceso de la consulta de las carreras técnicas en la base de datos");
        try {
            logger.debug("Iniciando la consulta a la base de datos");
            List<CarreraTecnica> listarCarrerasTecnicas = carreraTecnicaService.findAll();

            if (listarCarrerasTecnicas == null || listarCarrerasTecnicas.size() == 0) {
                logger.warn("No existen registros en la tabla carrera_tecnica");
                response.put("Mensaje", "No existen registros en la tabla carrera_tecnica");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            } else {
                logger.info("Obteniendo listado de la información de carrera_tecnica");
                return new ResponseEntity<List<CarreraTecnica>>(listarCarrerasTecnicas, HttpStatus.OK);
            }
        } catch (CannotCreateTransactionException e) {
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } catch (DataAccessException e) {
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje", "Error al momento de consultar la información a la base de datos");
            response.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
