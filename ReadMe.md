# How Maze Path Finder works:
1. Reading the image into pixels & Putting those pixels into symbols(S,E,#)/spaces
2. Track the shortest path from S to E using BFS(Breadth-First Search) and Put the coordinates in a list to backtrack the Path.
3. Draw the path on to the image

# How to run:
1. Store the mazes to be solved in images folder.
2. Change the respective input/output file names in the main method of MazeSolution class
3. Run MazeSolution class 
4. Now, Images folder will have the maze images with path traced on them.


## Challenging part:
Implementing the BFS.