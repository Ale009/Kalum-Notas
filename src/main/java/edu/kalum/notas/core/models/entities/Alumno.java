package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
//Acá se crean los getters y setters
@Data
//Anotaciones de JPA
@Table(name = "alumno")
@Entity
public class Alumno  implements Serializable {

    @Id
    @Column(name="carne", nullable = false)
    @NotEmpty(message = "Es necesario asignar un número de carné")
    private String carne;

    @Column(name="no_expediente", nullable = false)
    @NotEmpty(message = "Es necesario asignar un número de expediente")
    private String noExpediente;

    @Column(name="apellidos", nullable = false)
    @NotEmpty(message = "El campo apellidos no puede estar vacío")
    private String apellidos;

    @Column(name="nombres", nullable = false)
    @NotEmpty(message = "El campo nombres no puede estar vacío")
    private String nombres;

    @Column(name="email")
    @Email(message = "Debe agregar un email válido")
    private String email;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER)
    private List<AsignacionAlumno> asignaciones;

}
