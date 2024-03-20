import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static StringTokenizer st;

	static int n, m, max = Integer.MIN_VALUE;
	static int[] tree;

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		st = new StringTokenizer(br.readLine(), " ");
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		tree = new int[n];
		
		st = new StringTokenizer(br.readLine(), " ");
		
		for (int i = 0; i < n; i++) {
			tree[i] = Integer.parseInt(st.nextToken());
			max = Math.max(max, tree[i]);
		}
		
		int low = 0, hi = max;
		
		while (low < hi) {
			
			long sum = 0;
			int mid = low + (hi - low) / 2;
			
			for (int h : tree) {
				sum += Math.max(0, h - mid);
				if (sum > m) {
					break;
				}
			}
			
			if (sum < m) {
				hi = mid;
			} else {
				low = mid + 1;
			}
		}
		
		System.out.println(low - 1);
	}

}