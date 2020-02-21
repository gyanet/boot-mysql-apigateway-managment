package com.apigateway.managment.taskapigateway.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<Violation>();

}

