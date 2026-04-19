package com.iggacorp.logic.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.iggacorp.logic.Interactions;
import com.iggacorp.logic.Main;

import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;


@SuppressWarnings("serial")
public class Panel extends JPanel {

	public static JTextArea txt;
	public static JPanel buttonPanel;
	public static ArrayList<JButton[][]> BUTTON_LIST = new ArrayList<>();
	//Button Pages
	private static final String[][] BUTTON_PAGE_1 = {
			{"Clear Console","Chat","Delete channel"},
			{"Reset AI","Make All Text","Make Igg Files"},
			{"Previous page","Ideas","Next page"}};
	private static final String[][] BUTTON_PAGE_2 = {
			{"Get Text Channels","New Game","Activities"},
			{"Not implemented yet","Not implemented yet","Not implemented yet"},
			{"Previous page","Not implemented yet","Next page"}};
	private static final String[][] BUTTON_PAGE_3 = {
			{"Reset Users","Bypass","Not implemented yet"},
			{"Not implemented yet","Not implemented yet","Not implemented yet"},
			{"Previous page","Not implemented yet","Next page"}};
	/* BUTTON_PAGE TEMPLATE
	  private static final String[][] BUTTON_PAGE_1234123421342134213412342341241234 = {
			{"Not implemented yet","Not implemented yet","Not implemented yet"},
			{"Not implemented yet","Not implemented yet","Not implemented yet"},
			{"Previous page","Not implemented yet","Next page"}};	  
	*/
	//Button Pages array
	private static final String[][][] BUTTON_PAGES = {BUTTON_PAGE_1,BUTTON_PAGE_2,BUTTON_PAGE_3};
	public Panel() {
		super();
		makeButtons();
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
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,3));
		makeButtonPanel(BUTTON_LIST.get(pageNum));
		add(buttonPanel);
	}
	private void makeButtonPanel(JButton[][] i) {
		buttonPanel.removeAll();
		for(JButton[] a : i) {
			for(JButton b : a) {
				buttonPanel.add(b);
			}
		}
		GUI.frame.revalidate();
		GUI.frame.repaint();
	}
	//Button logic for the panel
	public static int pageNum = 0;
	private void makeButtons() {
		for(String[][] page : BUTTON_PAGES) {
			JButton[][] temp = new JButton[3][3];
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					temp[i][j] = new JButton(page[i][j]);
				}
			}
			BUTTON_LIST.add(temp);
		}
		for(JButton[][] a : BUTTON_LIST) {
			for(JButton[] b : a) {
				for(JButton c : b) {
					c.addActionListener(e->{
						switch(e.getActionCommand()) {
						case "Clear Console" -> GUI.clearConsole();
						case "Chat" ->{
							Interactions.CHAT_ENABLED=!Interactions.CHAT_ENABLED;
							if(Interactions.CHAT_ENABLED) {
								Main.bot.getChannelById(TextChannel.class,Main.IGGABOT_CHANNEL).sendMessage("Iggabot chatting enabled!").queue();
								System.out.println("Iggabot chatting enabled!");
							} else {
								Main.bot.getChannelById(TextChannel.class,Main.IGGABOT_CHANNEL).sendMessage("Iggabot chatting disabled!").queue();
								System.out.println("Iggabot chatting disabled!");
							}
						}
						case "Delete channel" ->{
							Category cat = Main.getIggabotChannelCategory();
							String str = "";
							str = Main.bot.getChannelById(TextChannel.class, Main.IGGABOT_CHANNEL).getName();
							Main.bot.getChannelById(TextChannel.class, Main.IGGABOT_CHANNEL).delete().complete();
							cat.createTextChannel(str).complete();
							Main.IGGABOT_CHANNEL = Main.getIggabotChannel();
						}
						case "Reset AI" -> Main.resetAI();
						case "Make All Text" -> { 
							try  {
								Main.getAllChats();
							} catch (Exception e1){
								e1.printStackTrace();
							}
						}
						case "Make Igg Files" ->{ 
							try  {
								Main.makeIggFiles();
							} catch (Exception e1){
								e1.printStackTrace();
							}
						}
						case "Ideas" ->{
							String str = "";
							try {
								for(String d : Files.readAllLines(Path.of(Main.PATH + "/Logs/Output/Ideas.txt"))){
									str += d + "\n";
								}
							} catch (Exception e1) {e1.printStackTrace();}
							System.out.println(str);
						}
						//Page 2
						case "Get Text Channels" -> Main.getTextChannels();
						case "Activities" -> toggle();//toggles the activities I/O
						case "New Game" -> {}//makes a new gambling game
						//Page 3
						case "Reset Users" -> {
							if(confirmation()) {
								//Resets the user files
							}
						}
						case "Bypass" -> {} //Bypasses the @everyone message
						//Default
						case "Next page" -> {
							int guh = ++pageNum;
							if(guh>=BUTTON_LIST.size()) {
								guh = 0;
							}
							pageNum=guh;
							makeButtonPanel(BUTTON_LIST.get(guh));
							GUI.frame.setTitle(GUI.IGGABOT_MESSAGE  + (pageNum+1));
						}
						case "Previous page" -> {
							int guh = --pageNum;
							if(guh<0) {
								guh = BUTTON_LIST.size()-1;
							}
							pageNum=guh;
							makeButtonPanel(BUTTON_LIST.get(guh));
							GUI.frame.setTitle(GUI.IGGABOT_MESSAGE + (pageNum+1));
						}
						case "Not implemented yet" ->{
							System.out.println("You're stupid if you clicked this");
						}
						default -> System.out.println("Incorrect button");
						}
					});
				}
			}
		}
	}
	//Sends a post that disabled the site
	private void toggle() {
		
	}
	private boolean confirmation() {
		int t = 0;
		int response = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (response == JOptionPane.YES_OPTION) {
			int response2 = JOptionPane.showConfirmDialog(null, "Are you sure??????", "Confirmation", JOptionPane.YES_NO_OPTION);
			if(response2 == JOptionPane.YES_OPTION) {
				t = 7;
			} else {
			    t = 99;
			}
		} else {
		    t = 99;
		}
		return t==7;
	}
}