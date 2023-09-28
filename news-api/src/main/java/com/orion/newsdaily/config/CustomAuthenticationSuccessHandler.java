package com.orion.newsdaily.config;

import com.orion.newsdaily.user.User;
import com.orion.newsdaily.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private WebSecurityConfiguration webSecurityConfiguration;

    private String targetUrl = "http://localhost:8000/";

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

            if (!targetUrl.contains("?email")) {
                targetUrl += "?email=" + URLEncoder.encode(email, "UTF-8") + "&name=" + URLEncoder.encode(username, "UTF-8");
            }

            // Log the token attributes
            System.out.println("Token Attributes here: " + tokenAttributes);
        }

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setRole("USER");
        user.setPassword("password");
        user.setIsDisabled(false);

        if(userService.findAllByName(0,100, username).isEmpty())
        {
            System.out.println("saved this data");
            userService.create(user);
        }

        // Your custom logic here, e.g., adding cookies
        response.addCookie(webSecurityConfiguration.createSessionCookie(webSecurityConfiguration.encode(authentication)));

        // Set the target URL before invoking the default behavior
        setDefaultTargetUrl(targetUrl);

        // Continue with the default behavior of saving and redirecting to the original request
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
