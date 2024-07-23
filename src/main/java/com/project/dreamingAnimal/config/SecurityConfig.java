package com.project.dreamingAnimal.config;
import com.project.dreamingAnimal.config.auth.PrincipalDetailsService;
import com.project.dreamingAnimal.config.auth.handlers.CustomAccessDeniedHandler;
import com.project.dreamingAnimal.config.auth.handlers.CustomAuthenticationEntryPoint;
import com.project.dreamingAnimal.config.auth.handlers.LoginFailureHandler;
import com.project.dreamingAnimal.config.oauth.PrincipalOauth2UserService;
import com.project.dreamingAnimal.config.auth.handlers.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration //
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // controller에 권한설정
public class SecurityConfig{

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    private final PrincipalDetailsService userDetailsService;

    public SecurityConfig(PrincipalDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    // Authentication 객체 생성 필요 코드
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return  new LoginFailureHandler();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        http.formLogin((formLogin) ->
            formLogin.loginPage("/loginForm").loginProcessingUrl("/login").successHandler(loginSuccessHandler()).failureHandler(loginFailureHandler()))
            .authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/admin/**").hasAnyRole("ADMIN") // /center/** url => admin  권한이 있는 사용자만 접근 가능
            .anyRequest().permitAll() // 권한을 주지않은 url 이 아니면 접근 허용
        ).oauth2Login((oauth2) -> oauth2
            .loginPage("/loginForm").userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
            .userService(principalOauth2UserService)).successHandler(loginSuccessHandler()).failureHandler(loginFailureHandler()))
            .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(customAuthenticationEntryPoint()) // 인증되지 않은 사용자가 접근할 때 처리
            .accessDeniedHandler(customAccessDeniedHandler())); // 권한이 없는 사용자가 접근할 때 처리
        http.csrf(CsrfConfigurer::disable).cors(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
