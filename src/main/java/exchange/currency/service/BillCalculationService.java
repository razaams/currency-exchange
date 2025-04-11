package exchange.currency.service;

import exchange.currency.client.ExchangeRateClient;
import exchange.currency.domain.BillRequest;
import exchange.currency.domain.BillResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BillCalculationService {

    private final DiscountCalculationService discountCalculationService;
    private final ExchangeRateClient exchangeRateClient;

    public BillResponse calculatePayableBill(BillRequest billRequest) {

        double calculatedDiscount = discountCalculationService.calculateDiscountAmount(billRequest);

        double calculatedBill = Math.max(billRequest.getTotal() - calculatedDiscount, 0);
        double payableAmount = exchangeRateClient.convert(billRequest.getOriginalCurrency(), billRequest.getTargetCurrency(), calculatedBill);

        return BillResponse.builder().payableAmount(payableAmount).currency(billRequest.getTargetCurrency()).build();
    }
}
