package com.epam.session.managment;

import com.epam.session.model.SessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent> {

  private final SessionStore sessionStore;

  @Override
  public void onApplicationEvent(SessionDestroyedEvent event) {
    sessionStore.getAllActiveSessionsInfo().remove(event.getId());
  }

}
