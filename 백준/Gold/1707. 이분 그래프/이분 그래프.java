import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    enum Bipartite {
        UNDEFINED, RED, BLUE
    }

    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static ArrayList<Short>[] graph;
    static Bipartite[] bipartite;
    static boolean isBipartite;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int k = Integer.parseInt(br.readLine());

        for (int testcase = 0; testcase < k; testcase++) {

            st = new StringTokenizer(br.readLine());

            short v = Short.parseShort(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            graph = new ArrayList[v + 1];
            bipartite = new Bipartite[v + 1];

            for (int i = 1; i < v + 1; i++) {
                graph[i] = new ArrayList<>();
                bipartite[i] = Bipartite.UNDEFINED;
            }

            for (int i = 0; i < e; i++) {
                st = new StringTokenizer(br.readLine());
                short v1 = Short.parseShort(st.nextToken());
                short v2 = Short.parseShort(st.nextToken());

                graph[v1].add(v2);
                graph[v2].add(v1);
            }

            isBipartite = true;

            for (int i = 1; i <= v; i++) {
                if (bipartite[i] == Bipartite.UNDEFINED) {
                    dfs((short) i, Bipartite.RED);
                }
            }

            for (short node = 1; node <= v; node++) {

                for (short adj : graph[node]) {
                    if (bipartite[node] == bipartite[adj]) {
                        isBipartite = false;
                        break;
                    }
                }

                if (!isBipartite) {
                    break;
                }
            }


            sb.append(isBipartite ? "YES\n" : "NO\n");
        }

        System.out.println(sb);
    }

    static void dfs(short node, Bipartite b) {

        if (!isBipartite ||
                (bipartite[node] != Bipartite.UNDEFINED && bipartite[node] != b)) {
            isBipartite = false;
            return;
        }

        bipartite[node] = b;

        for (short adj : graph[node]) {
            if (bipartite[adj] == Bipartite.UNDEFINED) {
                dfs(adj, b == Bipartite.RED ? Bipartite.BLUE : Bipartite.RED);
            }
        }
    }
}
