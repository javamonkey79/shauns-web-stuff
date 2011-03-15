package com.test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class AccelaSimulator {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		JFrame mainFrame = new JFrame( "Accela Simulator" );
		mainFrame.setLayout( new MigLayout() );

		final JTextArea inputTextArea = new JTextArea();
		inputTextArea.setBorder( new LineBorder( Color.black ) );
		mainFrame.add( new JScrollPane( inputTextArea ), "w 350, h 325, wrap" );

		final JTextArea outputTextArea = new JTextArea();
		outputTextArea.setBorder( new LineBorder( Color.black ) );
		mainFrame.add( new JScrollPane( outputTextArea ), "w 350, h 325, wrap" );
		outputTextArea.setEditable( false );

		JButton clearButton = new JButton( "Clear Output" );
		clearButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent actionEvent ) {
				outputTextArea.setText( "" );
			}
		} );

		mainFrame.add( clearButton, "split 2, ax right, w 50, h 25" );

		JButton runButton = new JButton( "Run" );
		runButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent actionEvent ) {
				outputTextArea.setText( runScript( inputTextArea.getText() ) );
			}
		} );

		mainFrame.add( runButton, "ax right, w 50, h 25" );

		mainFrame.setBounds( 200, 200, 375, 800 );
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		mainFrame.setVisible( true );
	}

	private static String runScript( String scriptText ) {
		try {
			Context cx = Context.enter();
			Scriptable scope = cx.initStandardObjects( null );
			Object scriptResult = cx.evaluateString( scope, scriptText, "script", 1, null );
			return String.valueOf( scriptResult );
		}
		catch ( Exception exception ) {
			return exception.getMessage();
		}
	}
}
