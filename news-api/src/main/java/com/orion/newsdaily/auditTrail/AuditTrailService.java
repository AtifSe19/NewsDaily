package com.orion.newsdaily.auditTrail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orion.newsdaily.user.User;
import com.orion.newsdaily.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditTrailService {
    @Autowired
    private final AuditTrailRepo auditTrailRepo;
    @Autowired
    private final UserService userService;


    public void create(Authentication authentication, long newsId) {
        String username=authentication.getName();

        User user=userService.findByUserName(username);

        AuditTrail record=new AuditTrail();
        record.setNewsId(newsId);
        record.setCreatedAt(LocalDateTime.now());
        record.setIpAddress(getIpAddress());
        record.setUser(user);

        auditTrailRepo.save(record);

    }

    public String getIpAddress(){
        try {
            // Send an HTTP GET request to httpbin.org
            URL url = new URL("https://httpbin.org/ip");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            String ipAddress = jsonNode.get("origin").asText();

//            System.out.println("External IP Address: " + ipAddress);
            return ipAddress;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
