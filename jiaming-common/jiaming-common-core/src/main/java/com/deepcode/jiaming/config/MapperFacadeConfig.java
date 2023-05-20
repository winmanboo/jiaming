package com.deepcode.jiaming.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author winmanboo
 * @date 2023/5/20 21:31
 */
@Configuration
public class MapperFacadeConfig {
  @Bean
  public MapperFacade mapperFacade() {
    return new DefaultMapperFactory.Builder().build().getMapperFacade();
  }
}
