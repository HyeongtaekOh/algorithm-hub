import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int n, s;
	static int[] a;
	static Map<Integer, Integer> left, right;
	
	public static void main(String[] args) throws IOException {
		
		st = new StringTokenizer(br.readLine(), " ");
		n = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());
		
		a = new int[n];
		
		st = new StringTokenizer(br.readLine(), " ");
		
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(st.nextToken());
		}
		
		left = new HashMap<>();
		right = new HashMap<>();
		
		subset(n >> 1, 0, 0, left);
		subset(n, n >> 1, 0, right);
		
		long answer = 0;
		
		for (Map.Entry<Integer, Integer> entry : left.entrySet()) {
			int key = entry.getKey();
			long value = entry.getValue();

			if (right.containsKey(s - key)) {
				answer += value * right.get(s - key);
			}
		}
		
		System.out.println(s != 0 ? answer : answer - 1);
	}
	
	private static void subset(int end, int index, int sum, Map<Integer, Integer> map) {
		
		if (index == end) {
			if (!map.containsKey(sum)) {
				map.put(sum, 1);
			} else {
				map.put(sum, map.get(sum) + 1);
			}
			
			return;
		}
		
		subset(end, index + 1, sum + a[index], map);
		subset(end, index + 1, sum, map);		
	}
}
