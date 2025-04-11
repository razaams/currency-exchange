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

        validateBill(bill);

        double discount = customerDiscountContext.applyDiscount(bill) + amountDiscountContext.applyDiscount(bill);

        System.out.println("Discount calculated: "+ discount);

        return Math.max(discount, 0);
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
