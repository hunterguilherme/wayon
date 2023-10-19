package com.wayon.transferencia.core.modelmapper;

import com.wayon.transferencia.domain.model.Transferencia;
import com.wayon.transferencia.domain.model.dto.TransferenciaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.ui.ModelMap;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Transferencia.class, TransferenciaDTO.class);
        return modelMapper;
    }
}