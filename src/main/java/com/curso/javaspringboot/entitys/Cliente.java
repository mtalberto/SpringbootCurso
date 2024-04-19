package com.curso.javaspringboot.entitys;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="Clientes")
public class Cliente  implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    @Size(min=4 , max=20)
    @Column(nullable = false)
    private String nombre;
    
    @NotEmpty
    @Size(min = 4, max=20)
    @Column(nullable = false)
    private String apellido;

    @NotEmpty
    @Email
    @Column(nullable = false,unique = true)
    private String email;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    //se crea la fecha de forma automatica
    @PrePersist
    public void prePersist(){
        fecha = new Date();
    }

}
