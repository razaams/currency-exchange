package exchange.currency.discount.strategy;


import exchange.currency.discount.BillAmountDiscount;
import exchange.currency.domain.BillRequest;
import org.springframework.stereotype.Component;

@Component
public class PerHundredAmountDiscountStrategy implements BillAmountDiscount {

    private static final double BULK_DISCOUNT_THRESHOLD = 100;

    private static final double BULK_DISCOUNT_AMOUNT = 5;

    @Override
    public double calculateDiscount(BillRequest bill) {
        return (bill.getTotal() / BULK_DISCOUNT_THRESHOLD) * BULK_DISCOUNT_AMOUNT;
    }

    @Override
    public boolean supports(BillRequest bill) {
        return bill.getTotal() >= BULK_DISCOUNT_THRESHOLD;
    }

}
