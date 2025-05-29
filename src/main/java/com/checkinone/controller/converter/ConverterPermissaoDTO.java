package com.checkinone.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.checkinone.client.dto.PermissaoDTO;

@Component
public class ConverterPermissaoDTO implements Converter<String, PermissaoDTO> {

	@Override
	public PermissaoDTO convert(String id) {
		if (StringUtils.hasText(id)) {
			PermissaoDTO permissaoDTO = new PermissaoDTO();
			permissaoDTO.setId(Long.valueOf(id));
			
			return permissaoDTO;
		}
		
		return null;
	}

}
