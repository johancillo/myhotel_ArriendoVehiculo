package com.myhotel.springboot.app.arriendos.models.dto;

import lombok.Data;

@Data
public class AutomovilDTO {

	private String patente;
	private String vehiculo;
	private String marca;
	private String modelo;
	private String tipo;
	private Integer anio;
	private Integer kilometraje;
	private Integer cilindrada;
	private Integer mantenciones;
	private Integer numPuertas;
	private Integer capacidadPasajeros;
	private Integer capacidadMaleteros;
}
