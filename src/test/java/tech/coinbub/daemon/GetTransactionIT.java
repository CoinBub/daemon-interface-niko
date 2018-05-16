package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.niko.ScriptPublicKey;
import tech.coinbub.daemon.niko.ScriptSignature;
import tech.coinbub.daemon.niko.Transaction;
import tech.coinbub.daemon.niko.TransactionDetail;
import tech.coinbub.daemon.niko.TxInput;
import tech.coinbub.daemon.niko.TxOutput;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class GetTransactionIT {
    @Test
    public void canGetTransaction(final Niko niko) {
        final Transaction tx = niko.gettransaction("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a");
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))),
                property("version", is(equalTo(1L))),
                property("time", is(equalTo(1524090557L))),
                property("locktime", is(equalTo(0L))),
                property("vin", hasSize(1)),
                property("vout", hasSize(1)),
                property("amount", is(equalTo(new BigDecimal("0.0")))),
                property("confirmations", is(equalTo(1L))),
                property("generated", is(equalTo(true))),
                property("blockhash", is(equalTo("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d"))),
                property("blockindex", is(equalTo(0L))),
                property("blocktime", is(equalTo(1524090557L))),
                property("timereceived", is(equalTo(1524090561L))),
                property("details", hasSize(1))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo("mtNiPdupyDA7LfG9Mbwx3qXweoiZKSsTtY"))),
                property("category", is(equalTo(TransactionDetail.Category.immature))),
                property("amount", is(equalTo(new BigDecimal("0.0"))))
        ));
        
        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final TxInput in = tx.vin.get(0);
        assertThat(in, hasOnly(
                property("coinbase", is(equalTo("01770101"))),
                property("sequence", is(equalTo(4294967295L)))
        ));

        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final TxOutput out = tx.vout.get(0);
        assertThat(out, hasOnly(
                property("value", is(equalTo(new BigDecimal("0.0")))),
                property("n", is(equalTo(0L))),
                property("scriptPubKey", is(not(nullValue())))
        ));

        // Identical to that found in `TestGetBlock.supportsShortTransactionList`
        final ScriptPublicKey key = out.scriptPubKey;
        assertThat(key, hasOnly(
                property("asm", is(equalTo("OP_DUP OP_HASH160 8d0ab384611937a37dd357e2d593b102bde0c763 OP_EQUALVERIFY OP_CHECKSIG"))),
                property("hex", is(equalTo("76a9148d0ab384611937a37dd357e2d593b102bde0c76388ac"))),
                property("type", is(equalTo(ScriptPublicKey.Type.pubkeyhash))),
                property("reqSigs", is(equalTo(1L))),
                property("addresses", hasSize(1))
        ));
        assertThat(key.addresses.get(0), is(equalTo("mtNiPdupyDA7LfG9Mbwx3qXweoiZKSsTtY")));
    }

    @Test
    public void supportsMultipleVouts(final Niko niko) {
        final Transaction tx = niko.gettransaction("64c664bf2d296d6ea629cf5177eee69806ce2cdc39771b5115e6de28291e6b39");
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("64c664bf2d296d6ea629cf5177eee69806ce2cdc39771b5115e6de28291e6b39"))),
                property("version", is(equalTo(1L))),
                property("time", is(equalTo(1524769683L))),
                property("locktime", is(equalTo(64L))),
                property("vin", hasSize(1)),
                property("vout", hasSize(2)),
                property("amount", is(equalTo(new BigDecimal("10.0")))),
                property("fee", is(nullValue())),
                property("confirmations", is(equalTo(0L))),
                property("generated", is(nullValue())),
                property("blockhash", is(nullValue())),
                property("blockindex", is(nullValue())),
                property("blocktime", is(nullValue())),
                property("timereceived", is(equalTo(1524769683L))),
                property("details", hasSize(1))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo("n2FqeWBwAYEkJiANYGRy7jBL3n8uNPCZMa"))),
                property("category", is(equalTo(TransactionDetail.Category.receive))),
                property("amount", is(equalTo(new BigDecimal("10.0")))),
                property("fee", is(nullValue()))
        ));
        
        final TxInput in = tx.vin.get(0);
        assertThat(in, hasOnly(
                property("txid", is(equalTo("17862ba71b9e93acaf68a02f79f4f2bd7cecbc466c5f6e9a08312064850e9ce0"))),
                property("sequence", is(equalTo(4294967294L))),
                property("vout", is(equalTo(0L))),
                property("scriptSig", is(not(nullValue())))
        ));

        final ScriptSignature sig = in.scriptSig;
        assertThat(sig, hasOnly(
                property("asm", is(equalTo("304402205bbb3fd2f0a73f710d298feb7c83e14a841de72a73bf34e941e455460390e9f902200306b3f28b6647c5bf55c42f20777a0eb2809a15827d9957bd158dfa1be3846e01 032da351eef0ee32cf6825003c17ec9a6f0cc89be405cd4cb825664fac988c837f"))),
                property("hex", is(equalTo("47304402205bbb3fd2f0a73f710d298feb7c83e14a841de72a73bf34e941e455460390e9f902200306b3f28b6647c5bf55c42f20777a0eb2809a15827d9957bd158dfa1be3846e0121032da351eef0ee32cf6825003c17ec9a6f0cc89be405cd4cb825664fac988c837f")))
        ));

        final TxOutput out = tx.vout.get(0);
        assertThat(out, hasOnly(
                property("value", is(equalTo(new BigDecimal("99999989.9999")))),
                property("n", is(equalTo(0L))),
                property("scriptPubKey", is(not(nullValue())))
        ));

        final ScriptPublicKey key = out.scriptPubKey;
        assertThat(key, hasOnly(
                property("asm", is(equalTo("OP_DUP OP_HASH160 b0dc8fd06fef708657ead0dd2d49a127b6ff18ce OP_EQUALVERIFY OP_CHECKSIG"))),
                property("hex", is(equalTo("76a914b0dc8fd06fef708657ead0dd2d49a127b6ff18ce88ac"))),
                property("type", is(equalTo(ScriptPublicKey.Type.pubkeyhash))),
                property("reqSigs", is(equalTo(1L))),
                property("addresses", hasSize(1))
        ));
        assertThat(key.addresses.get(0), is(equalTo("mwe7SiXx47UrGAMjzNCxMMLMwvjqugULfJ")));
    }
}
