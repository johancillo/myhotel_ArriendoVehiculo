package com.myhotel.springboot.app.arriendos.models.dto;

import lombok.Data;

@Data
public class CamionDTO {

	private String patente;
	private String vehiculo;
	private String marca;
	private String modelo;
	private Integer anio;
	private Integer kilometraje;
	private Integer cilindrada;
	private Integer mantenciones;
	private String tipo;
	private Integer toneladas;
	private Integer ejes;
}
