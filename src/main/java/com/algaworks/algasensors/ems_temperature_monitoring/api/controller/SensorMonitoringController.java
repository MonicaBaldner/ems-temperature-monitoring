package com.algaworks.algasensors.ems_temperature_monitoring.api.controller;

import com.algaworks.algasensors.ems_temperature_monitoring.api.model.SensorMonitoringOutput;
import com.algaworks.algasensors.ems_temperature_monitoring.domain.model.SensorId;
import com.algaworks.algasensors.ems_temperature_monitoring.domain.model.SensorMonitoring;
import com.algaworks.algasensors.ems_temperature_monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {

    private final SensorMonitoringRepository sensorMonitoringRepository;

    @GetMapping
    public SensorMonitoringOutput getDetail(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);

        return SensorMonitoringOutput.builder()
                .id(sensorMonitoring.getId().getValue())
                .enabled(sensorMonitoring.getEnabled())
                .lastTemperature(sensorMonitoring.getLastTemperature())
                .updatedAt(sensorMonitoring.getUpdatedAt())
                .build();
    }

    private SensorMonitoring findByIdOrDefault(TSID sensorId) {
        return sensorMonitoringRepository.findById(new SensorId(sensorId))
                .orElse(SensorMonitoring.builder()
                        .id(new SensorId(sensorId))
                        .enabled(false)
                        .lastTemperature(null)
                        .updatedAt(null)
                        .build());
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        //aula 11.03
        if(Boolean.TRUE.equals(sensorMonitoring.getEnabled())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        //fim aula 11.03
        sensorMonitoring.setEnabled(true);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

    @SneakyThrows //aula 11.04
    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        //aula 11.04
        if(Boolean.FALSE.equals(sensorMonitoring.getEnabled())){
            Thread.sleep(Duration.ofSeconds(10));
        }
        //fim aula 11.04
        sensorMonitoring.setEnabled(false);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

}

