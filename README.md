# Spring boot starter JWT [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.cmeza/spring-boot-starter-jwt/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.cmeza/spring-boot-starter-jwt)

## Features ##

* Path to generate jwt
* Path to validate jwt
* Protected route with jwt
* EntryPoint handler
* AccessDenied handler
* JWT encoder
* Error attributes (Default response)
* Advanced Gateway configuration

## Dependencies ##

* **Java >= 8**
* **spring-boot-starter-parent >= 2.4.0 && < 3.0.0**
* **spring-boot-starter-web**
* **spring-boot-starter-security**
* **@EnableWebSecurity annotation**
* **Extended JwtSecurityConfigurerAdapter class**

## Maven Integration ##

```xml

<dependency>
    <groupId>com.cmeza</groupId>
    <artifactId>spring-boot-starter-jwt</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Routes ##

| NAME           | ROUTE BY DEFAULT | EXPOSED BY DEFAULT |
|----------------|------------------|--------------------|
| Token Generate | /auth/token      | YES                |
| Token validate | /auth/validate   | YES                |
| Assured route  | /**              | YES                |

## Properties ##

```yaml
spring:
  security:
    jwt:
      configuration:
        secret-key: [ YOUR SECRET KEY ]
        token-prefix: Bearer
        time: 1h
        time-suffix:
          hour: h
          minute: min
          second: sec
          millisecond: mill
          year: y
          month: m
          week: w
          day: d
      paths:
        token:
          path: /auth/token
          exposed: true
          role: [ YOUR ROLES ]
          role-prefix: ROLE_
        validate:
          path: /auth/validate
          exposed: true
          role: [ YOUR ROLES ]
          role-prefix: ROLE_
        secured:
          path: /**
          exposed: true
          role: [ YOUR ROLES ]
          role-prefix: ROLE_
```

| PROPERTIES                                                | REQUIRED |    DEFAULT     | DESCRIPTION                                                                                                                        |
|-----------------------------------------------------------|:--------:|:--------------:|:-----------------------------------------------------------------------------------------------------------------------------------|
| spring.security.jwt.configuration                         |          |                |                                                                                                                                    |
| spring.security.jwt.configuration.secret-key              |   YES    |                | Secret key to generate the JWT                                                                                                     | 
| spring.security.jwt.configuration.token-prefix            |   YES    |     Bearer     | Token prefix, if no prefix is required set the value of ''                                                                         |
| spring.security.jwt.configuration.time                    |   YES    |       1h       | Token lifetime (format set in properties spring.security.jwt.configuration.time-prefix) Example: <br>- 1d<br>- 5h<br>- 1d 3h 4 sec |
| spring.security.jwt.configuration.time-suffix.hour        |   YES    |       h        | Hour suffix                                                                                                                        |
| spring.security.jwt.configuration.time-suffix.minute      |   YES    |      min       | Minute suffix                                                                                                                      |
| spring.security.jwt.configuration.time-suffix.second      |   YES    |      sec       | Second suffix                                                                                                                      |
| spring.security.jwt.configuration.time-suffix.millisecond |   YES    |      mill      | Millisecond suffix                                                                                                                 |
| spring.security.jwt.configuration.time-suffix.year        |   YES    |       y        | Year suffix                                                                                                                        |
| spring.security.jwt.configuration.time-suffix.month       |   YES    |       m        | Month suffix                                                                                                                       |
| spring.security.jwt.configuration.time-suffix.week        |   YES    |       w        | Week suffix                                                                                                                        |
| spring.security.jwt.configuration.time-suffix.day         |   YES    |       d        | Day suffix                                                                                                                         |
| spring.security.jwt.paths                                 |          |                |                                                                                                                                    |
| spring.security.jwt.paths.token                           |   NOT    |                |                                                                                                                                    |
| spring.security.jwt.paths.token.path                      |   YES    |  /auth/token   | Exposed route to generate token                                                                                                    |
| spring.security.jwt.paths.token.exposed                   |   YES    |      true      | Enable/disable exposed route                                                                                                       |
| spring.security.jwt.paths.token.role                      |   NOT    |      true      | Enable/disable exposed route                                                                                                       |
| spring.security.jwt.paths.token.role-prefix               |   NOT    |     ROLE_      | Role prefix (only works if spring.security.jwt.paths.token.role is set)                                                            |
| spring.security.jwt.paths.validate                        |   NOT    |                |                                                                                                                                    |
| spring.security.jwt.paths.validate.path                   |   YES    | /auth/validate | Exposed route to validate token                                                                                                    |
| spring.security.jwt.paths.validate.exposed                |   YES    |      true      | Enable/disable exposed route                                                                                                       |
| spring.security.jwt.paths.validate.role                   |   NOT    |      true      | Enable/disable exposed route                                                                                                       |
| spring.security.jwt.paths.validate.role-prefix            |   NOT    |     ROLE_      | Role prefix (only works if spring.security.jwt.paths.token.role is set)                                                            |
| spring.security.jwt.paths.secured                         |   NOT    |                |                                                                                                                                    |
| spring.security.jwt.paths.secured.path                    |   YES    |      /**       | Exposed route secured with jwt token                                                                                               |
| spring.security.jwt.paths.secured.exposed                 |   YES    |      true      | Enable/disable exposed route                                                                                                       |
| spring.security.jwt.paths.secured.role                    |   NOT    |      true      | Enable/disable exposed route                                                                                                       |
| spring.security.jwt.paths.secured.role-prefix             |   NOT    |     ROLE_      | Role prefix (only works if spring.security.jwt.paths.token.role is set)                                                            |

## Log Level ##

```yaml
logging:
  level:
    com.cmeza.spring.security.jwt: INFO
```

| LEVEL |                                          DESCRIPTION                                          |
|-------|:---------------------------------------------------------------------------------------------:|
| INFO  | Prints the default credentials, only if the JwtUserDetailsService has not been implemented(1) |
| DEBUG |                          Prints information about exposed routes(2)                           |

#### Case (1) INFO

```text
| ---------------------------------------------------------------------- |
| ---------------------- Default Jwt credentials ----------------------- |
| ---------------------------------------------------------------------- |
| Jwt request: {"username":"[YOUR USERNAME]","password":"[YOUR PASSWORD]"}
| Jwt User: user
| Jwt Password: c6c8ab02-d306-4dd1-9a1b-e7951ed3f0cf
```

#### Case (2) DEBUG

```text
| ---------------------------------------------------------------------- |
| ------------------------------- TOKEN -------------------------------- |
| ---------------------------------------------------------------------- |
| Path: /auth/token
| Role: Any role
| Role Prefix: ROLE_
|
| ---------------------------------------------------------------------- |
| ------------------------------ VALIDATE ------------------------------ |
| ---------------------------------------------------------------------- |
| Path: /auth/validate
| Role: Any role
| Role Prefix: ROLE_
|
| ---------------------------------------------------------------------- |
| ------------------------------ SECURED ------------------------------- |
| ---------------------------------------------------------------------- |
| Path: /**
| Role: Any role
| Role Prefix: ROLE_
```

## 1. Default configuration ##

```java

@EnableWebSecurity
public class SecurityConfig extends JwtSecurityConfigurerAdapter {

}
```

## 2. Simple configuration ##

First implement the interface **JwtUserDetailsService<R, E>** where: <br>

* R = Request to generate the token
* E = Response to generate the token

PathType enum:

* TOKEN = Generate token endpoint
* VALIDATE = Validate token endpoint
* SECURED = Secured endpoint

*NOTE:*

* *For more information about the default implementation, see the default methods of the **JwtUserDetailsService<R, E>** interface.*
* *For this example, LoginRequest and UserEntity will be used, these classes must be created previously*

```java

@Component
public class CustomJwtUserDetailsService implements JwtUserDetailsService<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse authenticate(UserDetails userDetails, List<GrantedAuthority> list) throws JwtBadCredentialsException {
        if (...Authentication logic ...){
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // Optional (add authorities if necessary)
            return new LoginResponse(...)// Returns an entity
        }
        throw new JwtBadCredentialsException("Bad credentials"); // Returns an exception
    }

    @Override
    public LoginAuthenticationToken convert(LoginRequest loginRequest) throws JwtAuthenticationException {
        // An instance of the LoginAuthenticationToken class is created with the attributes of its constructor
        return new LoginAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @Override
    public void tokenCreated(LoginResponse loginResponse, JwtDescriptor jwtDescriptor) {
        // Sets the token in the response entity
        loginResponse.setToken(jwtDescriptor.getEncodedToken());
    }

    //OPTIONAL
    @Override
    public void configureJwt(JwtBuilder jwtBuilder, LoginResponse loginResponse) {
        // Configure the jwt, you can modify the subject, claims, etc.
    }

    //OPTIONAL
    @Override
    public LoginResponse tokenToEntity(JwtDescriptor jwtDescriptor, List<GrantedAuthority> authorities) {
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  //Optional (add authorities if necessary)
        // Returns the response entity 
        // Only one of the two implementations should be used: tokenToEntity(...) or tokenToObject(...)
    }

    //OPTIONAL
    @Override
    public Object tokenToObject(JwtDescriptor jwtDescriptor, List<GrantedAuthority> authorities) {
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); //Optional (add authorities if necessary)
        // Return any object
        // Only one of the two implementations should be used: tokenToEntity(...) or tokenToObject(...)
        // Note: only works if tokenToEntity(...) has not been overridden
    }

    //OPTIONAL
    @Override
    public void onSuccessfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication, PathType pathType) {
        // Captures the event upon successful authentication
    }

    //OPTIONAL
    @Override
    public void onUnsuccessfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException, PathType pathType) {
        // Capture the event at the moment the authentication fails
    }
}
```

Implement the **JwtSecurityExceptionHandling** interface *(all methods are optional)*<br>
Where HandlerType enum:

* EXCEPTION = General exception
* ACCESS_DENIED = Exception thrown due to lack of privileges
* ENTRY_POINT = Exception thrown at authentication time

*NOTE: For more information about the default implementation, see the default methods of the **JwtSecurityExceptionHandling** interface.*

```java

@Component
public class CustomJwtSecurityExceptionHandling implements JwtSecurityExceptionHandling {

    //OPTIONAL
    @Override
    public void globalExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception, HttpStatus httpStatus, HandlerType handlerType) throws IOException, ServletException {
        // Handles both global exceptions, lack of privileges and authentication failure
    }

    //OPTIONAL
    @Override
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception, HttpStatus httpStatus) throws IOException, ServletException {
        // Catch global exceptions
    }

    //OPTIONAL
    @Override
    public void accessDeniedHandler(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Catch exceptions for lack of privileges
    }

    //OPTIONAL
    @Override
    public void entryPointHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Catch exceptions for authentication failure
    }

    //OPTIONAL
    @Override
    public Object exceptionHandler(HandlerType handlerType, Exception exception) {
        // Returns an error object to the client
    }

    //OPTIONAL
    @Override
    public boolean printException() {
        // Enable/disable exception printing (only works if globalExceptionHandler has not been overridden)
    }
}
```

Implement the JwtSecurityFilterChain interface (all methods are optional)<br>
Where PathType enum:

* TOKEN = Generate token endpoint
* VALIDATE = Validate token endpoint
* SECURED = Secured endpoint

*NOTE: For more information about the default implementation, see the default methods of the **JwtSecurityFilterChain** interface.*

```java

@Component
public class CustomJwtSecurityFilterChain implements JwtSecurityFilterChain {

    //OPTIONAL
    @Override
    public void globalConfigure(HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler) throws Exception {
        // Global HttpSecurity configuration (for any route)
    }

    //OPTIONAL
    @Override
    public void configureFilter(HttpSecurity http, PathType pathType) throws Exception {
        // Add additional configurations to the HttpSecurity by PathType
    }
}
```

Finally, override the configure() method of the JwtSecurityConfigurerAdapter class and apply the previous implementations.

```java

@EnableWebSecurity
public class SecurityConfig extends JwtSecurityConfigurerAdapter {
    private final CustomJwtUserDetailsService customJwtUserDetailsService;
    private final CustomJwtSecurityExceptionHandling customJwtSecurityExceptionHandling;
    private final CustomJwtSecurityFilterChain customJwtSecurityFilterChain;

    public SecurityConfig(...) {
        // Constructor
    }

    @Override
    protected void configure(JwtConfigurer jwtConfigurer) {
        jwtConfigurer.setJwtUserDetailsService(customJwtUserDetailsService); // Optional
        jwtConfigurer.setJwtSecurityExceptionHandling(customJwtSecurityExceptionHandling); // Optional
        jwtConfigurer.setJwtSecurityFilterChain(customJwtSecurityFilterChain); // Optional
    }
}
```

## 3. Advanced configuration ##

```java

@EnableWebSecurity
public class SecurityConfig extends JwtSecurityConfigurerAdapter {

    @Override
    protected void advancedConfigure(JwtAdvancedConfigurer jwtAdvancedConfigurer) {
        jwtAdvancedConfigurer
                .addJwtEncoder(new Base64JwtTokenEncoder())
                .addErrorAttributeOptionsInclude(ErrorAttributeOptions.Include.MESSAGE)
                .addErrorAttributeOptionsExclude(ErrorAttributeOptions.Include.MESSAGE)
                .setLoginFilterGateway([YOUR CUSTOM IMPLEMENTATION ])
                .setValidateFilterGateway([YOUR CUSTOM IMPLEMENTATION ])
                .setSecuredFilterGateway([YOUR CUSTOM IMPLEMENTATION ])
                .setJwtTokenManagerGateway([YOUR CUSTOM IMPLEMENTATION ])
                .setJwtEncodeManagerGateway([YOUR CUSTOM IMPLEMENTATION ])
                .setJwtHandlerGateway([YOUR CUSTOM IMPLEMENTATION ])
                .setJwtProviderGateway([YOUR CUSTOM IMPLEMENTATION ])
                .setSecurityConfigGateway([YOUR CUSTOM IMPLEMENTATION ])
    }
}
```

***

- **JwtEncoder** (Encode/decode the token)<br>
  Implement the JwtTokenEncoder interface

```java
public interface JwtTokenEncoder {
    String encode(String var1);

    String decode(String var1);

    default int order() {
        return 0;
    }
}
```

*NOTE: There is an implementation already created (Base64 **Base64JwtTokenEncoder**), you can add more than one encoder*

***

- **ErrorAttributeOptions** (INCLUDE/EXCLUDE)<br>
  Include/exclude attributes from the default response<br><br>

  Where Include enum: EXCEPTION, STACK_TRACE, MESSAGE, BINDING_ERRORS

***

- **LoginFilterGateway**

Responsible for processing the authentication when generating the token<br>

*NOTE: The default implementation is called **LoginFilterAdapter**.*

```java
public interface LoginFilterGateway {

    void doFilterInternalLogin(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain,
            RequestMatcher requestMatcher,
            JwtSecurityExceptionHandling jwtSecurityExceptionHandling,
            JwtUserDetailsServiceEvent jwtUserDetailsServiceEvent) throws ServletException, IOException;

}
```

- **ValidateFilterGateway**

Responsible for validating the token, endpoint /auth/validate<br>

*NOTE: The default implementation is called **ValidateFilterAdapter**.*

```java
public interface ValidateFilterGateway {

    void doFilterInternalValidate(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain,
            RequestMatcher requestMatcher,
            JwtSecurityExceptionHandling jwtSecurityExceptionHandling,
            JwtUserDetailsServiceEvent jwtUserDetailsServiceEvent) throws ServletException, IOException;

}
```

- **SecuredFilterGateway**

Responsible for validating the token, endpoint /**<br>

*NOTE: The default implementation is called **SecuredFilterAdapter**.*

```java
public interface SecuredFilterGateway {

    void doFilterInternalSecured(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain,
            RequestMatcher requestMatcher,
            JwtSecurityExceptionHandling jwtSecurityExceptionHandling,
            JwtUserDetailsServiceEvent jwtUserDetailsServiceEvent) throws ServletException, IOException;

}
```

- **JwtTokenManagerGateway**

Responsible for providing the management/creation of the jwt token<br>

*NOTE: The default implementation is called **JwtTokenManagerAdapter**.*

```java
public interface JwtTokenManagerGateway {
    User createUser(Authentication authentication, List<GrantedAuthority> authorities);

    JwtBuilderWrapper createJwtBuilder(String subject);

    Claims decodeJwt(String token);

}
```

- **JwtEncodeManagerGateway**

Responsible for processing the encoders that apply to the token<br>

*NOTE: The default implementation is called **JwtEncodeManagerAdapter**.*

```java
public interface JwtEncodeManagerGateway extends JwtTokenEncoder {
    String encode(String token);

    String decode(String token);

    default int order() {
        return 0;
    }
}
```

- **JwtHandlerGateway**

Responsible for receiving the request when creating the token<br>

*NOTE: The default implementation is called **JwtHandlerAdapter**.*

```java
public interface JwtHandlerGateway {
    ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
```

- **JwtProviderGateway**

Responsible for authenticating a new token and/or validating an incoming token<br>

*NOTE: The default implementation is called **JwtProviderAdapter**.*

```java
public interface JwtProviderGateway<R, E> {
    Authentication loginAuthenticate(LoginAuthenticationToken authentication, JwtUserDetailsService<R, E> jwtUserDetailsService) throws AuthenticationException;

    Authentication jwtAuthenticate(JwtAuthenticationToken authentication, JwtUserDetailsService<R, E> jwtUserDetailsService) throws AuthenticationException;
}
```

- **SecurityConfigGateway**

Responsible for configuring the HttpSecurity class<br>

*NOTE: The default implementation is called **SecurityConfigAdapter**.*

```java
public interface SecurityConfigGateway {

    SecurityFilterChain bindSecurity(HttpSecurity http, AbstractHttpConfigurer<?, HttpSecurity> httpConfigurer, JwtPathProperties pathProperties, PathType pathType) throws Exception;

    SimpleUrlHandlerMapping bindMapping(AbstractController abstractController, JwtPathProperties pathProperties);

    String hasAnyRole(String rolePrefix, String... authorities);
}
```

License
----

MIT
