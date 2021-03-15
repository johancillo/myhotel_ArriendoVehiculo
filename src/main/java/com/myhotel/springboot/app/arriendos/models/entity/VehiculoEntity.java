package com.myhotel.springboot.app.arriendos.models.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "vehiculo")
public class VehiculoEntity {

	@Id
	private String patente;
	private String vehiculo;
	private String marca;
	private String modelo;
	private String tipo;
	private Integer anio;
	private Integer kilometraje;
	private Integer cilindrada;
	private Integer mantenciones;
}
