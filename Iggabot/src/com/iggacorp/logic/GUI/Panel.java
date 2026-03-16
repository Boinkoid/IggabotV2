package com.iggacorp.logic.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.iggacorp.logic.Interactions;
import com.iggacorp.logic.Main;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;


@SuppressWarnings("serial")
public class Panel extends JPanel {

	public static JTextArea txt;
	private static final String[][] buttonNames = {{"Add Cmd","Chat","Not implemented yet"},{"Not implemented yet","Not implemented yet","Not implemented yet"},{"Not implemented yet","Not implemented yet","Not implemented yet"}};
	public Panel() {
		super();
		JPanel panel1 = new JPanel();
		setLayout(new BorderLayout());
		txt = new JTextArea();
		txt.setEditable(false);
		JScrollPane p = new JScrollPane(txt);
		p.setPreferredSize(new Dimension(400,550));
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
					case "Add Cmd" ->{
						createPopup();
					}
					case "Chat" ->{
						Interactions.CHAT_ENABLED=!Interactions.CHAT_ENABLED;
						if(Interactions.CHAT_ENABLED) {
							Main.bot.getChannelById(TextChannel.class,Main.IGGABOT_CHANNEL).sendMessage("Iggabot chatting enabled!").queue();
						} else {
							Main.bot.getChannelById(TextChannel.class,Main.IGGABOT_CHANNEL).sendMessage("Iggabot chatting disabled!").queue();
						}
					}
					case "Not implemented yet" ->{
						System.out.println("You're stupid if you clicked this");
					}
					}
				});
				panel2.add(b);
			}
		}
		add(panel2);
	}
	public static PopupInput createPopup() {

		PopupInput popup = new PopupInput();

		JFrame frame = new JFrame("Input");
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		popup.mainText = new JTextArea();
		JTextArea mainText = popup.mainText; 
		mainText.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "submit");
		mainText.getInputMap().put(KeyStroke.getKeyStroke("shift ENTER"), "insert-break");

		mainText.getActionMap().put("submit", new AbstractAction() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				frame.dispose();
				System.out.println("case \"" + popup.getTxt().split(" : ")[0] + "\" ->{\r\n"
						+ "\\\\Logic here\r\n"
						+ "}");
				System.out.println("Commands.slash(\"" + popup.getTxt().split(" : ")[0] + "\", \"" + popup.getTxt().split(" : ")[1] + "\", true)");
				popup.getOptions().forEach(a->{
					String str = ".addOption(";
					for(int i = 0; i<3; i++) {
						if(i==0) {
							str += "OptionType." + a[i] + ", ";
						} else if(i==1) {
							str += "\"" + a[i] + "\"" + ", ";
						} else {
							str += "\"" + a[i] + "\"" + ")";
						}
					}
					System.out.println(str);
				});
				
			}
		});
		frame.add(new JScrollPane(popup.mainText), BorderLayout.NORTH);

		popup.optionsPanel = new JPanel();
		popup.optionsPanel.setLayout(new BoxLayout(popup.optionsPanel, BoxLayout.Y_AXIS));

		JScrollPane scroll = new JScrollPane(popup.optionsPanel);
		frame.add(scroll, BorderLayout.CENTER);

		JButton addButton = new JButton("Add Option");
		frame.add(addButton, BorderLayout.SOUTH);

		addButton.addActionListener(e -> {

			JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JComboBox<String> dropdown =
					new JComboBox<>(new String[]{"USER","STRING","INT", "BOOLEAN", "ATTATCHMENT"});

			JTextField field1 = new JTextField(8);
			JTextField field2 = new JTextField(8);

			JButton remove = new JButton("Remove");

			row.add(dropdown);
			row.add(field1);
			row.add(field2);
			row.add(remove);

			popup.optionRows.add(new OptionRow(dropdown, field1, field2));

			remove.addActionListener(ev -> {
				popup.optionsPanel.remove(row);
				popup.optionsPanel.revalidate();
				popup.optionsPanel.repaint();
				popup.optionRows.remove(popup.optionRows.size());
			});

			popup.optionsPanel.add(row);
			popup.optionsPanel.revalidate();
		});

		frame.setVisible(true);

		return popup;
	}

	public static class PopupInput {

		JTextArea mainText;
		JPanel optionsPanel;
		List<OptionRow> optionRows = new ArrayList<>();

		public String getTxt() {
			return mainText.getText();
		}

		public List<String[]> getOptions() {
			List<String[]> list = new ArrayList<>();
			for (OptionRow r : optionRows) {
				list.add(new String[]{
						(String) r.dropdown.getSelectedItem(),
						r.field1.getText(),
						r.field2.getText()
				});
			}
			return list;
		}
	}

	static class OptionRow {

		JComboBox<String> dropdown;
		JTextField field1;
		JTextField field2;

		OptionRow(JComboBox<String> d, JTextField f1, JTextField f2) {
			dropdown = d;
			field1 = f1;
			field2 = f2;
		}
	}
}
