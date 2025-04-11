package exchange.currency.discount;


import exchange.currency.domain.BillRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CustomerDiscountContext {

	private final List<CustomerDiscount> customerDiscountStrategies;

	public double applyDiscount(BillRequest bill) {
		return customerDiscountStrategies.stream().filter(strategy -> strategy.supports(bill.getUser())).findFirst()
				.map(strategy -> strategy.calculateDiscount(bill)).orElse(0d);
	}
}
