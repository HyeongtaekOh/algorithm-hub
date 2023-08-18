import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int n, m;
	static int[] num;
	static int[][] tree;
	static int[] dummy = {0, 0};
	
	public static void main(String[] args) throws IOException {
		
		n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		
		num = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			num[i] = Integer.parseInt(st.nextToken());
		}
		int h = (int) Math.ceil(Math.log(n) / Math.log(2));
		tree = new int[1 << (h + 1)][2];
		
		init(1, 1, n);
		
		m = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			
			int op = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			if (op == 1) {
				update(a, b, 1, 1, n);
			} else {
				int[] res = query(1, a, b, 1, n);
				sb.append(res[0] + res[1]).append("\n");
			}
		}
		
		System.out.println(sb);
	}
	
	static void init(int node, int start, int end) {
		if (start == end) {
			tree[node][0] = num[start];
		} else {
			init(2 * node, start, (start + end) / 2);
			init(2 * node + 1, (start + end) / 2 + 1, end);
			
			getLargestTwo(tree[node], tree[2 * node], tree[2 * node + 1]);
		}
	}
	
	static int[] query(int node, int left, int right, int start, int end) {
		if (left > end || right < start) {
			return dummy;
		}
		
		if (left <= start && right >= end) {
			return tree[node];
		}
		
		int mid = (start + end) / 2;
		int[] lArr = query(2 * node, left, right, start, mid);
		int[] rArr = query(2  * node + 1, left, right, mid + 1, end);
		return getLargestTwo(new int[2], lArr, rArr);
	}
	
	static void update(int index, int newValue, int node, int start, int end) {
		if (index < start || index > end) {
			return;
		}
		
		if (start == end) {
			num[index] = newValue;
			tree[node][0] = newValue;
			return;
		}
		
		update(index, newValue, 2 * node, start, (start + end) / 2);
		update(index, newValue, 2 * node + 1, (start + end) / 2 + 1, end);
		getLargestTwo(tree[node], tree[2 * node], tree[2 * node + 1]);
	}
	
	static int[] getLargestTwo(int[] res, int[] a, int[] b) {
		
		int aIndex = 0;
		int bIndex = 0;
		
		for (int i = 0; i < 2; i++) {
			if (a[aIndex] > b[bIndex]) {
				res[i] = a[aIndex++];
			} else {
				res[i] = b[bIndex++];
			}
		}
		
		return res;
	}
}