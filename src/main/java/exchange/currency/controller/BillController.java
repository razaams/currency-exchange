package exchange.currency.controller;

import exchange.currency.domain.BillRequest;
import exchange.currency.domain.BillResponse;
import exchange.currency.service.BillCalculationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BillController {

    private final BillCalculationService billCalculationService;

    @PostMapping("/calculate")
    public ResponseEntity<BillResponse> calculatePayable(@Valid @RequestBody BillRequest billRequest) {
        return ResponseEntity.ok(billCalculationService.calculatePayableBill(billRequest));
    }
}
