import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int k, h;
	static String[] fold;
	static int[][] hall;
	
	/* 마지막까지 접었을 때 원래 종이의 모서리부분이 위치한 방향, 수평방향 마지막에 R 방향으로 접은 경우 isRight = true, 수직방향 마지막에 U 방향으로 접은 경우 isUp = true */
	static boolean isRight, isUp;
	
	public static void main(String[] args) throws IOException {
		
		k = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		
		fold = new String[2 * k];	// 접는 순서
		int L = 1 << k;				// 손수건 한 변의 길이
		hall = new int[L][L];		// 최종 구멍의 위치
		
		for (int i = 0, length = 2*k; i < length; ++i) {
			fold[i] = st.nextToken();
			
			/* 각 방향(수평, 수직)에 대하여 마지막으로 접는 방향을 저장 */
			if (fold[i].equals("R")) {
				isRight = true;
			} else if (fold[i].equals("L")) {
				isRight = false;
			} else if (fold[i].equals("U")) {
				isUp = true;
			} else {
				isUp = false;
			}
		}
		
		h = Integer.parseInt(br.readLine());
		
		/**
		 * 접는 순서는 각 방향(수평, 수직)의 마지막을 제외하고는 중요하지 않다.
		 * 마지막에 접힌 종이가 놓인 상태 (접힌 모서리의 위치)에 따라 구멍을 뚫었을 때 최종상태가 결정된다.
		 */
		if ((isRight && isUp && h == 0)
				|| (isRight && !isUp && h == 2)
				|| (!isRight && isUp && h == 1)
				|| (!isRight && !isUp && h == 3)) {
			/**
			 * 	1 0
			 *  3 2
			 */
			
			/* 왼쪽 위 칸 */
			for (int i = 0; i < L; i += 2) {
				for (int j = 0; j < L; j += 2) {
					hall[i][j] = 1;
				}
			}
			
			/* 오른쪽 위 칸 */
			for (int i = 0; i < L; i += 2) {
				for (int j = 1; j < L; j += 2) {
					hall[i][j] = 0;
				}
			}
			
			/* 왼쪽 아래 칸 */
			for (int i = 1; i < L; i += 2) {
				for (int j = 0; j < L; j += 2) {
					hall[i][j] = 3;
				}
			}
			
			/* 오른쪽 아래 칸 */
			for (int i = 1; i < L; i += 2) {
				for (int j = 1; j < L; j += 2) {
					hall[i][j] = 2;
				}
			}
			
		} else if ((isRight && isUp && h == 1)
				|| (isRight && !isUp && h == 3)
				|| (!isRight && isUp && h == 0)
				|| (!isRight && !isUp && h == 2)) {
			/**
			 * 	0 1
			 *  2 3
			 */
			
			/* 왼쪽 위 칸 */
			for (int i = 0; i < L; i += 2) {
				for (int j = 0; j < L; j += 2) {
					hall[i][j] = 0;
				}
			}
			
			/* 오른쪽 위 칸 */
			for (int i = 0; i < L; i += 2) {
				for (int j = 1; j < L; j += 2) {
					hall[i][j] = 1;
				}
			}
			
			/* 왼쪽 아래 칸 */
			for (int i = 1; i < L; i += 2) {
				for (int j = 0; j < L; j += 2) {
					hall[i][j] = 2;
				}
			}
			
			/* 오른쪽 아래 칸 */
			for (int i = 1; i < L; i += 2) {
				for (int j = 1; j < L; j += 2) {
					hall[i][j] = 3;
				}
			}
			
		} else if ((isRight && isUp && h == 2)
				|| (isRight && !isUp && h == 0)
				|| (!isRight && isUp && h == 3)
				|| (!isRight && !isUp && h == 1)) { 
			/**
			 * 	3 2
			 *  1 0
			 */
			
			/* 왼쪽 위 칸 */
			for (int i = 0; i < L; i += 2) {
				for (int j = 0; j < L; j += 2) {
					hall[i][j] = 3;
				}
			}
			
			/* 오른쪽 위 칸 */
			for (int i = 0; i < L; i += 2) {
				for (int j = 1; j < L; j += 2) {
					hall[i][j] = 2;
				}
			}
			
			/* 왼쪽 아래 칸 */
			for (int i = 1; i < L; i += 2) {
				for (int j = 0; j < L; j += 2) {
					hall[i][j] = 1;
				}
			}
			
			/* 오른쪽 아래 칸 */
			for (int i = 1; i < L; i += 2) {
				for (int j = 1; j < L; j += 2) {
					hall[i][j] = 0;
				}
			}
			
		} else {
			/**
			 * 	2 3
			 *  0 1
			 */
			
			/* 왼쪽 위 칸 */
			for (int i = 0; i < L; i += 2) {
				for (int j = 0; j < L; j += 2) {
					hall[i][j] = 2;
				}
			}
			
			/* 오른쪽 위 칸 */
			for (int i = 0; i < L; i += 2) {
				for (int j = 1; j < L; j += 2) {
					hall[i][j] = 3;
				}
			}
			
			/* 왼쪽 아래 칸 */
			for (int i = 1; i < L; i += 2) {
				for (int j = 0; j < L; j += 2) {
					hall[i][j] = 0;
				}
			}
			
			/* 오른쪽 아래 칸 */
			for (int i = 1; i < L; i += 2) {
				for (int j = 1; j < L; j += 2) {
					hall[i][j] = 1;
				}
			}
		}
		
		for (int i = 0; i < L; i++) {
			for (int j = 0; j < L; j++) {
				sb.append(hall[i][j]).append(" ");
			}
			sb.append("\n");
		}
		
		System.out.print(sb);
	}
}