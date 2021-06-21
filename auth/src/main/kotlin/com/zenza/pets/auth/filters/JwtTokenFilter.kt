package com.zenza.pets.auth.filters

import com.zenza.pets.auth.userdetails.UserDetailsServiceImpl
import com.zenza.pets.auth.utils.JwtTokenUtil
import com.zenza.pets.store.repository.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.logging.Level
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (header == null || header == "" || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        // get jwt token and validate
        val token = header.split(" ")[1].trim()

        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response)
            return
        }

        // get user identity and set it to spring security
        val username = jwtTokenUtil.getUsername(token)

        Logger.getLogger("JWT Filter Username").log(Level.WARNING, "Username: $username")

        val user = userRepository.findByUsername(jwtTokenUtil.getUsername(token))!!

        val userDetails = userDetailsServiceImpl.loadUserByUsername(user.username)

        val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication

        chain.doFilter(request, response)
    }

}