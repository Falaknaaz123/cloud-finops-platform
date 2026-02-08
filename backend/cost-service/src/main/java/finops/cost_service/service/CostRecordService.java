package finops.cost_service.service;

import finops.cost_service.dto.CostRecordRequest;
import finops.cost_service.dto.CostRecordResponse;
import finops.cost_service.dto.CostSummaryResponse;
import finops.cost_service.entity.CostRecord;
import finops.cost_service.repository.CostRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CostRecordService {

    private final CostRecordRepository costRecordRepository;

    @Transactional
    public CostRecordResponse createCostRecord(CostRecordRequest request) {
        CostRecord costRecord = new CostRecord();
        costRecord.setCloudProvider(request.getCloudProvider());
        costRecord.setServiceName(request.getServiceName());
        costRecord.setResourceId(request.getResourceId());
        costRecord.setResourceName(request.getResourceName());
        costRecord.setCost(request.getCost());
        costRecord.setCurrency(request.getCurrency());
        costRecord.setRegion(request.getRegion());
        costRecord.setAccountId(request.getAccountId());
        costRecord.setDepartment(request.getDepartment());
        costRecord.setEnvironment(request.getEnvironment());
        costRecord.setUsageDate(request.getUsageDate());

        CostRecord saved = costRecordRepository.save(costRecord);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CostRecordResponse> getAllCostRecords() {
        return costRecordRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CostRecordResponse getCostRecordById(Long id) {
        CostRecord costRecord = costRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost record not found with id: " + id));
        return toResponse(costRecord);
    }

    @Transactional(readOnly = true)
    public List<CostRecordResponse> getCostRecordsByProvider(String provider) {
        return costRecordRepository.findByCloudProvider(provider)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CostRecordResponse> getCostRecordsByService(String service) {
        return costRecordRepository.findByServiceName(service)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CostRecordResponse> getCostRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return costRecordRepository.findByUsageDateBetween(startDate, endDate)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CostSummaryResponse getCostSummary() {
        List<CostRecord> allRecords = costRecordRepository.findAll();

        BigDecimal totalCost = costRecordRepository.getTotalCost();
        if (totalCost == null) {
            totalCost = BigDecimal.ZERO;
        }

        // Cost by provider
        Map<String, BigDecimal> costByProvider = allRecords.stream()
                .collect(Collectors.groupingBy(
                        CostRecord::getCloudProvider,
                        Collectors.reducing(BigDecimal.ZERO, CostRecord::getCost, BigDecimal::add)
                ));

        // Cost by service
        Map<String, BigDecimal> costByService = allRecords.stream()
                .collect(Collectors.groupingBy(
                        CostRecord::getServiceName,
                        Collectors.reducing(BigDecimal.ZERO, CostRecord::getCost, BigDecimal::add)
                ));

        CostSummaryResponse summary = new CostSummaryResponse();
        summary.setTotalCost(totalCost);
        summary.setCurrency("USD");
        summary.setTotalRecords((long) allRecords.size());
        summary.setCostByProvider(costByProvider);
        summary.setCostByService(costByService);

        return summary;
    }

    @Transactional
    public void deleteCostRecord(Long id) {
        if (!costRecordRepository.existsById(id)) {
            throw new RuntimeException("Cost record not found with id: " + id);
        }
        costRecordRepository.deleteById(id);
    }

    private CostRecordResponse toResponse(CostRecord costRecord) {
        CostRecordResponse response = new CostRecordResponse();
        response.setId(costRecord.getId());
        response.setCloudProvider(costRecord.getCloudProvider());
        response.setServiceName(costRecord.getServiceName());
        response.setResourceId(costRecord.getResourceId());
        response.setResourceName(costRecord.getResourceName());
        response.setCost(costRecord.getCost());
        response.setCurrency(costRecord.getCurrency());
        response.setRegion(costRecord.getRegion());
        response.setAccountId(costRecord.getAccountId());
        response.setDepartment(costRecord.getDepartment());
        response.setEnvironment(costRecord.getEnvironment());
        response.setUsageDate(costRecord.getUsageDate());
        response.setCreatedAt(costRecord.getCreatedAt());
        return response;
    }
}