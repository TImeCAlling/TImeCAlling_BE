package TImeCAlling.spring.config;

import TImeCAlling.spring.auth.Handler.JwtAccessDeniedHandler;
import TImeCAlling.spring.auth.Handler.JwtAuthenticationEntryPoint;
import TImeCAlling.spring.auth.filter.JwtAuthenticationFilter;
import TImeCAlling.spring.auth.filter.JwtExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/css/**",
            "/js/**",
            "/file/**",
            "/images/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",
            "/h2/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable())
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/users/kakao/**").permitAll()
                        .requestMatchers("/api/users/token").permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);

        return http.build();
    }
}
