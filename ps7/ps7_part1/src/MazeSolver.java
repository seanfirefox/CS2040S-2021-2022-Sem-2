import java.util.ArrayList;

import java.util.LinkedList;

import java.util.Queue;

public class MazeSolver implements IMazeSolver {

	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;

	private static final int[][] DELTAS = new int[][] {

			{ -1, 0 }, // North

			{ 1, 0 }, // South

			{ 0, 1 }, // East

			{ 0, -1 } // West

	};

	private Maze maze;

	private boolean solved;

	private ArrayList<Integer> steps;

	public MazeSolver() {

		// TODO: Initialize variables.

		this.solved = false;

		this.maze = null;

	}

	private static class Point {

		int x;

		int y;

		Point parent;

		public Point(int x, int y) {

			this.x = x;

			this.y = y;

		}

	}

	@Override

	public void initialize(Maze maze) {

		// TODO: Initialize the solver.

		this.maze = maze;

	}

	@Override

	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {

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

	public static void main(String[] args) {

		try {

			Maze maze = Maze.readMaze("maze-sample.txt");

			IMazeSolver solver = new MazeSolver();

			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 3));

			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {

				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}