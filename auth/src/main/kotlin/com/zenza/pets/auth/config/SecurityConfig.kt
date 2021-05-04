package com.zenza.pets.auth.config

import com.zenza.pets.auth.filters.JwtTokenFilter
import com.zenza.pets.auth.userdetails.UserDetailsImpl
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Service
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.util.logging.Level
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true, // enables Secured annotation
//        jsr250Enabled = true, // enables RolesAllowed annotation
        prePostEnabled = true // enables PreAuthorize, PostAuthorize, PreFilter, PostFilter
)
@Service
class SecurityConfig(
        val userDetailsImpl: UserDetailsImpl,
        val jwtTokenFilter: JwtTokenFilter
): WebSecurityConfigurerAdapter() {

    private final val _tag = this::class.java.name

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsImpl)
    }

    override fun configure(http: HttpSecurity?) {
        http!!
                .cors()
                .and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint {
                    request: HttpServletRequest?, response: HttpServletResponse, ex: AuthenticationException ->
                    Logger.getLogger(_tag).log(Level.SEVERE, "Unauthorized request - {}", ex.message)
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
                }
                .and().authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
//                .and().addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()

        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    }

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder();
    }

}