package com.myhotel.springboot.app.arriendos.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "automovil")
public class AutomovilEntity {

	@Id
	private String patente;
	private String tipo;
	@Column(name = "numero_puertas")
	private Integer numPuertas;
	@Column(name = "capacidad_pasajeros")
	private Integer capacidadPasajeros;
	@Column(name = "capacidad_maleteros")
	private Integer capacidadMaleteros;

}