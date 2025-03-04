# Spring Security - Basic Authentication with In-Memory Users

## Overview
This project integrates Spring Security into a Spring Boot application to enable basic authentication. It demonstrates how to override the default authentication mechanism and use `InMemoryUserDetailsManager` to manage users in memory.

## Features
- Secures API endpoints using Spring Security.
- Implements `UserDetailsService` to load user credentials.
- Uses `InMemoryUserDetailsManager` to store user details.
- Supports basic authentication with username & password.
- All API endpoints require authentication by default.

## Implementation Details
### 1. Adding Spring Security
Spring Security is added as a dependency, which automatically applies security filters to authenticate every request.

### 2. Creating a Security Configuration Class
A custom security configuration class `SecurityConfig` is created to define authentication settings.

```java
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("root")
                .password("{noop}secret") // Plaintext password for now
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
```
- This method creates a user `root` with password `secret` and assigns roles `ADMIN` and `USER`.
- `{noop}` is used for plaintext passwords (should be replaced with `BCryptPasswordEncoder`).

### 3. Authentication Flow
1. When a request is made to any API, Spring Security intercepts it.
2. The user must provide a valid username and password.
3. `InMemoryUserDetailsManager` loads the user details.
4. If authentication is successful, access is granted; otherwise, the request is rejected.

## Future Improvements
- Replace `{noop}` with `BCryptPasswordEncoder` for better security.
- Implement authorization rules to allow/deny access based on roles.
- Store user details in a database instead of in-memory.

