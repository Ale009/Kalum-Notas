package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "modulo")
public class Modulo {
    @Id
    @Column(name="modulo_id", nullable = false, length = 128, unique = true)
    @NotEmpty(message = "Es necesario asignar un identificador de módulo")
    private String moduloId;

    @Column(name="nombre_modulo",nullable = false, length = 128, unique = true)
    @NotEmpty(message = "Es necesario asignar nombre de módulo")
    private String nombreModulo;

    @Column(name="numero_seminarios",nullable = false, length = 11, unique = true)
    @NotEmpty(message = "Es necesario asignar nombre de módulo")
    private int numeroSeminarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_carrera", referencedColumnName = "codigo_carrera")
    @NotNull(message = "El campo código de carrera no es válido")
    private CarreraTecnica carreraTecnica;

}
