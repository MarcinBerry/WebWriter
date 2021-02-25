import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class WebWriterTest {
    WebWriter webWriter;
    final String mainWebsite = "https://www.powiatleczynski.pl";
    final String subpage = "https://www.powiatleczynski.pl/kontakt";
    final String desktopDirectoryString = "C:\\Users\\"+System.getProperty("user.name") + "\\Desktop";

    @BeforeEach
    public void beforeAllTests() {
        webWriter = new WebWriter();
    }

    @Test
    public void readWebsiteTest() throws MalformedURLException {
        URL url = new URL(mainWebsite);
        String webString = webWriter.readWebsite(url);
        assertFalse(webString.isEmpty());
    }

    @Test
    public void writeWebsiteTest() throws MalformedURLException {
        URL url = new URL("https://powiatleczynski.pl/wladze/zarzad-powiatu/");
        webWriter.writeWebsite(url);
        assertTrue(new File(desktopDirectoryString+"\\Strona\\powiatleczynski.pl\\wladze\\zarzad-powiatu.html").isFile());
    }

    @Test
    public void getPathFromWebsiteTest() throws MalformedURLException {
        URL url = new URL("https://powiatleczynski.pl/wladze/zarzad-powiatu/");
        Path path = webWriter.getPathFromWebsite(url);
        assertTrue(path.equals(Paths.get("powiatleczynski.pl\\wladze\\zarzad-powiatu")));
        url = new URL("https://powiatleczynski.pl/wladze/rada-powiatu/");
        path = webWriter.getPathFromWebsite(url);
        assertTrue(path.equals(Paths.get("powiatleczynski.pl\\wladze\\rada-powiatu")));
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
    public void addURLtoList() throws MalformedURLException {
        URL url = new URL("https://powiatleczynski.pl/");
        webWriter.addURLtoList(url);
        assertTrue(webWriter.getURLList().get(0).equals(new URL("https://powiatleczynski.pl/")));
    }

    @Test
    public void isSubpageTest() throws MalformedURLException {
        URL homePage = new URL("https://www.powiatleczynski.pl/");
        webWriter.addURLtoList(homePage);
        URL url = new URL(subpage);
        assertTrue(webWriter.isSubpage(url));
        URL url1 = new URL("https://calendar.google.com/");
        assertFalse(webWriter.isSubpage(url1));
    }

    @Test
    public void htmlPatternTest() {
        String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\")\\s*>";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);

        String content = "<a href=\"oihasdojbewo-123-.html\">";
        Matcher matcher = pattern.matcher(content);
        assertTrue(matcher.find());

        content = "<a    href=\"oinkerwojn\" oiwefjnko>";
        matcher = pattern.matcher(content);
        assertFalse(matcher.find());
    }
    @Test
    public void getSubpagesStringList() {
        ArrayList<String> subpages = webWriter.appendSubpages("https://www.powiatleczynski.pl");
        assertEquals(subpages.size(), 25);
    }

    @Test
    public void createDirectories() throws MalformedURLException {
        URL url = new URL("https://www.powiatleczynski.pl/");
        webWriter.createDirectories(url);
        File directory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\www.powiatleczynski.pl");
        assertTrue(directory.isDirectory());

        if(directory.isDirectory())
            directory.delete();
    }
}
