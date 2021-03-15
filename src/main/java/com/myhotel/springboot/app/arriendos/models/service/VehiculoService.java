package com.myhotel.springboot.app.arriendos.models.service;

import java.util.HashMap;
import java.util.List;

import com.myhotel.springboot.app.arriendos.models.dto.MessageDTO;
import com.myhotel.springboot.app.arriendos.models.dto.VehiculoDTO;

public interface VehiculoService {

	public List<VehiculoDTO> consultaVehiculos();

	public HashMap<String, String> detalleVehiculo(String patente);

	public boolean isValido(String patente, String tipoVehiculo);

	public MessageDTO guardarVehiculo(VehiculoDTO vehiculo);

	public MessageDTO modificarVehiculo(VehiculoDTO vehiculo);

	public MessageDTO eliminarVehiculo(String patente);

}
