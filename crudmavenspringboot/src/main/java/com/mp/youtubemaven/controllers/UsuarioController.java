package com.mp.youtubemaven.controllers;

import com.mp.youtubemaven.dao.UsuarioDao;
import com.mp.youtubemaven.models.Usuario;
import com.mp.youtubemaven.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;


@RestController
public class UsuarioController {

    //Inyeccion de dependencias
    @Autowired    //UsuarioDao lo cargue sin hacer nada más
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Lucas");
        usuario.setApellido("Herrera");
        usuario.setEmail("lherrera@gmail.com");
        usuario.setTelefono("60356641");
        usuario.setPassword("admin");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return null; }

        return usuarioDao.getusuarios();
    }

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);

        return usuarioId != null;

    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash= argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "usuario123")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Herrera");
        usuario.setEmail("lherrera@gmail.com");
        usuario.setTelefono("60356641");
        usuario.setPassword("admin");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token,
                         @PathVariable Long id){
        if(!validarToken(token)){return; }
        usuarioDao.eliminar(id);
    }


/*
    @RequestMapping(value = "usuario789")
    public Usuario buscar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Herrera");
        usuario.setEmail("lherrera@gmail.com");
        usuario.setTelefono("60356641");
        usuario.setPassword("admin");
        return usuario;
    }*/
}
