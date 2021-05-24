import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TurboScanner in = new TurboScanner(System.in);
        byte numTests = (byte) in.nextInt();
        for (int i = 0 ; i<numTests;i++){
            byte height = (byte) in.nextInt();
            byte width = (byte) in.nextInt();
            byte[][] grid = new byte[height][width];
            byte numWheels = (byte) in.nextInt();
            byte[] wheelLocs = new byte[numWheels*2];
            int[] wheelTimes = new int[numWheels];
            for (int h = 0;h<height;h++){
                for (int w = 0;w<width;w++){
                    grid[h][w] = in.nextByte();
                }
                in.nextByte();
            }
            for(byte w = 0;w<numWheels;w++){
                wheelLocs[w*2] = (byte) in.nextInt();
                wheelLocs[w*2+1] = (byte) in.nextInt();
                wheelTimes[w] = in.nextInt();
            }
            byte[] positions = new byte[4];
            for(int p = 0;p<4;p++) {
                positions[p] = (byte) in.nextInt();
            }
            Lost l = new Lost(grid,wheelLocs,wheelTimes,positions);
            int[] result = l.result();
        }
    }
}
