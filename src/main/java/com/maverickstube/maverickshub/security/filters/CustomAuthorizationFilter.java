package com.maverickstube.maverickshub.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import static com.maverickstube.maverickshub.security.utils.SecurityUtils.JWT_PREFIX;
import static com.maverickstube.maverickshub.security.utils.SecurityUtils.PUBLIC_ENDPOINT;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

    // 1a. Retrieve request path
    // 1b. if the request path is a public path
    //        call the next filter in the chain and terminate this filters execution.

    // 2a. Retrieve (JWT) access token from the request header

    // 3. Decode the access token.

    // 4. extract the token permission
    // 5. add token permission to security context
    // 6. call the next filter in the filterChain


        String requestPath = request.getServletPath();
        boolean isRequestPathPublic = PUBLIC_ENDPOINT.contains(requestPath);
        if (isRequestPathPublic) filterChain.doFilter(request, response);
        String authorizationRequest =  request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationRequest != null) {


            String token = authorizationRequest.substring(JWT_PREFIX.length()).strip();

            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret".getBytes()))
                    .withIssuer("mavericks_hub")
                    .withClaimPresence("roles")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);

            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles")
                    .asList(SimpleGrantedAuthority.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
            filterChain.doFilter(request, response);
        }
}
