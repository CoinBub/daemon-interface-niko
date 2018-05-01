package tech.coinbub.daemon.niko;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    public String txid;
    public Long version;
    public Long time;
    public Long locktime;
    public List<TxInput> vin;
    public List<TxOutput> vout;
    
    // Long-form transaction data
    public BigDecimal amount;
    public BigDecimal fee;
    public Long confirmations;
    public Boolean generated;
    public String blockhash;
    public Long blockindex;
    public Long blocktime;
    public Long timereceived;
    public List<TransactionDetail> details;
    public String comment;
    public String to;
    
    public Transaction() {}
    public Transaction(final String txid) {
        this.txid = txid;
    }
}

//niko@test:~$ nikod gettransaction b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a
//{
//    "txid" : "b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a",
//    "version" : 1,
//    "time" : 1524090557,
//    "locktime" : 0,
//    "vin" : [
//        {
//            "coinbase" : "01770101",
//            "sequence" : 4294967295
//        }
//    ],
//    "vout" : [
//        {
//            "value" : 0.00000000,
//            "n" : 0,
//            "scriptPubKey" : {
//                "asm" : "OP_DUP OP_HASH160 8d0ab384611937a37dd357e2d593b102bde0c763 OP_EQUALVERIFY OP_CHECKSIG",
//                "hex" : "76a9148d0ab384611937a37dd357e2d593b102bde0c76388ac",
//                "reqSigs" : 1,
//                "type" : "pubkeyhash",
//                "addresses" : [
//                    "mtNiPdupyDA7LfG9Mbwx3qXweoiZKSsTtY"
//                ]
//            }
//        }
//    ],
//    "amount" : 0.00000000,
//    "confirmations" : 1,
//    "generated" : true,
//    "blockhash" : "37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d",
//    "blockindex" : 0,
//    "blocktime" : 1524090557,
//    "txid" : "b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a",
//    "time" : 1524090557,
//    "timereceived" : 1524090561,
//    "details" : [
//        {
//            "account" : "",
//            "address" : "mtNiPdupyDA7LfG9Mbwx3qXweoiZKSsTtY",
//            "category" : "immature",
//            "amount" : 0.00000000
//        }
//    ]
//}
