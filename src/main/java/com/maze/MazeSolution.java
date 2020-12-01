package com.maze;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class MazeSolution {

	public static final double GRAYSCALE_THRESHOLD = .4;

	public static void main(final String[] args) throws Exception {
		solveMazeFromFile("images/maze1.png", "images/maze1-solution.png");
		solveMazeFromFile("images/maze2.png", "images/maze2-solution.png");
		solveMazeFromFile("images/maze3.png", "images/maze3-solution.png");
	}

	private static void solveMazeFromFile(final String inFilename, final String outFilename) throws IOException {
		// Read in the file and extract maze
		System.out.println("[Image:'" + inFilename + "] Reading...");
		final BufferedImage image = ImageIO.read(new File(inFilename));
		final boolean[][] array = getBooleanArray(image);
		final StringBuffer sb = new StringBuffer();
		int count = 0;
		boolean hasStart = false;
		boolean hasExit = false;
		for (final boolean[] bs : array) {
			for (int i = 0; i < array.length; i++) {
				final boolean bs2 = bs[i];
				if (!bs2 && count == 0 && !hasStart) {
					sb.append("S");
					hasStart = true;
				} else if (!bs2 && count == array.length - 1 && !hasExit) {
					sb.append("E");
					hasExit = true;
				} else if (bs2)
					sb.append("#");
				else {
					sb.append(" ");
				}
			}
			sb.append("\n");
			count++;
		}

		final Maze maze = new Maze(sb.toString());
		final MazeSolver mazeSolver = new MazeSolver();
		final List<Coordinate> path = mazeSolver.solve(maze);
		maze.reset();

		// Generate and save solution image to file
		final Image solutionImage = overlaySolutionOntoImage(image, path);
		ImageIO.write((RenderedImage) solutionImage, "png", new File(outFilename));
		System.out.println("[Image:'" + inFilename + "] Finished!");
	}

	public static BufferedImage overlaySolutionOntoImage(final BufferedImage image, final List<Coordinate> path) {
		// For each coordinate in the solution paint the corresponding pixel red
		final BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++)
				img.setRGB(x, y, image.getRGB(x, y));
		for (final Coordinate coord : path)
			img.setRGB(coord.getY(), coord.getX(), new Color(255, 20, 147).getRGB());
		// Return the image
		return img;
	}

	public static boolean[][] getBooleanArray(final BufferedImage image) {

		// Declare the 2D boolean array
		final boolean[][] maze = new boolean[image.getHeight()][image.getWidth()];

		// For each space in the array, if the cooresponding pixel is under the
		// threshold make it true
		for (int row = 0; row < maze.length; row++)
			for (int col = 0; col < maze[0].length; col++) {
				// Extract color and corresponding grayscale then normalize to a percentage
				final Color pixelColor = new Color(image.getRGB(col, row));
				double grayScale = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue());
				grayScale /= 3.0;
				grayScale /= 255.0;
				// If under GRAYSCALE_THRESHOLD then make array entry true.
				if (grayScale < GRAYSCALE_THRESHOLD)
					maze[row][col] = true;
			}

		// Return the 2D array
		return maze;
	}

}
