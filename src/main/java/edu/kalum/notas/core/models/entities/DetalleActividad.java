package edu.kalum.notas.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "detalle_actividad")
@Entity

public class DetalleActividad implements Serializable {
    @Id
    @Column(name="detalle_actividad_id", nullable = false, length = 128, unique = true)
    @NotEmpty(message = "Es necesario asignar un identificador")
    private String detalleActividadId;

    @Column(name="nombre_actividad", nullable = false, length = 128, unique = true)
    @NotEmpty(message = "Es necesario asignar un nombre de actividad")
    private String nombreActividad;

    @Column(name="nota_actividad", nullable = false, length = 11, unique = true)
    @NotEmpty(message = "Es necesario asignar una nota de actividad")
    private int notaActividad;

    @Column(name = "fecha_creacion")
    @NotEmpty(message = "El campo fecha de creación no es válido")
    private Date fechaCreacion;

    @Column(name = "fecha_entrega")
    @NotEmpty(message = "El campo fecha de entrega no es válido")
    private Date fechaEntrega;

    @Column(name = "fecha_postergacion")
    @NotEmpty(message = "El campo fecha de postergación no es válido")
    private Date fechaPostergacion;

    @Column(name="estado", nullable = false, length = 1, unique = true)
    @NotEmpty(message = "Es necesario asignar un estado")
    private String estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seminario_id", referencedColumnName = "seminario_id")
    @NotEmpty(message = "El campo identificador de seminario no es válido")
    private Seminario seminario;


}
