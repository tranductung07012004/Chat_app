package org.example.Model;
import java.sql.Timestamp;

public class loginHistoryModel {
    private int login_id;
    private int user_id;
    private Timestamp login_time;

    public loginHistoryModel(int initLogin_id, int initUser_id, Timestamp initLogin_time) {
        this.login_id = initLogin_id;
        this.user_id = initUser_id;
        this.login_time = initLogin_time;
    }
}
