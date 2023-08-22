import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	static int n, w;
	static int[][] cases;
	static Data[][] dp;

	public static void main(String[] args) throws IOException {

		n = Integer.parseInt(br.readLine());
		w = Integer.parseInt(br.readLine());
		cases = new int[w + 1][2];
		dp = new Data[w + 1][w + 1];
		int[] p1 = { 1, 1 };
		int[] p2 = { n, n };

		for (int i = 1; i <= w; i++) {
			st = new StringTokenizer(br.readLine());
			cases[i][0] = Integer.parseInt(st.nextToken());
			cases[i][1] = Integer.parseInt(st.nextToken());
		}

		dp[1][0] = new Data(true, distance(p1, cases[1]), null);
		dp[0][1] = new Data(false, distance(p2, cases[1]), null);

		for (int i = 0; i <= w; i++) {
			for (int j = 0; j <= w; j++) {
				if (i == j || dp[i][j] != null) {
					continue;
				}

				if (i > j + 1) {
					int dist = dp[i - 1][j].dist + distance(cases[i], cases[i - 1]);
					dp[i][j] = new Data(true, dist, dp[i - 1][j]);
				} else if (i > j) {
					int minIndex = 0;
					int dist = dp[0][j].dist + distance(p1, cases[i]);
					for (int k = 1; k < i - 1; k++) {
						if (dist > dp[k][j].dist + distance(cases[k], cases[i])) {
							minIndex = k;
							dist = dp[k][j].dist + distance(cases[k], cases[i]);
						}
					}
					dp[i][j] = new Data(true, dist, dp[minIndex][j]);
//                    dp[i][j] = new Data(dist, 2);
				} else if (i + 1 < j) {
					int dist = dp[i][j - 1].dist + distance(cases[j], cases[j - 1]);
					dp[i][j] = new Data(false, dist, dp[i][j - 1]);
				} else {
					int minIndex = 0;
					int dist = dp[i][0].dist + distance(p2, cases[j]);
					for (int k = 1; k < j - 1; k++) {
						if (dist > dp[i][k].dist + distance(cases[k], cases[j])) {
							minIndex = k;
							dist = dp[i][k].dist + distance(cases[k], cases[j]);
						}
					}
					dp[i][j] = new Data(false, dist, dp[i][minIndex]);
				}
			}
		}

		dp[0][0] = new Data(true, Integer.MAX_VALUE, null);
		int minX = 0, minY = 0;

		for (int i = 1; i < w; i++) {

			if (dp[w][i].dist < dp[minX][minY].dist) {
				minX = w;
				minY = i;
			}

			if (dp[i][w].dist < dp[minX][minY].dist) {
				minX = i;
				minY = w;
			}
		}

		int[] seq = new int[w];

		Data tail = dp[minX][minY];
		for (int i = w - 1; i >= 0; --i) {
			seq[i] = tail.police ? 1 : 2;
			tail = tail.prev;
		}

		sb.append(dp[minX][minY].dist).append("\n");
		for (int i : seq) {
			sb.append(i).append("\n");
		}
		
		System.out.print(sb);
	}

	private static int distance(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}

	private static class Data {

		boolean police;
		int dist;
		Data prev;

		public Data(boolean police, int dist, Data prev) {
			this.police = police;
			this.dist = dist;
			this.prev = prev;
		}
	}
}