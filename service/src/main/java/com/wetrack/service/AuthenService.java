package com.wetrack.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wetrack.dao.UserRepository;
import com.wetrack.dao.UserTokenRepository;
import com.wetrack.model.User;
import com.wetrack.model.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.temporal.ChronoUnit;

import static com.wetrack.util.RsResponseUtils.*;

@Path("/")
@Component
@Produces(MediaType.APPLICATION_JSON)
public class AuthenService {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenService.class);

    @Autowired private Gson gson;
    @Autowired private UserRepository userRepository;
    @Autowired private UserTokenRepository userTokenRepository;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userLogin(String requestBody) {
        LOG.debug("POST /login/");

        LoginRequest loginRequest;
        try {
            loginRequest = gson.fromJson(requestBody, LoginRequest.class);
        } catch (JsonSyntaxException ex) {
            LOG.debug("The received body is not in valid format. Returning `400 Bad Request`...");
            return badRequest("The given request body is not in valid JSON format.");
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (username == null || username.isEmpty()) {
            LOG.debug("The given username is empty. Returning `400 Bad Request`...");
            return badRequest("The given username cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            LOG.debug("The given password is empty. Returning `400 Bad Request`...");
            return badRequest("The given password cannot be empty.");
        }

        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            LOG.debug("Login attempt with incorrect credential. Returning `401 Unauthorized`...");
            return unauthorized("The given username or password is incorrect.");
        }

        UserToken token = userTokenRepository.findByUsername(username);
        if (token == null) {
            token = new UserToken(username, 1, ChronoUnit.DAYS);
            userTokenRepository.insert(token);
        }

        LOG.debug("User logged in successfully.");
        return ok(gson.toJson(token));
    }

    static class LoginRequest {
        private String username;
        private String password;

        LoginRequest() {}

        LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        String getUsername() {
            return username;
        }
        void setUsername(String username) {
            this.username = username;
        }
        String getPassword() {
            return password;
        }
        void setPassword(String password) {
            this.password = password;
        }
    }
}
