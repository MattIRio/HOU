package com.heroesOfUbersharik.configuration;


import com.heroesOfUbersharik.AuthenticationSuccessHandler;
import com.heroesOfUbersharik.model.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSecurity
@EnableWebSocketMessageBroker
public class SecurityConfiguration implements WebSocketMessageBrokerConfigurer {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/login","/mainpage", "/images/regPage.png", "/video/video.mp4", "/register", "/css/**", "/images/google.png", "/images/git.png", "/images/main.png", "/images/logo.png", "/oauth2/authorization/github", "/oauth2/authorization/google", "/oauth2/**").permitAll();
                    registry.requestMatchers("/login").anonymous();
                    registry.requestMatchers("/profileform", "/mainpage").authenticated();
                    registry.anyRequest().authenticated();
                })
            .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                )
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .usernameParameter("email")
                            .failureUrl("/login?error=true")
                            .successHandler(new AuthenticationSuccessHandler())
                            .defaultSuccessUrl("/profileform", true)
                            .permitAll();

                })
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Страница логина для OAuth2
                        .defaultSuccessUrl("/profileform", true) // URL после успешной аутентификации по OAuth2
                )
                .build();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/main/public");
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails normalUser = User.builder()
//                .username("gc")
//                .password("$2a$12$pLlEDlW7J3LJMLMl0Uv8Xu.NO1TYDvrMmIpoDhpHZ3So65XlsR.Vy")
//                .roles("USER")
//                .build();
//        UserDetails adminUser = User.builder()
//                .username("admin")
//                .password("$2a$12$4MVGfzHJ2C370at3MTGHdeX6z/kon2X5KbVWZTGfqjWBhj.KnQBuC")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }


    @Bean
    public UserDetailsService userDetailsService(MyUserDetailService myUserDetailService) {
        return myUserDetailService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(MyUserDetailService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
