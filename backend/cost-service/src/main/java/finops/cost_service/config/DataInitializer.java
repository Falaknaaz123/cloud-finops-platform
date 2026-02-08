package finops.cost_service.config;

import finops.cost_service.entity.CostRecord;
import finops.cost_service.repository.CostRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final CostRecordRepository costRecordRepository;

    @Override
    public void run(String... args) {
        // Only initialize if database is empty
        if (costRecordRepository.count() == 0) {
            log.info("Initializing sample cost data...");
            List<CostRecord> sampleData = createSampleData();
            costRecordRepository.saveAll(sampleData);
            log.info("Successfully initialized {} cost records", sampleData.size());
        } else {
            log.info("Database already contains data. Skipping initialization.");
        }
    }

    private List<CostRecord> createSampleData() {
        List<CostRecord> records = new ArrayList<>();
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();

        // AWS Costs
        records.add(createCostRecord("AWS", "EC2", "i-1234567890abcdef0", "production-web-server-1",
                new BigDecimal("1247.50"), "USD", "us-east-1", "123456789012",
                "Engineering", "production", now.minusDays(1)));

        records.add(createCostRecord("AWS", "EC2", "i-0987654321fedcba0", "staging-web-server-1",
                new BigDecimal("523.75"), "USD", "us-east-1", "123456789012",
                "Engineering", "staging", now.minusDays(1)));

        records.add(createCostRecord("AWS", "RDS", "db-postgresql-prod-1", "production-database",
                new BigDecimal("2150.00"), "USD", "us-east-1", "123456789012",
                "Engineering", "production", now.minusDays(1)));

        records.add(createCostRecord("AWS", "S3", "finops-data-bucket", "data-storage-bucket",
                new BigDecimal("487.25"), "USD", "us-east-1", "123456789012",
                "Data", "production", now.minusDays(1)));

        records.add(createCostRecord("AWS", "Lambda", "cost-analyzer-function", "cost-analysis-lambda",
                new BigDecimal("125.50"), "USD", "us-east-1", "123456789012",
                "Engineering", "production", now.minusDays(1)));

        records.add(createCostRecord("AWS", "CloudWatch", "monitoring-logs", "application-monitoring",
                new BigDecimal("234.00"), "USD", "us-east-1", "123456789012",
                "Operations", "production", now.minusDays(1)));

        records.add(createCostRecord("AWS", "ELB", "prod-load-balancer", "production-alb",
                new BigDecimal("456.80"), "USD", "us-east-1", "123456789012",
                "Engineering", "production", now.minusDays(1)));

        // Azure Costs
        records.add(createCostRecord("Azure", "Virtual Machines", "vm-prod-app-01", "production-app-vm",
                new BigDecimal("892.30"), "USD", "eastus", "sub-azure-001",
                "Engineering", "production", now.minusDays(1)));

        records.add(createCostRecord("Azure", "SQL Database", "sqldb-prod-main", "production-sql-database",
                new BigDecimal("1567.90"), "USD", "eastus", "sub-azure-001",
                "Engineering", "production", now.minusDays(1)));

        records.add(createCostRecord("Azure", "Blob Storage", "storage-prod-data", "production-blob-storage",
                new BigDecimal("321.45"), "USD", "eastus", "sub-azure-001",
                "Data", "production", now.minusDays(1)));

        records.add(createCostRecord("Azure", "App Service", "app-prod-api", "production-api-service",
                new BigDecimal("678.20"), "USD", "eastus", "sub-azure-001",
                "Engineering", "production", now.minusDays(1)));

        // GCP Costs
        records.add(createCostRecord("GCP", "Compute Engine", "instance-prod-1", "production-compute-instance",
                new BigDecimal("734.60"), "USD", "us-central1", "project-gcp-001",
                "Engineering", "production", now.minusDays(1)));

        records.add(createCostRecord("GCP", "Cloud Storage", "bucket-prod-data", "production-storage-bucket",
                new BigDecimal("412.35"), "USD", "us-central1", "project-gcp-001",
                "Data", "production", now.minusDays(1)));

        records.add(createCostRecord("GCP", "Cloud SQL", "cloudsql-prod-main", "production-cloud-sql",
                new BigDecimal("1123.75"), "USD", "us-central1", "project-gcp-001",
                "Engineering", "production", now.minusDays(1)));

        records.add(createCostRecord("GCP", "BigQuery", "bigquery-analytics", "analytics-warehouse",
                new BigDecimal("567.90"), "USD", "us-central1", "project-gcp-001",
                "Data", "production", now.minusDays(1)));

        // Add some development environment costs
        records.add(createCostRecord("AWS", "EC2", "i-dev-web-001", "dev-web-server",
                new BigDecimal("142.50"), "USD", "us-west-2", "123456789012",
                "Engineering", "development", now.minusDays(1)));

        records.add(createCostRecord("AWS", "RDS", "db-dev-postgres", "dev-database",
                new BigDecimal("234.75"), "USD", "us-west-2", "123456789012",
                "Engineering", "development", now.minusDays(1)));

        records.add(createCostRecord("Azure", "Virtual Machines", "vm-dev-test-01", "dev-test-vm",
                new BigDecimal("178.40"), "USD", "westus", "sub-azure-001",
                "Engineering", "development", now.minusDays(1)));

        records.add(createCostRecord("GCP", "Compute Engine", "instance-dev-1", "dev-compute-instance",
                new BigDecimal("156.30"), "USD", "us-west1", "project-gcp-001",
                "Engineering", "development", now.minusDays(1)));

        // Add some historical data (last 7 days)
        for (int i = 2; i <= 7; i++) {
            LocalDateTime date = now.minusDays(i);
            double variance = 0.8 + (random.nextDouble() * 0.4); // 80% to 120% of base cost

            records.add(createCostRecord("AWS", "EC2", "i-1234567890abcdef0", "production-web-server-1",
                    new BigDecimal(1247.50 * variance), "USD", "us-east-1", "123456789012",
                    "Engineering", "production", date));

            records.add(createCostRecord("AWS", "RDS", "db-postgresql-prod-1", "production-database",
                    new BigDecimal(2150.00 * variance), "USD", "us-east-1", "123456789012",
                    "Engineering", "production", date));

            records.add(createCostRecord("Azure", "SQL Database", "sqldb-prod-main", "production-sql-database",
                    new BigDecimal(1567.90 * variance), "USD", "eastus", "sub-azure-001",
                    "Engineering", "production", date));
        }

        return records;
    }

    private CostRecord createCostRecord(String cloudProvider, String serviceName, String resourceId,
                                        String resourceName, BigDecimal cost, String currency,
                                        String region, String accountId, String department,
                                        String environment, LocalDateTime usageDate) {
        CostRecord record = new CostRecord();
        record.setCloudProvider(cloudProvider);
        record.setServiceName(serviceName);
        record.setResourceId(resourceId);
        record.setResourceName(resourceName);
        record.setCost(cost);
        record.setCurrency(currency);
        record.setRegion(region);
        record.setAccountId(accountId);
        record.setDepartment(department);
        record.setEnvironment(environment);
        record.setUsageDate(usageDate);
        return record;
    }
}