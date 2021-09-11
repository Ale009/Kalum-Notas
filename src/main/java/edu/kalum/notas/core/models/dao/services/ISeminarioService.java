package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.Modulo;
import edu.kalum.notas.core.models.entities.Seminario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISeminarioService {

    public List<Seminario> findAll();
    public Page<Seminario> findAll(Pageable pageable);
    public Seminario save(Seminario seminario);
    public Seminario findById(String id);
    public void delete(Seminario seminario);
    public void deleteById(String id);
}
