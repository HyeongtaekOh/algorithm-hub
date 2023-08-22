import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int r, c, m, answer = 0;
	static Shark[][] map;
	static Set<Shark> sharks = new HashSet<>();
	static Queue<Shark> killed = new ArrayDeque<>();

	public static void main(String[] args) throws IOException {
		
		st = new StringTokenizer(br.readLine());
		
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		map = new Shark[r + 1][c + 1];
				
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int speed = Integer.parseInt(st.nextToken());
			int direction = Integer.parseInt(st.nextToken());
			int size = Integer.parseInt(st.nextToken());
			
			map[x][y] = new Shark(x, y, size, speed, direction);
			sharks.add(map[x][y]);
		}
		
		for (int i = 1; i <= c; i++) {

			fishing(i);
			
			for (Shark shark : sharks) {
				map[shark.x][shark.y] = null;
			}
			
			for (Shark shark : sharks) {
				move(shark, shark.direction == 1 || shark.direction == 4);
			}
			
			for (Shark shark : sharks) {
				int x = shark.x;
				int y = shark.y;
				
				if (map[x][y] == null) {
					map[x][y] = shark;
				} else if (map[x][y].size < shark.size) {
					killed.offer(map[x][y]);
					map[x][y] = shark;
				} else {
					killed.offer(shark);
				}
			}
			
			for (int k = 0, size = killed.size(); k < size; ++k) {
				sharks.remove(killed.poll());
			}
		}
		
		System.out.println(answer);
	}
	
	private static void fishing(int col) {
		for (int row = 1; row <= r; row++) {
			if (map[row][col] != null) {
				answer += map[row][col].size;
				sharks.remove(map[row][col]);
				map[row][col] = null;
				return;
			}
		}
	}
	
	private static void move(Shark shark, boolean reversed) {
		
		int cur = shark.direction < 3 ? shark.x : shark.y;
		int length = shark.direction < 3 ? r : c;
		int s = shark.speed % (2 * length - 2);
		int dest = 0;
		
		if (!reversed) {
			if (s <= length - cur) {
				dest = cur + s;
			} else if (s <= 2 * length - cur - 1) {
				shark.reverse();
				dest = 2 * length - s - cur;
			} else {
				dest = s - (2 * length - 2) + cur;
			}
		} else {
			if (s <= cur - 1) {
				dest = cur - s;
			} else if (s <= length + cur - 2) {
				shark.reverse();
				dest = s - cur + 2;
			} else {
				dest = (2 * length - 2) - s + cur;
			}
		}
		
		if (shark.direction < 3) {
			shark.x = dest;
		} else {
			shark.y = dest;
		}
	}
	
	private static class Shark {
		
		int x, y;
		int size;
		int speed;
		int direction;
		
		public Shark(int x, int y, int size, int speed, int direction) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.speed = speed;
			this.direction = direction;
		}
		
		void reverse() {
			if (direction < 3) {
				direction = 3 - direction;
			} else {
				direction = 7 - direction;
			}
		}
	}
}