package org.example.flink.operator;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlayerProfitHolder {
    String playerId;
    BigDecimal totalProfit;
}
