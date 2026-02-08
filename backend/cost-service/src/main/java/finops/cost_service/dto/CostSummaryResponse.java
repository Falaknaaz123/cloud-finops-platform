package finops.cost_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostSummaryResponse {
    private BigDecimal totalCost;
    private String currency;
    private Long totalRecords;
    private Map<String, BigDecimal> costByProvider;
    private Map<String, BigDecimal> costByService;
}