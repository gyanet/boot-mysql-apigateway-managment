package com.apigateway.managment.taskapigateway.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Log
public class Violation {

    private String fieldName;
    private String message;

}
