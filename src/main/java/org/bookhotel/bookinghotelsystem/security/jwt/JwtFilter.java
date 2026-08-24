package org.bookhotel.bookinghotelsystem.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bookhotel.bookinghotelsystem.exception.JwtVerifyException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private final JwtToken jwtToken;

    public JwtFilter(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        doBefore(request, response);
        filterChain.doFilter(request, response);
    }

    public void doBefore(HttpServletRequest request, HttpServletResponse response) {
        var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) return;
        var token = header.substring("bearer".length()).trim();
        try {
            var verified = jwtToken.tokenVerifier(token);

            List<String> authorities = verified.getClaim("authorities").asList(String.class);

            var simpleGrantedAuthorities = authorities.stream()
                    .map(a -> new SimpleGrantedAuthority(a))
                    .toList();

            var auth = new UsernamePasswordAuthenticationToken(
                    verified.getSubject(),
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (JWTVerificationException e) {
            throw new JwtVerifyException(e.getMessage());
        }
    }
}
