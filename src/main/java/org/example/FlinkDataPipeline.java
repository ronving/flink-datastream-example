package org.example;

import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.example.flink.dto.BetslipEvent;
import org.example.flink.dto.PlayerProfit;
import org.example.flink.operator.ExtractPlayerProfit;
import org.example.flink.operator.ParseBetslipEvent;
import org.example.flink.operator.PlayerTotalProfit;
import org.example.flink.sink.MySqlSink;

import static org.example.kafka.Consumer.createStringConsumerForTopic;

public class FlinkDataPipeline {

    public static void main(String[] args) throws Exception {
        flinkDataStream();
    }

    public static void flinkDataStream() throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        FlinkKafkaConsumer<String> flinkKafkaConsumer = createFlinkKafkaConsumer();
        flinkKafkaConsumer.setStartFromEarliest();

        SingleOutputStreamOperator<BetslipEvent> dataStream = environment.addSource(flinkKafkaConsumer)
                                                                         .map(new ParseBetslipEvent());

        dataStream.addSink(MySqlSink.createBetslipSink());

        // player profit counter datastream
        dataStream.map(new ExtractPlayerProfit())
                  .keyBy(PlayerProfit::getPlayerId)
                  .window(TumblingProcessingTimeWindows.of(Time.seconds(4)))
                  .aggregate(new PlayerTotalProfit())
                  .addSink(MySqlSink.createProfitSink());

        environment.execute();
    }

    private static FlinkKafkaConsumer<String> createFlinkKafkaConsumer() {
        String inputTopic = "flink_input";
        String consumerGroup = "betslips";
        String kafkaAddress = "localhost:9092";

        return createStringConsumerForTopic(inputTopic, kafkaAddress, consumerGroup);
    }
}
