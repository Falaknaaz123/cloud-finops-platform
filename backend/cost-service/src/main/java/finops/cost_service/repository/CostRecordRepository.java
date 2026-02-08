package finops.cost_service.repository;

import finops.cost_service.entity.CostRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CostRecordRepository extends JpaRepository<CostRecord, Long> {

    List<CostRecord> findByCloudProvider(String cloudProvider);

    List<CostRecord> findByServiceName(String serviceName);

    List<CostRecord> findByUsageDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT SUM(c.cost) FROM CostRecord c")
    BigDecimal getTotalCost();

    @Query("SELECT SUM(c.cost) FROM CostRecord c WHERE c.cloudProvider = :provider")
    BigDecimal getTotalCostByProvider(String provider);

    @Query("SELECT c.serviceName, SUM(c.cost) FROM CostRecord c GROUP BY c.serviceName")
    List<Object[]> getCostByService();
}