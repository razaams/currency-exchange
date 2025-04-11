package exchange.currency;

import exchange.currency.discount.CustomerDiscount;
import exchange.currency.discount.strategy.AffiliateDiscountStrategy;
import exchange.currency.discount.strategy.EmployeeDiscountStrategy;
import exchange.currency.discount.strategy.LoyaltyDiscountStrategy;
import exchange.currency.domain.User;
import exchange.currency.domain.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class CustomerDiscountStrategyApplicabilityTests {

    @Test
    void testEmployeeApplicable() {

        User employee = new User(UserType.EMPLOYEE, LocalDate.now());
        CustomerDiscount employeeDiscountStrategy = new EmployeeDiscountStrategy();

        assertTrue(employeeDiscountStrategy.supports(employee));
    }

    @Test
    void testAffiliateApplicable() {
        User affiliate = new User(UserType.AFFILIATE, LocalDate.now());
        CustomerDiscount affiliateDiscountStrategy = new AffiliateDiscountStrategy();

        assertTrue(affiliateDiscountStrategy.supports(affiliate));
    }

    @Test
    void testLoyalCustomerApplicable() {
        User loyalCustomer = new User(UserType.CUSTOMER, LocalDate.now().minusYears(3));
        CustomerDiscount loyaltyDiscountStrategy = new LoyaltyDiscountStrategy();

        assertTrue(loyaltyDiscountStrategy.supports(loyalCustomer));
    }

    @Test
    void testNotLoyalCustomerApplicable() {
        User loyalCustomer = new User(UserType.CUSTOMER, LocalDate.now());
        CustomerDiscount loyaltyDiscountStrategy = new LoyaltyDiscountStrategy();

        assertFalse(loyaltyDiscountStrategy.supports(loyalCustomer));
    }
}
