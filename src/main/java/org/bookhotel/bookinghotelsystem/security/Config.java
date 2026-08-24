package org.bookhotel.bookinghotelsystem.security;

import jakarta.transaction.Transactional;
import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.AuthorityRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.RoleRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.RoomRequestDTO;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.exception.AccessDeniedExceptionHandler;
import org.bookhotel.bookinghotelsystem.exception.AuthenticationExceptionHandler;
import org.bookhotel.bookinghotelsystem.security.jwt.JwtFilter;
import org.bookhotel.bookinghotelsystem.service.*;
import org.bookhotel.bookinghotelsystem.service.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Config {

    @Bean
    @Transactional
    public CommandLineRunner commandLineRunner(AuthorityService authorityService,
                                               RoleService roleService,
                                               UserService userService,
                                               RoomService roomService,
                                               BookingService bookingService) {
        return args -> {
            if (userService.isExistByUsername("Alireza")) return;

            var create = authorityService.addAuthority(new AuthorityRequestDTO("CREATE"));
            var read = authorityService.addAuthority(new AuthorityRequestDTO("READ"));
            var update = authorityService.addAuthority(new AuthorityRequestDTO("UPDATE"));
            var delete = authorityService.addAuthority(new AuthorityRequestDTO("DELETE"));
            var cancel = authorityService.addAuthority(new AuthorityRequestDTO("CANCEL"));

            var admin = roleService.addRole(new RoleRequestDTO("ADMIN", Set.of("CREATE", "READ", "UPDATE", "DELETE", "CANCEL")));
            var user = roleService.addRole(new RoleRequestDTO("USER", Set.of("CREATE", "READ", "CANCEL")));

            userService.addUser(new AdminUserRequestDTO("Alireza", "alirezamousaviseyed1386@gmail.com", "password", Set.of("ADMIN")));
            userService.addUser(new AdminUserRequestDTO("Xerxes", "xerxes@gmail.com", "password", Set.of("USER")));

            roomService.addRoom(new RoomRequestDTO("101", 1500000L, 4, RoomStatus.AVAILABLE));
            roomService.addRoom(new RoomRequestDTO("102", 1000000L, 2, RoomStatus.AVAILABLE));
            roomService.addRoom(new RoomRequestDTO("103", 1000000L, 1, RoomStatus.RESERVED));


        };
    }

    private final JwtFilter jwtFilter;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;
    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    public Config(JwtFilter jwtFilter, AuthenticationExceptionHandler authenticationExceptionHandler, AccessDeniedExceptionHandler accessDeniedExceptionHandler) {
        this.jwtFilter = jwtFilter;
        this.authenticationExceptionHandler = authenticationExceptionHandler;
        this.accessDeniedExceptionHandler = accessDeniedExceptionHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) {
        security.csrf(csrf -> csrf.disable());
        security.authorizeHttpRequests(sfc -> {
            sfc.requestMatchers("/api/auth/**").permitAll();
            //TODO: check other like swagger or etc
            sfc.anyRequest().authenticated();
        });

        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        security.exceptionHandling(exp -> {
            exp.accessDeniedHandler(accessDeniedExceptionHandler);
            exp.authenticationEntryPoint(authenticationExceptionHandler);

        });

        return security.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserServiceImpl user) {
        var auth = new DaoAuthenticationProvider(user);
        auth.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(auth);
    }

}
