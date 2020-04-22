import com.george.http.HttpBuilder;
import com.george.http.HttpRequest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpRequest http = new HttpRequest("localhost", 50000);
        http.setUrl("publish");
        System.out.println(http.get());
        System.out.println(HttpBuilder.get("localhost", 50000, "publish").send());
        System.out.println(HttpBuilder.get("localhost:50000/publish").send());
    }

}
