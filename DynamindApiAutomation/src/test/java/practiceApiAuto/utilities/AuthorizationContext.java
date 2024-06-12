package practiceApiAuto.utilities;


public class AuthorizationContext {
    private static String authorizationToken;

    public static String getAuthorizationToken() {
        return authorizationToken;
    }

    public static void setAuthorizationToken(String token) {
        authorizationToken = token;
    }
}
