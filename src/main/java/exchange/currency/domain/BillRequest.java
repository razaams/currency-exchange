package exchange.currency.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillRequest {
    @NotNull(message = "Bill items cannot be null")
    @Size(min = 1, message = "Bill items cannot be empty")
    private List<Item> items;

    @Valid
    @NotNull(message = "Bill user cannot be null")
    private User user;

    @NotEmpty(message = "Original currency cannot be null")
    private String originalCurrency;

    @NotEmpty(message = "Target currency cannot be null")
    private String targetCurrency;

    public double getTotal() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    public double getNonGroceryTotal() {
        return items.stream().filter(item -> !item.isGrocery()).mapToDouble(Item::getPrice).sum();
    }
}
