package me.ifmo.lab4webbackend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.ifmo.lab4webbackend.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private UserService userService;

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = extractToken(request);

        if (jwt != null && this.jwtManager.validateToken(jwt)) {
            try {
                String username = this.jwtManager.getUsername(jwt);
                UserDetails userDetails = this.userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception exception) {
                log.error("Cannot set user authentication: {}", exception.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
