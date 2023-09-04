import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int n, m, k;
	static List<int[]>[] graph;
	static int[] visited;
	static PriorityQueue<Long>[] d;
	static PriorityQueue<long[]> heap = new PriorityQueue<>(Comparator.comparingLong(a->a[1]));
	
	public static void main(String[] args) throws IOException {
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		graph = new List[n];
		visited = new int[n];
		d = new PriorityQueue[n];
		
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
			d[i] = new PriorityQueue<>(Comparator.reverseOrder());
			d[i].offer(Long.MAX_VALUE);
		}
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken());
			
			graph[a].add(new int[] {b, c});
		}
		
		int count = 0;
		d[0].offer(0L);
		heap.offer(new long[] {0, 0});
		
		while (!heap.isEmpty()) {
			
			long[] node = heap.poll();
			int vtx = (int) node[0];
			long distance = node[1];
			
			if (visited[vtx] == k) {
				continue;
			}
			
			if (++visited[vtx] == k && ++count == n) {
				break;
			}
			
			for (int[] adj : graph[vtx]) {
				if (visited[adj[0]] < k && d[adj[0]].peek() > distance + adj[1]) {
					d[adj[0]].offer(distance + adj[1]);
					heap.offer(new long[] {adj[0], distance + adj[1]});
				}
			}
		}
		
		for (int i = 0; i < n; i++) {
			
			if (d[i].size() <= k) {
				sb.append(-1).append("\n");
				continue;
			}
			
			while (d[i].size() > k) {
				d[i].poll();
			}
			
			sb.append(d[i].poll()).append("\n");
		}
		
		System.out.print(sb);
	}
}