package com.store.object;

import com.store.domain.Customer;
import com.store.dto.ProductOrder;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.autoconfigure.json.*;
import org.springframework.boot.test.json.*;
import org.springframework.test.context.junit4.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class ObjectTest {

    private JacksonTester<Customer> json;
    private JacksonTester<ProductOrder> jsonProd;

    @Test
    public void testSerialize() throws Exception {
        Customer details =
                new Customer("kronus", 9999999, "av beigin", 75548484, "kronus@wing.com");
        assertThat(this.json.write(details)).hasJsonPathStringValue("@.dni");
        assertThat(this.json.write(details)).extractingJsonPathStringValue("@.name")
                .isEqualTo("kronus");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"prodId\":3,\"amount\":1}";
        assertThat(this.jsonProd.parse(content))
                .isEqualTo(new ProductOrder(3, 1));
        assertThat(this.jsonProd.parseObject(content).getProdId()).isEqualTo(1);
    }
}
