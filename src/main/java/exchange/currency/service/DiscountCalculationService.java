package exchange.currency.service;

import exchange.currency.discount.BillAmountDiscountContext;
import exchange.currency.discount.CustomerDiscountContext;
import exchange.currency.domain.BillRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiscountCalculationService {

    private final CustomerDiscountContext customerDiscountContext;

    private final BillAmountDiscountContext amountDiscountContext;

    public double calculateDiscountAmount(BillRequest bill) {

        double discount = customerDiscountContext.applyDiscount(bill) + amountDiscountContext.applyDiscount(bill);

        System.out.println("Discount calculated: "+ discount);

        return Math.max(discount, 0);
    }
}
