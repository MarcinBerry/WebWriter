import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebWriterTest {

    @Test
    public void readTest() {
        WebWriter webWriter = new WebWriter();
        assertTrue(webWriter.bytes.length == 0);
        assertTrue(webWriter.read("https://www.powiatleczynski.pl"));
        assertTrue(webWriter.bytes.length > 10);
        assertFalse(webWriter.read("https://ww.powiatleczynski.pl"));

    }

    @Test
    public void saveTest() {
        String username = System.getProperty("user.name");
        WebWriter webWriter = new WebWriter();
        boolean success = false;
        if(webWriter.read("https://www.powiatleczynski.pl"))
            success = webWriter.save("C:\\Users\\" + username + "\\Desktop\\Strona\\web.html");
        assertTrue(success);
    }

}
