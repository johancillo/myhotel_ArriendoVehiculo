package com.myhotel.springboot.app.arriendos.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhotel.springboot.app.arriendos.config.MapperUtils;
import com.myhotel.springboot.app.arriendos.models.dao.AutomovilDao;
import com.myhotel.springboot.app.arriendos.models.dao.CamionDao;
import com.myhotel.springboot.app.arriendos.models.dao.VehiculoDao;
import com.myhotel.springboot.app.arriendos.models.dto.MessageDTO;
import com.myhotel.springboot.app.arriendos.models.dto.VehiculoDTO;
import com.myhotel.springboot.app.arriendos.models.entity.AutomovilEntity;
import com.myhotel.springboot.app.arriendos.models.entity.CamionEntity;
import com.myhotel.springboot.app.arriendos.models.entity.VehiculoEntity;
import com.myhotel.springboot.app.arriendos.models.service.VehiculoService;

@Service
public class VehiculoServicesImpl implements VehiculoService {

	final Logger logger = LoggerFactory.getLogger(VehiculoServicesImpl.class);
	@Autowired
	private AutomovilDao automovilDao;
	@Autowired
	private CamionDao camionDao;
	@Autowired
	private VehiculoDao vehiculoDao;

	private static final String AUTOMOVIL = "AUTOMOVIL";
	private static final String CAMION = "CAMION";

	@Override
	public MessageDTO eliminarVehiculo(String patente) {
		MessageDTO msg = new MessageDTO();
		Optional<VehiculoEntity> req = vehiculoDao.findById(patente);
		if (req.isPresent()) {
			vehiculoDao.deleteById(patente);
			switch (req.get().getVehiculo().toUpperCase()) {
			case AUTOMOVIL:
				automovilDao.deleteById(patente);
				break;
			case CAMION:
				camionDao.deleteById(patente);
				break;

			default:
				logger.error("Validar tipo de vehiculo");
			}

			msg.setStatus("OK");
			msg.setMessage("Vehiculo eliminado");

		} else {
			msg.setStatus("NOK");
			msg.setMessage(String.format("No existe vehiculo con patente: %s", patente));
		}
		return msg;

	}

	@Override
	public MessageDTO modificarVehiculo(VehiculoDTO vehiculo) {
		MessageDTO msg = validaIntegridadInput(vehiculo);
		if (msg.getStatus().equalsIgnoreCase("OK")) {
			if (buscaVehiculo(vehiculo.getPatente())) {
				VehiculoEntity req = mapeoVehiculo(vehiculo);
				vehiculoDao.save(req);
				if (vehiculo.getVehiculo().toUpperCase().equalsIgnoreCase(AUTOMOVIL)) {
					AutomovilEntity reqAuto = mapeoAutoIngreso(vehiculo);
					automovilDao.save(reqAuto);
				} else if (vehiculo.getVehiculo().toUpperCase().equalsIgnoreCase(CAMION)) {
					CamionEntity reqCamion = mapeoCamionIngreso(vehiculo);
					camionDao.save(reqCamion);
				}
			} else {
				msg.setStatus("NOK");
				msg.setMessage(String.format("No se encuentra vehiculo con patente : %s", vehiculo.getPatente()));
			}
		}
		return msg;
	}

	private MessageDTO validaIntegridadInput(VehiculoDTO vehiculo) {
		MessageDTO msg = new MessageDTO();
		msg.setStatus("OK");
		if (vehiculo.getPatente().equalsIgnoreCase("") || vehiculo.getPatente().equalsIgnoreCase(null)
				|| vehiculo.getTipo().equalsIgnoreCase("") || vehiculo.getTipo().equalsIgnoreCase(null)
				|| vehiculo.getAnio() == null || vehiculo.getKilometraje() == null
				|| vehiculo.getCilindrada() == null) {
			msg.setMessage("Faltan campos de ingreso de vehiculo");
			msg.setStatus("NOK");

		} else {

			switch (vehiculo.getVehiculo().toUpperCase()) {
			case CAMION:
				if (vehiculo.getCantidadEjes() == null || vehiculo.getCapacidadToneladas() == null) {
					msg.setMessage(
							"Faltan campos de ingreso de Camion. Requeridos:" + " Capacidad Toneladas - Cantidad Ejes");
					msg.setStatus("NOK");
				}
				break;
			case AUTOMOVIL:
				if (vehiculo.getCapacidadMaleteros() == null || vehiculo.getCapacidadPasajeros() == null
						|| vehiculo.getNumPuertas() == null) {
					msg.setMessage("Faltan campos de ingreso de Automovil. Requeridos: Capacidad Maleteros - "
							+ "Capacidad pasajeros - Numero de puertas");
					msg.setStatus("NOK");
				}
				break;
			default:
				msg.setMessage("El vehiculo debe ser Camion o Automovil");
				msg.setStatus("NOK");
				break;
			}
		}
		return msg;
	}

	private CamionEntity mapeoCamionIngreso(VehiculoDTO vehiculo) {
		CamionEntity reqCamion = new CamionEntity();
		reqCamion.setEjes(vehiculo.getCantidadEjes());
		reqCamion.setPatente(vehiculo.getPatente());
		reqCamion.setTipo(vehiculo.getTipo());
		reqCamion.setToneladas(vehiculo.getCapacidadToneladas());
		return reqCamion;
	}

	private AutomovilEntity mapeoAutoIngreso(VehiculoDTO vehiculo) {
		AutomovilEntity reqAuto = new AutomovilEntity();
		reqAuto.setTipo(vehiculo.getTipo());
		reqAuto.setPatente(vehiculo.getPatente());
		reqAuto.setNumPuertas(vehiculo.getNumPuertas());
		reqAuto.setCapacidadPasajeros(vehiculo.getCapacidadPasajeros());
		reqAuto.setCapacidadMaleteros(vehiculo.getCapacidadMaleteros());
		return reqAuto;
	}

	@Override
	public boolean isValido(String patente, String tipoVehiculo) {
		boolean valido = false;
		VehiculoEntity req = vehiculoDao.findById(patente).orElse(null);
		CamionEntity reqCamion = new CamionEntity();
		AutomovilEntity reqAuto = new AutomovilEntity();

		if (tipoVehiculo.toUpperCase().equalsIgnoreCase(AUTOMOVIL)) {
			reqAuto = automovilDao.findById(patente).orElse(null);
		} else {
			reqCamion = camionDao.findById(patente).orElse(null);
		}
		if (req == null && reqAuto == null || req == null && reqCamion == null) {
			valido = true;
		}
		return valido;
	}

	private VehiculoEntity mapeoVehiculo(VehiculoDTO vehiculo) {

		VehiculoEntity req = new VehiculoEntity();
		req.setAnio(vehiculo.getAnio());
		req.setCilindrada(vehiculo.getCilindrada());
		req.setKilometraje(vehiculo.getKilometraje());
		req.setMantenciones(vehiculo.getMantenciones());
		req.setMarca(vehiculo.getMarca());
		req.setModelo(vehiculo.getModelo());
		req.setPatente(vehiculo.getPatente());
		req.setTipo(vehiculo.getTipo());
		req.setVehiculo(vehiculo.getVehiculo());

		return req;
	}

	@Override
	public List<VehiculoDTO> consultaVehiculos() {
		List<VehiculoDTO> resp = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<VehiculoDTO> req = (List<VehiculoDTO>) MapperUtils.mapAsList(vehiculoDao.findAll(),
				new TypeToken<List<VehiculoDTO>>() {
				}.getType());
		resp.addAll(req);
		return resp;
	}

	@Override
	public HashMap<String, String> detalleVehiculo(String patente) {

		HashMap<String, String> response = new HashMap<>();
		try {
			VehiculoEntity req = vehiculoDao.findById(patente).orElseGet(null);
			if (req != null) {
				response = mapeoAutoConsulta(req);
			} else if (req == null) {
				return null;
			}
		} catch (Exception ex) {
			logger.error(String.format("Error-> Error en proceso de mapoeo de automovil: %s", ex));
		}
		return response;
	}

	private HashMap<String, String> mapeoAutoConsulta(VehiculoEntity vehiculoEntity) {
		AutomovilEntity reqAuto = new AutomovilEntity();
		CamionEntity reqCamion = new CamionEntity();

		HashMap<String, String> vehiculo = new HashMap<>();
		if (vehiculoEntity.getVehiculo().equalsIgnoreCase(AUTOMOVIL)) {
			reqAuto = automovilDao.findById(vehiculoEntity.getPatente()).orElse(null);
		} else {
			reqCamion = camionDao.findById(vehiculoEntity.getPatente()).orElse(null);
		}

		if (reqAuto != null) {
			vehiculo.put("patente", vehiculoEntity.getPatente());
			vehiculo.put("vehiculo", vehiculoEntity.getVehiculo());
			vehiculo.put("marca", vehiculoEntity.getMarca());
			vehiculo.put("modelo", vehiculoEntity.getModelo());
			vehiculo.put("tipo", vehiculoEntity.getTipo());
			vehiculo.put("anio", vehiculoEntity.getAnio().toString());
			vehiculo.put("kilometraje", vehiculoEntity.getKilometraje().toString());
			vehiculo.put("cilindrada", vehiculoEntity.getCilindrada().toString());
			vehiculo.put("mantenciones", vehiculoEntity.getMantenciones().toString());
			if (vehiculoEntity.getVehiculo().equalsIgnoreCase(AUTOMOVIL)) {
				vehiculo.put("numPuertas", reqAuto.getNumPuertas().toString());
				vehiculo.put("capacidadPasajeros", reqAuto.getCapacidadPasajeros().toString());
				vehiculo.put("capacidadMaleteros", reqAuto.getCapacidadMaleteros().toString());
			} else {
				vehiculo.put("capacidad_toneladas", reqCamion.getToneladas().toString());
				vehiculo.put("cantidad_ejes", reqCamion.getEjes().toString());
			}

			return vehiculo;
		} else {
			logger.warn("Info-> No se encuentran registros del vehiculo");
			return null;
		}

	}

	@Override
	public MessageDTO guardarVehiculo(VehiculoDTO vehiculo) {

		MessageDTO msg = validaIntegridadInput(vehiculo);
		if (!msg.getStatus().equalsIgnoreCase("NOK")) {
			if (vehiculo.getPatente() != null && isValido(vehiculo.getPatente(), vehiculo.getVehiculo())) {
				VehiculoEntity req = mapeoVehiculo(vehiculo);
				vehiculoDao.save(req);
				if (vehiculo.getVehiculo().toUpperCase().equalsIgnoreCase(AUTOMOVIL)) {
					AutomovilEntity reqAuto = mapeoAutoIngreso(vehiculo);
					automovilDao.save(reqAuto);
				} else if (vehiculo.getVehiculo().toUpperCase().equalsIgnoreCase(CAMION)) {
					CamionEntity reqCamion = mapeoCamionIngreso(vehiculo);
					camionDao.save(reqCamion);
				}
				msg.setStatus("OK");
				msg.setMessage(String.format("Vehiculo con patente [%s] guardado", vehiculo.getPatente()));

				return msg;
			} else {
				msg.setStatus("NOK");
				msg.setMessage(String.format("Vehiculo con patente [%s] ya existe", vehiculo.getPatente()));
				return msg;
			}
		} else {
			return msg;
		}

	}

	private boolean buscaVehiculo(String patente) {
		Optional<VehiculoEntity> req = vehiculoDao.findById(patente);
		if (req.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
}
