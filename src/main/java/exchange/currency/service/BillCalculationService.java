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

        validateBill(billRequest);

        double calculatedDiscount = discountCalculationService.calculateDiscountAmount(billRequest);

        double calculatedBill = Math.max(billRequest.getTotal() - calculatedDiscount, 0);
        double payableAmount = exchangeRateClient.convert(billRequest.getOriginalCurrency(), billRequest.getTargetCurrency(), calculatedBill);

        return BillResponse.builder().payableAmount(payableAmount).currency(billRequest.getTargetCurrency()).build();
    }

    public void validateBill(BillRequest bill) {
        if (bill == null)
            throw new IllegalArgumentException("Bill cannot be null");

        if (bill.getUser() == null)
            throw new IllegalArgumentException("Bill user cannot be null");

        if (bill.getUser().getUserType() == null)
            throw new IllegalArgumentException("Bill user type cannot be null");

        if (bill.getUser().getCustomerSince() == null)
            throw new IllegalArgumentException("Bill user customer since cannot be null");

        if (bill.getItems() == null)
            throw new IllegalArgumentException("Bill items cannot be null");

        if (bill.getItems().isEmpty())
            throw new IllegalArgumentException("Bill items cannot be empty");
    }
}
