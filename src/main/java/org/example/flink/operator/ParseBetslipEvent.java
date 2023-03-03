package org.example.flink.operator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.example.flink.dto.BetslipEvent;
import org.example.flink.dto.Constants;

public class ParseBetslipEvent implements MapFunction<String, BetslipEvent> {

    static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BetslipEvent map(String kafkaEventMessage) throws Exception {

        BetslipEvent betslipEvent = objectMapper.readValue(kafkaEventMessage, BetslipEvent.class);
        String status = betslipEvent.getMessageEventType() == Constants.PLACED_EVENT ? Constants.STATUS_PLACED : Constants.STATUS_SETTLED;
        betslipEvent.setStatus(status);
        if (status.equals("SETTLED")) {
            betslipEvent.setReturnAmount(betslipEvent.getReturnAmount());
            betslipEvent.setSettledAt(betslipEvent.getSettledAt());
        }
        return betslipEvent;
    }

}
