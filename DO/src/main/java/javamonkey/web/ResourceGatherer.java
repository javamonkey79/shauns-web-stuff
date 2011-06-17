package javamonkey.web;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javamonkey.web.link.LinkParser;

public class ResourceGatherer {
	
	private static final LinkParser LINK_PARSER = new LinkParser();

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		String link = "http://live.nightowlgames.net/MyLairServer/DelegateServlet?act=loadPlayerJSON";
		String cookieData =
			"JSESSIONID=B6F346B1CDC376D0C888AC70248DCF15; DUNGEON_OVERLORD_live=fmpn7g3f84nfv03n9cvj0c9ds7; fbs_110362692359888=\"access_token=110362692359888%7C2.AQCRqtl-1BZ971h6.3600.1308297600.1-507492112%7CY5NexJGm0XHg-D-t5nUQ_NO0Pfg&base_domain=nightowlgames.net&expires=1308297600&secret=IN_BYfjz9_uKlSgXXLF_gQ__&session_key=2.AQCRqtl-1BZ971h6.3600.1308297600.1-507492112&sig=a26d7e23bd38bd55c9e2d259f3c79be6&uid=507492112\"";
		
		String linkPageSource = LINK_PARSER.getLinkPageSource(link, cookieData, true);
		JSONObject playerData = new JSONObject(linkPageSource);
		
		JSONArray dungeons = playerData.getJSONArray("dungeons");
		for (int i = 0; i < dungeons.length(); i++) {
			JSONObject dungeonInfo = dungeons.getJSONObject(i);
			
			String dungeonId = dungeonInfo.getString("id");
			link = "http://live.nightowlgames.net/MyLairServer/DelegateServlet?act=loadDungeonJSON&id=" + dungeonId;
			
			linkPageSource = LINK_PARSER.getLinkPageSource(link, cookieData, false);
			JSONObject dungeon = new JSONObject(linkPageSource);
			JSONArray resources = dungeon.getJSONArray("resources");

			for (int j = 0; j < resources.length(); j++) {
				JSONObject resource = resources.getJSONObject(j);
				String type = resource.getString("type");
				
				if(StringUtils.equals("InfluenceResource", type)){
					continue;
				}
				
				int quantity = (int)resource.getDouble("quantity");
				
				System.out.println(String.format("dungeon: %s, quantity: %s, type: %s", dungeonId, quantity, type));
				
				link = String.format( "http://live.nightowlgames.net/MyLairServer/DelegateServlet?act=pickupResourcesJSON&dungeonId=%s&rsrc_type=%s&amount=%s",dungeonId, type, quantity);
				linkPageSource = LINK_PARSER.getLinkPageSource(link, cookieData, false);
				
				System.out.println(linkPageSource);
				Thread.sleep(1000);
			}
		}
	}

}
