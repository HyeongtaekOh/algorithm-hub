import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	static int n, m, k;
	static long[] num, tree;

	public static void main(String[] args) throws IOException {

		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		num = new long[n+1];
		
		for (int i = 1; i <= n; i++) {
			num[i] = Long.parseLong(br.readLine());
		}

		int h = (int) (Math.log(n) / Math.log(2)) + 1;

		tree = new long[1 << (h + 1) + 1];

		init(1, 1, n);
		
		for (int i = 0; i < m + k; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			long c = Long.parseLong(st.nextToken());
			
			if (a == 1) {
				update2(b, c - num[b], 1, 1, n);
			} else {
				sb.append(query(1, b, (int) c, 1, n)).append("\n");
			}
		}
		System.out.println(sb);

	}

	static void init(int node, int start, int end) {
		if (start == end) {
			tree[node] = num[start];
			return;
		}
		
		init(2 * node, start, (start + end) / 2);
		init(2 * node + 1, (start + end) / 2 + 1, end);
		tree[node] = tree[2 * node] + tree[2 * node + 1];
	}

	static long query(int node, int left, int right, int start, int end) {

		if (left > end || right < start) {
			return 0;
		}

		if (left <= start && right >= end) {
			return tree[node];
		}

		int mid = (start + end) / 2;

		return query(2 * node, left, right, start, mid) + query(2 * node + 1, left, right, mid + 1, end);
	}

	static void update(int index, long newValue, int node, int start, int end) {

		if (index < start || index > end) {
			return;
		}

		if (start == end) {
			num[start] = newValue;
			tree[node] = newValue;
			return;
		}

		int mid = (start + end) / 2;
		update(index, newValue, 2 * node, start, mid);
		update(index, newValue, 2 * node + 1, mid + 1, end);
		tree[node] = tree[2 * node] + tree[2 * node + 1];

	}

	static void update2(int index, long diff, int node, int start, int end) {

		if (index < start || index > end) {
			return;
		}

		if (start == end) {
			num[start] += diff;
			tree[node] += diff;
			return;
		}

		tree[node] += diff;
		update2(index, diff, 2 * node, start, (start + end) / 2);
		update2(index, diff, 2 * node + 1, (start + end) / 2 + 1, end);
	}
}