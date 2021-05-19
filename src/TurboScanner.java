import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class TurboScanner implements AutoCloseable{
    BufferedReader br;

    public TurboScanner(InputStream in) {
        br = new BufferedReader(new InputStreamReader(in));
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public String next() throws IOException {
        int i = 0;
        char ascii = '_';
        char[] chars = new char[13];
        while (ascii != ' ' && ascii != '\n') {
            ascii = (char) br.read();
            chars[i++] = ascii;
        }
        return new String(chars).trim();
    }

    public String nextLine() throws IOException {
        return br.readLine();
    }

    public byte nextByte() throws IOException {
        //Will break with non ascii characters, be aware
        return (byte) br.read();
    }
    public void close() throws IOException {
        br.close();
    }
}
