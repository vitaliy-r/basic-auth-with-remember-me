package com.epam.session.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SessionStore {

  private Map<String, Authentication> allActiveSessionsInfo;

  public SessionStore() {
    allActiveSessionsInfo = new HashMap<>();
  }

}
