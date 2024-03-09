import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static final int divisor = 1_000_000_000;
	static final int pow = 1 << 10;
	static int N;
	static int[][][] dp;

	public static void main(String[] args) throws IOException {

		N = Integer.parseInt(br.readLine());
		dp = new int[N + 1][10][pow];

		for (int i = 1; i < 10; i++) {
			dp[1][i][1 << i] = 1;
		}

		for (int n = 2; n <= N; n++) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < pow; j++) {

					if ((j & (1 << i)) == 0) {
						continue;
					}

					if (i > 0) {
						dp[n][i][j] = moduloAdd(dp[n][i][j], dp[n - 1][i - 1][j]);
						dp[n][i][j] = moduloAdd(dp[n][i][j], dp[n - 1][i - 1][j & ~(1 << i)]);
					}
					
					if (i < 9) {
						dp[n][i][j] = moduloAdd(dp[n][i][j], dp[n - 1][i + 1][j]);
						dp[n][i][j] = moduloAdd(dp[n][i][j], dp[n - 1][i + 1][j & ~(1 << i)]);
					}
				}
			}
		}

		int answer = 0;

		for (int i = 0; i < 10; i++) {
			answer = moduloAdd(answer, dp[N][i][1023]);
		}
		
		System.out.println(answer);
	}

	private static int moduloAdd(int a, int b) {
		return (a + b) % divisor;
	}
}