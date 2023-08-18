import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	static int n, m, answer = Integer.MAX_VALUE;
	static char[][] board;
	static int originRedX, originRedY, originBlueX, originBlueY;
	static int redX, redY, blueX, blueY, goalX, goalY;
	static int[][][][] pi;
	static int[] dirPer = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	public static void main(String[] args) throws IOException {

		st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		board = new char[n][m];
		pi = new int[n][m][4][];

		for (int i = 0; i < n; i++) {
			String line = br.readLine();
			for (int j = 0; j < m; j++) {
				board[i][j] = line.charAt(j);
				if (board[i][j] == 'R') {
					originRedX = i;
					originRedY = j;
					board[i][j] = '.';
				} else if (board[i][j] == 'B') {
					originBlueX = i;
					originBlueY = j;
					board[i][j] = '.';
				} else if (board[i][j] == 'O') {
					goalX = i;
					goalY = j;
				}
			}
		}

		for (int i = 1; i < n - 1; ++i) {
			point:
			for (int j = 1; j < m - 1; ++j) {
				for (int dir = 0; dir < 4; dir++) {
					if (board[i][j] == '#') {
						continue point;
					}
					int x = i, y = j;
					/* dir방향으로 기울였을 때 도달 위치 (0: 위, 1: 아래, 2: 왼쪽, 3: 오른쪽) */
					while (true) {
						if (x == goalX && y == goalY) {
							break;
						}

						if (board[x][y] == '#') {
							if (dir == 0) {
								++x;
							} else if (dir == 1) {
								--x;
							} else if (dir == 2) {
								++y;
							} else {
								--y;
							}
							break;
						}

						if (dir == 0) {
							--x;
						} else if (dir == 1) {
							++x;
						} else if (dir == 2) {
							--y;
						} else {
							++y;
						}
					}

					pi[i][j][dir] = new int[] { x, y };
				}
			}
		}

		for (int dir = 0; dir < 4; dir++) {
			redX = originRedX;
			redY = originRedY;
			blueX = originBlueX;
			blueY = originBlueY;
			dfs(0, dir);
		}
		
		System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
	}

	static void dfs(int cnt, int dir) {
		
		if (redX == goalX && redY == goalY) {
			answer = Math.min(answer, cnt);
			return;
		}
		
		if (cnt == 10) {
			return;
		}
		
		int tempRedX = redX;
		int tempRedY = redY;
		int tempBlueX = blueX;
		int tempBlueY = blueY;

		int piRedX = pi[redX][redY][dir][0];
		int piRedY = pi[redX][redY][dir][1];
		int piBlueX = pi[blueX][blueY][dir][0];
		int piBlueY = pi[blueX][blueY][dir][1];

		if (piRedX == goalX && piRedY == goalY
				&& piBlueX == goalX && piBlueY == goalY) {
			return;
		} else if (dir == 0 && redY == blueY && piRedX == piBlueX) {
				if (redX < blueX) {
					tempRedX = piRedX;
					tempBlueX = piBlueX + 1;
				} else {
					tempRedX = piRedX + 1;
					tempBlueX = piBlueX;
				}
			
		} else if (dir == 1 && redY == blueY && piRedX == piBlueX) {
				if (redX < blueX) {
					tempRedX = piRedX - 1;
					tempBlueX = piBlueX;
				} else {
					tempRedX = piRedX;
					tempBlueX = piBlueX - 1;
				}
			
		} else if (dir == 2 && redX == blueX && piRedY == piBlueY) {
				if (redY < blueY) {
					tempRedY = piRedY;
					tempBlueY = piBlueY + 1;
				} else {
					tempRedY = piRedY + 1;
					tempBlueY = piBlueY;
				}
		} else if (dir == 3 && redX == blueX && piRedY == piBlueY) {
				if (redY < blueY) {
					tempRedY = piRedY - 1;
					tempBlueY = piBlueY;
				} else {
					tempRedY = piRedY;
					tempBlueY = piBlueY - 1;
				}
			
		} else {
			tempRedX = piRedX;
			tempRedY = piRedY;
			tempBlueX = piBlueX;
			tempBlueY = piBlueY;
		}
        
		for (int i = 0; i < 4; i++) {
			if (i != dir && (Math.abs(i - dir) != 1 || i + dir == 3)) {
				redX = tempRedX;
				redY = tempRedY;
				blueX = tempBlueX;
				blueY = tempBlueY;
				dirPer[cnt] = dir;
				dfs(cnt + 1, i);
			}
		}
	}
}