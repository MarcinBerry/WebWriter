import org.junit.jupiter.api.Test;

public class TestClass
{
    @Test
    public void test() {
        int from = 90;
        int to = 81;
        String type = "link";

        String stringToShow = "";
        String postFix = "";

        switch(type){
            case "protokół":
                stringToShow = "\nProtokół Nr ";
                postFix = "";
                break;
            case "zarządzenie":
                stringToShow = "Zarządzenie Starosty Nr ";
                postFix = "/2020 z dnia 2020 r.";
                break;
            case "link":
                stringToShow = "/bip/79_spleczna/fckeditor/file/Zarządzenie%20Starosty/2020//Zarządzenie ";
                postFix = "-2020.pdf";
                break;
        }

        to --;
        while(true) {
            if(from < to) {
                System.out.println(stringToShow + from + postFix);
                from++;
            }
            else if (from > to) {
                System.out.println(stringToShow + from + postFix);
                from--;
            }
            else {
                return;
            }
        }
    }
}
