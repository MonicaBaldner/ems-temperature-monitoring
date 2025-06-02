package com.algaworks.algasensors.ems_temperature_monitoring.infrastructure.rabbitmq;

import com.algaworks.algasensors.ems_temperature_monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.ems_temperature_monitoring.domain.service.SensorAlertService;
import com.algaworks.algasensors.ems_temperature_monitoring.domain.service.TemperatureMonitoringService;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

import static com.algaworks.algasensors.ems_temperature_monitoring.infrastructure.rabbitmq.RabbitMQConfig.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService; //aula 12.15
    private final SensorAlertService sensorAlertService; //aula 12.17
/*substituido pelo abaixo na 12.17

 //  @RabbitListener(queues = QUEUE) substituido pelo abaixo na 12.16
    @RabbitListener(queues = QUEUE, concurrency = "2-3") //aula 12.16
    @SneakyThrows

   */
/*comentado na 12.15
    public void handle(@Payload TemperatureLogData temperatureLogData,
                       @Headers Map<String, Object> headers
    ) {
       TSID sensorId = temperatureLogData.getSensorId();
        Double temperature = temperatureLogData.getValue();
        log.info("Temperature updated: SensorId {} Temp {}", sensorId, temperature);
        log.info("Headers: {}", headers);
*//*

    public void handle(@Payload TemperatureLogData temperatureLogData){ //12.15
       temperatureMonitoringService.processTemperatureReading(temperatureLogData); //12.15
        Thread.sleep(Duration.ofSeconds(5));
    }
*/

    //aula 12.17
    @RabbitListener(queues = QUEUE_PROCESS_TEMPERATURE, concurrency = "2-3")
    @SneakyThrows
    public void handleProcessingTemperature(@Payload TemperatureLogData temperatureLogData) {
        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }

    @RabbitListener(queues = QUEUE_ALERTING, concurrency = "2-3")
    @SneakyThrows
    public void handleAlerting(@Payload TemperatureLogData temperatureLogData) {
      //  log.info("Alerting: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
        sensorAlertService.handleAlert(temperatureLogData); //12.17
        Thread.sleep(Duration.ofSeconds(5));
    }
    //fim 12.17
}
