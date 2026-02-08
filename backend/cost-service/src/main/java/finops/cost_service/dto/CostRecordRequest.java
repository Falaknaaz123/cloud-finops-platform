package finops.cost_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostRecordRequest {

    @NotBlank(message = "Cloud provider is required")
    private String cloudProvider;

    @NotBlank(message = "Service name is required")
    private String serviceName;

    @NotBlank(message = "Resource ID is required")
    private String resourceId;

    @NotBlank(message = "Resource name is required")
    private String resourceName;

    @NotNull(message = "Cost is required")
    @Positive(message = "Cost must be positive")
    private BigDecimal cost;

    @NotBlank(message = "Currency is required")
    private String currency;

    private String region;

    private String accountId;

    private String department;

    private String environment;

    @NotNull(message = "Usage date is required")
    private LocalDateTime usageDate;
}