package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "seminario")
public class Seminario implements Serializable {

    @Id
    @Column(name="seminario_id", nullable = false, length = 128, unique = true)
    @NotEmpty(message = "Es necesario asignar un identificador de seminario")
    private String seminarioId;

    @Column(name="nombre_seminario",nullable = false, length = 128, unique = true)
    @NotEmpty(message = "Es necesario asignar nombre de seminario")
    private String nombreSeminario;

    @Column(name = "fecha_inicio")
    @NotEmpty(message = "El campo fecha inicio no es válido")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @NotEmpty(message = "El campo fecha final no es válido")
    private Date fechaFin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modulo_id", referencedColumnName = "modulo_id")
    @NotEmpty(message = "El campo id no es válido")
    private Modulo moduloId;






}
