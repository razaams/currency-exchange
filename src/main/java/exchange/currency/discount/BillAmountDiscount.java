package exchange.currency.discount;


import exchange.currency.domain.BillRequest;

public interface BillAmountDiscount {

    double calculateDiscount(BillRequest bill);

    boolean supports(BillRequest bill);
}
