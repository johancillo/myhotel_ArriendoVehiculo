package com.myhotel.springboot.app.arriendos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.myhotel.springboot.app.arriendos.models.entity.AutomovilEntity;

public interface AutomovilDao extends CrudRepository<AutomovilEntity, String> {

}
