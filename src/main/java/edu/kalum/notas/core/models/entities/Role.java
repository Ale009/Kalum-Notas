package edu.kalum.notas.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "role_app")
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="nombre", nullable = false, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;
}
