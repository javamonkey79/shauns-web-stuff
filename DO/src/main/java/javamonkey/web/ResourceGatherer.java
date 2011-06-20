package javamonkey.web;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javamonkey.web.link.LinkParser;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResourceGatherer {

	private static final LinkParser LINK_PARSER = new LinkParser();

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {

		JFrame mainFrame = new JFrame("DO Collector");

		mainFrame.setLayout(new MigLayout("wrap 1"));

		final JTextArea cookieText = new JTextArea();
		cookieText.setBorder(new LineBorder(Color.BLACK));
		mainFrame.add(new JScrollPane(cookieText), "w 200px, h 100px");

		final JCheckBox collectAll = new JCheckBox("Collect All", true);
		mainFrame.add(collectAll, "wrap");

		final JCheckBox shockAll = new JCheckBox("Shock All", true);
		mainFrame.add(shockAll, "wrap");

		JButton doIt = new JButton("Do It");
		mainFrame.add(doIt, "wrap");
		doIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cookieData = parseText(cookieText.getText());

				System.out.println(cookieData);

				File cookieFile = new File("cookie.txt");
				if (StringUtils.isNotBlank(cookieData)) {
					try {
						FileUtils.writeStringToFile(cookieFile, cookieData);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						cookieData = FileUtils.readFileToString(cookieFile);
					} catch (IOException e1) {
						throw new RuntimeException(
								"Could not load cookie file and nothing in text.");
					}
				}

				System.out.println(cookieData);
				System.out.println(shockAll.isSelected());
				System.out.println(collectAll.isSelected());

				String link = "http://live.nightowlgames.net/MyLairServer/DelegateServlet?act=loadPlayerJSON";

				try {
					String linkPageSource = LINK_PARSER.getLinkPageSource(link,
							cookieData, false);

					System.out.println(linkPageSource);

					JSONObject playerData = new JSONObject(linkPageSource);

					if (StringUtils.contains(linkPageSource, "not logged in.")) {
						System.out.println("reset the cookie");
						return;
					}

					JSONArray dungeons = playerData.getJSONArray("dungeons");
					for (int i = 0; i < dungeons.length(); i++) {
						JSONObject dungeonInfo = dungeons.getJSONObject(i);

						String dungeonId = dungeonInfo.getString("id");
						link = "http://live.nightowlgames.net/MyLairServer/DelegateServlet?act=loadDungeonJSON&id="
								+ dungeonId;

						linkPageSource = LINK_PARSER.getLinkPageSource(link,
								cookieData, false);
						JSONObject dungeon = new JSONObject(linkPageSource);

						if (shockAll.isSelected()) {
							shockAllWarlocks(cookieData, dungeonId, dungeon);
						}

						if (collectAll.isSelected()) {
							pickupResources(cookieData, dungeonId, dungeon);
						}
					}
				} catch (Throwable throwable) {
					throw new RuntimeException(throwable);
				}
				System.out
						.println("=================================================\nactions complete\n=================================================");
			}

		});

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(200, 200, 200, 300);
		mainFrame.setVisible(true);
	}

	private static String parseText(String text) {
		if (StringUtils.isNotBlank(text)) {
			Pattern pattern = Pattern.compile("^\\s*Cookie\\[(.*)\\]$",
					Pattern.MULTILINE);
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				return matcher.group(1);
			}
		}

		return text;
	}

	private static void shockAllWarlocks(String cookieData, String dungeonId,
			JSONObject dungeon) throws JSONException, Throwable,
			InterruptedException {
		JSONArray creatures = dungeon.getJSONArray("dungeonMobs");
		for (int j = 0; j < creatures.length(); j++) {
			JSONObject creature = creatures.getJSONObject(j);
			int happiness = creature.getInt("happiness");
			int id = creature.getInt("id");
			String type = creature.getString("type");

			if (StringUtils.equalsIgnoreCase(type, "Warlock")) {
				while (happiness > 10) {
					
					System.out.println(String.format("Shocking Warlock: %s, current happiness: %s", id, happiness));
					
					String shockWarlockUrl = String
							.format("http://live.nightowlgames.net/MyLairServer/DelegateServlet?act=forceWorkJSON&dungeonId=%s&id=%s",
									dungeonId, id);

					System.out.println(shockWarlockUrl);

					System.out.println(LINK_PARSER.getLinkPageSource(
							shockWarlockUrl, cookieData, false));

					happiness -= 10;
					
					Thread.sleep(500);
				}
			}
		}
	}

	private static void pickupResources(String cookieData, String dungeonId,
			JSONObject dungeon) throws JSONException, Throwable,
			InterruptedException {

		JSONArray resources = dungeon.getJSONArray("resources");
		for (int j = 0; j < resources.length(); j++) {
			JSONObject resource = resources.getJSONObject(j);
			String type = resource.getString("type");

			if (StringUtils.equals("InfluenceResource", type)) {
				continue;
			}

			int quantity = (int) resource.getDouble("quantity");

			System.out.println(String.format(
					"dungeon: %s, quantity: %s, type: %s", dungeonId, quantity,
					type));

			System.out
					.println(LINK_PARSER.getLinkPageSource(
							String.format(
									"http://live.nightowlgames.net/MyLairServer/DelegateServlet?act=pickupResourcesJSON&dungeonId=%s&rsrc_type=%s&amount=%s",
									dungeonId, type, quantity), cookieData,
							false));

			Thread.sleep(500);
		}
	}

}
