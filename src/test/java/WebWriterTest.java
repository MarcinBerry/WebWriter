import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    public void resolveNameTest() {
        String hostName = webWriter.getHostName("https://www.powiatleczynski.pl");
        assertEquals("powiatleczynski", hostName);
        assertNotEquals("https://www.powiatleczynski.pl", hostName);
    }

    @Test
    public void getSubpages() {
        ArrayList<String> subpages = webWriter.getSubpages("https://www.powiatleczynski.pl");
        assertEquals(subpages.size(), 26);

        //Pattern: <a\s+href\s*=\s*("[^"]*"|[^\d>]*)\s*>
    }
}
