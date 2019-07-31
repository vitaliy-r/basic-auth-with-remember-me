package com.epam.session;

import java.util.Map;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class BoundSessionInfo implements HttpSessionBindingListener {

  private SessionStore sessionStore;
  private SessionInfo sessionInfo;

  public BoundSessionInfo() {
  }

  BoundSessionInfo(SessionStore sessionStore, SessionInfo sessionInfo) {
    this.sessionStore = sessionStore;
    this.sessionInfo = sessionInfo;
  }

  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    Map<String, Authentication> sessions = sessionStore.getAllActiveSessionsInfo();
    SessionInfo session = ((BoundSessionInfo) event.getValue()).sessionInfo;
    sessions.putIfAbsent(session.getSessionId(), session.getAuthentication());
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    Map<String, Authentication> sessions = sessionStore.getAllActiveSessionsInfo();
    SessionInfo session = ((BoundSessionInfo) event.getValue()).sessionInfo;
    sessions.remove(session.getSessionId());
  }

}