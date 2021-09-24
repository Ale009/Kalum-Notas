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
@Table(name = "usuario_app")
@Entity
public class Usuario  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password", nullable = false, length = 128)
    private String password;

    @Column(name="enabled", nullable = false)
    private Boolean enabled;

    @Column(name="nombres", nullable = false, length = 128)
    private String nombres;

    @Column(name="apellidos", nullable = false, length = 128)
    private String apellidos;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="telefono", nullable = false, length = 128)
    private String telefono;

    @Column(name="direccion", nullable = false, length = 128)
    private String direccion;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"),
              uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","role_id"})})
            
    private List<Role> roles;
}
