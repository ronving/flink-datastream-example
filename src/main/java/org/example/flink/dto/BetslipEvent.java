package org.example.flink.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
public class BetslipEvent {
    private String messageEventType;
    private Long betslipId;
    private String playerId;
    private BigDecimal wagerAmount;
    private BigDecimal returnAmount;
    private BigDecimal odds;
    private LocalDateTime placedAt;
    private LocalDateTime settledAt;
    @JsonIgnore
    private String status;
}
