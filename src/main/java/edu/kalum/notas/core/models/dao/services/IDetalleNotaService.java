package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.Clase;
import edu.kalum.notas.core.models.entities.DetalleNota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDetalleNotaService {

    public List<DetalleNota> findAll();
    public Page<DetalleNota> findAll(Pageable pageable);
    public DetalleNota save(DetalleNota detalleNota);
    public DetalleNota findById(String id);
    public void delete(DetalleNota detalleNota);
    public void deleteById(String id);
}
