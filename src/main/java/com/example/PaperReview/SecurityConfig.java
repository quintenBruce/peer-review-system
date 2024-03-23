package com.example.PaperReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        // Define your user details (if needed)
//        return null;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // Define your password encoder
//        return new BCryptPasswordEncoder();
//    }

/////////////////////////////////////////////////////

//    @Bean
//    SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .formLogin(Customizer.withDefaults());
//
//        return http.build();
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//
//    }

//
//    @Autowired
//    private DataSource dataSource;
@Bean
public PasswordEncoder passwordEncoder() {
    // Define your password encoder
    return new BCryptPasswordEncoder();
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/signup")).permitAll()
                        .anyRequest().authenticated()
                ).headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults()
                )
                .logout((logout) ->
                        logout.deleteCookies("remove")
                                .invalidateHttpSession(false)
                                .logoutSuccessUrl("/login")
                )
                .formLogin((login) -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .csrf((csrf) -> csrf.disable());;

        return http.build();
    }
@Bean
DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
            .setType(H2)
            .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
            .build();
}

    @Bean
    UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("user1")
//                .passwordEncoder(passwordEncoder()::encode)
//                .password("password")
//                .roles("USER")
//                .build();

//        UserDetails admin = User.builder()
//                .username("admin")
//                .passwordEncoder(passwordEncoder()::encode)
//                .password("password")
//                .roles("USER")
//                .build();
//
//        UserDetails testUser = User.builder()
//                .username("user2")
//                .passwordEncoder(passwordEncoder()::encode)
//                .password("password")
//                .roles("USER")
//                .build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
////        users.createUser(user);
//        users.createUser(admin);
//        users.createUser(testUser); // Add the third test user
        return users;
    }
}
