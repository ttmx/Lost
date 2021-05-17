import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TurboScanner in = new TurboScanner(System.in);
        byte numTests = (byte) in.nextInt();
        for (int i = 0 ; i<numTests;i++){
            byte height = (byte) in.nextInt();
            byte width = (byte) in.nextInt();
            byte[][] grid = new byte[height][width];
            byte wheels = (byte) in.nextInt();
            for (int h = 0;h<height;h++){
                for (int w = 0;w<width;w++){
                    grid[h][w] = in.nextByte();
                }
                assert (in.nextByte() == '\n');
            }
        }
    }
}
