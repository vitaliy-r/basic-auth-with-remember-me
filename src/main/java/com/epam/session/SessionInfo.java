package com.epam.session;

import java.util.Map;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SessionInfo implements HttpSessionBindingListener {

  private String sessionId;
  private Authentication authentication;
  private SessionStore sessionStore;

  public SessionInfo() {
  }

  SessionInfo(String sessionId, Authentication authentication,
      SessionStore sessionStore) {
    this.sessionId = sessionId;
    this.authentication = authentication;
    this.sessionStore = sessionStore;
  }

  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    Map<String, Authentication> sessions = sessionStore.getSessionInfo();
    SessionInfo session = (SessionInfo) event.getValue();
    sessions.putIfAbsent(session.sessionId, session.authentication);
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    Map<String, Authentication> sessions = sessionStore.getSessionInfo();
    SessionInfo session = (SessionInfo) event.getValue();
    sessions.remove(session.sessionId);
  }

}