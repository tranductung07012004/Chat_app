package org.example.Model;
import java.sql.Timestamp;

public class messageOfUserModel {
    private long message_user_id;
    private int from_user;
    private int to_user;
    private Timestamp chat_time;
    private String chat_content;
    private int isDeleted;
    private int isDeletedAll;

    public messageOfUserModel(long initMessage_user_id,
                              int initFrom_user,
                              int initTo_user,
                              Timestamp initChat_time,
                              String initChat_content,
                              int initIsDeleted,
                              int initIsDeletedAll) {
        this.message_user_id = initMessage_user_id;
        this.from_user = initFrom_user;
        this.to_user = initTo_user;
        this.chat_time = initChat_time;
        this.chat_content = initChat_content;
        this.isDeleted = initIsDeleted;
        this.isDeletedAll = initIsDeletedAll;

    }
}
