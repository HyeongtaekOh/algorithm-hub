import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int r, c, answer = 0;
	static String[][] map;
	static boolean find;
	static boolean[][] visited;
	static int[] dx = {-1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		visited = new boolean[r][c];
		
		for (int i = 0; i < r; i++) {
			String line = br.readLine();
			for (int j = 0; j < c; j++) {
				visited[i][j] = line.charAt(j) == 'x';
			}
		}
		
		for (int i = 0; i < r; i++) {
			find = false;
			dfs(i, 0);
		}
		
		System.out.println(answer);
	}
	
	static void dfs(int row, int col) {
		if (find) {
			return;
		}
		
		if (col == c-1) {
			answer++;
			find = true;
			visited[row][col] = true;
			return;
		}
		
		visited[row][col] = true;
		
		for (int i = 0; i < 3; i++) {
			int nrow = row + dx[i];
			int ncol = col + 1;
			if (nrow < 0 || nrow >= r) {
				continue;
			}
			if (!visited[nrow][ncol]) {
				dfs(nrow, ncol);
			}
		}
	}
}