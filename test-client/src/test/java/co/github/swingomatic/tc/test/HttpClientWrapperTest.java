package co.github.swingomatic.tc.test;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.tc.http.HttpClientWrapper;
import com.thoughtworks.xstream.XStream;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


/**
 * The Class HttpClientWrapperTest.
 */
public class HttpClientWrapperTest {

	/** The Constant LIST_COMPONENTS_URL. */
	private final static String LIST_COMPONENTS_URL = "http://localhost:8088/list-components";

	/** The http client. */
	private HttpClientWrapper httpClient;
	
	/**
	 * Test post.
	 */
	@Test
	public void testPost() throws Exception{
		httpClient = new HttpClientWrapper();		
		String response = httpClient.post(LIST_COMPONENTS_URL, null);
                XStream xstream = new XStream();
                ApplicationCommand ac = (ApplicationCommand)xstream.fromXML(response);
                assertNotNull(ac);
		assertNotNull(getValue("/list",response));
	}
	
	
	
	
	//helper methods
	
	/**
	 * Gets the scale value from the supplied xml.
	 *
	 * @param xpathExpression the xpath expression
	 * @param xml the xml
	 * @return the value
	 * @throws Exception the exception
	 */
	private String getValue(String xpathExpression, String xml) throws Exception {

		String value = null;
		
		// Standard of reading a XML file
		Document xmlDocument = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder()
				.parse(new InputSource(new StringReader(xml)));
		XPathExpression expr = null;
		// Create a XPathFactory
		XPathFactory xFactory = XPathFactory.newInstance();
		// Create a XPath object
		XPath xpath = xFactory.newXPath();
		// Compile the XPath expression
		expr = xpath.compile(xpathExpression);
		// Run the query and get a nodeset
		value = expr.evaluate(xmlDocument);

		return value;

	}
	
}
