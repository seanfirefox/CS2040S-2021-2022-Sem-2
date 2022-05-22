import java.util.*;

import java.util.function.Function;

public class MazeSolver implements IMazeSolver {

	private static final int TRUE_WALL = Integer.MAX_VALUE;

	private static final int EMPTY_SPACE = 0;

	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;

	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(

			Room::getNorthWall,

			Room::getEastWall,

			Room::getWestWall,

			Room::getSouthWall

	);

	private static final int[][] DELTAS = new int[][] {

			{ -1, 0 }, // North

			{ 0, 1 }, // East

			{ 0, -1 }, // West

			{ 1, 0 } // South

	};

	private Maze maze;

	int[][] fearCount;

	boolean[][] visited;

	PriorityQueue<Point> pq;

	int vertices;

	public MazeSolver() {

		// TODO: Initialize variables.

		this.maze = null;

		this.fearCount = null;

	}

	@Override

	public void initialize(Maze maze) {

		// TODO: Initialize the solver.

		this.maze = maze;

	}

	private static class Point implements Comparator<Point> {

		int x;

		int y;

		int fear;

		Point parent;

		public Point() {}

		public Point(int x, int y) {

			this.x = x;

			this.y = y;

		}

		public Point(int x, int y, int fear) {

			this.x = x;

			this.y = y;

			this.fear = fear;

		}

		@Override

		public int compare(Point point1, Point point2) {

			if (point1.fear < point2.fear)

				return -1;

			if (point1.fear > point2.fear)

				return 1;

			return 0;

		}

	}

	@Override

	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {

		// TODO: Find minimum fear level.

		if (maze == null) {

			throw new Exception("Oh no! You cannot call me without initializing the maze!");

		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||

				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {

			throw new IllegalArgumentException("Invalid start/end coordinate");

		}

		fearCount = new int[maze.getRows()][maze.getColumns()];

		visited = new boolean[maze.getRows()][maze.getColumns()];

		vertices = maze.getRows() * maze.getColumns();

		pq = new PriorityQueue<Point>(vertices, new Point());

		for (int i = 0; i < maze.getRows(); ++i) {

			for (int j = 0; j < maze.getColumns(); ++j) {

				fearCount[i][j] = Integer.MAX_VALUE;

				visited[i][j] = false;

			}

		}

		pq.add(new Point(startRow, startCol, 0));

		Point end;

		fearCount[startRow][startCol] = 0;

		while (!pq.isEmpty()) {

			Point curr = pq.remove();

			if(!visited[curr.x][curr.y]) {

				visited[curr.x][curr.y] = true;

				visitAllDirection(curr.x, curr.y, curr.fear);

			}

		}

		return fearCount[endRow][endCol] == Integer.MAX_VALUE

				? null

				: fearCount[endRow][endCol];

	}

	public void visitAllDirection(int row, int col, int fear) {

		int currFear;

		int newFear;

		for(int direction = 0; direction < 4; direction++) {

			int rowCheck = row + DELTAS[direction][0];

			int colCheck = col + DELTAS[direction][1];

			if (!(rowCheck < 0 || rowCheck >= maze.getRows())) {

				if (!(colCheck < 0 || colCheck >= maze.getColumns())) {

					Function<Room, Integer> funct = WALL_FUNCTIONS.get(direction);

					int wallFear = funct.apply(maze.getRoom(row, col));

					if (!(visited[rowCheck][colCheck]) && wallFear != Integer.MAX_VALUE) {

						currFear = wallFear == 0

								? 1

								: wallFear;

						newFear = currFear + fearCount[row][col];

						fearCount[rowCheck][colCheck] =

								Math.min(newFear, fearCount[rowCheck][colCheck]);

						pq.add(new Point(rowCheck, colCheck, fearCount[rowCheck][colCheck]));

					}

				}

			}

		}

	}

	@Override

	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {

		if (maze == null) {

			throw new Exception("Oh no! You cannot call me without initializing the maze!");

		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||

				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {

			throw new IllegalArgumentException("Invalid start/end coordinate");

		}

		fearCount = new int[maze.getRows()][maze.getColumns()];

		visited = new boolean[maze.getRows()][maze.getColumns()];

		vertices = maze.getRows() * maze.getColumns();

		pq = new PriorityQueue<Point>(vertices, new Point());

		for (int i = 0; i < maze.getRows(); ++i) {

			for (int j = 0; j < maze.getColumns(); ++j) {

				fearCount[i][j] = Integer.MAX_VALUE;

				visited[i][j] = false;

			}

		}

		pq.add(new Point(startRow, startCol, 0));

		Point end;

		fearCount[startRow][startCol] = 0;

		while (!pq.isEmpty()) {

			Point curr = pq.remove();

			if(!visited[curr.x][curr.y]) {

				visited[curr.x][curr.y] = true;

				visitAllDirectionBonus(curr.x, curr.y);

			}

		}

		return fearCount[endRow][endCol] == Integer.MAX_VALUE

				? null

				: fearCount[endRow][endCol];

	}

	public void visitAllDirectionBonus(int row, int col) {

		int newFear;

		for(int direction = 0; direction < 4; direction++) {

			int rowCheck = row + DELTAS[direction][0];

			int colCheck = col + DELTAS[direction][1];

			if (!(rowCheck < 0 || rowCheck >= maze.getRows())) {

				if (!(colCheck < 0 || colCheck >= maze.getColumns())) {

					Function<Room, Integer> funct = WALL_FUNCTIONS.get(direction);

					int wallFear = funct.apply(maze.getRoom(row, col));

					if (!(visited[rowCheck][colCheck]) && wallFear != Integer.MAX_VALUE) {

						newFear = Math.max(wallFear, fearCount[row][col]);

						fearCount[rowCheck][colCheck] =

								wallFear == 0

										? Math.min(1 + fearCount[row][col], fearCount[rowCheck][colCheck])

										: Math.min(newFear, fearCount[rowCheck][colCheck]);

						pq.add(new Point(rowCheck, colCheck, fearCount[rowCheck][colCheck]));

					}

				}

			}

		}

	}

	@Override

	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {

		// TODO: Find minimum fear level given new rules and special room.

		return null;

	}

	public static void main(String[] args) {

		try {

			Maze maze = Maze.readMaze("haunted-maze-sample.txt");

			IMazeSolver solver = new MazeSolver();

			solver.initialize(maze);

			System.out.println(solver.bonusSearch(1, 2, 1, 5));

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}