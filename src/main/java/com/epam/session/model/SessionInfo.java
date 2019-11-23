package com.epam.session.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;

@Data
@AllArgsConstructor
public class SessionInfo {

  private String sessionId;
  private Authentication authentication;

}