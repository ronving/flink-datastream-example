package org.example.flink.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlayerProfit {
    private String playerId;
    private BigDecimal totalProfit;
}
