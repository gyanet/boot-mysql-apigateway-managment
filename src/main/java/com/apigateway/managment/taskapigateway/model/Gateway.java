package com.apigateway.managment.taskapigateway.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "gateways" ,uniqueConstraints = @UniqueConstraint(columnNames={"serial_number", "ip_address"}))
public class Gateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The serial number field is mandatory.")
    @Column(name = "serial_number")
    private String serialNumber;

    @NotBlank(message = "The name field is mandatory.")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "The ip address field is mandatory.")
    @Column(name = "ip_address")
    private String ipAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gateway", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<PeripheralDevice> peripheralDevices;
}
