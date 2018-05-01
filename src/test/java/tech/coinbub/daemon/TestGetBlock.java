package tech.coinbub.daemon;

import tech.coinbub.daemon.niko.Block;
import tech.coinbub.daemon.niko.ScriptPublicKey;
import tech.coinbub.daemon.niko.Transaction;
import tech.coinbub.daemon.niko.TxInput;
import tech.coinbub.daemon.niko.TxOutput;
import java.math.BigDecimal;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class TestGetBlock {
    @Test
    public void supportsShortTransactionList(final Niko niko) {
        final Block block = niko.getblock("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d");
        Assertions.assertAll(
                () -> { assertThat(block.hash, is(equalTo("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d"))); },
                () -> { assertThat(block.confirmations, is(equalTo(1L))); },
                () -> { assertThat(block.size, is(equalTo(175L))); },
                () -> { assertThat(block.height, is(equalTo(119L))); },
                () -> { assertThat(block.version, is(equalTo(7L))); },
                () -> { assertThat(block.merkleroot, is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))); },
                () -> { assertThat(block.mint, is(equalTo(new BigDecimal("0.0")))); },
                () -> { assertThat(block.time, is(equalTo(1524090557L))); },
                () -> { assertThat(block.nonce, is(equalTo(1905786880L))); },
                () -> { assertThat(block.bits, is(equalTo("1e1392e7"))); },
                () -> { assertThat(block.difficulty, is(equalTo(new BigDecimal("1.9956E-4")))); },
                () -> { assertThat(block.blocktrust, is(equalTo("d1424"))); },
                () -> { assertThat(block.chaintrust, is(equalTo("33c2f65"))); },
                () -> { assertThat(block.previousblockhash, is(equalTo("6f7ac94cd55f03e9121dc682e8075822c4bb03c0323301706f98e7af8d7dcd7d"))); },
                () -> { assertThat(block.flags, is(equalTo("proof-of-work"))); },
                () -> { assertThat(block.proofhash, is(equalTo("0000015a957f6eb0689d50c624cf09808baaa1d1096f5c1c1e43c290b4684349"))); },
                () -> { assertThat(block.entropybit, is(equalTo(1L))); },
                () -> { assertThat(block.modifier, is(equalTo("b33791cc95a6841b"))); },
                () -> { assertThat(block.modifierv2, is(equalTo("e5f282096ead2d028d407ecca7ce95ea62e30a67334eab5c6656a2b847582c3d"))); },
                () -> { assertThat(block.tx, hasSize(1)); }
        );

        final Transaction tx = block.tx.get(0);
        Assertions.assertAll(
                () -> { assertThat(tx.txid, is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))); },
                () -> { assertThat(tx.version, is(nullValue())); },
                () -> { assertThat(tx.time, is(nullValue())); },
                () -> { assertThat(tx.locktime, is(nullValue())); },
                () -> { assertThat(tx.vin, is(nullValue())); },
                () -> { assertThat(tx.vout, is(nullValue())); }
        );
    }

    @Test
    public void supportsLongTransactionList(final Niko niko) {
        final Block block = niko.getblock("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d", true);
        // Block details verified in `supportsShortTransactionList()`
        final Transaction tx = block.tx.get(0);
        Assertions.assertAll(
                () -> { assertThat(tx.txid, is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))); },
                () -> { assertThat(tx.version, is(equalTo(1L))); },
                () -> { assertThat(tx.time, is(equalTo(1524090557L))); },
                () -> { assertThat(tx.locktime, is(equalTo(0L))); },
                () -> { assertThat(tx.vin, hasSize(1)); },
                () -> { assertThat(tx.vout, hasSize(1)); }
        );

        final TxInput in = tx.vin.get(0);
        Assertions.assertAll(
                () -> { assertThat(in.coinbase, is(equalTo("01770101"))); },
                () -> { assertThat(in.sequence, is(equalTo(4294967295L))); }
        );

        final TxOutput out = tx.vout.get(0);
        Assertions.assertAll(
                () -> { assertThat(out.value, is(equalTo(new BigDecimal("0.0")))); },
                () -> { assertThat(out.n, is(equalTo(0L))); }
        );

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
    
}
