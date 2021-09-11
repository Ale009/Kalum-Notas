package edu.kalum.notas.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "carrera_tecnica")
public class CarreraTecnica {

    @Id
    @Column(name = "codigo_carrera")
    private String codigoCarrera;

    @Column(name = "nombre")
    @NotNull(message = "El campo fecha no es válido")
    private String nombre;


}
