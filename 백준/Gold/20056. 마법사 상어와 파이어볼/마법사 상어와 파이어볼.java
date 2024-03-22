import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int N, M, K, answer;
	static ArrayDeque<FireBall>[][] map;
	static ArrayDeque<FireBall> Q = new ArrayDeque<>();
	static HashSet<Point> S = new HashSet<>();

	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

	public static void main(String[] args) throws IOException {

		st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new ArrayDeque[N][N];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = new ArrayDeque<>();
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());

			FireBall fb = new FireBall(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1,
					Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()));

			Q.offer(fb);
			map[fb.r][fb.c].offer(fb);
		}

		for (int i = 0; i < K; i++) {
			
			answer = 0;
			int fbNum = Q.size();
			S.clear();

			for (int j = 0; j < fbNum; j++) {

				FireBall fb = Q.poll();
				map[fb.r][fb.c].poll();

				fb.move();
			}
			
			for (Point p : S) {
				
				if (map[p.x][p.y].size() > 1) {
					fuse(p);
				} else {
					Q.offer(map[p.x][p.y].peek());
				}
				
				for (FireBall f : map[p.x][p.y]) {
					answer += f.m;
				}
			}
		}
		
		System.out.println(answer);
	}

	static void fuse(Point p) {
		
		int size = 0;
		int speed = 0;
		int len = map[p.x][p.y].size();
		int directionOffset = 0;
		
		FireBall fb = map[p.x][p.y].poll();
		size += fb.m;
		speed += fb.s;
		boolean isEven = fb.d % 2 == 0;
		
		for (int i = 1; i < len; i++) {
			fb = map[p.x][p.y].poll();
			size += fb.m;
			speed += fb.s;
			
			if ((isEven && fb.d % 2 != 0) || (!isEven && fb.d % 2 == 0)) {
				directionOffset = 1;
			}
		}
		
		if (size < 5) {
			return;
		}
		
		size /= 5;
		speed /= len;
		
		
		for (int i = 0; i < 4; i++) {
			FireBall newFb = new FireBall(p.x, p.y, size, speed, 2 * i + directionOffset);
			map[p.x][p.y].offer(newFb);
			Q.offer(newFb);
		}
	}

	static class FireBall {
		int r;
		int c;
		int m;
		int s;
		int d;

		public FireBall(int r, int c, int m, int s, int d) {
			super();
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}

		@Override
		public String toString() {
			return "FireBall [r=" + r + ", c=" + c + ", m=" + m + ", s=" + s + ", d=" + d + "]";
		}

		public void move() {

			int nr = (r + dx[d] * s) % N;
			int nc = (c + dy[d] * s) % N;

			if (nr < 0) {
				nr += N;
			}
			if (nc < 0) {
				nc += N;
			}
			
			r = nr;
			c = nc;

			S.add(new Point(nr, nc));
			map[nr][nc].offer(this);
		}
	}

	static class Point {

		int x;
		int y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

	}
}