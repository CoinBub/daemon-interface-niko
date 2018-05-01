package tech.coinbub.daemon.niko;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDetail {
    public String account;
    public String address;
    public Category category;
    public BigDecimal amount;
    public BigDecimal fee;

    public enum Category {
        immature,
        send,
        receive
    }
}