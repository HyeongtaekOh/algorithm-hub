import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static boolean[] visitedIsland;
    static int islandIndex = 0;
    static int[][] graph;

    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, -1, 0, 1 };

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visited[i][j] && map[i][j] > 0) {
                    bfs(i, j);
                }
            }
        }
        
        visited = new boolean[N][M];
        graph = new int[islandIndex + 1][islandIndex + 1];
        visitedIsland = new boolean[islandIndex + 1];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] > 0 && !visitedIsland[map[i][j]]) {
                    findEdge(map[i][j], i, j);
                }
            }
        }

        int answer = 0;
        visitedIsland = new boolean[islandIndex + 1];
        int[] d = new int[islandIndex + 1];
        for (int j = 2; j <= islandIndex; j++) {
            d[j] = Integer.MAX_VALUE;
        }

        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(i -> i[1]));

        q.offer(new int[] {1, d[1]});
        int count = 0;
        while (!q.isEmpty()) {

            int[] node = q.poll();

            if (visitedIsland[node[0]]) {
                continue;
            }

            visitedIsland[node[0]] = true;
            d[node[0]] = node[1];
            answer += d[node[0]];


            if (++count == islandIndex) {
                break;
            }

            for (int k = 1; k <= islandIndex; k++) {
                if (!visitedIsland[k] && graph[node[0]][k] > 0
                        && d[k] > graph[node[0]][k]) {
                    d[k] = graph[node[0]][k];
                    q.offer(new int[] { k, d[k]});
                }
            }

        }
        System.out.println(count == islandIndex ? answer : -1);
    }

    private static void bfs(int sx, int sy) {

        ++islandIndex;
        Queue<int[]> q = new ArrayDeque<>();
        visited[sx][sy] = true;
        q.offer(new int[] { sx, sy });

        while (!q.isEmpty()) {

            int[] node = q.poll();
            int x = node[0];
            int y = node[1];
            map[x][y] = islandIndex;

            addAdjacentNodesToQueue(q, x, y);
        }
    }

    private static void findEdge(int island, int ix, int iy) {

        visitedIsland[island] = true;

        Queue<int[]> q = new ArrayDeque<>();
        visited[ix][iy] = true;
        q.offer(new int[] { ix, iy });

        while (!q.isEmpty()) {

            int[] node = q.poll();
            int x = node[0];
            int y = node[1];

            for (int i = 0; i < 4; i++) {

                int nx = x;
                int ny = y;

                int distance = 0;

                do {
                    nx += dx[i];
                    ny += dy[i];

                    if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
                        break;
                    }

                    if (map[nx][ny] == map[x][y]) {
                        break;
                    }

                    if (map[nx][ny] > 0 && distance > 1) {
                        int adj = map[nx][ny];
                        if (graph[island][adj] == 0) {
                            graph[island][adj] = graph[adj][island] = distance;
                        } else {
                            graph[island][adj] = Math.min(graph[island][adj], distance);
                            graph[adj][island] = graph[island][adj];
                        }
                    }

                    ++distance;
                } while (map[nx][ny] == 0);
            }

            addAdjacentNodesToQueue(q, x, y);
        }
    }

    private static void addAdjacentNodesToQueue(Queue<int[]> q, int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
                continue;
            }

            if (map[nx][ny] == 0 || visited[nx][ny]) {
                continue;
            }

            visited[nx][ny] = true;
            q.offer(new int[] { nx, ny });
        }
    }
}
