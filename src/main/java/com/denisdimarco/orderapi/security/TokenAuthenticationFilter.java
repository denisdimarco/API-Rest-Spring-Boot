package com.denisdimarco.orderapi.security;

import com.denisdimarco.orderapi.entity.User;
import com.denisdimarco.orderapi.exception.NoDataFoundException;
import com.denisdimarco.orderapi.repository.UserRepository;
import com.denisdimarco.orderapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


    try {
        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && userService.validateToken(jwt)) {
            String username = userService.getUsernameFromToken(jwt);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NoDataFoundException("User does not exist"));

            UserPrincipal principal = UserPrincipal.create(user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    } catch (Exception e) {
        log.error("User authentication error");
    }
    filterChain.doFilter(request, response);

    }

    public String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }


}
