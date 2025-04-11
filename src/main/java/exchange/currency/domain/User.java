package exchange.currency.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull(message = "Bill user type cannot be null")
    private UserType userType;

    @NotNull(message = "Bill user customer since cannot be null")
    private LocalDate customerSince;
}
