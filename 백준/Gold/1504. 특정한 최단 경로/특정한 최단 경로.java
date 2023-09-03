import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static long answer = Long.MAX_VALUE;
    static int N, E, v1, v2;
    static List<int[]>[] graph;
    static long[] d1, d2;
    static int[] prev1, prev2;
    static boolean[] visited;
    static boolean passV1, passV2;
    static PriorityQueue<long[]> heap1 = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
    static PriorityQueue<long[]> heap2 = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        graph = new List[N];
        d1 = new long[N];
        d2 = new long[N];
        prev1 = new int[N];
        prev2 = new int[N];
        visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>(300);
            d1[i] = d2[i] = Long.MAX_VALUE;
            prev1[i] = prev2[i] = -1;
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());

            graph[a].add(new int[]{b, c});
            graph[b].add(new int[]{a, c});
        }

        st = new StringTokenizer(br.readLine());
        v1 = Integer.parseInt(st.nextToken()) - 1;
        v2 = Integer.parseInt(st.nextToken()) - 1;

        dijkstra(d1, prev1, heap1, v1);
        dijkstra(d2, prev2, heap2, v2);

        /**
         *  1 -> v1 -> v2 -> N
         */
        boolean canGo = d1[0] != Long.MAX_VALUE;
        long dist1 = getDistance(0, canGo, d1, prev1, v2, d2);

        /**
         *  1 -> v2 -> v1 -> N
         */
        canGo = d2[0] != Long.MAX_VALUE;
        long dist2 = getDistance(0, canGo, d2, prev2, v1, d1);

        answer = Math.min(dist1, dist2);

        System.out.println(answer != Long.MAX_VALUE ? answer : -1);
    }

    private static long getDistance(long dist1, boolean canGo, long[] d1, int[] prev1, int v2, long[] d2) {
        dist1 += d1[0];
        if (pass(prev1, 0, v2) || pass(prev1, N - 1, v2)) {
            dist1 += d1[N - 1];
            canGo = canGo && d1[N - 1] != Long.MAX_VALUE;
        } else if (canGo) {
            dist1 += d1[v2] + d2[N - 1];
            canGo = canGo && d1[v2] != Long.MAX_VALUE && d2[N - 1] != Long.MAX_VALUE;
        }

        if (!canGo) {
            dist1 = Long.MAX_VALUE;
        }
        return dist1;
    }

    private static void dijkstra(long[] d, int[] prev, PriorityQueue<long[]> heap, int start) {

        int count = 0;
        visited = new boolean[N];
        d[start] = 0;
        heap.offer(new long[]{start, 0});

        while (!heap.isEmpty()) {

            long[] node = heap.poll();
            int vtx = (int) node[0];
            long distance = node[1];

            if (visited[vtx]) {
                continue;
            }

            visited[vtx] = true;

            if (++count == N) {
                break;
            }

            for (int[] adj : graph[vtx]) {
                if (!visited[adj[0]] && d[adj[0]] > distance + adj[1]) {
                    prev[adj[0]] = vtx;
                    d[adj[0]] = distance + adj[1];
                    heap.offer(new long[]{adj[0], d[adj[0]]});
                }
            }
        }
    }

    private static boolean pass(int[] prev, int start, int find) {

        int node = start;

        while (node >= 0) {
            node = prev[node];
            if (node == find) {
                return true;
            }
        }

        return false;
    }
}
