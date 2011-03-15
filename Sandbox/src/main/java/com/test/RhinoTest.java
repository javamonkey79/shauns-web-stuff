package com.test;

import java.io.FileNotFoundException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class RhinoTest {

	/**
	 * @param args
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	public static void main( String[] args ) throws Exception {
		//Main.main( args );

//		Context cx = ShellContextFactory.getGlobal().enterContext();
		Context cx = Context.enter();

		Scriptable scope = cx.initStandardObjects( null );
//		Scriptable scope = new ImporterTopLevel( cx );

		String scriptText = "";

//		Scriptable jsRoot = Context.toObject( root, scope );
//		scriptText += "load('C:\\temp\\jquery.js');";
//		scriptText += "var scope = new Packages.org.mozilla.javascript.ImporterTopLevel(Packages.org.mozilla.javascript.Context.enter());";
//		scriptText += "importPackage(java.util);";

//		scriptText += "var factory = new Packages.javax.script.ScriptEngineManager(); ";
//		scriptText += "var engine = new Packages.javax.script.ScriptEngineManager(); ";
//		scriptText += "var context = engine.getContext();";
//		scriptText += "context.setAttribute( \"name\", \"JavaScript\", ScriptContext.ENGINE_SCOPE );";

		scriptText += "var manager = new Packages.javax.script.ScriptEngineManager(); ";
		scriptText += "var engine = manager.getEngineByName( \"js\" ); ";

		String scriptFile = "/temp/env.rhino.1.2.js";
//		String scriptFile = "/temp/printme.js";
		scriptText += "var eval = engine.eval( new Packages.java.io.FileReader( new Packages.java.io.File( \"" + scriptFile + "\" ) ) ) ";

		//		scriptText += "engine.eval('/temp/jquery.js'); ";

		cx.evaluateString( scope, scriptText, "script", 1, null );

//		ScriptEngineManager manager = new ScriptEngineManager();
//		ScriptEngine engine = manager.getEngineByName( "js" );
//		Object eval = engine.eval( new FileReader( new File( "test.js" ) ) );

	}
}
