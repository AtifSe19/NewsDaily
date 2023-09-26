package com.orion.newsdaily.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.orion.newsdaily.user.User;
import com.orion.newsdaily.user.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.WebUtils;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableMethodSecurity
@Configuration
public class WebSecurityConfiguration {

    @Value("${spring.web.security.ignored:/error,/ui/**,/favicon.ico,/h2-console/**}")
    private String[] ignored = { "/error", "/ui/**", "/favicon.ico","/h2-console/**" };

    @Value("${spring.web.security.ignored.get:/h2-console/**,/actuator,/actuator/**}")
    private String[] ignoredGet = { "/h2-console/**","/actuator","/actuator/**" };

    @Value("${spring.web.security.api:/api/**}")
    private String api = "/api/**";

    @Value("${spring.web.security.session.cookie.name:JWT-Token}")
    private String sessionId = "JWT-Token";

    @Value("${spring.web.security.jwt.secret.key:fBnKDJkuDDBeejkgYCK+zz4pcyc+bfrYeTTkOqyj7Uo}")
    private String secretKey = "fBnKDJkuDDBeejkgYCK+zz4pcyc+bfrYeTTkOqyj7Uo";

    @Value("${spring.web.security.session.expiry.seconds:28800}")
    private int sessionExpirySeconds = 28800;

    private final UserService userService;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;





//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        return new ProviderManager(Arrays.asList(yourAuthenticationProvider()));
//    }
//
//    @Bean
//    public AuthenticationProvider yourAuthenticationProvider() {
//        return new DaoAuthenticationProvider() {{
//            setUserDetailsService(userService);
//        }};
//    }

//    @Autowired
//    private AuthenticationManager authenticationManager;

//    public WebSecurityConfiguration(AuthenticationManager authenticationManager)
//    {
//        this.authenticationManager = authenticationManager;
//    }

//    public WebSecurityConfiguration(UserService userService, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder)
//    {
//        this.jwtDecoder = jwtDecoder;
//        this.jwtEncoder = jwtEncoder;
//        this.userService = userService;
//    }


    public WebSecurityConfiguration(UserService userService) {
        this.userService = userService;
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "RSA");
        this.jwtEncoder = new NimbusJwtEncoder(new ImmutableSecret<>(secretKeySpec));
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }


    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/h2-console/**", "GET"), // Allow GET requests to h2-console
                new AntPathRequestMatcher("/h2-console/**", "POST"),
//                new AntPathRequestMatcher("/actuator/**", "GET"),
//                new AntPathRequestMatcher("/actuator/**", "POST"),
                new AntPathRequestMatcher("/api/v1/users/signup", "POST"),
                //new AntPathRequestMatcher("api/v1/newsTag/{id}", "POST"),
                new AntPathRequestMatcher("/api/v1/news", "GET")
        );
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.authorizeRequests().requestMatchers("GET", Arrays.toString(ignoredGet)).permitAll();
//        http.authorizeRequests().requestMatchers(Arrays.toString(ignored)).permitAll();

//        http.authorizeHttpRequests(config -> {
//            config.requestMatchers("/api/v1/news/").permitAll();
//            config.anyRequest().authenticated();
//        }).oauth2Login(Customizer.withDefaults());

//        http.oauth2Login(withDefaults())
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .successHandler(customAuthenticationSuccessHandler())
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .scope("email") // Request the 'email' scope
//                                .userAuthoritiesAttributeName("email") // Map 'email' to authorities
//                        )
//                );
        http.oauth2Login(withDefaults())
                .oauth2Login(oauth2Login -> oauth2Login
                        .successHandler(customAuthenticationSuccessHandler())
                );

        http.formLogin(config -> config.successHandler(authenticationSuccessHandler()));

        http.exceptionHandling(config -> config.defaultAuthenticationEntryPointFor(authenticationEntryPoint(),
                AntPathRequestMatcher.antMatcher(api)));

//        http.csrf(config -> config.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
        http.csrf(config -> config.disable());
        http.authorizeHttpRequests(config -> config.anyRequest().authenticated());

        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(config -> config.opaqueToken(withDefaults()));
        http.logout(config -> config.addLogoutHandler(new CookieClearingLogoutHandler(sessionId)));
        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }


    public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

        private String targetUrl = "/api/v1/news/customLoginRequest"; // Change this to your desired target URL

        @Override
        public void onAuthenticationSuccess(
                HttpServletRequest request,
                HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {

            String email = "";
            String username = "";

            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

                // Retrieve OAuth2 token details
                Map<String, Object> tokenAttributes = oauth2User.getAttributes();

                email = (String) tokenAttributes.get("email");
                username = (String) tokenAttributes.get("name");

                // Add the 'email' as a query parameter to the target URL
                targetUrl += "?email=" + URLEncoder.encode(email, "UTF-8")+"&name=" + URLEncoder.encode(username, "UTF-8");

                // Log the token attributes
                System.out.println("Token Attributes here: " + tokenAttributes);
            }

            User user = new User();

            user.setUsername(username);
            user.setEmail(email);
            user.setRole("USER");
            user.setPassword("password");
            user.setLoggedIn(true);

            if(!userService.findAllByName(1,100, username).isEmpty())
            {
                userService.create(user);
                userService.loadUserByUsername(username);
            }

//            try{
//                UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, user.getPassword());
//
//                Authentication authentication2 = authenticationManager.authenticate(authReq);
//
//                System.out.println("My Authorities: "+ authentication2.getAuthorities());
//            }catch (Exception e)
//            {
//                System.out.println("Custom Exception");
//                System.out.println(e.getMessage());
//            }







//             Set the "USER" role for users logging in with Google
//             Check if the user is logging in via Google

//            System.out.println("Working");
//            // Add the "USER" role to the existing authorities
//            List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
//            authorities.add(new SimpleGrantedAuthority("USER"));
//            authentication = new UsernamePasswordAuthenticationToken(
//                    authentication.getPrincipal(),
//                    authentication.getCredentials(),
//                    authorities
//            );

            // Your custom logic here, e.g., adding cookies
            response.addCookie(createSessionCookie(encode(authentication)));

            // Set the target URL before invoking the default behavior
            setDefaultTargetUrl(targetUrl);

            // Continue with the default behavior of saving and redirecting to the original request
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not Authorized");
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, auth) -> response.addCookie(createSessionCookie(encode(auth)));
    }

    private Cookie createSessionCookie(String token) {
        Cookie cookie = new Cookie(sessionId, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    private String encode(Authentication auth) {
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(auth.getName())
                .id(UUID.randomUUID().toString())
                .issuedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .expiresAt(LocalDateTime.now().plusSeconds(sessionExpirySeconds).toInstant(ZoneOffset.UTC))
                .build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(header, claims));
        return jwt.getTokenValue();
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return request -> resolveBearerToken(WebUtils.getCookie(request, sessionId));
    }

    private String resolveBearerToken(Cookie cookie) {
        String token = null;
        if (cookie != null) {
            token = cookie.getValue();
        }
        return token;
    }


    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        return token -> introspectorToken(token);
    }

    private OAuth2AuthenticatedPrincipal introspectorToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            UserDetails userDetails = userService.loadUserByUsername(jwt.getId(),jwt.getSubject());
            return new DefaultOAuth2User(userDetails.getAuthorities(), Map.of("sub", userDetails.getUsername()), "sub");
        } catch (Exception e) {
            throw new CredentialsExpiredException(e.getMessage(), e);
        }
    }
}