package com.myhotel.springboot.app.arriendos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.springboot.app.arriendos.models.dto.MessageDTO;
import com.myhotel.springboot.app.arriendos.models.dto.VehiculoDTO;
import com.myhotel.springboot.app.arriendos.models.service.VehiculoService;

@RestController
public class ArriendoController {

	@Autowired
	private VehiculoService vehiculoService;

	@GetMapping("/listar")
	public List<VehiculoDTO> listar() {
		return vehiculoService.consultaVehiculos();

	}

	@GetMapping("/buscar/detalle/{patente}")
	public ResponseEntity<?> detalleVehiculo(@PathVariable("patente") String patente) {

		if (vehiculoService.detalleVehiculo(patente).get("patente") != null) {
			return new ResponseEntity<>(vehiculoService.detalleVehiculo(patente), HttpStatus.OK);
		} else {

			return new ResponseEntity<>("No existe vehiculo", HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/insertar")
	public ResponseEntity<MessageDTO> guardarVehiculo(@RequestBody VehiculoDTO vehiculo) {
		MessageDTO msg = vehiculoService.guardarVehiculo(vehiculo);
		if (msg.getStatus().equalsIgnoreCase("OK")) {
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/modificar")
	public ResponseEntity<MessageDTO> modificarVehiculo(@RequestBody VehiculoDTO vehiculo) {
		MessageDTO msg = vehiculoService.modificarVehiculo(vehiculo);
		if (msg.getStatus().equalsIgnoreCase("OK")) {
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{patente}")
	public ResponseEntity<MessageDTO> eliminarVehiculo(@PathVariable("patente") String patente) {
		MessageDTO msg = vehiculoService.eliminarVehiculo(patente);
		if (msg.getStatus().equalsIgnoreCase("OK")) {
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/test/{patente}")
	public boolean test(@PathVariable("patente") String patente) {
		return vehiculoService.isValido(patente, "camion");

	}

}
