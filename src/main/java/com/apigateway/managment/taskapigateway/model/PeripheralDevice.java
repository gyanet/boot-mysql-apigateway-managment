package com.apigateway.managment.taskapigateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder(builderMethodName = "newInstance")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "peripherals")
/*@NamedQueries(
        @NamedQuery(name = "PeripheralDevice.findByVendor", query = "SELECT NEW com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO " +
                "(p.id, p.uid, p.tiempoRespuesta, p.vendor, p.dateCreated, p.status) " +
                "FROM peripherals p WHERE p.vendor = TRIM(:VENDOR)")
)*/
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
    @JoinColumn(name = "gateway_id")
    @EqualsAndHashCode.Exclude
    private Gateway gateway;
}
