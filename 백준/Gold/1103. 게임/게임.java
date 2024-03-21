import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, M;
	static int[][] map, dp;
	static boolean[][] visited;
	static boolean hasCycle = false;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		dp = new int[N][M];
		visited = new boolean[N][M];
		
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = line.charAt(j) == 'H' ? -1 : line.charAt(j) - '0';
			}
		}
		
		System.out.println(dfs(0, 0));
	}
	
	private static int dfs(int x, int y) {
		
		if (hasCycle) {
			return -1;
		}
		
		if (x < 0 || x >= N || y < 0 || y >= M || map[x][y] < 0) {
			return 0;
		}
		
		if (dp[x][y] > 0) {
			return dp[x][y];
		}
		
		if (visited[x][y]) {
			hasCycle = true;
			return -1;
		}
		
		int count = 0;
		visited[x][y] = true;
		
		for (int i = 0; i < 4; i++) {
			
			int nx = x + dx[i] * map[x][y];
			int ny = y + dy[i] * map[x][y];
			int hop = dfs(nx, ny);
			
			if (hop < 0) {
				return -1;
			}
			
			count = Math.max(count, dfs(nx, ny));
		}
		
		visited[x][y] = false;
		
		return dp[x][y] = 1 + count;
	}
}