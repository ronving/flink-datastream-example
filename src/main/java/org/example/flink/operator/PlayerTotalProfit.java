package org.example.flink.operator;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.example.flink.dto.PlayerProfit;

import java.math.BigDecimal;

public class PlayerTotalProfit implements AggregateFunction<PlayerProfit, PlayerProfitHolder, PlayerProfit> {
    @Override
    public PlayerProfitHolder createAccumulator() {
        return new PlayerProfitHolder();
    }

    @Override
    public PlayerProfitHolder add(PlayerProfit profit, PlayerProfitHolder holder) {
        holder.setPlayerId(holder.playerId);
        holder.setTotalProfit(profit.getTotalProfit());

        return holder;
    }

    @Override
    public PlayerProfitHolder merge(PlayerProfitHolder holder, PlayerProfitHolder acc1) {
        final BigDecimal mergedProfit = holder.getTotalProfit().add(acc1.getTotalProfit());
        holder.setTotalProfit(mergedProfit);

        return holder;
    }

    @Override
    public PlayerProfit getResult(PlayerProfitHolder holder) {
        PlayerProfit playerProfit = new PlayerProfit();
        playerProfit.setPlayerId(holder.playerId);
        playerProfit.setTotalProfit(holder.getTotalProfit());

        return playerProfit;
    }
}
