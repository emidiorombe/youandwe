package br.com.yaw.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

@Path("test")
public class TestService {
	
	@Path("ping")
	@GET
	@Produces("text/plain")
	public String ping() {
		return "pong";
	}
	
	@Path("catalogo")
	@GET
	@Produces("text/xml")
	public String getCatalogo() {
		String ret = "";
		try{
			Document document = DocumentHelper.parseText(getXml());
			
			ret = document.asXML();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
		
	}

	private String getXml() {
		String xml = "<catalog> " +

	    "<product productId=\"1\">" +
	        "<name>Nokia 6010</name>" +
	        "<description>Easy to use without sacrificing style, the Nokia 6010 phone offers functional voice communication supported by text messaging, multimedia messaging, mobile internet, games and more</description>" +
	        "<price>99.99</price>" +
	        "<image>Nokia_6010.gif</image>" +
	        "<series>6000</series>" +
	        "<triband>false</triband>" +
	        "<camera>false</camera>" +
	        "<video>false</video>" +
	        "<highlight1>MMS</highlight1>" +
			"<highlight2>Large color display</highlight2>" +
			"<qtyInStock>2</qtyInStock>" +
	    "</product>" +

	    "<product productId=\"2\"> " +
        "<name>Nokia 3100 Blue</name>" +
        "<description>Light up the night with a glow-in-the-dark cover - when it's charged with light you can easily find your phone in the dark. When you get a call, the Nokia 3100 phone flashes in tune with your ringing tone. And when you snap on a Nokia Xpress-onâ„¢ gaming cover*, you'll get luminescent light effects in time to the gaming action.</description>" +
        "<price>139</price>" +
        "<image>Nokia_3100_blue.gif</image>" +
        "<series>3000</series>" +
        "<triband>true</triband>" +
        "<camera>false</camera>" +
        "<video>false</video>" +
        "<highlight1>Glow-in-the-dark</highlight1>" +
		"<highlight2>Flashing lights</highlight2>" +
		"<qtyInStock>1</qtyInStock>" +
    "</product>" +

    "<product productId=\"3\"> " +
        "<name>Nokia 3100 Pink</name>" +
        "<description>Light up the night with a glow-in-the-dark cover - when it's charged with light you can easily find your phone in the dark. When you get a call, the Nokia 3100 phone flashes in tune with your ringing tone. And when you snap on a Nokia Xpress-onâ„¢ gaming cover*, you'll get luminescent light effects in time to the gaming action.</description>" +
        "<price>139</price>" +
        "<image>Nokia_3100_pink.gif</image>" +
        "<series>3000</series>" +
        "<triband>true</triband>" +
        "<camera>false</camera>" +
        "<video>false</video>" +
        "<highlight1>Glow-in-the-dark</highlight1>" +
		"<highlight2>Flashing lights</highlight2>" +
		"<qtyInStock>7</qtyInStock>" +
    "</product>" +

    "<product productId=\"4\"> " +
        "<name>Nokia 3120</name>" +
        "<description>Designed for both business and pleasure, the elegant Nokia 3120 phone offers a pleasing mix of features. Enclosed within its chic, compact body, you will discover the benefits of tri-band compatibility, a color screen, MMS, XHTML browsing, cheerful screensavers, and much more.</description>" +
        "<price>159.99</price>" +
        "<image>Nokia_3120.gif</image>" +
        "<series>3000</series>" +
        "<triband>true</triband>" +
        "<camera>false</camera>" +
        "<video>false</video>" +
        "<highlight1>Multimedia messaging</highlight1>" +
		"<highlight2>Animated screensavers</highlight2>" +
		"<qtyInStock>15</qtyInStock>" +
    "</product>" +

    "<product productId=\"5\"> " +
        "<name>Nokia 3220</name>" +
        "<description>The Nokia 3220 phone is a fresh new cut on some familiar ideas - animate your MMS messages with cute characters, see the music with lights that flash in time with your ringing tone, download wallpapers and screensavers with matching color schemes for the interface.</description>" +
        "<price>159.99</price>" +
        "<image>Nokia_3220.gif</image>" +
        "<series>3000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>false</video>" +
        "<highlight1>MIDI tones</highlight1>" +
		"<highlight2>Cut-out covers</highlight2>" +
		"<qtyInStock>5</qtyInStock>" +
    "</product>" +

    "<product productId=\"6\"> " +
        "<name>Nokia 3650</name>" +
        "<description>Messaging is more personal, versatile and fun with the Nokia 3650 camera phone.  Capture experiences as soon as you see them and send the photos you take to you friends and family.</description>" +
        "<price>199.99</price>" +
        "<image>Nokia_3650.gif</image>" +
        "<series>3000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>Infrared or Bluetooth</highlight1>" +
		"<highlight2>Built-in XHTML browser</highlight2>" +
		"<qtyInStock>8</qtyInStock>" +
    "</product>" +

    "<product productId=\"7\"> " +
        "<name>Nokia 6820</name>" +
        "<description>Messaging just got a whole lot smarter. The Nokia 6820 messaging device puts the tools you need for rich communication - full messaging keyboard, digital camera, mobile email, MMS, SMS, and Instant Messaging - right at your fingertips, in a small, sleek device.</description>" +
        "<price>299.99</price>" +
        "<image>Nokia_6820.gif</image>" +
        "<series>6000</series>" +
        "<triband>true</triband>" +
        "<camera>true</camera>" +
        "<video>false</video>" +
        "<highlight1>Messaging keyboard</highlight1>" +
		"<highlight2>Bluetooth </highlight2>" +
		"<qtyInStock>11</qtyInStock>" +
    "</product>" +

    "<product productId=\"8\"> " +
        "<name>Nokia 6670</name>" +
        "<description>Classic business tools meet your creative streak in the Nokia 6670 imaging smartphone. It has a Netfront Web browser with PDF support, document viewer applications for email attachments, a direct printing application, and a megapixel still camera that also shoots up to 10 minutes of video.</description>" +
        "<price>309.99</price>" +
        "<image>Nokia_6670.gif</image>" +
        "<series>6000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>PDF support</highlight1>" +
		"<highlight2>4x digital zoom</highlight2>" +
		"<qtyInStock>16</qtyInStock>" +
    "</product>" +

    "<product productId=\"9\"> " +
        "<name>Nokia 6620</name>" +
        "<description>Shoot a basket. Shoot a movie. Video phones from Nokia... the perfect way to save and share lifeâ€™s playful moments. Feel connected.</description>" +
        "<price>329.99</price>" +
        "<image>Nokia_6620.gif</image>" +
        "<series>6000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>Bluetooth technology</highlight1>" +
		"<highlight2>Up to 10 minutes video</highlight2>" +
		"<qtyInStock>6</qtyInStock>" +
    "</product>" +

    "<product productId=\"10\"> " +
        "<name>Nokia 3230 Silver</name>" +
        "<description>Get creative with the Nokia 3230 smartphone. Create your own ringing tones, print your mobile images, play multiplayer games over a wireless Bluetooth connection, and browse HTML and xHTML Web pages. Plus, with a 32 MB MMC card and expandable memory, you'll have lots of space to be creative.</description>" +
        "<price>369</price>" +
        "<image>Nokia_3230_black.gif</image>" +
        "<series>3000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>1.3 megapixel</highlight1>" +
		"<highlight2>Expandable memory</highlight2>" +
		"<qtyInStock>8</qtyInStock>" +
    "</product>" +

    "<product productId=\"11\"> " +
        "<name>Nokia 3230 Bronze</name>" +
        "<description>Get creative with the Nokia 3230 smartphone. Create your own ringing tones, print your mobile images, play multiplayer games over a wireless Bluetooth connection, and browse HTML and xHTML Web pages. Plus, with a 32 MB MMC card and expandable memory, you'll have lots of space to be creative.</description>" +
        "<price>369</price>" +
        "<image>Nokia_3230_bronze.gif</image>" +
        "<series>3000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>1.3 megapixel</highlight1>" +
		"<highlight2>Expandable memory</highlight2>" +
		"<qtyInStock>20</qtyInStock>" +
    "</product>" +

    "<product productId=\"12\"> " +
        "<name>Nokia 6630</name>" +
        "<description>Not only is the Nokia 6630 imaging smartphone a 1.3 megapixel digital imaging device (1.3 megapixel camera sensor, effective resolution 1.23 megapixels for image capture, image size 1280 x 960 pixels), it's also a portable office and a modern rich-media machine.</description>" +
        "<price>379</price>" +
        "<image>Nokia_6630.gif</image>" +
        "<series>6000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>1.3 megapixel</highlight1>" +
		"<highlight2>6x smooth digital zoom</highlight2>" +
		"<qtyInStock>11</qtyInStock>" +
    "</product>" +

    "<product productId=\"13\"> " +
        "<name>Nokia 7610 black</name>" +
        "<description>The Nokia 7610 imaging phone with its sleek, compact design stands out in any crowd. Cut a cleaner profile with a megapixel camera and 4x digital zoom. Quality prints are all the proof you need of your cutting edge savvy.</description>" +
        "<price>399.99</price>" +
        "<image>Nokia_7610_black.gif</image>" +
        "<series>7000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>Up to 10 minutes video</highlight1>" +
		"<highlight2>8 MB internal memory</highlight2>" +
		"<qtyInStock>15</qtyInStock>" +
    "</product>" +

    "<product productId=\"14\"> " +
        "<name>Nokia 7610 white</name>" +
        "<description>The Nokia 7610 imaging phone with its sleek, compact design stands out in any crowd. Cut a cleaner profile with a megapixel camera and 4x digital zoom. Quality prints are all the proof you need of your cutting edge savvy.</description>" +
        "<price>399.99</price>" +
        "<image>Nokia_7610_white.gif</image>" +
        "<series>7000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>Up to 10 minutes video</highlight1>" +
		"<highlight2>8 MB internal memory</highlight2>" +
		"<qtyInStock>21</qtyInStock>" +
    "</product>" +

    "<product productId=\"15\"> " +
        "<name>Nokia 6680</name>" +
        "<description>The Nokia 6680 is an imaging smartphone that's as sophisticated and successful as you are. With two integrated digital cameras (1.3 megapixels in the back, VGA in the front), you can conduct face-to-face video calls from your phone.</description>" +
        "<price>509</price>" +
        "<image>Nokia_6680.gif</image>" +
        "<series>6000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>Music player</highlight1>" +
		"<highlight2>Video sharing capability</highlight2>" +
		"<qtyInStock>17</qtyInStock>" +
    "</product>" +

    "<product productId=\"16\"> " +
        "<name>Nokia 9300</name>" +
        "<description>The Nokia 9300 combines popular voice communication features with important productivity applications in one well-appointed device. Now the tools you need to stay in touch and on top of schedules, email, news, and messages are conveniently at your fingertips.</description>" +
        "<price>599</price>" +
        "<image>Nokia_9300_close.gif</image>" +
        "<series>9000</series>" +
        "<triband>true</triband>" +
        "<camera>false</camera>" +
        "<video>false</video>" +
        "<highlight1>Full keyboard</highlight1>" +
		"<highlight2>Full keyboard</highlight2>" +
		"<qtyInStock>9</qtyInStock>" +
    "</product>" +

    "<product productId=\"17\"> " +
        "<name>Nokia 9500</name>" +
        "<description>Fast data connectivity with Wireless LAN. Browse the Internet in full color, on a wide, easy-to-view screen. Work with office documents not just email with attachments and memos, but presentations and databases too.</description>" +
        "<price>799.99</price>" +
        "<image>Nokia_9500_close.gif</image>" +
        "<series>9000</series>" +
        "<triband>true</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>Office Tools</highlight1>" +
		"<highlight2>80MB internal memory</highlight2>" +
		"<qtyInStock>5</qtyInStock>" +
    "</product>" +

    "<product productId=\"18\"> " +
        "<name>Nokia N90</name>" +
        "<description>Twist and shoot. It's a pro-photo taker. A personal video-maker. Complete with Carl Zeiss Optics for crisp, bright images you can view, edit, print and share. Meet the Nokia N90.</description>" +
        "<price>999</price>" +
        "<image>Nokia_N90.gif</image>" +
        "<series>9000</series>" +
        "<triband>false</triband>" +
        "<camera>true</camera>" +
        "<video>true</video>" +
        "<highlight1>Carl Zeiss Optics</highlight1>" +
		"<highlight2>2 megapixel camera</highlight2>" +
		"<qtyInStock>23</qtyInStock>" +
    "</product>" +

    "</catalog>";		
	    return xml;
	}
}
