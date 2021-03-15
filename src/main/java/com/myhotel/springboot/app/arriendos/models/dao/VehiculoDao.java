package com.myhotel.springboot.app.arriendos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.myhotel.springboot.app.arriendos.models.entity.VehiculoEntity;

public interface VehiculoDao extends CrudRepository<VehiculoEntity, String> {

}
