package tech.coinbub.daemon;

import tech.coinbub.daemon.Niko;
import tech.coinbub.daemon.niko.Transaction;
import tech.coinbub.daemon.niko.TransactionDetail;
import com.googlecode.jsonrpc4j.JsonRpcClientException;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class TestSendToAddress {
    @Test
    public void throwsErrorOnInvalidAddress(final Niko niko) {
        final JsonRpcClientException ex = Assertions.assertThrows(JsonRpcClientException.class, () -> {
            niko.sendtoaddress("abc", BigDecimal.ONE);
        });
        assertThat(ex.getMessage(), is(equalTo("Invalid NIKO address")));
    }

    @Test
    public void supportsNoComments(final Niko niko) {
        final String txid = niko.sendtoaddress("mhhhbW3S4XqYiJSzW2kx6krxYSm2JhGRqd", BigDecimal.ONE);
        final Transaction tx = niko.gettransaction(txid);
        assertThat(tx.amount, is(equalTo(new BigDecimal("-1.0"))));
    }

    @Test
    public void supportsSourceComment(final Niko niko) {
        final String txid = niko.sendtoaddress("mhhhbW3S4XqYiJSzW2kx6krxYSm2JhGRqd", BigDecimal.ONE, "test transaction!");
        final Transaction tx = niko.gettransaction(txid);
        Assertions.assertAll(
                () -> { assertThat(tx.amount, is(equalTo(new BigDecimal("-1.0")))); },
                () -> { assertThat(tx.details, hasSize(1)); },
                () -> { assertThat(tx.comment, is(equalTo("test transaction!"))); }
        );
        
        final TransactionDetail detail = tx.details.get(0);
        Assertions.assertAll(
                () -> { assertThat(detail.account, isEmptyString()); },
                () -> { assertThat(detail.address, is(equalTo("mhhhbW3S4XqYiJSzW2kx6krxYSm2JhGRqd"))); },
                () -> { assertThat(detail.category, is(equalTo(TransactionDetail.Category.send))); },
                () -> { assertThat(detail.amount, is(equalTo(new BigDecimal("-1.0")))); }
        );
    }

    @Test
    public void supportsDestinationComment(final Niko niko) {
        final String txid = niko.sendtoaddress("mhhhbW3S4XqYiJSzW2kx6krxYSm2JhGRqd", BigDecimal.ONE, "test transaction!", "receiving test!");
        final Transaction tx = niko.gettransaction(txid);
        Assertions.assertAll(
                () -> { assertThat(tx.amount, is(equalTo(new BigDecimal("-1.0")))); },
                () -> { assertThat(tx.details, hasSize(1)); },
                () -> { assertThat(tx.comment, is(equalTo("test transaction!"))); },
                () -> { assertThat(tx.to, is(equalTo("receiving test!"))); }
        );
        
        final TransactionDetail detail = tx.details.get(0);
        Assertions.assertAll(
                () -> { assertThat(detail.account, isEmptyString()); },
                () -> { assertThat(detail.address, is(equalTo("mhhhbW3S4XqYiJSzW2kx6krxYSm2JhGRqd"))); },
                () -> { assertThat(detail.category, is(equalTo(TransactionDetail.Category.send))); },
                () -> { assertThat(detail.amount, is(equalTo(new BigDecimal("-1.0")))); }
        );
    }
}
