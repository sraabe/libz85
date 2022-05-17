package io.raabe.libz85;

import java.util.Arrays;

/**
 * Java implementation of ZeroMQ's Z85 encoding, loosely
 * following the reference C implementation at
 * https://raw.githubusercontent.com/zeromq/rfc/master/src/spec_32.c
 * @see <a href="https://rfc.zeromq.org/spec/32/">Z85 RFC</a>
 */
public class Z85
{
    private static char encoder[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', 
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
        'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', '.', '-', 
        ':', '+', '=', '^', '!', '/', '*', '?', 
        '&', '<', '>', '(', ')', '[', ']', '{', 
        '}', '@', '%', '$', '#'
    };

    private static byte decoder[] = {
        0x00, 0x44, 0x00, 0x54, 0x53, 0x52, 0x48, 0x00, 
        0x4b, 0x4c, 0x46, 0x41, 0x00, 0x3f, 0x3e, 0x45, 
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 
        0x08, 0x09, 0x40, 0x00, 0x49, 0x42, 0x4a, 0x47, 
        0x51, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 
        0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x30, 0x31, 0x32, 
        0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 
        0x3b, 0x3c, 0x3d, 0x4d, 0x00, 0x4e, 0x43, 0x00, 
        0x00, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 
        0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 
        0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 
        0x21, 0x22, 0x23, 0x4f, 0x00, 0x50, 0x00, 0x00
    };

    public static String encode(byte[] data)
    {
        int i = 0;
        long val = 0;
        StringBuffer sb = new StringBuffer();

        while (i < data.length)
        {
            val = val * 256 + (0xff & data[i++]);

            // Add padding if input's length is not a multiple of 4
            if (i == data.length && i % 4 != 0)
            {
                int j = 4 - (i % 4);
                i += j;
                val *= Math.pow(256, j);
            }

            if(i % 4 == 0)
            {
                int div = 52200625;
                while (0 != div)
                {
                    sb.append(encoder[(int)(val / div % 85)]);
                    div /= 85;
                }
                val = 0;
            }
        }

        return sb.toString();
    }

    public static byte[] decode(String s)
    {
        return decode(s.toCharArray());
    }

    public static byte[] decode(String s, int len)
    {
        return Arrays.copyOf(decode(s), len);
    }

    public static byte[] decode(char[] s, int len)
    {
        return Arrays.copyOf(decode(s), len);
    }

    public static byte[] decode(char[] s)
    {
        if (s.length % 5 != 0)
            return null;
        byte[] ret = new byte[s.length * 4 / 5];
        int i = 0, n = 0;
        long val = 0;

        while (i < s.length)
        {
            val = val * 85 + (decoder[s[i++] - 32] & 0xff);

            if (i % 5 == 0)
            {
                int div = 16777216;
                while(0 != div)
                {
                    ret[n++] = (byte) ((val / div) % 256);
                    div /= 256;
                }
                val = 0;
            }
        }

        return ret;
    }
}
