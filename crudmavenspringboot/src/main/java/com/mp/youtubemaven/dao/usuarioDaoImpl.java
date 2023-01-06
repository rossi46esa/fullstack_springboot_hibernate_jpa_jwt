package com.mp.youtubemaven.dao;

import com.mp.youtubemaven.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository //Tendra funcionalidad de acceder al repositorio de la base de datos
@Transactional //Da la funcionalidad de crear consultas a la base de datos
public class usuarioDaoImpl implements UsuarioDao {

    @PersistenceContext
    private EntityManager entityManager; //servira para hacer la conexion con la base de datos
    @Override
    public List<Usuario> getusuarios() {
        String query = "FROM Usuario"; //se coloca el nombre de la clase, No el de la tabla de MySql
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);  //merge sirve para actualizar o crear datos pero más que
                                        //es para actualizar, no crea el id
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
        .getResultList();

        if(lista.isEmpty()){
            return null;
        }

        String passwordHashed = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if ( argon2.verify(passwordHashed, usuario.getPassword())){
            return lista.get(0);
        }
        return null;

        /*if (lista.isEmpty()){
            return false;
        } else {
            return true;
        }
        Para hacer más abreviado el if anterior, podemos hacerlo en una sola linea  */

    }
}
