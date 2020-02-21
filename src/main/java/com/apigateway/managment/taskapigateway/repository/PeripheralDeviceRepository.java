package com.apigateway.managment.taskapigateway.repository;

import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeripheralDeviceRepository extends JpaRepository<PeripheralDevice, Long> {
    Optional<PeripheralDevice> findByUid(Long uuid);
    void deleteById(Long deviceId);
}
