package com.deepcode.jiaming.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author winmanboo
 * @date 2023/5/20 21:31
 */
@Configuration
public class JacksonConfig {
  @Value("${jackson.date-format:yyyy-MM-dd HH:mm:ss}")
  private String datePattern;

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
    return jacksonObjectMapperBuilder -> {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
      LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer(formatter);
      LocalDateTimeSerializer serializer = new LocalDateTimeSerializer(formatter);
      jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, serializer);
      jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, deserializer);
    };
  }
}
