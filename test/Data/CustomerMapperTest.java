/*
@author Andreas Bergmann (cph-ab435@cphbusiness.dk)
 */
package Data;
import Model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;


public class CustomerMapperTest {
    CustomerMapper customerMapper;
    Customer actualCustomer;

    @BeforeEach
    public void setUp() {
        customerMapper = new CustomerMapper();
    }


    @Test
    public void getId(){
        actualCustomer = customerMapper.getAllCustomers().get(0);

        int expected = 1;
        int actual = actualCustomer.getId();

        assertEquals(expected, actual);
    }
}