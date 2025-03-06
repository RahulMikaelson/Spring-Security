# Spring Security - Role-Based Authentication with MongoDB

## Overview
This project implements authentication and authorization using Spring Security with role-based access control (RBAC). It integrates MongoDB for user storage and follows best practices such as password encryption, tokenless authentication using Basic Auth, and user role management.

## Features
- **User Management:** Supports adding, retrieving, and deleting users.
- **Role-Based Access Control:**
    - `SUPER_ADMIN` can delete users.
    - `ADMIN` can access `/api/v1/user/**` endpoints.
    - `USER` can access `/api/v1/products/**` endpoints.
    - `/api/v1/default` is publicly accessible.
- **Secure Password Storage:** Uses BCrypt for hashing passwords.
- **Tokenless Authentication:** Uses HTTP Basic authentication.
- **Admin User Initialization:** A default `admin` user is created at startup if one does not already exist.

## Implementation Details

### 1. Security Configuration
- CSRF protection and form login are disabled since authentication is handled via HTTP Basic authentication.
- User roles are used to restrict access to different API endpoints.

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)  // Disables CSRF as we're using stateless authentication
            .formLogin(AbstractHttpConfigurer::disable) // Disables form-based login
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/default").permitAll()
                .requestMatchers("/api/v1/user/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/{id}/delete").hasRole("SUPER_ADMIN")
                .requestMatchers("/api/v1/products/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
```

### 2. User Entity
- `UserEntity` extends `UserDetails` to integrate with Spring Security.
- The `getAuthorities()` method provides the user's roles.

```java
@Data
@Document(collection = "users")
public class UserEntity implements UserDetails {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
    }
}
```

### 3. User Service
- `UserService` implements `UserDetailsService` to load users from MongoDB.
- This service is used by Spring Security for authentication.

```java
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
```

### 4. Admin User Initialization
- A default `admin` user is created at startup if one does not already exist.
- Uses `CommandLineRunner` instead of `@PostConstruct` for reliable execution after the application starts.

```java
@Slf4j
@Component
@RequiredArgsConstructor
public class InitSetupConfig implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Override
    public void run(String... args) {
        if (!userRepo.existsByUsername("admin")) {
            UserEntity user = new UserEntity();
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("secret"));
            user.setRoles(List.of("SUPER_ADMIN", "ADMIN", "USER"));
            userRepo.save(user);
            log.info("Successfully added Admin user");
        } else {
            log.info("Admin user already exists");
        }
    }
}
```

## API Endpoints

| Method | Endpoint | Access Control |
|--------|---------|----------------|
| GET | `/api/v1/default` | Public |
| GET | `/api/v1/user` | ADMIN |
| POST | `/api/v1/user/save` | ADMIN |
| DELETE | `/api/v1/user/{id}/delete` | SUPER_ADMIN |
| GET | `/api/v1/products` | USER |


---



