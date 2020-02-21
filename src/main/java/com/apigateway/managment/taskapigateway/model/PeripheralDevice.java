package com.apigateway.managment.taskapigateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "peripherals")
public class PeripheralDevice {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "UUID field is mandatory.")
    @Column(name = "uid")
    private Long uid;

    @NotBlank(message = "Vendor field is mandatory.")
    @Column(name = "vendor")
    private String vendor;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "date_created")
    private Date dateCreated = new Date();

    @NotBlank(message = "status field is mandatory.")
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Gateway gateway;
}
