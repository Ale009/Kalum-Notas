package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUsuarioDao extends JpaRepository<Usuario,Long> {

    public Usuario findByUsername(String username);

    @Query("select u from Usuario u where u.apellidos like %?1% ")
    public List<Usuario> findLikeApellidos(String apellidos);
}
