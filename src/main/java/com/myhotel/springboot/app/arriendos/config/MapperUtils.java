package com.myhotel.springboot.app.arriendos.config;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperUtils {

	/* ************************************************************** */
	/* Dependencies */
	/* ************************************************************** */

	private static ModelMapper modelMapper = new ModelMapper();

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Convert a collection to a list.
	 * 
	 * @param source           : collection source
	 * @param destinationClass : destination entity class in list
	 * @return list of destination class
	 */
	public static List<?> mapAsList(Object source, Type targetListType) {
		if (source == null) {
			return null;
		}
		List<?> lstAddress = modelMapper.map(source, targetListType);
		return lstAddress;
	}

	/**
	 * Map the source into a new object of destination class type.
	 * 
	 * @param source           : source object
	 * @param destinationClass : type of the new object
	 * @return new object
	 */
	public static Object map(Object source, Class<?> destinationClass) {
		if (source == null) {
			return null;
		}
		return modelMapper.map(source, destinationClass);
	}

	public static void addMapper(PropertyMap<?, ?> propertyMap) {
		modelMapper.addMappings(propertyMap);
	}

}
