package exchange.currency;

import exchange.currency.domain.BillRequest;
import exchange.currency.domain.Item;
import exchange.currency.domain.User;
import exchange.currency.domain.UserType;
import exchange.currency.service.BillCalculationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class BillValidationTests {

    @Autowired
    private BillCalculationService billCalculationService;

    @Test
    void testValidateBill_NullBill() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            billCalculationService.calculatePayableBill(null);
        });

        assertEquals("Bill cannot be null", ex.getMessage());
    }

    @Test
    void testValidateBill_NullUser() {
        BillRequest billWithNullUser = BillRequest.builder().user(null).items(List.of(new Item())).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            billCalculationService.calculatePayableBill(billWithNullUser);
        });

        assertEquals("Bill user cannot be null", ex.getMessage());
    }

    @Test
    void testValidateBill_User_NullUserType() {
        BillRequest billWithNullUserType = BillRequest.builder().user(new User()).items(List.of(new Item())).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            billCalculationService.calculatePayableBill(billWithNullUserType);
        });

        assertEquals("Bill user type cannot be null", ex.getMessage());
    }

    @Test
    void testValidateBill_User_NullCustomerSince() {

        BillRequest billWithNullCustomerSince = BillRequest.builder().user(new User(UserType.CUSTOMER, null))
                .items(List.of(new Item())).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            billCalculationService.calculatePayableBill(billWithNullCustomerSince);
        });

        assertEquals("Bill user customer since cannot be null", ex.getMessage());
    }

    @Test
    void testValidateBill_NullItems() {
        BillRequest billWithNullItems = BillRequest.builder().user(new User(UserType.CUSTOMER, LocalDate.now())).items(null).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            billCalculationService.calculatePayableBill(billWithNullItems);
        });

        assertEquals("Bill items cannot be null", ex.getMessage());
    }

    @Test
    void testValidateBill_EmptyItems() {
        BillRequest billWithEmptyItems = BillRequest.builder().user(new User(UserType.CUSTOMER, LocalDate.now()))
                .items(Collections.emptyList()).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            billCalculationService.calculatePayableBill(billWithEmptyItems);
        });

        assertEquals("Bill items cannot be empty", ex.getMessage());
    }

    @Test
    void testBill_getTotal() {
        BillRequest bill = BillRequest.builder()
                .items(Arrays.asList(new Item("Room cooler", 40, false), new Item("Air condition", 90, true)))
                .build();

        assertEquals(130, bill.getTotal());
    }

    @Test
    void testBill_getNonGroceryTotal() {
        BillRequest bill = BillRequest.builder().items(Arrays.asList(new Item("Room cooler", 40, false),
                new Item("Air condition", 90, true), new Item("Chair", 30, true))).build();

        assertEquals(40, bill.getNonGroceryTotal());
    }

}
