import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int n, answer = 0;;
	static int[] queen;

	public static void main(String[] args) throws IOException {

		n = Integer.parseInt(br.readLine());
		queen = new int[n];

		backtrack(0);
		
		System.out.println(answer);
	}

	static void backtrack(int row) {
		if (row == n) {
			answer++;
			return;
		}

		for (int col = 0; col < n; col++) {
			if (possibleQueen(row, col)) {
				queen[row] = col;
				backtrack(row + 1);
			}
		}
	}
	
	static boolean possibleQueen(int row, int col) {
			
		for (int i = 0; i < row; i++) {
			if (queen[i] == col) {
				return false;
			}
			if (row - i == Math.abs(col - queen[i])) {
				return false;
			}
		}
		
		return true;
	}
}