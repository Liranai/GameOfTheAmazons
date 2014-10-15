package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Board;

public class AmazonUI extends JFrame {

	private static final long serialVersionUID = -2399200433217264450L;
	private JPanel boardPanel;
	public static int SQUARESIZE = 55;

	public static BufferedImage Foot_steps;
	public static BufferedImage Queen_White;
	public static BufferedImage Queen_Black;
	public static BufferedImage Arrow;

	public AmazonUI(Board board, InfoPanel panel, MouseListener listener) {
		super("MainFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension((int) (10.5 * 30 + 1) + 16, (int) (10.5 * 30 + 1) + 41));
		setResizable(true);

		addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent evt) {
				Component c = (Component) evt.getSource();

				int width = c.getSize().width - 16;
				int height = c.getSize().height - 41;

				int squaresize = (int) Math.min(width / 10.5, height / 10.5);
				AmazonUI.SQUARESIZE = squaresize;
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});

		// TODO: Correct screensize
		// setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		// setUndecorated(true);

		try {
			// TODO: load transparent files
			Foot_steps = ImageIO.read(new File("footsteps3.gif"));
			Queen_White = ImageIO.read(new File("white_queen.gif"));
			Queen_Black = ImageIO.read(new File("black_queen.gif"));
			Arrow = ImageIO.read(new File("arrowCCW.gif"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		setLayout(new BorderLayout());
		boardPanel = new AmazonBoardPanel(board);
		boardPanel.addMouseListener(listener);
		add(boardPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.EAST);

		// addMenuBar(this);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
