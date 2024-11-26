package org.example.Model;
import java.sql.Timestamp;

public class groupChatModel {
    private int group_id;
    private String group_name;
    private Timestamp time_created;

    public groupChatModel(int initGroup_id, String initGroup_name, Timestamp initTime_created) {
        this.group_id = initGroup_id;
        this.group_name = initGroup_name;
        this.time_created = initTime_created;
    }
}
