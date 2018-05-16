package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.niko.Block;
import tech.coinbub.daemon.niko.ScriptPublicKey;
import tech.coinbub.daemon.niko.Transaction;
import tech.coinbub.daemon.niko.TxInput;
import tech.coinbub.daemon.niko.TxOutput;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class TestGetBlock {
    @Test
    public void supportsShortTransactionList(final Niko niko) {
        final Block block = niko.getblock("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d");
        assertThat(block, hasOnly(
                property("hash", is(equalTo("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d"))),
                property("confirmations", is(equalTo(1L))),
                property("size", is(equalTo(175L))),
                property("height", is(equalTo(119L))),
                property("version", is(equalTo(7L))),
                property("merkleroot", is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))),
                property("mint", is(equalTo(new BigDecimal("0.0")))),
                property("time", is(equalTo(1524090557L))),
                property("nonce", is(equalTo(1905786880L))),
                property("bits", is(equalTo("1e1392e7"))),
                property("difficulty", is(equalTo(new BigDecimal("1.9956E-4")))),
                property("blocktrust", is(equalTo("d1424"))),
                property("chaintrust", is(equalTo("33c2f65"))),
                property("previousblockhash", is(equalTo("6f7ac94cd55f03e9121dc682e8075822c4bb03c0323301706f98e7af8d7dcd7d"))),
                property("flags", is(equalTo("proof-of-work"))),
                property("proofhash", is(equalTo("0000015a957f6eb0689d50c624cf09808baaa1d1096f5c1c1e43c290b4684349"))),
                property("entropybit", is(equalTo(1L))),
                property("modifier", is(equalTo("b33791cc95a6841b"))),
                property("modifierv2", is(equalTo("e5f282096ead2d028d407ecca7ce95ea62e30a67334eab5c6656a2b847582c3d"))),
                property("tx", hasSize(1))
        ));

        final Transaction tx = block.tx.get(0);
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))),
                property("version", is(nullValue())),
                property("time", is(nullValue())),
                property("locktime", is(nullValue())),
                property("vin", is(nullValue())),
                property("vout", is(nullValue()))
        ));
    }

    @Test
    public void supportsLongTransactionList(final Niko niko) {
        final Block block = niko.getblock("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d", true);
        // Block details verified in `supportsShortTransactionList()`
        final Transaction tx = block.tx.get(0);
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))),
                property("version", is(equalTo(1L))),
                property("time", is(equalTo(1524090557L))),
                property("locktime", is(equalTo(0L))),
                property("vin", hasSize(1)),
                property("vout", hasSize(1))
        ));

        final TxInput in = tx.vin.get(0);
        assertThat(in, hasOnly(
                property("coinbase", is(equalTo("01770101"))),
                property("sequence", is(equalTo(4294967295L)))
        ));

        final TxOutput out = tx.vout.get(0);
        assertThat(out, hasOnly(
                property("value", is(equalTo(new BigDecimal("0.0")))),
                property("n", is(equalTo(0L))),
                property("scriptPubKey", is(not(nullValue())))
        ));

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
    
}
