package com.mp.youtubemaven.dao;

import com.mp.youtubemaven.models.Usuario;

import java.util.List;

public interface UsuarioDao {
    List<Usuario> getusuarios();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}
