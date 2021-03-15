package com.myhotel.springboot.app.arriendos.models.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "camion")
public class CamionEntity {

	@Id
	private String patente;

	private String tipo;
	private Integer toneladas;
	private Integer ejes;

}
