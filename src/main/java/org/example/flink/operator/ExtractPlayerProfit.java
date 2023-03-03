package org.example.flink.operator;

import org.apache.flink.api.common.functions.MapFunction;
import org.example.flink.dto.BetslipEvent;
import org.example.flink.dto.Constants;
import org.example.flink.dto.PlayerProfit;

public class ExtractPlayerProfit implements MapFunction<BetslipEvent, PlayerProfit> {

    @Override
    public PlayerProfit map(BetslipEvent betslip) {

        PlayerProfit profit = new PlayerProfit();
        profit.setPlayerId(betslip.getPlayerId());

        if (betslip.getStatus().equals(Constants.STATUS_PLACED)) {
            profit.setTotalProfit(betslip.getWagerAmount().negate());
        } else {
            profit.setTotalProfit(betslip.getReturnAmount().subtract(betslip.getWagerAmount()));
        }

        return profit;
    }
}
