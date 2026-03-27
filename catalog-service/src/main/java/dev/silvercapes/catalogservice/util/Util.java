package dev.silvercapes.catalogservice.util;

import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class Util {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
