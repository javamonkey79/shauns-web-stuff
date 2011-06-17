package javamonkey.web;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class SurveyXmlQuery {

	/**
	 * @param args
	 * @throws Exception
	 * @throws DocumentException
	 */
	@SuppressWarnings("all")
	public static void main( String[] args ) throws DocumentException, Exception {
//		Document document = DocumentHelper.parseText( FileUtils.readFileToString( new File( "./region-scaner-surveys-bull-marsh.xml" ) ) );
		Document document = DocumentHelper.parseText( FileUtils.readFileToString( new File( "./region-scaner-surveys.xml" ) ) );

//		String mineType = "FeldsparMine";
		String mineType = "ShallowMantleMine";
//		String mineType = "BasaltMine";
//		String mineType = "PrimordialWaterMine";
//		String mineType = "MithrilMine";
		List< Node > nodes = document.selectNodes( "//e[text()='" +
				mineType +
				"']/../../../.." );
		for( Node node : nodes ) {
			Document innerDocument = DocumentHelper.parseText(node.asXML());
			
			Node selectSingleNode = innerDocument.selectSingleNode("//e[text()='" +
				mineType +
				"']/../../position/text()");
			if(selectSingleNode != null){
			System.out.print(
			selectSingleNode.asXML() + " :: ");
			
			Node positionNode = innerDocument.selectSingleNode( "//position" );
			
			String positionText = positionNode.getText();
			MountainPositionCalculator.main(new String[]{positionText});
			
			List<Node> validDungeons = innerDocument.selectNodes("//e[text()='" +
					mineType +
			"']/..");
			for (Node dungeon : validDungeons) {
				System.out.println(dungeon.asXML());
			}}
		}
	}

}
