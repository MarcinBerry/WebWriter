import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WebWriter {
    public byte[] bytes = new byte[0];
    ArrayList<URL> urlList = new ArrayList<>();

    public boolean read(String url) {
        try (InputStream in = new URL(url).openStream()) {
            bytes = in.readAllBytes();
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeWeb(String fileString) {
        try (OutputStream out = new FileOutputStream(fileString)) {
            out.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getHostName(String urlString) {
        try {
            String hostString = new URL(urlString).getHost();
            return hostString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLastSubpageName(String urlString) {

        try {
            return Paths.get(new URL(urlString).toURI()).getName(2).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getSubpages(String urlString) {
        ArrayList<String> subpages = new ArrayList<>();
        read(urlString);
        try {
            String content = new String(bytes, "UTF-8");
            String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\d>]*)\\s*>";
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
