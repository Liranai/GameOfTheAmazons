package ai.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ai.ArtificialIntelligence;

public class AIUI extends JFrame {

	private static final long serialVersionUID = -8753141173259243122L;
	private JPanel nodePanel;
	public static int NODESIZE = 55;

	public AIUI(ArtificialIntelligence ai, MouseListener listener) {
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
				AIUI.NODESIZE = squaresize;
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
	}

}
