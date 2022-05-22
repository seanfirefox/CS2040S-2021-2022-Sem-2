import java.lang.reflect.Array;

import java.util.ArrayList;

import java.util.LinkedList;

import java.util.Queue;

public class MazeSolverWithPower implements IMazeSolverWithPower {

	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;

	private static int[][] DELTAS = new int[][] {

			{ -1, 0 }, // North

			{ 1, 0 }, // South

			{ 0, 1 }, // East

			{ 0, -1 } // West

	};

	private Maze maze;

	private boolean solved;

	private ArrayList<Integer> steps;

	private boolean[][][] visited;

	public MazeSolverWithPower() {

		// TODO: Initialize variables.

		this.solved = false;

		this.maze = null;

	}

	@Override

	public void initialize(Maze maze) {

		// TODO: Initialize the solver.

		this.maze = maze;

	}

	private static class Point {

		int x;

		int y;

		int superpowers;

		Point parent;

		public Point(int x, int y) {

			this.x = x;

			this.y = y;

		}

		public Point(int x, int y, int superpowers) {

			this.x = x;

			this.y = y;

			this.superpowers = superpowers;

		}

	}

	@Override

	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {

		// TODO: Find shortest path.

		// TODO: Find shortest path.

		if (maze == null) {

			throw new Exception("Oh no! You cannot call me without initializing the maze!");

		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||

				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {

			throw new IllegalArgumentException("Invalid start/end coordinate");

		}

		solved = false;

		boolean[][] visited = new boolean[maze.getRows()][maze.getColumns()];

		steps = new ArrayList<>();

		for (int i = 0; i < maze.getRows(); ++i) {

			for (int j = 0; j < maze.getColumns(); ++j) {

				visited[i][j] = false;

				maze.getRoom(i, j).onPath = false;

			}

		}

		int counter = 0;

		int answer = 0;

		Point src = new Point(startRow, startCol);

		src.parent = null;

		Point marked = null;

		Queue<Point> queue =  new LinkedList<>();

		queue.add(src);

		visited[src.x][src.y] = true;

		maze.getRoom(src.x, src.y).onPath = true;

		while (!queue.isEmpty()) {

			int len = queue.size();

			steps.add(len);

			for (int i = 0; i < len; i++) {

				Point currPoint = queue.poll();

				if (currPoint.x == endRow && currPoint.y == endCol) {

					solved = true;

					marked = currPoint;

					answer = counter;

				}

				for (int direction = 0; direction < 4; ++direction) {

					if (canGo(currPoint.x, currPoint.y, direction)) { // can we go in that direction?

						// yes we can :)

						Point nextPoint =

								new Point(currPoint.x + DELTAS[direction][0], currPoint.y + DELTAS[direction][1]);

						nextPoint.parent = currPoint;

						System.out.println(nextPoint.x + " " + nextPoint.y);

						if (!visited[nextPoint.x][nextPoint.y]) {

							visited[nextPoint.x][nextPoint.y] = true;

							queue.add(nextPoint);

						}

					}

				}

			}

			counter++;

		}

		if (solved) {

			while (marked.parent != null) {

				maze.getRoom(marked.x, marked.y).onPath = true;

				marked = marked.parent;

			}

			return answer;

		}

		return null;

	}

	private boolean canGo(int row, int col, int dir) {

		// not needed since our maze has a surrounding block of wall

		// but Joe the Average Coder is a defensive coder!

		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;

		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {

			case NORTH:

				return !maze.getRoom(row, col).hasNorthWall();

			case SOUTH:

				return !maze.getRoom(row, col).hasSouthWall();

			case EAST:

				return !maze.getRoom(row, col).hasEastWall();

			case WEST:

				return !maze.getRoom(row, col).hasWestWall();

		}

		return false;

	}

	@Override

	public Integer numReachable(int k) throws Exception {

		// TODO: Find number of reachable rooms.

		if (k > steps.size() - 1 || k < 0) {

			return 0;

		} else {

			return steps.get(k);

		}

	}

	@Override

	public Integer pathSearch(int startRow, int startCol, int endRow,

							  int endCol, int superpowers) throws Exception {

		// TODO: Find shortest path with powers allowed.

		if (maze == null) {

			throw new Exception("Oh no! You cannot call me without initializing the maze!");

		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||

				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {

			throw new IllegalArgumentException("Invalid start/end coordinate");

		}

		solved = false;

		visited = new boolean[maze.getRows()][maze.getColumns()][superpowers + 1];

		steps = new ArrayList<>();

		for (int i = 0; i < maze.getRows(); ++i) {

			for (int j = 0; j < maze.getColumns(); ++j) {

				maze.getRoom(i, j).onPath = false;

			}

		}

		int counter = 0;

		int answer = 0;

		boolean[][] stepsHelper = new boolean[maze.getRows()][maze.getColumns()];

		Point src = new Point(startRow, startCol, superpowers);

		src.parent = null;

		Point marked = null;

		Queue<Point> queue =  new LinkedList<>();

		queue.add(src);

		visited[src.x][src.y][superpowers] = true;

		maze.getRoom(src.x, src.y).onPath = true;

		while (!queue.isEmpty()) {

			int len = queue.size();

			int stepsTaken = 0;

			for (int i = 0; i < len; i++) {

				Point currPoint = queue.poll();

				if (!stepsHelper[currPoint.x][currPoint.y]) {

					stepsHelper[currPoint.x][currPoint.y] = true;

					stepsTaken++;

					if (currPoint.x == endRow && currPoint.y == endCol) {

						solved = true;

						marked = currPoint;

						answer = counter;

					}

				}

				for (int direction = 0; direction < 4; ++direction) {

					int checkCanFly = canFly(currPoint.x, currPoint.y, direction, currPoint.superpowers);

					if (checkCanFly == 1) { // can we go in that direction?

						// yes we can :)

						Point nextPoint =

								new Point(currPoint.x + DELTAS[direction][0],

										currPoint.y + DELTAS[direction][1],

										currPoint.superpowers);

						nextPoint.parent = currPoint;

						if (!visited[nextPoint.x][nextPoint.y][nextPoint.superpowers]) {

							visited[nextPoint.x][nextPoint.y][nextPoint.superpowers] = true;

						}

						queue.add(nextPoint);

					} else if (checkCanFly == 2) {

						Point nextPoint =

								new Point(currPoint.x + DELTAS[direction][0],

										currPoint.y + DELTAS[direction][1],

										currPoint.superpowers - 1);

						nextPoint.parent = currPoint;

						if (!visited[nextPoint.x][nextPoint.y][nextPoint.superpowers]) {

							visited[nextPoint.x][nextPoint.y][nextPoint.superpowers] = true;

						}

						queue.add(nextPoint);

					} else {}

				}

			}

			counter++;

			steps.add(stepsTaken);

		}

		if (solved) {

			while (marked.parent != null) {

				maze.getRoom(marked.x, marked.y).onPath = true;

				marked = marked.parent;

			}

			return answer;

		}

		return null;

	}

	private int canFly(int row, int col, int dir, int superpower) {

		// not needed since our maze has a surrounding block of wall

		// but Joe the Average Coder is a defensive coder!

		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return 0;

		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return 0;

		if (visited[row + DELTAS[dir][0]][col + DELTAS[dir][1]][superpower]) return 0;

		switch (dir) {

			case NORTH:

				return !maze.getRoom(row, col).hasNorthWall()

						? 1

						: superpower > 0 && !visited[row +

						DELTAS[dir][0]][col + DELTAS[dir][1]][superpower - 1]

						? 2

						: 0;

			case SOUTH:

				return !maze.getRoom(row, col).hasSouthWall()

						? 1

						: superpower > 0 && !visited[row +

						DELTAS[dir][0]][col + DELTAS[dir][1]][superpower - 1]

						? 2

						: 0;

			case EAST:

				return !maze.getRoom(row, col).hasEastWall()

						? 1

						: superpower > 0 && !visited[row +

						DELTAS[dir][0]][col + DELTAS[dir][1]][superpower - 1]

						? 2

						: 0;

			case WEST:

				return !maze.getRoom(row, col).hasWestWall()

						? 1

						: superpower > 0 && !visited[row +

						DELTAS[dir][0]][col + DELTAS[dir][1]][superpower - 1]

						? 2

						: 0;

		}

		return 0;

	}

	public static void main(String[] args) {

		try {

			Maze maze = Maze.readMaze("maze-sample.txt");

			IMazeSolverWithPower solver = new MazeSolverWithPower();

			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 4, 2, 2));

			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {

				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}

