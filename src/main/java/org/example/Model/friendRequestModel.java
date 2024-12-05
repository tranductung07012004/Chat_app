package org.example.Model;
import java.sql.Timestamp;

public class friendRequestModel {
    private int from_user_id;
    private int target_user_id;
    private Timestamp time_created;

    public friendRequestModel(int initFrom_user_id, int initTarget_user_id, Timestamp initTime_created) {
        this.from_user_id = initFrom_user_id;
        this.target_user_id = initTarget_user_id;
        this.time_created = initTime_created;
    }
}
