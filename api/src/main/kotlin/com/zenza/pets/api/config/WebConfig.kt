package com.zenza.pets.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import java.util.concurrent.TimeUnit

@Configuration
class WebConfig: WebMvcConfigurationSupport() {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/petimg/**")
                .addResourceLocations("classpath:/assets/images/pets/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic())
    }
}