import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class WebWriterTest {
    WebWriter webWriter;
    final String mainWebsite = "https://www.powiatleczynski.pl";
    final String subpage = "https://www.powiatleczynski.pl/kontakt";

    @BeforeEach
    public void beforeAllTests() {
        webWriter = new WebWriter();
    }

    @Test
    public void readCorrectURLTest() {
        assertEquals(0, webWriter.bytes.length);
        assertTrue(webWriter.readWebsite(mainWebsite));
        assertTrue(webWriter.bytes.length > 10);
    }

    @Test
    public void readIncorrectURLTest() {
        assertEquals(0, webWriter.bytes.length);
        assertFalse(webWriter.readWebsite("https://ww.powiatleczynski.pl/"));
        assertEquals(0, webWriter.bytes.length);
    }
    @Test
    public void writeWebTest() {
        String username = System.getProperty("user.name");
        boolean success = false;
        if(webWriter.readWebsite(mainWebsite))
            success = webWriter.writeWebsite("C:\\Users\\" + username + "\\Desktop\\Strona\\web.html");
        assertTrue(success);
    }

    @Test
    public void getHostNameFromHostTest() {
        String hostName = webWriter.getHomePageName(mainWebsite);
        assertEquals("www.powiatleczynski.pl", hostName);
        assertNotEquals("https://www.powiatleczynski.pl", hostName);
    }

    @Test
    public void getHostNameFromSubpageTest() {
        String hostName = webWriter.getHomePageName(subpage);
        assertEquals("www.powiatleczynski.pl", hostName);
    }

    @Test
    public void getLastSubpageName() {
        String subpageName = webWriter.getLastSubpageName(subpage);
        assertEquals("kontakt", subpageName);
        subpageName = webWriter.getLastSubpageName("https://www.powiatleczynski.pl/gmina/gmina-cycow/");
        assertEquals("gmina-cycow", subpageName);
        subpageName = webWriter.getLastSubpageName("https://powiatleczynski.pl/2021/02/19/ostrzezenie-meteorologiczne-28/");
        assertEquals("ostrzezenie-meteorologiczne-28", subpageName);
    }

    @Test
    public void isFromFamily() {
        webWriter.setHomePage(mainWebsite);
        assertTrue(webWriter.isFromSameHomePage(subpage));
        String anotherSubpageString = "https://www.youtube.com/watch?v=ikJE3skbyzc&list=RDMMRRnFG-5_Ces&index=2";
        assertFalse(webWriter.isFromSameHomePage(anotherSubpageString));
    }

    /*@Test
    public void getSubpages() {
        ArrayList<String> subpages = webWriter.getSubpages("https://www.powiatleczynski.pl");
        assertEquals(subpages.size(), 26);
        String patternString = "http://[a-z\\./0-9\\-]";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(subpages.toString());
        //assertEquals(26, matcher.groupCount());
    }*/

    @Test
    public void createRootDirectory() {
        String hostName = webWriter.getHomePageName(mainWebsite);
        webWriter.setHomePageDirectory(hostName);
        webWriter.createHomePageDirectory();
        File rootDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + hostName);
        assertTrue(rootDirectory.isDirectory());
        if(rootDirectory.isDirectory())
            rootDirectory.delete();

    }
}
