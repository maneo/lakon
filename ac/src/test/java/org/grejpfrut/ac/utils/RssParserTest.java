package org.grejpfrut.ac.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;

import org.grejpfrut.ac.data.Item;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class RssParserTest extends TestCase {

    private static final String DATE = "13.07.2006";

	private static final String TITLE = "Liban: Lotnisko w Bejrucie ostrzelane";

    private static final String CATEGORY = "Wiadomoœci.Gazeta.pl";

    private static final String LINK = "http://wiadomosci.gazeta.pl/wiadomosci/1,53600,3481099.html?skad=rss";

    private static final String SUMMARY = "Okrêty izraelskie ostrzela³y w czwartek wieczorem "
            + "zbiorniki paliwa na lotnisku w Bejrucie. Co najmniej jeden zbiornik stan¹³ "
            + "w p³omieniach. Lotnictwo izraelskie zrzuci³o nad po³udniowymi "
            + "przedmieœciami Bejrutu ulotki w jêzyku arabskim, wzywaj¹ce "
            + "ludnoœæ do trzymania siê z daleka od obiektów Hezbollahu.";
    
    private Date date;
    
    

    @Override
	protected void setUp() throws Exception {
		
    	this.date =  new SimpleDateFormat("dd.MM.yyyy").parse(DATE); 
    	
	}



	public void testSimpleXml() {

        final SAXParser parser;
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
            final XMLReader reader = parser.getXMLReader();

            final RssParser p = new RssParser();

            reader.setContentHandler(p);
            reader.parse(new InputSource(this.getClass().getResourceAsStream(
                    "/single_news.xml")));

            List<Item> items = p.getItems();
            assertEquals(items.size(), 1);
            assertEquals(items.get(0).getLink(), LINK);
            assertEquals(items.get(0).getTitle(), TITLE);
            assertEquals(items.get(0).getCategory(), CATEGORY);
            assertEquals(items.get(0).getSummary(), SUMMARY);
            final String rssDate = new SimpleDateFormat("yyyy.MM.dd").format(items.get(0).getDate());
            final String excDate = new SimpleDateFormat("yyyy.MM.dd").format(this.date);
            assertEquals(excDate, rssDate);

        } catch (ParserConfigurationException e) {
            fail("Parser configuration process failed");
        } catch (SAXException e) {
            fail("Error during parsing");
        } catch (IOException e) {
            fail("Error while opening xml file");
        }

    }
}
