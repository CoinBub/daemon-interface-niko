package tech.coinbub.daemon.niko;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Unspent {
    public String txid;
    public Long vout;
    public String address;
    public String account;
    public String scriptPubKey;
    public BigDecimal amount;
    public Long confirmations;
    public Boolean spendable;
}

//niko@test:~$ nikod listunspent
//[
//    {
//        "txid" : "318340a7cdde8c5894ed37df8fb6feb1bc85155a156255f56d729bca7d45bb4e",
//        "vout" : 0,
//        "address" : "mhhhbW3S4XqYiJSzW2kx6krxYSm2JhGRqd",
//        "account" : "",
//        "scriptPubKey" : "76a91417f88e9048c6077605c33641a451b7b2b905ad5288ac",
//        "amount" : 1.00000000,
//        "confirmations" : 55,
//        "spendable" : true
//    },
//    {
//        "txid" : "7653cd34463806c1aa5e4393a8b4f99b3077cebf4fce6f2efa2d648e00641931",
//        "vout" : 0,
//        "address" : "mfrnoci1Qj1MAjwcYmzNGTeQk2CPAJKUrL",
//        "scriptPubKey" : "76a91403c065aa4e428240a3ff6cfbd4f507666f74198388ac",
//        "amount" : 100000000.00000000,
//        "confirmations" : 115,
//        "spendable" : true
//    },
//    {
//        "txid" : "b1fc5345e95d9334c22ac982d9b86fe73874580e9baf24c859ccf5b972db1a69",
//        "vout" : 0,
//        "address" : "mnaoZRNHeT6p34MSrNVq1VwhCUwK6w41nZ",
//        "scriptPubKey" : "76a9144d8340af8888a5e1641c4faf512cb20b50fc9d3e88ac",
//        "amount" : 100000000.00000000,
//        "confirmations" : 119,
//        "spendable" : true
//    }
//]
