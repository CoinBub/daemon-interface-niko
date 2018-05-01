package tech.coinbub.daemon.niko;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TxOutput {
    public BigDecimal value;
    public long n;
    public ScriptPublicKey scriptPubKey;
}
