package javamonkey.web;

import javamonkey.web.link.LinkFileHelper;
import junit.framework.Assert;

import org.junit.Test;

public class LinkFileHelperTest {

	@Test
	public void testGetTvShowLinks() throws Throwable {
		Assert.assertEquals( 2, new LinkFileHelper().getTvShowLinks().size() );
	}
}
