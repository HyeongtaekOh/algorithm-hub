import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int h, w, keys, docs, count;
    static char[][] map;
    static boolean[][] visited;
    static ArrayDeque<int[]>[] doors;
    static ArrayDeque<int[]> entrance;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());

        for (int tc = 0; tc < T; tc++) {
            st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            map = new char[h][w];
            visited = new boolean[h][w];
            doors = new ArrayDeque[26];
            entrance = new ArrayDeque<>();
            keys = 0;
            docs = 0;
            count = 0;

            for (int i = 0; i < 26; i++) {
                doors[i] = new ArrayDeque<>();
            }

            for (int i = 0; i < h; i++) {
                String line = br.readLine();
                for (int j = 0; j < w; j++) {
                    map[i][j] = line.charAt(j);
                    if (map[i][j] == '$') {
                        docs++;
                    }
                }
            }

            String keyLine = br.readLine();

            if (!keyLine.equals("0")) {
                for (int i = 0, len = keyLine.length(); i < len; i++) {
                    keys = keys | (1 << (keyLine.charAt(i) - 'a'));
                }
            }

            for (int i = 0; i < h; i++) {

                char cLeft = map[i][0];
                char cRight = map[i][w - 1];

                if (cLeft >= 'A' && cLeft <= 'Z' && (keys & (1 << (cLeft - 'A'))) == 0) {
                    doors[cLeft - 'A'].add(new int[] {i, 0});
                } else if (cLeft != '*') {
                    entrance.add(new int[] {i, 0});
                }

                if (cRight >= 'A' && cRight <= 'Z' && (keys & (1 << (cRight - 'A'))) == 0) {
                    doors[cRight - 'A'].add(new int[] {i, w - 1});
                } else if (cRight != '*') {
                    entrance.add(new int[] {i, w - 1});
                }
            }

            for (int i = 1; i < w - 1; i++) {

                char cTop = map[0][i];
                char cBottom = map[h - 1][i];

                if (cTop >= 'A' && cTop <= 'Z' && (keys & (1 << (cTop - 'A'))) == 0) {
                    doors[cTop - 'A'].add(new int[] {0, i});
                } else if (cTop != '*') {
                    entrance.add(new int[] {0, i});
                }

                if (cBottom >= 'A' && cBottom <= 'Z' && (keys & (1 << (cBottom - 'A'))) == 0) {
                    doors[cBottom - 'A'].add(new int[] {h - 1, i});
                } else if (cBottom != '*') {
                    entrance.add(new int[] {h - 1, i});
                }
            }

            while (!entrance.isEmpty()) {

                int[] e = entrance.poll();
                if (!visited[e[0]][e[1]]) {
                    visited[e[0]][e[1]] = true;
                    dfs(e[0], e[1]);
                }
            }

            sb.append(count).append("\n");
        }

        System.out.print(sb);
    }

    private static void updateKeys(char key) {
        if ((keys & (1 << (key - 'a'))) == 0) {
            while (!doors[key - 'a'].isEmpty()) {
                int[] door = doors[key - 'a'].poll();
                map[door[0]][door[1]] = '.';
                entrance.add(new int[] {door[0], door[1]});
            }
            keys = keys | (1 << (key - 'a'));
        }
    }

    private static void dfs(int x, int y) {

        if (count == docs) {
            return;
        }

        if (map[x][y] == '$') {
            count++;
        } else if (map[x][y] >= 'a' && map[x][y] <= 'z') {
            updateKeys(map[x][y]);
            map[x][y] = '.';
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= h || ny < 0 || ny >= w) {
                continue;
            }

            if (visited[nx][ny] || map[nx][ny] == '*') {
                continue;
            }

            if (map[nx][ny] >= 'A' && map[nx][ny] <= 'Z') {
                int alphaBit = 1 << (map[nx][ny] - 'A');
                if ((keys & alphaBit) != 0) {
                    map[nx][ny] = '.';
                } else {
                    doors[map[nx][ny] - 'A'].add(new int[] {nx, ny});
                    continue;
                }
            }

            visited[nx][ny] = true;
            dfs(nx, ny);
        }
    }
}
