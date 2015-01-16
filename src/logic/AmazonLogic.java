package logic;

import experimenting.AutomatedRun;
import gui.AmazonUI;
import gui.InfoPanel;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.GameObject;
import model.Move;
import model.Queen;
import ai.ArtificialIntelligence;
import ai.mcts2;

@Getter
public class AmazonLogic implements MouseListener, Runnable {

	private Board board;
	private boolean running, moved, winner = false;
	private AmazonUI GUI;
	private Queen selectedQueen;
	private Point target;
	@Setter
	private boolean currentTurn;
	private Vector<ArtificialIntelligence> AIs;

	private int[][] Directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

	public AmazonLogic(InfoPanel panel, ArtificialIntelligence... selectedAIs) {
		AIs = new Vector<ArtificialIntelligence>();
		for (ArtificialIntelligence AI : selectedAIs)
			AIs.add(AI);
		constructBoard();

		running = true;
		moved = true;
		GUI = new AmazonUI(board, panel, this);
	}

	public AmazonLogic(ArtificialIntelligence... selectedAIs) {
		AIs = new Vector<ArtificialIntelligence>();
		for (ArtificialIntelligence AI : selectedAIs)
			AIs.add(AI);
		constructBoard();

		running = true;
		moved = true;
	}

	public void constructBoard() {
		board = new Board();
		board.addQueen(new Queen(new Point(0, 3), false));
		board.addQueen(new Queen(new Point(3, 0), false));
		board.addQueen(new Queen(new Point(6, 0), false));
		board.addQueen(new Queen(new Point(9, 3), false));

		board.addQueen(new Queen(new Point(0, 6), true));
		board.addQueen(new Queen(new Point(3, 9), true));
		board.addQueen(new Queen(new Point(6, 9), true));
		board.addQueen(new Queen(new Point(9, 6), true));

		currentTurn = true;
	}

	public void run() {
		while (running) {
			if (moved) {
				moved = false;
				for (ArtificialIntelligence AI : AIs) {
					if (AI.isColor() == currentTurn) {
						Move move = AI.getMove(board.clone());
						if (move != null) {
							endTurn(move);
						}
					}
				}
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for (ArtificialIntelligence AI : AIs) {
			AI.calculateAverage();
		}

		exportData(AutomatedRun.FILE_NAME);
	}

	public void exportMove(String name) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name + ".txt", true)));

			if (board.getArrows().size() == 0) {
				writer.write("");
			}

		} catch (IOException e) {
		}
	}

	public void exportData(String name) {
		System.out.println("Writing file");

		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name + ".txt", true)));

			if (AutomatedRun.GAME_NUMBER == 0) {

				writer.write("White");

				for (ArtificialIntelligence AI : AIs) {
					if (AI.isColor() == true) {
						writer.write(": " + AI.getName());
					}
				}

				writer.write("\tBlack");

				for (ArtificialIntelligence AI : AIs) {
					if (AI.isColor() == false) {
						writer.write(": " + AI.getName());
					}
				}

				writer.write("\tdepth: " + mcts2.DEPTH);
				writer.write("\titerations: " + mcts2.ITERATIONS);
				writer.newLine();

				writer.write("Num\tWin\tMin_time_W\tAvg_time_W\tMax_time_W\tMin_time_B\tAvg_time_B\tMax_time_B\tMoves_W\tMoves_B\tMin_nodes_W\tAvg_nodes_W\tMax_nodes_W\tMin_nodes_B\tAvg_nodes_B\tMax_nodes_B");
			}
			writer.newLine();

			writer.write(AutomatedRun.GAME_NUMBER + 1 + "\t");
			writer.write(winner ? "W\t" : "B\t");

			for (ArtificialIntelligence AI : AIs) {
				if (AI.isColor() == true) {
					writer.write(AI.getMIN_TIME() + "\t" + AI.getAVG_TIME() + "\t" + AI.getMAX_TIME() + "\t");
				}
			}

			for (ArtificialIntelligence AI : AIs) {
				if (AI.isColor() == false) {
					writer.write(AI.getMIN_TIME() + "\t" + AI.getAVG_TIME() + "\t" + AI.getMAX_TIME() + "\t");
				}
			}

			for (ArtificialIntelligence AI : AIs) {
				if (AI.isColor() == true) {
					writer.write(AI.getMOVES() + "\t");
				}
			}

			for (ArtificialIntelligence AI : AIs) {
				if (AI.isColor() == false) {
					writer.write(AI.getMOVES() + "\t");
				}
			}

			for (ArtificialIntelligence AI : AIs) {
				if (AI.isColor() == true) {
					writer.write(AI.getMIN_NODES() + "\t" + AI.getAVG_NODES() + "\t" + AI.getMAX_NODES() + "\t");
				}
			}

			for (ArtificialIntelligence AI : AIs) {
				if (AI.isColor() == false) {
					writer.write(AI.getMIN_NODES() + "\t" + AI.getAVG_NODES() + "\t" + AI.getMAX_NODES() + "\t");
				}
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		Point point = new Point((int) (Math.floor((me.getX() - (0.5 * AmazonUI.SQUARESIZE)) / AmazonUI.SQUARESIZE)), (int) (Math.floor(me.getY() / AmazonUI.SQUARESIZE)));

		if (selectedQueen == null) {
			if (board.hasQueen(point) != null && board.hasQueen(point).isColor() == currentTurn) {
				selectedQueen = board.hasQueen(point);
				target = null;
				board.setTarget(null);
				board.setHighlight(point);
				// currentTurn = !currentTurn;
			}
		} else if (selectedQueen != null && board.hasQueen(point) != null && selectedQueen.isColor() == currentTurn && board.hasQueen(point).isColor() == currentTurn
				&& selectedQueen != board.hasQueen(point)) {
			selectedQueen = board.hasQueen(point);
			target = null;
			board.setTarget(null);
			board.setHighlight(point);
		} else if (selectedQueen != null && target != null && target.equals(point) && selectedQueen.isColor() == currentTurn) {
			target = null;
			board.setTarget(null);
			// selectedQueen = board.hasQueen(point);
			// target = null;
			// board.setTarget(null);
			// board.setHighlight(point);
		} else if (selectedQueen != null && target != null && point.equals(selectedQueen.getPosition())) {
			Move move = new Move(selectedQueen, target, point);
			endTurn(move);
			selectedQueen = null;
			target = null;
			board.setHighlight(null);
			board.setTarget(null);
			// if (move.validate(board)) {
			// board.move(selectedQueen, target, point);
			// selectedQueen = null;
			// target = null;
			// board.setHighlight(null);
			// board.setTarget(null);
			// if (checkMoves()) {
			// System.out.println("Game over!");
			// }
			// }
		} else {
			if (board.isEmpty(point)) {
				if (target != null) {
					Move move = new Move(selectedQueen, target, point);
					// System.out.println("Q: " + selectedQueen.getPosition().x
					// + "," + selectedQueen.getPosition().y + " to: " +
					// target.x + "," + target.y + " shoot: " + point.x + "," +
					// point.y);
					endTurn(move);
					selectedQueen = null;
					target = null;
					board.setHighlight(null);
					board.setTarget(null);
					// if (move.validate(board)) {
					// board.move(selectedQueen, target, point);
					// selectedQueen = null;
					// target = null;
					// board.setHighlight(null);
					// board.setTarget(null);
					// if (checkMoves()) {
					// System.out.println("Game over!");
					// }
					// }
				} else {
					target = point;
					board.setTarget(point);
				}
			}
		}
		GUI.repaint();

	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void endTurn(Move move) {
		if (move.validate(board)) {
			board.move(move);
			currentTurn = !currentTurn;
			// board.printBoard();
			moved = true;
		}
		if (GUI != null) {
			GUI.validate();
			GUI.repaint();
		}

		// System.out.println("Board repainted");
		if (checkMoves()) {
			running = false;
			System.out.println("Game over!");
		}
	}

	public boolean checkMoves() {
		boolean whiteDead = true, blackDead = true;

		for (Queen queen : board.getQueens()) {
			for (int i = 0; i < 8; i++) {

				int x = queen.getPosition().x + Directions[i][0];
				int y = queen.getPosition().y + Directions[i][1];

				if (x < 0 || y < 0 || x >= 10 || y >= 10)
					continue;
				if (board.getField()[x][y] == GameObject.Empty)
					if (queen.isColor())
						whiteDead = false;
					else
						blackDead = false;
			}
		}

		if (whiteDead || blackDead) {
			if (currentTurn && blackDead) {
				System.out.println("White won!");
				winner = true;
				// Black lost
			} else if (currentTurn && whiteDead) {
				System.out.println("Black won!");
				// White lost
			} else if (!currentTurn && whiteDead) {
				System.out.println("Black won!");
				// White lost
			} else if (!currentTurn && blackDead) {
				System.out.println("White won!");
				winner = true;
				// Black lost
			}
			return true;
		}
		return false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
