package exchange.currency.discount.strategy;


import exchange.currency.discount.CustomerDiscount;
import exchange.currency.domain.BillRequest;
import exchange.currency.domain.User;
import exchange.currency.domain.UserType;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDiscountStrategy implements CustomerDiscount {

	private static final double DISCOUNT_PERCENTAGE = 30;

	@Override
	public double calculateDiscount(BillRequest bill) {
		return (DISCOUNT_PERCENTAGE / 100) * bill.getNonGroceryTotal();
	}

	@Override
	public boolean supports(User user) {
		return UserType.EMPLOYEE.equals(user.getUserType());
	}
}
