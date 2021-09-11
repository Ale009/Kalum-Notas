package edu.kalum.notas.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "asignacion_alumno")
public class AsignacionAlumno{

    @Id
    @Column(name = "asignacion_id")
    private String asignacionId;

    @Column(name = "fecha_asignacion")
    @NotEmpty(message = "El campo fecha no es válido")
    private Date fechaAsignacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carne", referencedColumnName = "carne")
    @NotEmpty(message = "El campo carné no es válido")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clase_id", referencedColumnName = "clase_id")
    @NotEmpty(message = "El campo clase no es válido")
    private Clase clase;
}
