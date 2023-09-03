import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, M, X;
    static List<int[]>[] graph, graphReversed;
    static int[] dFromX, dToX;
    static boolean[] visited;
    static PriorityQueue<int[]> heapFromX = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
    static PriorityQueue<int[]> heapToX = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken()) - 1;

        graph = new List[N];
        graphReversed = new List[N];
        dFromX = new int[N];
        dToX = new int[N];

        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>(10);
            graphReversed[i] = new ArrayList<>(10);
            dFromX[i] = Integer.MAX_VALUE;
            dToX[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());

            graph[a].add(new int[]{b, c});
            graphReversed[b].add(new int[]{a, c});
        }

        dijkstra(graph, dFromX, heapFromX);
        dijkstra(graphReversed, dToX, heapToX);

        int maxIndex = 0;

        for (int i = 1; i < N; i++) {
            if (dFromX[i] + dToX[i] > dFromX[maxIndex] + dToX[maxIndex]) {
                maxIndex = i;
            }
        }

        System.out.println(dFromX[maxIndex] + dToX[maxIndex]);
    }

    static void dijkstra(List<int[]>[] g, int[] d, PriorityQueue<int[]> heap) {

        int count = 0;
        visited = new boolean[N];
        d[X] = 0;
        heap.offer(new int[]{X, d[X]});

        while (!heap.isEmpty()) {

            int[] node = heap.poll();
            int student = node[0];
            int distance = node[1];

            if (visited[student]) {
                continue;
            }

            visited[student] = true;

            if (++count == N) {
                break;
            }

            for (int[] adj : g[student]) {
                if (!visited[adj[0]] && d[adj[0]] > distance + adj[1]) {
                    d[adj[0]] = distance + adj[1];
                    heap.offer(new int[] {adj[0], d[adj[0]]});
                }
            }
        }
    }
}
