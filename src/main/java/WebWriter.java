import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebWriter {
    ArrayList<URL> urlList = new ArrayList<>();
    private File desktopDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop");

    public String readWebsite(URL url) {
        try (InputStream in = url.openStream()) {
            byte[] bytes = in.readAllBytes();
            return new String(bytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean writeWebsite(URL url) {
        createDirectories(url);
        File file;
            if(isHomePage(url))
                file = new File(desktopDirectory + "\\" + url.getHost() + "\\" + url.getHost() + ".html");
            else
                file = new File(desktopDirectory + "\\" + url.getHost() + "\\" + getFileFrom(url));
        try (InputStream in = url.openStream();
             OutputStream out = new FileOutputStream(file)) {
            out.write(in.readAllBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isHomePage(URL url) {
        String file = url.getFile();
        return file.equals("/") || file.equals("") ? true : false;
    }

    public boolean isSubpage(URL url) {
        return urlList.get(0).getHost().equals(url.getHost());
    }


    public boolean createDirectories(URL url) {
        File directory;
        if (isHomePage(url)) {
            directory = new File(desktopDirectory + "\\" + url.getHost());
        } else {
            directory = new File(desktopDirectory + "\\" +url.getHost() + "\\" + url.getFile());
        }
        return directory.mkdirs();
    }

    public ArrayList<String> readAndAppendSubpagesFrom(String urlString) {
        ArrayList<String> subpagesList = new ArrayList<>();
        try {
            String content = readWebsite(new URL(urlString));
            //String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\d>]*)\\s*>"; //Old pattern from book
            String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\")\\s*>";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String match = matcher.group();
                String subpageString = match.substring(match.indexOf("\"") + 1, match.lastIndexOf("\""));
                if(subpagesList.stream().anyMatch(s -> s.equals(subpageString)));
                    subpagesList.add(subpageString);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return subpagesList;
    }

    public List<URL> getURLList() {
        return urlList;
    }

    public void addURLtoList(URL url) {
        if(!urlList.contains(url))
            urlList.add(url);
    }

    public File getFileFrom(URL url) {
        File file;
        if(url.getFile().endsWith("/").)


        return desktopDirectory;
    }
}
