package exchange.currency.discount.strategy;


import exchange.currency.discount.CustomerDiscount;
import exchange.currency.domain.BillRequest;
import exchange.currency.domain.User;
import exchange.currency.domain.UserType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoyaltyDiscountStrategy implements CustomerDiscount {

    private static final double DISCOUNT_PERCENTAGE = 5;

    @Override
    public double calculateDiscount(BillRequest bill) {
        return (DISCOUNT_PERCENTAGE / 100) * bill.getNonGroceryTotal();
    }

    @Override
    public boolean supports(User user) {
        return UserType.CUSTOMER.equals(user.getUserType())
                && user.getCustomerSince().isBefore(LocalDate.now().minusYears(2));
    }
}
