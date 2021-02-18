import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

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

    public boolean save(String fileString) {
        try (OutputStream out = new FileOutputStream(fileString)) {
            out.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
