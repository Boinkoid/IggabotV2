package com.iggacorp.logic.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	public static JTextArea txt;
	private static final String[][] buttonNames = {{"test","Not implemented yet","Not implemented yet"},{"Not implemented yet","Not implemented yet","Not implemented yet"},{"Not implemented yet","Not implemented yet","Not implemented yet"}};
	public Panel() {
		super();
		JPanel panel1 = new JPanel();
		setLayout(new BorderLayout());
		txt = new JTextArea();
		txt.setEditable(false);
		JScrollPane p = new JScrollPane(txt);
		p.setPreferredSize(new Dimension(400,600));
		panel1.add(p);
		panel1.setMinimumSize(new Dimension(400,600));
		panel1.setBackground(Color.green);
		add(panel1,BorderLayout.EAST);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(3,3));
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				JButton b = new JButton(buttonNames[i][j]);
				b.addActionListener(e->{
					switch(e.getActionCommand()) {
					case "" ->{

					}
					case "Not implemented yet" ->{
						System.out.println("You're stupid if you clicked this");
					}
					/*case "" ->{

					}
					case "" ->{

					}
					case "" ->{

					}
					case "" ->{

					}
					case "" ->{

					}
					case "" ->{

					}
					case "" ->{

					}*/
					}
				});
				panel2.add(b);
			}
		}
		add(panel2);
	}
}
