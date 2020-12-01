package com.maze;

import java.util.Arrays;

public class Maze {
	private static final int ROAD = 0;
	private static final int WALL = 1;
	private static final int START = 2;
	private static final int EXIT = 3;

	private int[][] maze;
	private boolean[][] visited;
	private Coordinate start;
	private Coordinate end;

	public Maze(final String input) {
		initializeMaze(input);
	}

	private void initializeMaze(String text) {
		if (text == null || (text = text.trim()).length() == 0) {
			throw new IllegalArgumentException("empty lines data");
		}

		final String[] lines = text.split("[\r]?\n");
		maze = new int[lines.length][lines[0].length()];
		visited = new boolean[lines.length][lines[0].length()];

		for (int row = 0; row < getHeight(); row++) {
			if (lines[row].length() != getWidth()) {
				throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was " + lines[row].length()
						+ " but should be " + getWidth() + ")");
			}

			for (int col = 0; col < getWidth(); col++) {
				if (lines[row].charAt(col) == '#')
					maze[row][col] = WALL;
				else if (lines[row].charAt(col) == 'S') {
					maze[row][col] = START;
					start = new Coordinate(row, col);
				} else if (lines[row].charAt(col) == 'E') {
					maze[row][col] = EXIT;
					end = new Coordinate(row, col);
				} else
					maze[row][col] = ROAD;
			}
		}
	}

	public int getHeight() {
		return maze.length;
	}

	public int getWidth() {
		return maze[0].length;
	}

	public Coordinate getEntry() {
		return start;
	}

	public Coordinate getExit() {
		return end;
	}

	public boolean isExit(final int x, final int y) {
		return x == end.getX() && y == end.getY();
	}

	public boolean isStart(final int x, final int y) {
		return x == start.getX() && y == start.getY();
	}

	public boolean isExplored(final int row, final int col) {
		return visited[row][col];
	}

	public boolean isWall(final int row, final int col) {
		return maze[row][col] == WALL;
	}

	public void setVisited(final int row, final int col, final boolean value) {
		visited[row][col] = value;
	}

	public boolean isValidLocation(final int row, final int col) {
		if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
			return false;
		}
		return true;
	}

	public String toString(final int[][] maze) {
		final StringBuilder result = new StringBuilder(getWidth() * (getHeight() + 1));
		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				if (maze[row][col] == ROAD) {
					result.append(' ');
				} else if (maze[row][col] == WALL) {
					result.append('#');
				} else if (maze[row][col] == START) {
					result.append('S');
				} else if (maze[row][col] == EXIT) {
					result.append('E');
				} else {
					result.append('.');
				}
			}
			result.append('\n');
		}
		return result.toString();
	}

	public void reset() {
		for (int i = 0; i < visited.length; i++)
			Arrays.fill(visited[i], false);
	}
}
