package edu.kalum.notas.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "detalle_nota")
@Entity
public class DetalleNota implements Serializable {

    @Id
    @Column(name="detalle_nota_id", nullable = false, length = 128, unique = true)
    @NotEmpty(message = "Es necesario asignar un identificador")
    private String detalleNotaId;

    @Column(name="valor_nota", nullable = false, length = 11, unique = true)
    @NotEmpty(message = "Es necesario asignar un valor de nota")
    private int valorNota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carne", referencedColumnName = "carne")
    @NotEmpty(message = "El campo carné no es válido")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detalle_actividad_id", referencedColumnName = "detalle_actividad_id")
    @NotEmpty(message = "El campo carné no es válido")
    private DetalleActividad detalleActividad;
}

