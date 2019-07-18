package com.epam.session;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;

public class SessionStore {

  private Map<String, Authentication> sessionInfo;

  public SessionStore() {
    sessionInfo = new HashMap<>();
  }

  public Map<String, Authentication> getSessionInfo() {
    return sessionInfo;
  }

}
