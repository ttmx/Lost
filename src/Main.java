import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        try (TurboScanner in = new TurboScanner(System.in)) {
            byte N = (byte) in.nextInt();
            for (byte testCase = 0; testCase < N; testCase++) {
                byte R = (byte) in.nextInt();
                byte C = (byte) in.nextInt();
                byte M = (byte) in.nextInt();

                byte[][] grid = new byte[R][C];
                LostBuilder builder = new LostBuilder(grid, R, C, M);

                for (byte j = 0; j < C; j++) {
                    grid[0][j] = in.nextByte();
                }
                in.nextByte();
                for (byte i = 0; i < R - 1; i++) {
                    for (byte j = 0; j < C; j++) {
                        grid[i + 1][j] = in.nextByte();
                        builder.processCell(i, j);
                    }
                    in.nextByte();
                }
                for (byte j = 0; j < C; j++) {
                    builder.processCell((byte) (R - 1), j);
                }

                for (int i = 0; i < M; i++) {
                    byte r = (byte) in.nextInt();
                    byte c = (byte) in.nextInt();
                    int t = in.nextInt();
                    builder.addMagicalEdge(i, r, c, t);
                }
                byte rj = (byte) in.nextInt();
                byte cj = (byte) in.nextInt();
                byte rk = (byte) in.nextInt();
                byte ck = (byte) in.nextInt();
                builder.setJohnStart(rj, cj);
                builder.setKateStart(rk, ck);

                System.out.println("Case #" + testCase);
                System.out.println(builder.build().result());
            }
        }
    }
}
