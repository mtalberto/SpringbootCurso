package com.curso.javaspringboot.entitys;

import java.io.Serializable;

import java.time.ZoneId;
import java.time.ZonedDateTime;



import javax.validation.Valid;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Clientes")
public class Cliente implements Serializable {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nombre;
    @NonNull
   
    private String apellido;
    @NonNull
    @Email(message = "Formato de correo electrónico inválido.")
    @NotBlank(message = "El correo electrónico no puede estar vacío") 
    private String email;
    @NonNull
    // @Temporal(TemporalType.DATE)

    private ZonedDateTime fecha;

    @PrePersist
    public void prePersist() {
        fecha = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
    }

}
