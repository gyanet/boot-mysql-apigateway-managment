package com.apigateway.managment.taskapigateway.repository;

import com.apigateway.managment.taskapigateway.model.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GatewayRepository extends JpaRepository<Gateway, Long> {
    Optional<Gateway> findGatewayByIpAddress(String ipAddress);
    Optional<Gateway> findGatewayBySerialNumber(String serialNumber);

}
