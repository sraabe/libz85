package io.raabe.libz85;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class Z85Test
{
    private static final String TEST_DATA_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    private static final int TEST_DATA_STRING_LENGTH = TEST_DATA_STRING.length();
    private static final String TEST_DATA_STRING_ENCODED = "oMa@.z6iLiB9hsxwn=p5AV/BqBrC1ewPPECv@DmawN/*[BAIQDvpT7>x(^=#zF786y?W#xaAIa8ay]ykwODpdzeS@cBy/rdz/M$mzEWx>x>hv7BrCZFaz+E}z/PziwPP59z!%ujwGU?#xkRc2vqGT$BZV)5";

    private static final String TEST_DATA_STRING_2 = "Test";
    private static final int TEST_DATA_STRING_LENGTH_2 = TEST_DATA_STRING_2.length();
    private static final String TEST_DATA_STRING_ENCODED_2 = "raQb)";

    private static final byte TEST_DATA_BYTES[] = {
        (byte) 0x86, (byte) 0x4f, (byte) 0xd2, (byte) 0x6f,
        (byte) 0xb5, (byte) 0x59, (byte) 0xf7, (byte) 0x5b
    };
    private static final String TEST_DATA_BYTES_ENCODED = "HelloWorld";

    @Test
    void testDecode()
    {
        assertArrayEquals(TEST_DATA_BYTES, Z85.decode(TEST_DATA_BYTES_ENCODED));
        assertArrayEquals(TEST_DATA_BYTES, Z85.decode(TEST_DATA_BYTES_ENCODED, TEST_DATA_BYTES.length));
        assertArrayEquals(TEST_DATA_BYTES, Z85.decode(Z85.encode(TEST_DATA_BYTES)));

        assertEquals(TEST_DATA_STRING, new String(Arrays.copyOf(Z85.decode(TEST_DATA_STRING_ENCODED), TEST_DATA_STRING_LENGTH)));
        assertEquals(TEST_DATA_STRING, new String(Z85.decode(TEST_DATA_STRING_ENCODED, TEST_DATA_STRING_LENGTH)));
        assertArrayEquals(TEST_DATA_STRING.getBytes(), Z85.decode(TEST_DATA_STRING_ENCODED, TEST_DATA_STRING_LENGTH));

        assertEquals(TEST_DATA_STRING_2, new String(Arrays.copyOf(Z85.decode(TEST_DATA_STRING_ENCODED_2), TEST_DATA_STRING_LENGTH_2)));
        assertEquals(TEST_DATA_STRING_2, new String(Z85.decode(TEST_DATA_STRING_ENCODED_2, TEST_DATA_STRING_LENGTH_2)));
        assertArrayEquals(TEST_DATA_STRING_2.getBytes(), Z85.decode(TEST_DATA_STRING_ENCODED_2));
        assertArrayEquals(TEST_DATA_STRING_2.getBytes(), Z85.decode(TEST_DATA_STRING_ENCODED_2, TEST_DATA_STRING_LENGTH_2));
    }

    @Test
    void testEncode()
    {
        assertEquals(TEST_DATA_STRING_ENCODED, Z85.encode(TEST_DATA_STRING.getBytes()));
        assertEquals(TEST_DATA_STRING_ENCODED_2, Z85.encode(TEST_DATA_STRING_2.getBytes()));
        assertEquals(TEST_DATA_BYTES_ENCODED, Z85.encode(TEST_DATA_BYTES));
    }
}
