package edu.utn.TpFinal.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class InfraestructuraSessionFilter extends OncePerRequestFilter {

    @Autowired
    private static final String iToken = "Antena";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String sessionToken = request.getHeader("Authorization");


        if(iToken.equals(sessionToken)) {
            filterChain.doFilter(request, response);
        }

        else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
