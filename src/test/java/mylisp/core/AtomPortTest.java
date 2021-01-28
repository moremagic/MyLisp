package mylisp.core;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class AtomPortTest {

    @Test
    void getValue() {
        InputStream inputstream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        AtomPort atomPort1 = new AtomPort(inputstream);
        assertEquals(inputstream, atomPort1.getValue());

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                //NOP
            }
        };

        AtomPort atomPort2 = new AtomPort(outputStream);
        assertEquals(outputStream, atomPort2.getValue());
    }

    @Test
    void testToString() {
        InputStream inputstream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        AtomPort atomPort1 = new AtomPort(inputstream);
        assertEquals(inputstream.toString(), atomPort1.getValue().toString());

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                //NOP
            }
        };

        AtomPort atomPort2 = new AtomPort(outputStream);
        assertEquals(outputStream.toString(), atomPort2.getValue().toString());
    }

    @Test
    void testEquals() {
        InputStream inputstream1 = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        InputStream inputstream2 = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        AtomPort a = new AtomPort(inputstream1);
        AtomPort b = new AtomPort(inputstream1);
        AtomPort c = new AtomPort(inputstream2);
        assertFalse(a == b);
        assertFalse(a == c);
        assertFalse(b == c);
        assertFalse(a.equals(c));
        assertFalse(b.equals(c));
        assertEquals(a, b);
    }

    @Test
    void testHashCode() {
        InputStream inputstream1 = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        InputStream inputstream2 = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        AtomPort a = new AtomPort(inputstream1);
        AtomPort b = new AtomPort(inputstream1);
        AtomPort c = new AtomPort(inputstream2);
        assertFalse(a == b);
        assertFalse(a == c);
        assertFalse(b == c);
        assertFalse(a.hashCode() == c.hashCode());
        assertFalse(b.hashCode() == c.hashCode());
        assertEquals(a.hashCode(), b.hashCode());
    }
}