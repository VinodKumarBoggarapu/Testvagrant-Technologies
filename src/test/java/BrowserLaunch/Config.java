package BrowserLaunch;

public class Config {

    private static String browser;
    private static String authenticationURL;

    public static void setBrowser(String browser) {
        Config.browser = browser;
    }

    public static String getBrowser() {
        return browser;
    }

    public static void setAuthenticationURL(String authenticationURL) {
        Config.authenticationURL = authenticationURL;
    }

    public static String getAuthenticationURL() {
        return authenticationURL;
    }
}
