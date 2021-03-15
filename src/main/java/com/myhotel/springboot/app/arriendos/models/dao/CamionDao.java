package com.myhotel.springboot.app.arriendos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.myhotel.springboot.app.arriendos.models.entity.CamionEntity;

public interface CamionDao extends CrudRepository<CamionEntity, String> {

}
