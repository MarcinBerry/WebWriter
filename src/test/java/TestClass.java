import org.junit.jupiter.api.Test;

public class TestClass
{
    @Test
    public void test() {
        int counter = 28;
        String stringToShow = "/bip/79_spleczna/fckeditor/file/Zarządzenie%20Starosty/2021//Zarządzenie ";
        //String stringToShow = "Zarządzenie Starosty Nr ";
        String postFix = "-2021.pdf";
        //String postFix = " z dnia 2021 r.";
        for (int i = counter; i >= 24; i--) {
            System.out.println(stringToShow + i + postFix);
        }
    }
}
