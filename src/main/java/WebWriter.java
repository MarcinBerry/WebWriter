import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebWriter {
    public byte[] bytes = new byte[0];
    ArrayList<URL> urlList = new ArrayList<>();
    private String homePageString;
    private String rootDirectory = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\";

    public boolean readWebsite(String url) {
        try (InputStream in = new URL(url).openStream()) {
            bytes = in.readAllBytes();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeWebsite(String fileString) {
        try (OutputStream out = new FileOutputStream(fileString)) {
            out.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getHomePageName(String urlString) {
        try {
            return new URL(urlString).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLastSubpageName(String urlString) {
        try {
            URL url = new URL(urlString);
            Path subpagesPath = Paths.get(url.toURI().getPath());
            int i = subpagesPath.getNameCount();
            Path lastSubpagePath = subpagesPath.getName(i-1);
            return lastSubpagePath.toString();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setHomePage(String urlString) {
        homePageString = urlString;
    }

    public boolean isFromSameHomePage(String urlString) {
        try {
            String homePageString = new URL(this.homePageString).getHost();
            String newHomePageString = new URL(urlString).getHost();
            return homePageString.equals(newHomePageString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setHomePageDirectory(String homePageDirectory) {
        this.rootDirectory += homePageDirectory;
    }

    public boolean createHomePageDirectory() {
        return new File(rootDirectory).mkdirs();
    }

   public ArrayList<String> getSubpages(String urlString) {
        ArrayList<String> subpages = new ArrayList<>();
        readWebsite(urlString);
        try {
            String content = new String(bytes, "UTF-8");
            //String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\d>]*)\\s*>"; //Old pattern from book
            String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\")\\s*>";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String match = matcher.group();
                String hostString = match.substring(match.indexOf("\"") + 1, match.lastIndexOf("\""));
                subpages.add(hostString);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return subpages;
    }
}
