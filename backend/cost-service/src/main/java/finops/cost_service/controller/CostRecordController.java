package finops.cost_service.controller;

import finops.cost_service.dto.CostRecordRequest;
import finops.cost_service.dto.CostRecordResponse;
import finops.cost_service.dto.CostSummaryResponse;
import finops.cost_service.service.CostRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/costs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CostRecordController {

    private final CostRecordService costRecordService;

    @PostMapping
    public ResponseEntity<CostRecordResponse> createCostRecord(@Valid @RequestBody CostRecordRequest request) {
        CostRecordResponse response = costRecordService.createCostRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CostRecordResponse>> getAllCostRecords() {
        List<CostRecordResponse> records = costRecordService.getAllCostRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostRecordResponse> getCostRecordById(@PathVariable Long id) {
        CostRecordResponse response = costRecordService.getCostRecordById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<CostRecordResponse>> getCostRecordsByProvider(@PathVariable String provider) {
        List<CostRecordResponse> records = costRecordService.getCostRecordsByProvider(provider);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/service/{service}")
    public ResponseEntity<List<CostRecordResponse>> getCostRecordsByService(@PathVariable String service) {
        List<CostRecordResponse> records = costRecordService.getCostRecordsByService(service);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<CostRecordResponse>> getCostRecordsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<CostRecordResponse> records = costRecordService.getCostRecordsByDateRange(startDate, endDate);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/summary")
    public ResponseEntity<CostSummaryResponse> getCostSummary() {
        CostSummaryResponse summary = costRecordService.getCostSummary();
        return ResponseEntity.ok(summary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCostRecord(@PathVariable Long id) {
        costRecordService.deleteCostRecord(id);
        return ResponseEntity.noContent().build();
    }
}