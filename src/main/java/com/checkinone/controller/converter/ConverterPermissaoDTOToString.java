package com.checkinone.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.checkinone.client.dto.PermissaoDTO;

@Component
public class ConverterPermissaoDTOToString implements Converter<PermissaoDTO, String> {

	@Override
	public String convert(PermissaoDTO permissaoDTO) {
		if (permissaoDTO != null && permissaoDTO.getId() != null) {
			return permissaoDTO.getId().toString();
		}
		
		return null;
	}

}
