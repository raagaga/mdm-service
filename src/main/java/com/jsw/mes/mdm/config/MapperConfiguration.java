package com.jsw.mes.mdm.config;

import com.jsw.mes.mdm.mapper.PlantMapper;
import com.jsw.mes.mdm.mapper.PlantMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public PlantMapper plantMapper(){
        return new PlantMapperImpl();
    }

}
