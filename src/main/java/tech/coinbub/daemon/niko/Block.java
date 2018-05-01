package tech.coinbub.daemon.niko;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a single block in the blockchain.
 * 
 * Received when calling `getblock`.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Block {
    public String hash;
    public Long confirmations;
    public Long size;
    public Long height;
    public Long version;
    public String merkleroot;
    public BigDecimal mint;
    public Long time;
    public Long nonce;
    public String bits;
    public BigDecimal difficulty;
    public String blocktrust;
    public String chaintrust;
    public String previousblockhash;
    public String flags;
    public String proofhash;
    public Long entropybit;
    public String modifier;
    public String modifierv2;
    public List<Transaction> tx;
}

//niko@test:~$ nikod getblock 37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d
//{
//    "hash" : "37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d",
//    "confirmations" : 1,
//    "size" : 175,
//    "height" : 119,
//    "version" : 7,
//    "merkleroot" : "b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a",
//    "mint" : 0.00000000,
//    "time" : 1524090557,
//    "nonce" : 1905786880,
//    "bits" : "1e1392e7",
//    "difficulty" : 0.00019956,
//    "blocktrust" : "d1424",
//    "chaintrust" : "33c2f65",
//    "previousblockhash" : "6f7ac94cd55f03e9121dc682e8075822c4bb03c0323301706f98e7af8d7dcd7d",
//    "flags" : "proof-of-work",
//    "proofhash" : "0000015a957f6eb0689d50c624cf09808baaa1d1096f5c1c1e43c290b4684349",
//    "entropybit" : 1,
//    "modifier" : "b33791cc95a6841b",
//    "modifierv2" : "e5f282096ead2d028d407ecca7ce95ea62e30a67334eab5c6656a2b847582c3d",
//    "tx" : [
//        "b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"
//    ]
//}
//niko@test:~$ nikod getblock 37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d true
//{
//    "hash" : "37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d",
//    "confirmations" : 1,
//    "size" : 175,
//    "height" : 119,
//    "version" : 7,
//    "merkleroot" : "b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a",
//    "mint" : 0.00000000,
//    "time" : 1524090557,
//    "nonce" : 1905786880,
//    "bits" : "1e1392e7",
//    "difficulty" : 0.00019956,
//    "blocktrust" : "d1424",
//    "chaintrust" : "33c2f65",
//    "previousblockhash" : "6f7ac94cd55f03e9121dc682e8075822c4bb03c0323301706f98e7af8d7dcd7d",
//    "flags" : "proof-of-work",
//    "proofhash" : "0000015a957f6eb0689d50c624cf09808baaa1d1096f5c1c1e43c290b4684349",
//    "entropybit" : 1,
//    "modifier" : "b33791cc95a6841b",
//    "modifierv2" : "e5f282096ead2d028d407ecca7ce95ea62e30a67334eab5c6656a2b847582c3d",
//    "tx" : [
//        {
//            "txid" : "b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a",
//            "txid" : "b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a",
//            "version" : 1,
//            "time" : 1524090557,
//            "locktime" : 0,
//            "vin" : [
//                {
//                    "coinbase" : "01770101",
//                    "sequence" : 4294967295
//                }
//            ],
//            "vout" : [
//                {
//                    "value" : 0.00000000,
//                    "n" : 0,
//                    "scriptPubKey" : {
//                        "asm" : "OP_DUP OP_HASH160 8d0ab384611937a37dd357e2d593b102bde0c763 OP_EQUALVERIFY OP_CHECKSIG",
//                        "hex" : "76a9148d0ab384611937a37dd357e2d593b102bde0c76388ac",
//                        "reqSigs" : 1,
//                        "type" : "pubkeyhash",
//                        "addresses" : [
//                            "mtNiPdupyDA7LfG9Mbwx3qXweoiZKSsTtY"
//                        ]
//                    }
//                }
//            ]
//        }
//    ]
//}
