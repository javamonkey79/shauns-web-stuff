package javamonkey.web;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javamonkey.web.link.LinkParser;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
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
		mainFrame.add(cookieText, "w 150px");

		final JCheckBox collectAll = new JCheckBox("Collect All", true);
		mainFrame.add(collectAll, "wrap");

		final JCheckBox shockAll = new JCheckBox("Shock All", true);
		mainFrame.add(shockAll, "wrap");

		JButton doIt = new JButton("Do It");
		mainFrame.add(doIt, "wrap");
		doIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cookieData = cookieText.getText();

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

					// String cookieData = args.length == 0 ||
					// StringUtils.isBlank(args[0])
					// ?
					// "JSESSIONID=115D9DDD70B4099A078814C5B5A2BD17; DUNGEON_OVERLORD_live=hc175qvn8b5vvincevil6st015; fbs_110362692359888=\"access_token=110362692359888%7C2.AQCj3VWHGA6Vac_h.3600.1308337200.1-507492112%7Cy3JFNqqzMaM9gPeMbizMajPYwB4&base_domain=nightowlgames.net&expires=1308337200&secret=N740koc7mEZivuudAJHgkA__&session_key=2.AQCj3VWHGA6Vac_h.3600.1308337200.1-507492112&sig=2a0c1d8beeb4f40cb824e44cc25dcbd4&uid=507492112\""
					// : args[0];

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
			}
		});

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(200, 200, 100, 200);
		mainFrame.setVisible(true);
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

			if (StringUtils.equalsIgnoreCase(type, "Warlock") && happiness > 25) {
				System.out
						.println(LINK_PARSER.getLinkPageSource(
								String.format(
										"http://live.nightowlgames.net/MyLairServer/DelegateServlet?act=forceWorkJSON&dungeonId=%s&id=%s",
										dungeonId, id), cookieData, false));

				Thread.sleep(1000);
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

			Thread.sleep(1000);
		}
	}

}
