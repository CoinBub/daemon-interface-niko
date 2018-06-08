package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static tech.coinbub.daemon.GetBlockHashIT.HEIGHT;
import tech.coinbub.daemon.normalization.model.Block;
import tech.coinbub.daemon.normalization.model.Transaction;
import tech.coinbub.daemon.normalization.model.TransactionDetail;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class NormalizedNikoIT {
    @Test
    public void testGetblockhash(final NormalizedNiko normalized) {
        assertThat(normalized.getblockhash(HEIGHT), is(equalTo("06e21472e1832d4c9b926b96e5e038e8932f502e3fb0b98b6359ebe8812c0449")));
    }

    @Test
    public void testGetblock(final NormalizedNiko normalized) {
        final Block block = normalized.getblock("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d");
        assertThat(block, hasOnly(
                property("hash", is(equalTo("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d"))),
                property("confirmations", is(equalTo(1L))),
                property("size", is(equalTo(175L))),
                property("height", is(equalTo(119L))),
                property("time", is(equalTo(1524090557L))),
                property("previousblockhash", is(equalTo("6f7ac94cd55f03e9121dc682e8075822c4bb03c0323301706f98e7af8d7dcd7d"))),
                property("tx", hasSize(1))
        ));

        assertThat(block.tx.get(0), is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a")));
    }

    @Test
    public void testGettransaction(final NormalizedNiko normalized) {
        final Transaction tx = normalized.gettransaction("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a");
        assertThat(tx, hasOnly(
                property("id", is(equalTo("b237ae95d8cf3c0af24948248fbafe447512826f85cf9c3288f9c83634b7fa9a"))),
                property("time", is(equalTo(1524090557L))),
                property("amount", is(equalTo(new BigDecimal("0.0")))),
                property("confirmations", is(equalTo(1L))),
                property("blockhash", is(equalTo("37762e5ce945ff724d96623cc94a5983f1ba9322224a5ac68d3d8e899615373d"))),
                property("details", hasSize(1))
        ));

        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("address", is(equalTo("mtNiPdupyDA7LfG9Mbwx3qXweoiZKSsTtY"))),
                property("amount", is(equalTo(new BigDecimal("0.0"))))
        ));
    }

    @Test
    public void testGetnewaddress(final NormalizedNiko normalized) {
        assertThat(normalized.getnewaddress().length(), is(equalTo(34)));
    }

    @Test
    public void testSendToAddressNoComments(final NormalizedNiko normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE);
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", not(isEmptyString())),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001")))),
                property("confirmations", is(equalTo(0L))),
                property("time", is(not(nullValue()))),
                property("details", hasSize(1))
        ));

        assertThat(tx.details.get(0), hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001"))))
        ));
    }

    @Test
    public void testSendToAddressSourceComment(final NormalizedNiko normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE, "test transaction!");
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", not(isEmptyString())),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001")))),
                property("confirmations", is(equalTo(0L))),
                property("time", is(not(nullValue()))),
                property("comment_from", is(equalTo("test transaction!"))),
                property("details", hasSize(1))
        ));

        assertThat(tx.details.get(0), hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001"))))
        ));
    }

    @Test
    public void testSendToAddressDestComment(final NormalizedNiko normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE, "test transaction!", "receiving test!");
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", not(isEmptyString())),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001")))),
                property("time", is(not(nullValue()))),
                property("confirmations", is(equalTo(0L))),
                property("details", hasSize(1)),
                property("comment_from", is(equalTo("test transaction!"))),
                property("comment_to", is(equalTo("receiving test!")))
        ));
    
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.0001"), new BigDecimal("0.0001"))))
        ));
    }

}
