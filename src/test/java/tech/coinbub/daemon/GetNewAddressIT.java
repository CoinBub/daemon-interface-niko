package tech.coinbub.daemon;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class GetNewAddressIT {
    @Test
    public void canGetAddressForDefaultAccount(final Niko niko) {
        final String address = niko.getnewaddress();
        assertThat(address.length(), is(equalTo(34)));
    }
}
