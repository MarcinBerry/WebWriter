import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class WebWriterTest {
    WebWriter webWriter;
    URL homeWebsite;
    URL firstSubpage;
    URL secondSubpage;
    File desktopDirectory;

    @BeforeEach
    public void beforeAllTests() {
        webWriter = new WebWriter();
        desktopDirectory = new File("C:\\Users\\"+System.getProperty("user.name") + "\\Desktop");
        try {
            homeWebsite = new URL("https://www.powiatleczynski.pl");
            firstSubpage = new URL("https://www.powiatleczynski.pl/kontakt/");
            secondSubpage = new URL("https://www.powiatleczynski.pl/wladze/zarzad-powiatu");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readWebsiteTest() {
        String webString = webWriter.readWebsite(homeWebsite);
        assertFalse(webString.isEmpty());
    }

    @Test
    public void writeHomepageTest() {
        webWriter.writeWebsite(homeWebsite);
        File directory = new File(desktopDirectory + "\\www.powiatleczynski.pl");
        assertTrue(directory.isDirectory());
        if(directory.isDirectory())
            directory.delete();
        File file = new File(desktopDirectory + "\\www.powiatleczynski.pl\\www.powiatleczynski.pl.html");
        assertTrue(file.isFile());
        if(file.isFile())
            file.delete();
    }

    @Test
    public void writeSubpageTest() {
        webWriter.writeWebsite(firstSubpage);
        File directory = new File(desktopDirectory + "\\www.powiatleczynski.pl\\kontakt");
        assertTrue(directory.isDirectory());

        File file = new File(desktopDirectory + "\\www.powiatleczynski.pl\\kontakt\\kontakt.html");
        assertTrue(file.isFile());
        if(file.isFile())
            file.delete();
        if(directory.isDirectory()) {
            directory.delete();
            directory.getParentFile().delete();
        }
    }
    @Test
    public void addURLtoList() throws MalformedURLException {
        webWriter.addURLtoList(homeWebsite);
        assertTrue(webWriter.getURLList().get(0).toString().equals(new URL("https://www.powiatleczynski.pl").toString()));
    }

    @Test
    public void isSubpageTest() {
        webWriter.addURLtoList(homeWebsite);
        assertTrue(webWriter.isSubpage(firstSubpage));
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
    public void createDirectoriesHomepageTest() {
        webWriter.createDirectories(homeWebsite);
        File directory = new File(desktopDirectory + "\\www.powiatleczynski.pl");
        assertTrue(directory.isDirectory());
        if(directory.isDirectory())
            directory.delete();
    }

    @Test
    public void createDirectoriesSubpageTest() {
        webWriter.createDirectories(firstSubpage);
        File directory = new File(desktopDirectory + "\\www.powiatleczynski.pl\\kontakt");
        assertTrue(directory.isDirectory());
        if(directory.isDirectory())
            directory.delete();
    }

    @Test
    public void getDirectoryPathFromFirstSubpageTest() {
        File filePath = webWriter.getDirectoryPath(firstSubpage);
        assertTrue(filePath.toString().equals(desktopDirectory +"\\www.powiatleczynski.pl\\kontakt"));
    }

    @Test
    public void getDirectoryPathFromSecondSubpageTest() {
        File filePath = webWriter.getDirectoryPath(secondSubpage);
        assertTrue(filePath.toString().equals(desktopDirectory +"\\www.powiatleczynski.pl\\wladze\\zarzad-powiatu"));
    }

    @Test
    public void getDirectoryPathFromHomepageTest() {
        File filePath = webWriter.getDirectoryPath(homeWebsite);
        assertTrue(filePath.toString().equals(desktopDirectory +"\\www.powiatleczynski.pl"));
    }

    @Test
    public void getFileNameTest() {
        String fileName = webWriter.getFileName(homeWebsite);
        assertTrue(fileName.equals("\\www.powiatleczynski.pl.html"));
        fileName = webWriter.getFileName(firstSubpage);
        assertTrue(fileName.equals("\\kontakt.html"));
    }


}