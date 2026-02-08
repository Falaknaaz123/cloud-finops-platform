package finops.cost_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostRecordResponse {
    private Long id;
    private String cloudProvider;
    private String serviceName;
    private String resourceId;
    private String resourceName;
    private BigDecimal cost;
    private String currency;
    private String region;
    private String accountId;
    private String department;
    private String environment;
    private LocalDateTime usageDate;
    private LocalDateTime createdAt;
}