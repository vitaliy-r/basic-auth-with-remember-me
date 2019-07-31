package com.epam.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

  static final String USER_AUTHENTICATION_INFO_ATTRIBUTE_NAME = "auth_info";

  private final SessionStore sessionStore;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication) {
    HttpSession session = request.getSession();
    SessionInfo sessionInfo = new SessionInfo(session.getId(), authentication);
//    BoundSessionInfo boundSessionInfo = new BoundSessionInfo(sessionStore, sessionInfo);
    session.setAttribute(USER_AUTHENTICATION_INFO_ATTRIBUTE_NAME, sessionInfo);
  }

}