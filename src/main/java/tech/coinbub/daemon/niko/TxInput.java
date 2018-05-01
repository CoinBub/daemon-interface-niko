package tech.coinbub.daemon.niko;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TxInput {
    public String coinbase;
    public long sequence;

    public String txid;
    public Long vout;
    public ScriptSignature scriptSig;
}
