package com.maverickstube.maverickshub.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverickstube.maverickshub.dtos.requests.LoginRequest;
import com.maverickstube.maverickshub.dtos.response.BaseResponse;
import com.maverickstube.maverickshub.dtos.response.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try{
            // 1. Retrieve authentication credentials from the request body
            InputStream requestBodyStream = request.getInputStream();
            // 2. convert the json data from 1 to java object (LoginRequest)
            LoginRequest loginRequest = objectMapper.readValue(requestBodyStream, LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            // 3. create an authentication object that is not yet authenticated
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(username, password);
            // 4a. pass the unAuthentication  authenticated object to the authenticationManager
            // 4b. Get back the authentication result from the authenticationManager
            Authentication authenticationResult =
                    authenticationManager.authenticate(authentication);
            // 5. Put the authentication result in the security context
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
                return authenticationResult;
        }catch(IOException exception){
            throw new BadCredentialsException(exception.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = generateAccessToken(authResult);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setMessage("successful Authentication");
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(true);
        baseResponse.setCode(HttpStatus.OK.value());

        response.getOutputStream().write(objectMapper.writeValueAsBytes(baseResponse));
        response.flushBuffer();
        chain.doFilter(request, response);
    }

    private static String generateAccessToken(Authentication authResult) {
        return JWT   .create()
                    .withIssuer("mavericks_hub")
                    .withArrayClaim("roles", getClaimsFrom(authResult.getAuthorities()))
                    .withExpiresAt( Instant.now().plusSeconds(24 * 60 * 60))
                    .sign(Algorithm.HMAC512("secret"));
    }

    private static String[] getClaimsFrom(Collection <? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map((grantedAuthority)-> grantedAuthority.getAuthority())
                .toArray(String[]::new);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception) throws IOException, ServletException {

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(false);
        baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());

        response.getOutputStream()
                .write(objectMapper.writeValueAsBytes(baseResponse));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.flushBuffer();
    }

}
