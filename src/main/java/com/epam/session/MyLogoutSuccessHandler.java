package com.epam.session;

import static com.epam.session.MyLoginSuccessHandler.USER_AUTHENTICATION_INFO_ATTRIBUTE_NAME;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.removeAttribute(USER_AUTHENTICATION_INFO_ATTRIBUTE_NAME);
    }
  }

}