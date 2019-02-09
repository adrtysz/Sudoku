import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;

import main.java.generator.*;

import org.junit.jupiter.api.Test;

class BoardTest {
	
	private int[][] sudoku = {
			{8,6,0,0,2,0,0,0,0},
			{0,0,0,7,0,0,0,5,9},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,6,0,8,0,0},
			{0,4,0,0,0,0,0,0,0},
			{0,0,5,3,0,0,0,0,7},
			{0,0,0,0,0,0,0,0,0},
			{0,2,0,0,0,0,6,0,0},
			{0,0,7,5,0,9,0,0,0}
	};
	
	private SudokuSolver solver;
	
	@Before
	public void initSolver() {
		solver = new SudokuSolver(sudoku);
	}
	

	@Test
	public void testSolve() {
		for(int i=1;i<10;i++) {
			for(int j=1;j<10;j++) {
				solver.solve(i,j);
			}
		}
	}
	
	@Test
	public void testVerifySolve() {
		int[][] board = solver.getBoard();
		for(int i=1;i<10;i++) {
			for(int j=1;j<10;j++) {
				assertEquals(sudoku[i][j], board[i][j]);
			}
		}
	}

}
