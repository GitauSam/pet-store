package com.zenza.pets.auth.filters

import com.zenza.pets.auth.utils.JwtTokenUtil
import com.zenza.pets.store.repository.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
        val jwtTokenUtil: JwtTokenUtil,
        val userRepository: UserRepository
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (header == "" || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        // get jwt token and validate
        val token = header.split(" ")[1].trim()

        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response)
            return
        }

        val userDetails: UserDetails

        userDetails = userRepository.findByEmail(jwtTokenUtil.getEmail(token)) as UserDetails

        val authentication: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                userDetails, null, if (userDetails == null) listOf<GrantedAuthority>() else userDetails.authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication

        chain.doFilter(request, response)

    }

}