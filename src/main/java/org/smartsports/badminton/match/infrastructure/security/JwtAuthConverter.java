package org.smartsports.badminton.match.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserDetailsService userDetailsService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());
        return new JwtAuthenticationToken(jwt, userDetails.getAuthorities());
    }
}