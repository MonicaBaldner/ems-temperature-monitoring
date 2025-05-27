package com.algaworks.algasensors.ems_temperature_monitoring.domain.repository;

import com.algaworks.algasensors.ems_temperature_monitoring.domain.model.SensorAlert;
import com.algaworks.algasensors.ems_temperature_monitoring.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorAlertRepository extends JpaRepository<SensorAlert, SensorId> {
}

