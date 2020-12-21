package transactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataProviderManager {

    private Object manager;
    private Object transactionManager;
    private boolean nestedTransaction;

}
