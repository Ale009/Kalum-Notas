package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.CarreraTecnica;
import edu.kalum.notas.core.models.entities.Clase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICarreraTecnicaService {
    public List<CarreraTecnica> findAll();
    public Page<CarreraTecnica> findAll(Pageable pageable);
    public CarreraTecnica save(CarreraTecnica carreraTecnica);
    public CarreraTecnica findByCodigoCarrera(String codigoCarrera);
    public void deleteByCodigoCarrera(String codigoCarrera);
}
