package finops.cost_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cost_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cloudProvider; // AWS, Azure, GCP

    @Column(nullable = false)
    private String serviceName; // EC2, S3, RDS, etc.

    @Column(nullable = false)
    private String resourceId;

    @Column(nullable = false)
    private String resourceName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(nullable = false)
    private String currency; // USD, EUR, etc.

    private String region;

    private String accountId;

    private String department;

    private String environment; // dev, staging, production

    @Column(nullable = false)
    private LocalDateTime usageDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}