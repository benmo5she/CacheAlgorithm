package com.hit.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CacheUnitView extends Observable implements View {

	@Override
	public void start() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

	private void createAndShowGUI() {

		JFrame frame = new JFrame("HelloWorldSwing");
		GridLayout myLayout = new GridLayout(0, 2);
		JPanel pane = new JPanel();
		frame.setContentPane(pane);
		pane.setLayout(myLayout);
		JButton button;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		button = new JButton("Load a Request");
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 30;
		c.ipadx = 10;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(button, c);

		button = new JButton("Show Statistics");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		pane.add(button, c);

		JTextArea content = new JTextArea();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 500;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(content, c);

		frame.pack();
		frame.setVisible(true);

	}

}
