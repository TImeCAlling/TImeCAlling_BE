package TImeCAlling.spring.auth.filter;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.auth.Handler.JwtExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (JwtExceptionHandler | UsernameNotFoundException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletRequest req,
                                 HttpServletResponse res, Throwable ex) throws IOException {
        ApiResponse<Object> apiResponse = ApiResponse.onFailure("COMMON401", HttpStatus.UNAUTHORIZED.name(), ex.getMessage());
        String responseBody = new ObjectMapper().writeValueAsString(apiResponse);
        res.setStatus(status.value());
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(responseBody);
    }

}
