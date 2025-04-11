package exchange.currency.discount;


import exchange.currency.domain.BillRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BillAmountDiscountContext {

    private final List<BillAmountDiscount> amountDiscountStrategies;

    public double applyDiscount(BillRequest bill) {
        return amountDiscountStrategies.stream().filter(strategy -> strategy.supports(bill)).findFirst()
                .map(strategy -> strategy.calculateDiscount(bill)).orElse(0d);
    }
}
