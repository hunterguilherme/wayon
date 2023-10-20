package com.wayon.transferencia.core.modelmapper;

import com.wayon.transferencia.domain.model.FinancialTransfer;
import com.wayon.transferencia.domain.model.dto.FinancialTransfereDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(FinancialTransfer.class, FinancialTransfereDTO.class);
        return modelMapper;
    }
}