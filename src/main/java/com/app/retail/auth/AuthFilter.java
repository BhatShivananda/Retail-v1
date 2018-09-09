package com.app.retail.auth;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.retail.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Order(2)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthFilter extends OncePerRequestFilter {

  private final AuthFilterProps authFilterProps;

  @Override
  public boolean shouldNotFilter(HttpServletRequest request) {
    String servletPathInfo = request.getServletPath();
    boolean bypassAuth = false;
    if (StringUtils.isNotBlank(servletPathInfo)) {
      for (String url : StringUtils.split(authFilterProps.getCommaSeparatedBypassUrlList(),
              ",")) {
        Pattern pattern = Pattern.compile(url);
        Matcher matcher = pattern.matcher(servletPathInfo);
        if (matcher.lookingAt()) {
          bypassAuth = true;
        }
      }
    }
    return bypassAuth;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeaderValue = request.getHeader("Authorization");
    if (authFilterProps.isProceedOnUnauthorized()) {
      filterChain.doFilter(request, response);
      // Temp auth implementation -- access token is hardcoded
    } else if (authFilterProps.getAccessToken().equalsIgnoreCase(authHeaderValue)) {
      filterChain.doFilter(request, response);
    } else {
      return401(request, response);
    }
  }

  private void return401(HttpServletRequest request, HttpServletResponse response) throws IOException {
    LOGGER.error("Invalid access token: " + request.getHeader("Authorization") + " --- Auth exception from {}",
        authFilterProps.getServiceName());
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse("Access is not authorized")));
  }
}
