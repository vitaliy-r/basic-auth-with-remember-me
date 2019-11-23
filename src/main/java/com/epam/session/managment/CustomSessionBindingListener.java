package com.epam.session.managment;

import com.epam.session.model.SessionInfo;
import com.epam.session.model.SessionStore;
import java.util.Map;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class CustomSessionBindingListener implements HttpSessionBindingListener {

  private SessionStore sessionStore;
  private SessionInfo sessionInfo;

  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    Map<String, Authentication> sessions = sessionStore.getAllActiveSessionsInfo();
    SessionInfo session = ((CustomSessionBindingListener) event.getValue()).sessionInfo;
    sessions.putIfAbsent(session.getSessionId(), session.getAuthentication());
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    Map<String, Authentication> sessions = sessionStore.getAllActiveSessionsInfo();
    SessionInfo session = ((CustomSessionBindingListener) event.getValue()).sessionInfo;
    sessions.remove(session.getSessionId());
  }

}