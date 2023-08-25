import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static StringBuilder sb = new StringBuilder();

	static char[][] print; // 설계 도면
	static int[] m = new int[2], z = new int[2]; // 출발, 도착 좌표
	static int blockCount; // 도면에 존재하는 블록의 수

	/* 방향 벡터 (상 / 하 / 좌 / 우) */
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());

		int r = Integer.parseInt(st.nextToken()); // 행의 수
		int c = Integer.parseInt(st.nextToken()); // 열의 수

		print = new char[r][c];
		blockCount = 0;
		/* 도면 정보를 입력받고 출발지점, 도착지점, 블록의 수 할당 */
		for (int i = 0; i < r; i++) {
			String line = br.readLine();
			for (int j = 0; j < c; j++) {
				print[i][j] = line.charAt(j);
				if (print[i][j] == 'M') {
					m[0] = i;
					m[1] = j;
				} else if (print[i][j] == 'Z') {
					z[0] = i;
					z[1] = j;
				}

				if (print[i][j] != '.') {
					++blockCount;
				}
			}
		}

		/**
		 * 각각 m, z 위치에서 서로를 향해 출발했을 때 정체가 발생한 좌표, 직전 이동방향, 해당 좌표까지 통과한 블록 수 (0: x 좌표, 1:
		 * y좌표, 2: 직전 이동방향, 3: 통과한 블록 수)
		 */
		int[] mStuck = new int[4];
		int[] zStuck = new int[4];

		/**
		 * M, Z에서 각각 출발하여 서로의 위치로 갈 수 있는 방향으로 forward. 모든 블록은 이동에 필요하고 길은 유일하므로 한 가지
		 * 방향으로만 수행된다. M, Z에서 출발한 forwarding 모두 도면의 지워진 부분에 도달하게 된다. 이때 각 forwarding
		 * 과정에서 통과한 블록의 수와 직전 이동 방향을 mStuck, zStuck에 저장.
		 */
		for (int i = 0; i < 4; ++i) {
			int nx = m[0] + dx[i];
			int ny = m[1] + dy[i];

			if (nx < 0 || nx >= r || ny < 0 || ny >= c) {
				continue;
			}

			if (i == 0
					&& (print[nx][ny] == '|' || print[nx][ny] == '+' || print[nx][ny] == '1' || print[nx][ny] == '4')) {
				forward(nx, ny, 0, 1, mStuck);
			} else if (i == 1
					&& (print[nx][ny] == '|' || print[nx][ny] == '+' || print[nx][ny] == '2' || print[nx][ny] == '3')) {
				forward(nx, ny, 1, 1, mStuck);
			} else if (i == 2
					&& (print[nx][ny] == '-' || print[nx][ny] == '+' || print[nx][ny] == '1' || print[nx][ny] == '2')) {
				forward(nx, ny, 2, 1, mStuck);
			} else if (i == 3
					&& (print[nx][ny] == '-' || print[nx][ny] == '+' || print[nx][ny] == '3' || print[nx][ny] == '4')) {
				forward(nx, ny, 3, 1, mStuck);
			}
		}
		for (int i = 0; i < 4; ++i) {
			int nx = z[0] + dx[i];
			int ny = z[1] + dy[i];

			if (nx < 0 || nx >= r || ny < 0 || ny >= c) {
				continue;
			}

			if (i == 0
					&& (print[nx][ny] == '|' || print[nx][ny] == '+' || print[nx][ny] == '1' || print[nx][ny] == '4')) {
				forward(nx, ny, 0, 1, zStuck);
			} else if (i == 1
					&& (print[nx][ny] == '|' || print[nx][ny] == '+' || print[nx][ny] == '2' || print[nx][ny] == '3')) {
				forward(nx, ny, 1, 1, zStuck);
			} else if (i == 2
					&& (print[nx][ny] == '-' || print[nx][ny] == '+' || print[nx][ny] == '1' || print[nx][ny] == '2')) {
				forward(nx, ny, 2, 1, zStuck);
			} else if (i == 3
					&& (print[nx][ny] == '-' || print[nx][ny] == '+' || print[nx][ny] == '3' || print[nx][ny] == '4')) {
				forward(nx, ny, 3, 1, zStuck);
			}
		}

		/**
		 * M, Z는 둘 다 같은 좌표에서 멈추게 되고, 이 위치가 도면의 지워진 부분.
		 */
		if (mStuck[2] == 0) {
			sb.append(zStuck[0] + 1).append(' ').append(zStuck[1] + 1).append(' ');
		} else {
			sb.append(mStuck[0] + 1).append(' ').append(mStuck[1] + 1).append(' ');
		}

		/**
		 * M, Z가 지워진 부분에 도달하기까지 통과한 블록의 수의 합이 전체 블록의 수보다 적을 경우, 해당 좌표는 두 번 통과해야 하는 십자형
		 * 블록이 있던 위치라는 것을 의미. 그 외 경우는 M, Z가 지워진 부분에 도달하기 직전에 취하고 있는 방향에 따라 블록이 정해진다.
		 */
		if (mStuck[3] + zStuck[3] < blockCount) {
			sb.append('+');
		} else if (mStuck[2] + zStuck[2] == 1) {
			sb.append('|');
		} else if (mStuck[2] + zStuck[2] == 5) {
			sb.append('-');
		} else if (mStuck[2] + zStuck[2] == 2) {
			sb.append('1');
		} else if (mStuck[2] + zStuck[2] == 4) {
			sb.append('3');
		} else if (mStuck[2] == 0) {
			sb.append('4');
		} else {
			sb.append('2');
		}

		sb.append('\n');

		System.out.println(sb);
	}

	/**
	 * 
	 * @param x         : 현재 행 위치
	 * @param y         : 현재 열 위치
	 * @param direction : 직전 이동 방향
	 * @param count     : 현재까지 지나온 블록의 수
	 * @param stuck     : 정체 위치(도면의 지워진 부분)에 도달했을 경우 관련 정보를 저장할 배열
	 */
	static void forward(int x, int y, int direction, int count, int[] stuck) {

		int nx = x, ny = y;

		/**
		 * 현재 블록의 종류와 직전 이동 방향에 따라 다음에 이동할 위치가 정해진다. 도면은 잘 설계되어 있어서 위 기준에 따라 정해진 다음 위치가
		 * 이동할 수 없는 경로일 경우는 도면에서 지워진 부분인 경우가 유일하다. 따라서 현재 위치가 빈 칸인 경우, 지워진 부분에 도달한 것이므로
		 * forwarding 중단.
		 */
		switch (print[x][y]) {
		case '|':
			nx += direction == 0 ? -1 : 1;
			break;
		case '-':
			ny += direction == 2 ? -1 : 1;
			break;
		case '+':
			if (direction < 2) {
				nx += direction == 0 ? -1 : 1;
			} else {
				ny += direction == 2 ? -1 : 1;
			}
			break;
		case '1':
			if (direction == 0) {
				++ny;
				direction = 3;
			} else {
				++nx;
				direction = 1;
			}
			break;
		case '2':
			if (direction == 1) {
				++ny;
				direction = 3;
			} else {
				--nx;
				direction = 0;
			}
			break;
		case '3':
			if (direction == 1) {
				--ny;
				direction = 2;
			} else {
				--nx;
				direction = 0;
			}
			break;
		case '4':
			if (direction == 0) {
				--ny;
				direction = 2;
			} else {
				++nx;
				direction = 1;
			}
			break;
		default:
			stuck[0] = x;
			stuck[1] = y;
			stuck[2] = direction;
			stuck[3] = count;
			return;
		}

		forward(nx, ny, direction, 1 + count, stuck);
	}
}
