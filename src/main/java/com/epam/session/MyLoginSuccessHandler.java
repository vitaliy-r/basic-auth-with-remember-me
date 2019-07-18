package com.epam.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private SessionStore sessionStore;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication) {
    HttpSession session = request.getSession();
    SessionInfo info = new SessionInfo(session.getId(), authentication, sessionStore);
    session.setAttribute("info", info);
  }

}