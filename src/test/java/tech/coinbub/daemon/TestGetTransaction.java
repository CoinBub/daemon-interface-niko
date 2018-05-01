package tech.coinbub.daemon;

import tech.coinbub.daemon.Niko;
import tech.coinbub.daemon.niko.ScriptPublicKey;
import tech.coinbub.daemon.niko.ScriptSignature;
import tech.coinbub.daemon.niko.Transaction;
import tech.coinbub.daemon.niko.TransactionDetail;
import tech.coinbub.daemon.niko.TxInput;
import tech.coinbub.daemon.niko.TxOutput;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class TestGetTransaction {
    @Test
    public void canGetTransaction(final Niko niko) {
        final Transaction tx = niko.gettransaction("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a");
        Assertions.assertAll(
                () -> { assertThat(tx.txid, is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))); },
                () -> { assertThat(tx.version, is(equalTo(1L))); },
                () -> { assertThat(tx.time, is(equalTo(1524090557L))); },
                () -> { assertThat(tx.locktime, is(equalTo(0L))); },
                () -> { assertThat(tx.vin, hasSize(1)); },
                () -> { assertThat(tx.vout, hasSize(1)); },
                () -> { assertThat(tx.amount, is(equalTo(new BigDecimal("0.0")))); },
                () -> { assertThat(tx.confirmations, is(equalTo(1L))); },
                () -> { assertThat(tx.generated, is(equalTo(true))); },
                () -> { assertThat(tx.blockhash, is(equalTo("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d"))); },
                () -> { assertThat(tx.blockindex, is(equalTo(0L))); },
                () -> { assertThat(tx.blocktime, is(equalTo(1524090557L))); },
                () -> { assertThat(tx.timereceived, is(equalTo(1524090561L))); },
                () -> { assertThat(tx.details, hasSize(1)); }
        );
        
        final TransactionDetail detail = tx.details.get(0);
        Assertions.assertAll(
                () -> { assertThat(detail.account, isEmptyString()); },
                () -> { assertThat(detail.address, is(equalTo("mtNiPdupyDA7LfG9Mbwx3qXweoiZKSsTtY"))); },
                () -> { assertThat(detail.category, is(equalTo(TransactionDetail.Category.immature))); },
                () -> { assertThat(detail.amount, is(equalTo(new BigDecimal("0.0")))); }
        );
        
        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final TxInput in = tx.vin.get(0);
        Assertions.assertAll(
                () -> { assertThat(in.coinbase, is(equalTo("01770101"))); },
                () -> { assertThat(in.sequence, is(equalTo(4294967295L))); }
        );

        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final TxOutput out = tx.vout.get(0);
        Assertions.assertAll(
                () -> { assertThat(out.value, is(equalTo(new BigDecimal("0.0")))); },
                () -> { assertThat(out.n, is(equalTo(0L))); }
        );

        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final ScriptPublicKey key = out.scriptPubKey;
        Assertions.assertAll(
                () -> { assertThat(key.asm, is(equalTo("OP_DUP OP_HASH160 8d0ab384611937a37dd357e2d593b102bde0c763 OP_EQUALVERIFY OP_CHECKSIG"))); },
                () -> { assertThat(key.hex, is(equalTo("76a9148d0ab384611937a37dd357e2d593b102bde0c76388ac"))); },
                () -> { assertThat(key.type, is(equalTo(ScriptPublicKey.Type.pubkeyhash))); },
                () -> { assertThat(key.reqSigs, is(equalTo(1L))); },
                () -> { assertThat(key.addresses, hasSize(1)); },
                () -> { assertThat(key.addresses.get(0), is(equalTo("mtNiPdupyDA7LfG9Mbwx3qXweoiZKSsTtY"))); }
        );
    }

    @Test
    public void supportsMultipleVouts(final Niko niko) {
        final Transaction tx = niko.gettransaction("64c664bf2d296d6ea629cf5177eee69806ce2cdc39771b5115e6de28291e6b39");
        Assertions.assertAll(
                () -> { assertThat(tx.txid, is(equalTo("64c664bf2d296d6ea629cf5177eee69806ce2cdc39771b5115e6de28291e6b39"))); },
                () -> { assertThat(tx.version, is(equalTo(1L))); },
                () -> { assertThat(tx.time, is(equalTo(1524769683L))); },
                () -> { assertThat(tx.locktime, is(equalTo(64L))); },
                () -> { assertThat(tx.vin, hasSize(1)); },
                () -> { assertThat(tx.vout, hasSize(2)); },
                () -> { assertThat(tx.amount, is(equalTo(new BigDecimal("10.0")))); },
                () -> { assertThat(tx.fee, is(nullValue())); },
                () -> { assertThat(tx.confirmations, is(equalTo(0L))); },
                () -> { assertThat(tx.generated, is(nullValue())); },
                () -> { assertThat(tx.blockhash, is(nullValue())); },
                () -> { assertThat(tx.blockindex, is(nullValue())); },
                () -> { assertThat(tx.blocktime, is(nullValue())); },
                () -> { assertThat(tx.timereceived, is(equalTo(1524769683L))); },
                () -> { assertThat(tx.details, hasSize(1)); }
        );
        
        final TransactionDetail detail = tx.details.get(0);
        Assertions.assertAll(
                () -> { assertThat(detail.account, isEmptyString()); },
                () -> { assertThat(detail.address, is(equalTo("n2FqeWBwAYEkJiANYGRy7jBL3n8uNPCZMa"))); },
                () -> { assertThat(detail.category, is(equalTo(TransactionDetail.Category.receive))); },
                () -> { assertThat(detail.amount, is(equalTo(new BigDecimal("10.0")))); },
                () -> { assertThat(detail.fee, is(nullValue())); }
        );
        
        final TxInput in = tx.vin.get(0);
        Assertions.assertAll(
                () -> { assertThat(in.txid, is(equalTo("17862ba71b9e93acaf68a02f79f4f2bd7cecbc466c5f6e9a08312064850e9ce0"))); },
                () -> { assertThat(in.vout, is(equalTo(0L))); },
                () -> { assertThat(in.scriptSig, is(not(nullValue()))); }
        );

        final ScriptSignature sig = in.scriptSig;
        Assertions.assertAll(
                () -> { assertThat(sig.asm, is(equalTo("304402205bbb3fd2f0a73f710d298feb7c83e14a841de72a73bf34e941e455460390e9f902200306b3f28b6647c5bf55c42f20777a0eb2809a15827d9957bd158dfa1be3846e01 032da351eef0ee32cf6825003c17ec9a6f0cc89be405cd4cb825664fac988c837f"))); },
                () -> { assertThat(sig.hex, is(equalTo("47304402205bbb3fd2f0a73f710d298feb7c83e14a841de72a73bf34e941e455460390e9f902200306b3f28b6647c5bf55c42f20777a0eb2809a15827d9957bd158dfa1be3846e0121032da351eef0ee32cf6825003c17ec9a6f0cc89be405cd4cb825664fac988c837f"))); }
        );

        final TxOutput out = tx.vout.get(0);
        Assertions.assertAll(
                () -> { assertThat(out.value, is(equalTo(new BigDecimal("99999989.9999")))); },
                () -> { assertThat(out.n, is(equalTo(0L))); }
        );

        final ScriptPublicKey key = out.scriptPubKey;
        Assertions.assertAll(
                () -> { assertThat(key.asm, is(equalTo("OP_DUP OP_HASH160 b0dc8fd06fef708657ead0dd2d49a127b6ff18ce OP_EQUALVERIFY OP_CHECKSIG"))); },
                () -> { assertThat(key.hex, is(equalTo("76a914b0dc8fd06fef708657ead0dd2d49a127b6ff18ce88ac"))); },
                () -> { assertThat(key.type, is(equalTo(ScriptPublicKey.Type.pubkeyhash))); },
                () -> { assertThat(key.reqSigs, is(equalTo(1L))); },
                () -> { assertThat(key.addresses, hasSize(1)); },
                () -> { assertThat(key.addresses.get(0), is(equalTo("mwe7SiXx47UrGAMjzNCxMMLMwvjqugULfJ"))); }
        );
    }
}
    
//    niko@956c95926ef4:~$ nikod -rpcport=10001 -rpcuser=a -rpcpassword=b gettransaction 64c664bf2d296d6ea629cf5177eee69806ce2cdc39771b5115e6de28291e6b39
//{
//    "txid" : "64c664bf2d296d6ea629cf5177eee69806ce2cdc39771b5115e6de28291e6b39",
//    "version" : 1,
//    "time" : 1524769682,
//    "locktime" : 64,
//    "vin" : [
//        {
//            "txid" : "17862ba71b9e93acaf68a02f79f4f2bd7cecbc466c5f6e9a08312064850e9ce0",
//            "vout" : 0,
//            "scriptSig" : {
//                "asm" : "304402205bbb3fd2f0a73f710d298feb7c83e14a841de72a73bf34e941e455460390e9f902200306b3f28b6647c5bf55c42f20777a0eb2809a15827d9957bd158dfa1be3846e01 032da351eef0ee32cf6825003c17ec9a6f0cc89be405cd4cb825664fac988c837f",
//                "hex" : "47304402205bbb3fd2f0a73f710d298feb7c83e14a841de72a73bf34e941e455460390e9f902200306b3f28b6647c5bf55c42f20777a0eb2809a15827d9957bd158dfa1be3846e0121032da351eef0ee32cf6825003c17ec9a6f0cc89be405cd4cb825664fac988c837f"
//            },
//            "sequence" : 4294967294
//        }
//    ],
//    "vout" : [
//        {
//            "value" : 99999989.99990000,
//            "n" : 0,
//            "scriptPubKey" : {
//                "asm" : "OP_DUP OP_HASH160 b0dc8fd06fef708657ead0dd2d49a127b6ff18ce OP_EQUALVERIFY OP_CHECKSIG",
//                "hex" : "76a914b0dc8fd06fef708657ead0dd2d49a127b6ff18ce88ac",
//                "reqSigs" : 1,
//                "type" : "pubkeyhash",
//                "addresses" : [
//                    "mwe7SiXx47UrGAMjzNCxMMLMwvjqugULfJ"
//                ]
//            }
//        },
//        {
//            "value" : 10.00000000,
//            "n" : 1,
//            "scriptPubKey" : {
//                "asm" : "OP_DUP OP_HASH160 e37eda61964f4710de4ea40cbb15878a7cf64f32 OP_EQUALVERIFY OP_CHECKSIG",
//                "hex" : "76a914e37eda61964f4710de4ea40cbb15878a7cf64f3288ac",
//                "reqSigs" : 1,
//                "type" : "pubkeyhash",
//                "addresses" : [
//                    "n2FqeWBwAYEkJiANYGRy7jBL3n8uNPCZMa"
//                ]
//            }
//        }
//    ],
//    "amount" : -10.00000000,
//    "fee" : -0.00010000,
//    "confirmations" : 0,
//    "txid" : "64c664bf2d296d6ea629cf5177eee69806ce2cdc39771b5115e6de28291e6b39",
//    "time" : 1524769682,
//    "timereceived" : 1524769682,
//    "details" : [
//        {
//            "account" : "",
//            "address" : "n2FqeWBwAYEkJiANYGRy7jBL3n8uNPCZMa",
//            "category" : "send",
//            "amount" : -10.00000000,
//            "fee" : -0.00010000
//        }
//    ]
//}
