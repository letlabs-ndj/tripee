package com.lameute.chat_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserConnection {
  private long connectionId;
  private String connectionUsername;
  private String convId;
  private int unSeen;
  private boolean isOnline;
}
