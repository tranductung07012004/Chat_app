package org.example.Model;
import java.sql.Timestamp;

public class spamModel {
    private long spam_id;
    private int target_user;
    private Timestamp time_created;

    public spamModel(long initSpam_id, int initTarget_user, Timestamp initTime_created) {
        this.spam_id = initSpam_id;
        this.target_user = initTarget_user;
        this.time_created = initTime_created;
    }
}
