package practiceApiAuto.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPayload {
    static int appUserId;
    int id;
    String name;
    String email;
    String password;
    Long mobileNum;

    public static int getAppUserId() {
        return appUserId;
    }

    public static void setAppUserId(int appUserId) {
        UserPayload.appUserId=appUserId;
    }
}
