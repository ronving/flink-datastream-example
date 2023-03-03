package org.example.flink.sink;

import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.example.flink.dto.BetslipEvent;
import org.example.flink.dto.PlayerProfit;

public class MySqlSink extends RichSinkFunction<BetslipEvent> {
    public static SinkFunction<PlayerProfit> createProfitSink() {

        return JdbcSink.sink(
                "INSERT INTO player_profit (player_id, total_profit) VALUES(?, ?);",
                (ps, event) -> {
                    ps.setString(1, event.getPlayerId());
                    ps.setBigDecimal(2, event.getTotalProfit());
                },
                JdbcConfig.getExecutionConfig(),
                JdbcConfig.getConnectionOptions());
    }

    public static SinkFunction<BetslipEvent> createBetslipSink() {

        return JdbcSink.sink(
                "INSERT into betslip (betslip_id, player_id, wager_amount, return_amount, odds, placed_at, settled_at, status) values (?, ?, ?, ?, ?, ?, ?, ?);",
                (ps, event) -> {
                    ps.setLong(1, event.getBetslipId());
                    ps.setString(2, event.getPlayerId());
                    ps.setBigDecimal(3, event.getWagerAmount());
                    ps.setBigDecimal(4, event.getReturnAmount());
                    ps.setBigDecimal(5, event.getOdds());
                    ps.setDate(6, java.sql.Date.valueOf(event.getPlacedAt().toLocalDate()));
                    ps.setDate(7, java.sql.Date.valueOf(event.getSettledAt().toLocalDate()));
                    ps.setString(8, event.getStatus());
                    System.out.println("Execute : " + event.getStatus());
                },
                JdbcConfig.getExecutionConfig(),
                JdbcConfig.getConnectionOptions());
    }
}