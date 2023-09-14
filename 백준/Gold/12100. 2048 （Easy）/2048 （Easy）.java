import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int N, answer;
	static int[][] board;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static Queue<Integer> queue = new ArrayDeque<>();

	public static void main(String[] args) throws IOException {

		N = Integer.parseInt(br.readLine());

		board = new int[N][N];
		int[] data = new int[2];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < N; j++) {
				board[i][j] = Short.parseShort(st.nextToken());
				if (board[i][j] > 0) {
					data[1] = Math.max(data[1], board[i][j]);
				}
			}
		}
		
		dfs(0, data);
		System.out.println(answer);
	}

	private static void dfs(int depth, int[] data) {
		
		if (answer > data[1] * Math.pow(2, 5 - depth)) {
			return;
		}
		
		if (depth == 5 || data[0] == 1) {
			answer = Math.max(answer, data[1]);
			return;
		}

		for (int i = 0; i < 4; i++) {
			int[][] copy = new int[N][N];
			deepCopy(board, copy);
			move(i, data);
			dfs(depth + 1, data);
			deepCopy(copy, board);
		}
	}

	private static void move(int direction, int[] data) {

		data[0] = 0; // 블록 수 카운트

		for (int i = 0; i < N; i++) {
			int movingBlockIndex = -1;
			if (direction == 0) {			// Left

				for (int j = 0; j < N; j++) {

					if (board[i][j] > 0) {
						if (movingBlockIndex < 0) {
							data[0]++;
							movingBlockIndex = j;
						} else if (board[i][j] == board[i][movingBlockIndex]) {
							queue.add(board[i][movingBlockIndex] * 2);
							board[i][movingBlockIndex] = 0;
							board[i][j] *= 2;
							data[1] = Math.max(data[1], board[i][j]);
							movingBlockIndex = -1;
						} else {
							queue.add(board[i][movingBlockIndex]);
							if (j > movingBlockIndex + 1) {
								board[i][j - 1] = board[i][movingBlockIndex];
								board[i][movingBlockIndex] = 0;
							}

							data[0]++;
							movingBlockIndex = j;
						}
					}
				}
				
				if (movingBlockIndex >= 0) {
					queue.add(board[i][movingBlockIndex]);
				}
				
				for (int j = 0; j < N; j++) {
					if (!queue.isEmpty()) {
						board[i][j] = queue.poll();
					} else {
						board[i][j] = 0;
					}
				}
				
			} else if (direction == 1) {		// Right

				for (int j = N - 1; j >= 0; j--) {
					if (board[i][j] > 0) {
						if (movingBlockIndex < 0) {
							data[0]++;
							movingBlockIndex = j;
							continue;
						}
						
						if (board[i][j] == board[i][movingBlockIndex]) {
							
							queue.add(board[i][movingBlockIndex] * 2);
							board[i][movingBlockIndex] = 0;
							board[i][j] *= 2;
							data[1] = Math.max(data[1], board[i][j]);
							
							movingBlockIndex = -1;
						} else {
							queue.add(board[i][movingBlockIndex]);
							
							if (j < movingBlockIndex - 1) {
								board[i][j + 1] = board[i][movingBlockIndex];
								board[i][movingBlockIndex] = 0;
							}
							
							data[0]++;
							movingBlockIndex = j;
						}
					}
				}
				
				if (movingBlockIndex >= 0) {
					queue.add(board[i][movingBlockIndex]);
				}
				
				for (int j = N - 1; j >= 0; j--) {
					if (!queue.isEmpty()) {
						board[i][j] = queue.poll();
					} else {
						board[i][j] = 0;
					}
				}
				
			} else if (direction == 2) {		// Up
				
				for (int j = 0; j < N; j++) {		

					if (board[j][i] > 0) {
						if (movingBlockIndex < 0) {
							data[0]++;
							movingBlockIndex = j;
						} else if (board[j][i] == board[movingBlockIndex][i]) {
							queue.add(board[movingBlockIndex][i] * 2);
							board[movingBlockIndex][i] = 0;
							board[j][i] *= 2;
							data[1] = Math.max(data[1], board[j][i]);
							movingBlockIndex = -1;
						} else {
							queue.add(board[movingBlockIndex][i]);
							if (j > movingBlockIndex + 1) {
								board[j - 1][i] = board[movingBlockIndex][i];
								board[movingBlockIndex][i] = 0;
							}

							data[0]++;
							movingBlockIndex = j;
						}
					}
				}
				
				if (movingBlockIndex >= 0) {
					queue.add(board[movingBlockIndex][i]);
				}
				
				for (int j = 0; j < N; j++) {
					if (!queue.isEmpty()) {
						board[j][i] = queue.poll();
					} else {
						board[j][i] = 0;
					}
				}
				
			} else {							// Down
				
				for (int j = N - 1; j >= 0; j--) {

					if (board[j][i] > 0) {
						if (movingBlockIndex < 0) {
							data[0]++;
							movingBlockIndex = j;
						} else if (board[j][i] == board[movingBlockIndex][i]) {
							queue.add(board[movingBlockIndex][i] * 2);
							board[movingBlockIndex][i] = 0;
							board[j][i] *= 2;
							data[1] = Math.max(data[1], board[j][i]);
							movingBlockIndex = -1;
						} else {
							queue.add(board[movingBlockIndex][i]);
							if (j < movingBlockIndex - 1) {
								board[j + 1][i] = board[movingBlockIndex][i];
								board[movingBlockIndex][i] = 0;
							}

							data[0]++;
							movingBlockIndex = j;
						}
					}
				}
				
				if (movingBlockIndex >= 0) {
					queue.add(board[movingBlockIndex][i]);
				}
				
				for (int j = N - 1; j >= 0; j--) {
					if (!queue.isEmpty()) {
						board[j][i] = queue.poll();
					} else {
						board[j][i] = 0;
					}
				}
				
			}
		}
	}

	private static void deepCopy(int[][] arr, int[][] copy) {

		for (int i = 0; i < N; i++) {
			copy[i] = Arrays.copyOf(arr[i], N);
		}
	}
}