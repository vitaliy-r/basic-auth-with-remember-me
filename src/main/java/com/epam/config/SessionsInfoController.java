package com.epam.config;

import static java.util.stream.Collectors.joining;

import com.epam.session.SessionStore;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionsInfoController {

  @Autowired
  private SessionRegistry sessionRegistry;
  @Autowired
  private SessionStore sessionStore;

  @GetMapping
  public List<String> getAllSessionsFromSessionRegistry() {
    return sessionRegistry.getAllPrincipals().stream()
        .map(p -> sessionRegistry.getAllSessions(p, true))
        .map(sessions -> sessions.stream()
            .map(this::getSessionInfoAsString)
            .collect(joining("\n")))
        .collect(Collectors.toList());
  }

  @GetMapping("/active")
  public List<String> getAllActiveSessionsFromSessionRegistry() {
    return sessionRegistry.getAllPrincipals().stream()
        .map(p -> sessionRegistry.getAllSessions(p, false))
        .map(sessions -> sessions.stream()
            .map(this::getSessionInfoAsString)
            .collect(joining("\n")))
        .collect(Collectors.toList());
  }

  @GetMapping("/inactive")
  public List<String> getAllInactiveSessionsFromSessionRegistry() {
    return sessionRegistry.getAllPrincipals().stream()
        .map(p -> sessionRegistry.getAllSessions(p, true).stream()
            .filter(SessionInformation::isExpired)
            .collect(Collectors.toList()))
        .map(sessions -> sessions.stream()
            .map(this::getSessionInfoAsString)
            .collect(joining("\n")))
        .collect(Collectors.toList());
  }

  @GetMapping("/customstore/active")
  public Map<String, Authentication> getAllActiveSessionsFromCustomSessionStore() {
    return sessionStore.getSessionInfo();
  }

  private String getSessionInfoAsString(SessionInformation sessionInfo) {
    return sessionInfo.getSessionId() + ": " + sessionInfo.getPrincipal();
  }

}
