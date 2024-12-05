package org.example.Model;
import java.sql.Date;

public class activityHistoryModel {
    private long activty_id;
    private int user_id;
    private Date time_period;
    private int open_app;
    private int peopleChatted;
    private int groupChatted;

    public activityHistoryModel(long initActivty_id,
                                int initUser_id,
                                Date initTime_period,
                                int initOpen_app,
                                int initPeopleChatted,
                                int initGroupChatted) {
        this.activty_id = initActivty_id;
        this.user_id = initUser_id;
        this.time_period = initTime_period;
        this.open_app = initOpen_app;
        this.peopleChatted = initPeopleChatted;
        this.groupChatted = initGroupChatted;
    }
}
