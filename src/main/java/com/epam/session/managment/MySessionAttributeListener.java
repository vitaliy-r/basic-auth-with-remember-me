package com.epam.session.managment;

import com.epam.session.model.SessionInfo;
import com.epam.session.model.SessionStore;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@WebListener
@RequiredArgsConstructor
public class MySessionAttributeListener implements HttpSessionAttributeListener {

  private final SessionStore sessionStore;

  @Override
  public void attributeAdded(HttpSessionBindingEvent event) {
    if (event.getValue() instanceof SessionInfo) {
      SessionInfo session = (SessionInfo) event.getValue();
      sessionStore.getAllActiveSessionsInfo()
          .putIfAbsent(session.getSessionId(), session.getAuthentication());
    }
  }

  @Override
  public void attributeRemoved(HttpSessionBindingEvent event) {
    if (event.getValue() instanceof SessionInfo) {
      SessionInfo session = (SessionInfo) event.getValue();
      sessionStore.getAllActiveSessionsInfo().remove(session.getSessionId());
    }
  }

  @Override
  public void attributeReplaced(HttpSessionBindingEvent event) {
    if (event.getValue() instanceof SessionInfo) {
      SessionInfo session = (SessionInfo) event.getValue();
      sessionStore.getAllActiveSessionsInfo()
          .put(session.getSessionId(), session.getAuthentication());
    }
  }

}