package com.aguang.jinjuback.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:spring/application-redis.xml"})
public class ConfigClass {

}