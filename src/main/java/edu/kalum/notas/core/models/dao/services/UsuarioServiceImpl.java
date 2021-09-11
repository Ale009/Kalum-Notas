package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.dao.IUsuarioDao;
import edu.kalum.notas.core.models.entities.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario usuario = this.usuarioDao.findByUsername(username);

       if(usuario == null){
           throw new UsernameNotFoundException("No existe el usuario ".concat(username).concat(" valide sus credenciales"));
       }
       List<GrantedAuthority> authorities = usuario.getRoles()
               .stream()
               .map(role -> new SimpleGrantedAuthority(role.getNombre()))
               .collect(Collectors.toList());

       return new User(usuario.getUsername(),usuario.getPassword(), usuario.getEnabled(),true,true,true,authorities);
    }
}
