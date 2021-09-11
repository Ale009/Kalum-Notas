package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.dao.ICarreraTecnicaDao;
import edu.kalum.notas.core.models.entities.Alumno;
import edu.kalum.notas.core.models.entities.CarreraTecnica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraTecnicaServiceImpl implements ICarreraTecnicaService{

    @Autowired
    private ICarreraTecnicaDao carreraTecnicaDao;

    @Override
    public List<CarreraTecnica> findAll() {
        return this.carreraTecnicaDao.findAll();
    }

    @Override
    public Page<CarreraTecnica> findAll(Pageable pageable) {
        return this.carreraTecnicaDao.findAll(pageable);
    }

    @Override
    public CarreraTecnica save(CarreraTecnica carreraTecnica) {
        return this.carreraTecnicaDao.save(carreraTecnica);
    }

    @Override
    public CarreraTecnica findByCodigoCarrera(String codigoCarrera) {
       return carreraTecnicaDao.findByCodigoCarrera(codigoCarrera);
    }

    @Override
    public void deleteByCodigoCarrera(String codigoCarrera) {
        carreraTecnicaDao.deleteByCodigoCarrera(codigoCarrera);
    }
}
