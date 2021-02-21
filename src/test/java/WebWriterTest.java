import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class WebWriterTest {
    WebWriter webWriter;

    @BeforeEach
    public void beforeAllTests() {
        webWriter = new WebWriter();
    }

    @Test
    public void readCorrectURLTest() {
        assertTrue(webWriter.bytes.length == 0);
        assertTrue(webWriter.read("https://www.powiatleczynski.pl"));
        assertTrue(webWriter.bytes.length > 10);
    }

    @Test
    public void readIncorrectURLTest() {
        assertTrue(webWriter.bytes.length == 0);
        assertFalse(webWriter.read("https://ww.powiatleczynski.pl"));
        assertTrue(webWriter.bytes.length == 0);
    }
    @Test
    public void writeWebTest() {
        String username = System.getProperty("user.name");
        boolean success = false;
        if(webWriter.read("https://www.powiatleczynski.pl"))
            success = webWriter.writeWeb("C:\\Users\\" + username + "\\Desktop\\Strona\\web.html");
        assertTrue(success);
    }

    @Test
    public void getHostNameFromHostTest() {
        String hostName = webWriter.getHostName("https://www.powiatleczynski.pl");
        assertEquals("www.powiatleczynski.pl", hostName);
        assertNotEquals("https://www.powiatleczynski.pl", hostName);
    }

    @Test
    public void getHostNameFromSubpageTest() {
        String hostName = webWriter.getHostName("https://www.powiatleczynski.pl/kontakt");
        assertEquals("www.powiatleczynski.pl", hostName);
    }

    @Test
    public void getLastSubpageName() {
        String subpageName = webWriter.getLastSubpageName("https://www.powiatleczynski.pl/kontakt");
        assertEquals("kontakt", subpageName);
        subpageName = webWriter.getLastSubpageName("https://www.powiatleczynski.pl/gmina/gmina-cycow/");
        assertEquals("gmina-cycow", subpageName);
        subpageName = webWriter.getLastSubpageName("https://powiatleczynski.pl/2021/02/19/ostrzezenie-meteorologiczne-28/");
        assertEquals("ostrzezenie-meteorologiczne-28", subpageName);
    }

    @Test
    public void isFromFamily() {
        String urlString = "https://www.powiatleczynski.pl/";
        webWriter.setFamily(urlString);
        String subpageString = "https://www.powiatleczynski.pl/kontakt";
        assertTrue(webWriter.isFromSameFamily(subpageString));
        subpageString = "https://www.youtube.com/watch?v=ikJE3skbyzc&list=RDMMRRnFG-5_Ces&index=2";
        assertFalse(webWriter.isFromSameFamily(subpageString));
    }

    @Test
    public void getSubpages() {
        ArrayList<String> subpages = webWriter.getSubpages("https://www.powiatleczynski.pl");
        assertEquals(subpages.size(), 26);
        String patternString = "http://[a-z\\./0-9\\-]";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(subpages.toString());
        //assertEquals(26, matcher.groupCount());
    }

    @Test
    public void createRootDirectory() {
        String hostName = webWriter.getHostName("https://www.powiatleczynski.pl");
        webWriter.setRootDirectory(hostName);
        webWriter.createRootDirectory();
        File rootDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + hostName);
        assertTrue(rootDirectory.isDirectory());
        if(rootDirectory.isDirectory())
            rootDirectory.delete();
    }
}
