package edu.kalum.notas.core.controller;

import edu.kalum.notas.core.models.dao.services.IModuloService;
import edu.kalum.notas.core.models.entities.Modulo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kalum-notas/v1")
public class ModuloController {
    //para rastrear el paquete de petición
    private Logger logger = LoggerFactory.getLogger(ModuloController.class);

    //Cuando se necesita se llama para crear la instacia
    @Autowired
    private IModuloService moduloService;

    @GetMapping("/modulos")
    public ResponseEntity<?> listarModulos(){
        //Registra todas las posibles respuestas de la API
        Map<String, Object> response = new HashMap<>();
        //va imprimiendo los mensajes de debug, siempre y cuando sea en desarrollo
        logger.debug("Iniciando el proceso de la consulta de los módulos en la base de datos");
        try{
            logger.debug("Iniciando la consulta a la base de datos");
            List<Modulo> listaModulos = moduloService.findAll();

            if(listaModulos == null || listaModulos.size() == 0){
                logger.warn("No existen registros en la tabla modulo");
                response.put("Mensaje", "No existen registros en la tabla modulo");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else {
                logger.info("Obteniendo listado de la información de módulos");
                return new ResponseEntity<List<Modulo>>(listaModulos, HttpStatus.OK);
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
