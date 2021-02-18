import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void saveTest() {
        String username = System.getProperty("user.name");
        webWriter.read("https://www.powiatleczynski.pl");
        boolean success = false;
        if(webWriter.read("https://www.powiatleczynski.pl"))
            success = webWriter.save("C:\\Users\\" + username + "\\Desktop\\Strona\\web.html");
        assertTrue(success);
    }

    @Test
    public void resolveNameTest() {
        //webWriter.read("https://www.powiatleczynski.pl");
        try {
            String hostString = new URL("https://www.powiatleczynski.pl").getHost();
            int firstDot = hostString.indexOf(".")+1;
            int lastDot = hostString.lastIndexOf(".");
            hostString = hostString.substring(firstDot, lastDot);
            System.out.println(hostString);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
