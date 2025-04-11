package exchange.currency.discount;


import exchange.currency.domain.BillRequest;
import exchange.currency.domain.User;

public interface CustomerDiscount {
	double calculateDiscount(BillRequest bill);

	boolean supports(User user);
}
