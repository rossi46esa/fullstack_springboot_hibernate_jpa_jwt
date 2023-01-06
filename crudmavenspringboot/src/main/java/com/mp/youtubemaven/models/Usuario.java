package com.mp.youtubemaven.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity   //Se indica que va hacer referencia a la base de datos
@Table(name = "usuarios") //La bd puede tener muchas tablas, con esto indicamos la tabla de la que queremos el select
@ToString @EqualsAndHashCode
public class Usuario {

    //Indicamos los getters and setters por medio de la libreria lombok, para ahorrar espacio
    //Indicamos la columna de la bd a la cual hace referencia cada variable
    @Getter     @Setter @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Colocamos esto para que genere el id el solo
    @Id //Indicamos que es la llave primaria
    private long id;
    @Getter     @Setter @Column(name = "nombre")
    private String nombre;
    @Getter     @Setter @Column(name = "apellido")
    private String apellido;
    @Getter     @Setter @Column(name = "email")
    private String email;
    @Getter     @Setter @Column(name = "telefono")
    private String telefono;
    @Getter     @Setter @Column(name = "password")
    private String password;


}
