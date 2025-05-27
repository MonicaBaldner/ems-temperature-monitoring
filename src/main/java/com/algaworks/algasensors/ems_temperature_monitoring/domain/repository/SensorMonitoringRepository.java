package com.algaworks.algasensors.ems_temperature_monitoring.domain.repository;

import com.algaworks.algasensors.ems_temperature_monitoring.domain.model.SensorId;
import com.algaworks.algasensors.ems_temperature_monitoring.domain.model.SensorMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorMonitoringRepository extends JpaRepository<SensorMonitoring, SensorId> {
}

