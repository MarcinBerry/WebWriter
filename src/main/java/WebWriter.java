import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WebWriter {
    public byte[] bytes = new byte[0];


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
            int firstDot = hostString.indexOf(".")+1;
            int lastDot = hostString.lastIndexOf(".");
            hostString = hostString.substring(firstDot, lastDot);
            return hostString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<String> getSubpages(String urlString) {
        ArrayList<String> subpages = new ArrayList<>();

        return subpages;
    }
}
