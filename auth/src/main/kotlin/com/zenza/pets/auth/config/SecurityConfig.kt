package com.zenza.pets.auth.config

import com.zenza.pets.auth.filters.JwtTokenFilter
import com.zenza.pets.auth.userdetails.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
@EnableGlobalMethodSecurity(
//    securedEnabled = true,
    prePostEnabled = true
//    jsr250Enabled = true
)
class SecurityConfig(private val jwtTokenFilter: JwtTokenFilter,
                     private val userDetailsServiceImpl: UserDetailsServiceImpl,
                     private val bCryptPasswordEncoder: BCryptPasswordEncoder
): WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder)
    }

    override fun configure(http: HttpSecurity?) {
        http!!
                .headers().cacheControl().disable()
                .and().cors()
                .and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint { _: HttpServletRequest?, response: HttpServletResponse, ex: AuthenticationException ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
                }
                .and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/auth/**", "/petimg/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000")
        configuration.allowedMethods = listOf("GET", "POST")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}