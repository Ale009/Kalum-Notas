package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "clase")
public class Clase implements Serializable {
    @Id
    @Column(name="clase_id", nullable = false, length = 128, unique = true)
    @NotNull
    private String claseId;

    @Column(name = "ciclo")
    private int ciclo;

    @Column(name = "cupo_maximo")
    private int cupoMaximo;

    @Column(name = "cupo_minimo")
    private int cupoMinimo;

    @Column(name = "descripcion")
    private String descripcion;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "clase", fetch = FetchType.EAGER)
    private List<AsignacionAlumno> asignaciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_carrera", referencedColumnName = "codigo_carrera")
    @NotNull(message = "El campo código de carrera no es válido")
    private CarreraTecnica carreraTecnica;

}
