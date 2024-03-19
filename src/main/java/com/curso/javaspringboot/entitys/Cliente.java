package com.curso.javaspringboot.entitys;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="Clientes")
public class Cliente  implements Serializable{
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nombre;
    @NonNull
    private String apellido;
    @NonNull
    private String email;
    @NonNull
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    //se crea la fecha de forma automatica
    @PrePersist
    public void prePersist(){
        fecha = new Date();
    }

}
