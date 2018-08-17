package DataProviders;

import org.testng.annotations.DataProvider;

public class navBarData {
    @DataProvider(name = "NavBarData")
    public static Object[][] pages() {
        return new Object[][] {{"Features", "features"}, {"About", "about"}, {"Contact Us", "contact"}, {"Cartos Design", "home"}};
    }
}
