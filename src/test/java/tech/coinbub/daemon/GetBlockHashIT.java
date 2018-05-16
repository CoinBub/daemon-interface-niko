package tech.coinbub.daemon;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class GetBlockHashIT {
    public static final Long HEIGHT = 22L;

    @Test
    public void canGetBlockHash(final Niko niko) {
        final String best = niko.getblockhash(HEIGHT);
        assertThat(best, is(equalTo("06e21472e1832d4c9b926b96e5e038e8932f502e3fb0b98b6359ebe8812c0449")));
    }
}
