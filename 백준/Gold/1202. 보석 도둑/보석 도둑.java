import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int n, k;
    static Jewel[] jewels;
    static int[] bags;

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        jewels = new Jewel[n];
        bags = new int[k];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            jewels[i] = new Jewel(
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())
            );
        }

        for (int i = 0; i < k; i++) {
            bags[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(jewels, (j1, j2) -> {
            if (j1.weight == j2.weight) {
                return j2.price - j1.price;
            }
            return j1.weight - j2.weight;
        });
        Arrays.sort(bags);

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        long totalPrice = 0;
        int index = 0;

        for (int bag : bags) {
            while (index < n && jewels[index].weight <= bag) {
                maxHeap.offer(jewels[index++].price);
            }

            if (!maxHeap.isEmpty()) {
                totalPrice += maxHeap.poll();
            }
        }

        System.out.println(totalPrice);
    }

    static class Jewel {
        int weight;
        int price;

        public Jewel(int weight, int price) {
            this.weight = weight;
            this.price = price;
        }
    }
}
