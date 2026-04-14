package com.iggacorp.logic.GUI;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.springframework.boot.SpringApplication;

import com.iggacorp.logic.Main;
import com.iggacorp.logic.web.Site;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
public class GUI {
	private static final Path PATH = Main.PATH;
	public static final String IGGABOT_MESSAGE = "Iggabot                                    Page ";
	private static final String START_MESSAGE = "";
	//Main EDT
	public static void main(String[] args) throws Exception {
		//SpringApplication.run(Site.class, args);//Figure out how to make it so it doesnt print to System.out
		new GUI();
		new Main();
		loadLogs();
		while(Main.IGGABOT_CHANNEL==null) {}//do nothing, just waits till the channel var initializes otherwise it causes huge issues
		clearConsole();
		frame.setVisible(true);
		System.out.println(START_MESSAGE);
	}
	//Makes frame
	public static JFrame frame;
	public GUI() throws Exception {
		frame = new JFrame();
		frame.add(new Panel());
		frame.setTitle(IGGABOT_MESSAGE + (Panel.pageNum+1));
		frame.setIconImage(ImageIO.read(new File(PATH + "/Images/Teto.jpg")));
		frame.setSize(847,602);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
	//Loads the ouput logs
	static BufferedWriter outLog = create(PATH + "/Logs/Output/Log.txt");
	static BufferedWriter errLog = create(PATH + "/Logs/Output/Err.txt");;
	public static OutputStream out = new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			JTextArea txt = Panel.txt;
			String i = String.valueOf((char)b);
			txt.append(i);
			txt.setCaretPosition(txt.getDocument().getLength());
			outLog.write(i);
			outLog.flush();
		}
	};
	static OutputStream err = new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			JTextArea txt = Panel.txt;
			String i = String.valueOf((char)b);
			txt.append(i);
			txt.setCaretPosition(txt.getDocument().getLength());
			errLog.write(i);
			errLog.flush();
		}
	};
	private static void loadLogs() {
		try(FileWriter w = new FileWriter(Main.PATH + "/Logs/Output/ServerLogs.txt")) {
			ArrayList<List<Message>> guh = new ArrayList<>();
			for(TextChannel i : Main.bot.getTextChannels()) {
				if(!(i.getId().equals("1213318763651268649")||i.getId().equals("1222991601857597571")||i.getId().equals("1213320041676935168")||i.getId().equals("1258796845061378131")||i.getId().equals("1483303021243928656")||i.getId().equals("1242571289173168258")||i.getId().equals("1215154094289854464")||i.getId().equals("1211478753549746246")||i.getId().equals("1422661790147547156")||i.getId().equals("1213322706284581005")||i.getId().equals("1211473025514475618")||i.getId().equals("1474627951688679639")||i.getId().equals("1214061647258320946")||i.getId().equals("1393513733715923124")||i.getId().equals("1427841583503376384")||i.getId().equals("1213671602252947466")||i.getId().equals("1363296825280430182")||i.getId().equals("1393507211925393418")||i.getId().equals("1393508040837435502")||i.getId().equals("1222703528699760782")||i.getId().equals("1213319202908405800")||i.getId().equals("1220930784475742359")||i.getId().equals("1293692890756943924")||i.getId().equals("1421596963907829913")||i.getId().equals("1259173841784143902")||i.getId().equals("1327781164211114086")||i.getId().equals("1215813550094946374")))
					guh.add(i.getIterableHistory().submit().get());
			}
			String str = "";
			for(List<Message> b : guh) {
				for(Message i : b) {
					str += ("(" + i.getAuthor().getId() + ")" + i.getAuthor().getName() + ": " + i.getContentRaw() + "\n");
				}
			}
			w.write(str);
		} catch (Exception e) {e.printStackTrace();}
		System.setOut(new PrintStream(out));
		System.setErr(new PrintStream(err));
	}
	//Creates a bufferedwriter to get around the try/catch
	private static BufferedWriter create(String string) {
		BufferedWriter i = null;
		try {
			i = new BufferedWriter(new FileWriter(new File(string)));
		} catch(Exception e) {e.printStackTrace();}
		return i;
	}
	//Clears the console in the JTextArea
	public static void clearConsole() {
		Panel.txt.setText(null);
		frame.revalidate();
		frame.repaint();
	}
}
