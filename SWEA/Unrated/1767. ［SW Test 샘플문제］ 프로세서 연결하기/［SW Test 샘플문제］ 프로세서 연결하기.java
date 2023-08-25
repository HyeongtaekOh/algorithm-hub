import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int N, answer, maxCoreCount;
    static boolean[][] map;
    static int[] left, right, top, bottom;
    static List<int[]> cores;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            sb.append("#").append(tc).append(" ");

            N = Integer.parseInt(br.readLine());
            map = new boolean[N][N];
            left = new int[N];
            right = new int[N];
            top = new int[N];
            bottom = new int[N];
            Arrays.fill(left, -1);
            Arrays.fill(right, -1);
            Arrays.fill(top, -1);
            Arrays.fill(bottom, -1);

            cores = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = st.nextToken().equals("1");
                    if (map[i][j]) {
                        cores.add(new int[]{i, j});
                    }
                }
            }

            maxCoreCount = 0;
            answer = Integer.MAX_VALUE;
            dfs(0, 0, 0);
            sb.append(answer).append("\n");
        }

        System.out.println(sb);
    }

    private static void dfs(int cnt, int coreCount, int length) {

        /* 남은 코어를 다 연결해도 최대 코어 수만큼 연결할 수 없을 때 */
        if (coreCount + (cores.size() - cnt) < maxCoreCount) {
            return;
        }

        if (cnt == cores.size()) {
            answer = coreCount > maxCoreCount ? length : Math.min(answer, length);
            maxCoreCount = Math.max(coreCount, maxCoreCount);
            return;
        }

        int x = cores.get(cnt)[0];
        int y = cores.get(cnt)[1];

        if (x == 0 || x == N - 1 || y == 0 || y == N - 1) {
            dfs(cnt + 1, coreCount + 1, length);
            return;
        }

        int wire;

        if (top[y] < 0 && (wire = calculateWire(x, y, 0)) > 0) {

            boolean possible = true;

            for (int i = 0; i < x; i++) {
                if ((left[i] != -1 && left[i] >= y) || (right[i] != -1 && right[i] <= y)) {
                    possible = false;
                    break;
                }
            }
            if (possible) {
                top[y] = x;
                dfs(cnt + 1, coreCount + 1, length + wire);
                top[y] = -1;
            }
        }


        if (left[x] < 0 && (wire = calculateWire(x, y, 1)) > 0) {

            boolean possible = true;

            for (int i = 0; i < y; i++) {
                if ((top[i] != -1 && top[i] >= x) || (bottom[i] != -1 && bottom[i] <= x)) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                left[x] = y;
                dfs(cnt + 1, coreCount + 1, length + wire);
                left[x] = -1;
            }
        }

        if (bottom[y] < 0 && (wire = calculateWire(x, y, 2)) > 0) {

            boolean possible = true;

            for (int i = x + 1; i < N; i++) {
                if ((left[i] != -1 && left[i] >= y) || (right[i] != -1 && right[i] <= y)) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                bottom[y] = x;
                dfs(cnt + 1, coreCount + 1, length + wire);
                bottom[y] = -1;
            }
        }

        if (right[x] < 0 && (wire = calculateWire(x, y, 3)) > 0) {

            boolean possible = true;

            for (int i = y + 1; i < N; i++) {
                if ((top[i] != -1 && top[i] >= x) || (bottom[i] != -1 && bottom[i] <= x)) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                right[x] = y;
                dfs(cnt + 1, coreCount + 1, length + wire);
                right[x] = -1;
            }
        }

        dfs(cnt + 1, coreCount, length);
    }

    private static int calculateWire(int x, int y, int direction) {

        int wire = 0;
        int nx = x;
        int ny = y;

        do {
            nx += dx[direction];
            ny += dy[direction];

            if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
                break;
            }

            ++wire;
        } while (!map[nx][ny]);

        if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
            return wire;
        }

        return -1;
    }
}