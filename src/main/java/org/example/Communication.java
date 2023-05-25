package org.example;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String URL = "http://94.198.50.185:7081/api/users";

    private HttpEntity<User> getEntity(User user, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionId);

        return new HttpEntity<>(user, headers);
    }

    public ResponseEntity<List<User>> getAllUsers() {

        return restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
    }

    public ResponseEntity<String> addUser(User user, String sessionId) {

        return restTemplate.exchange(
                URL,
                HttpMethod.POST,
                getEntity(user, sessionId),
                String.class);
    }

    public ResponseEntity<String> editUser(User user, String sessionId) {

        return restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                getEntity(user, sessionId),
                String.class);
    }

    public ResponseEntity<String> deleteUser(User user, String sessionId) {

        return restTemplate.exchange(
                URL + "/" + user.getId(),
                HttpMethod.DELETE,
                getEntity(user, sessionId),
                String.class);
    }
}
