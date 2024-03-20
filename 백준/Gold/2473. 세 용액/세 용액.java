import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException {
		
		int N = Integer.parseInt(br.readLine());
		long[] sol = new long[N];
		
		st = new StringTokenizer(br.readLine(), " ");
		
		for (int i = 0; i < N; i++) {
			sol[i] = Long.parseLong(st.nextToken());
		}
		
		Arrays.sort(sol);
		
		int ansLeft = 0, ansMid = 1, ansRight = 2;
		long min = 3_000_000_001L;
		
		
		for (int left = 0; left < N - 2; left++) {
			
			int mid = left + 1, right = N - 1;
			
			while (mid < right) {
				
				long sum = sol[left] + sol[mid] + sol[right];
				
				if (min > Math.abs(sum)) {
					ansLeft = left;
					ansMid = mid;
					ansRight = right;
					min = Math.abs(sum);
				}
				
				if (sum < 0) {
					mid++;
				} else if (sum > 0) {
					right--;
				} else {
					break;
				}
			}
		}
		
		System.out.println(sol[ansLeft] + " " + sol[ansMid] + " " + sol[ansRight]);
	}
}
