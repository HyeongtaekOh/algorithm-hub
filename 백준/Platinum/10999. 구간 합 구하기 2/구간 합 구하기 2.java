import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	static int N, M, K;
	static long[] a, tree, lazy;

	public static void main(String[] args) throws IOException {

		st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		a = new long[N];
		int h = (int) Math.ceil(Math.log(N) / Math.log(2));
		int treeSize = 1 << (h + 1);
		tree = new long[treeSize];
		lazy = new long[treeSize];

		for (int i = 0; i < N; i++) {
			a[i] = Long.parseLong(br.readLine());
		}

		init(1, 0, N - 1);

		for (int i = 0, len = M + K; i < len; i++) {
			st = new StringTokenizer(br.readLine());
			if (st.nextToken().equals("1")) {
				update(1, 0, N - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1,
						Long.parseLong(st.nextToken()));
			} else {
				sb.append(sum(1, 0, N - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1))
						.append("\n");
			}
		}
		System.out.print(sb);
	}

	private static void init(int node, int start, int end) {

		if (start == end) {
			tree[node] = a[start];
			return;
		}

		int mid = start + (end - start) / 2;
		init(2 * node, start, mid);
		init(2 * node + 1, mid + 1, end);
		tree[node] = tree[2 * node] + tree[2 * node + 1];
	}

	private static long sum(int node, int start, int end, int left, int right) {

		lazyPropagate(node, start, end);
		
		if (left > end || right < start) {
			return 0;
		}

		if (left <= start && right >= end) {
			return tree[node];
		}

		int mid = start + (end - start) / 2;
		return sum(2 * node, start, mid, left, right) + sum(2 * node + 1, mid + 1, end, left, right);
	}

	private static void update(int node, int start, int end, int left, int right, long diff) {

		lazyPropagate(node, start, end);
		
		if (left > end || right < start) {
			return;
		}

		if (left <= start && right >= end) {
			tree[node] += diff * (end - start + 1);
			if (start < end) {
				lazy[2 * node] += diff;
				lazy[2 * node + 1] += diff;				
			}
			return;
		}

		int mid = start + (end - start) / 2;
		update(2 * node, start, mid, left, right, diff);
		update(2 * node + 1, mid + 1, end, left, right, diff);
		tree[node] = tree[2 * node] + tree[2 * node + 1];
	}

	private static void lazyPropagate(int node, int start, int end) {

		if (lazy[node] == 0) {
			return;
		}
		
		if (start == end) {
			a[start] += lazy[node];
		} else {
			lazy[2 * node] += lazy[node];
			lazy[2 * node + 1] += lazy[node];
		}
		
		tree[node] += lazy[node] * (end - start + 1);
		lazy[node] = 0;
	}
}