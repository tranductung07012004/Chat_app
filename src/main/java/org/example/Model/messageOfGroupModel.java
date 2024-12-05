package org.example.Model;
import java.sql.Timestamp;

public class messageOfGroupModel {
    private long message_group_id;
    private int from_user;
    private int to_group;
    private Timestamp chat_time;
    private String chat_content;
    private int isDeleted;
    private int isDeletedAll;

    public messageOfGroupModel(long initMessage_group_id,
                               int initFrom_user,
                               int initTo_group,
                               Timestamp initChat_time,
                               String initChat_content,
                               int initIsDeleted,
                               int initIsDeletedAll) {

        this.message_group_id = initMessage_group_id;
        this.from_user = initFrom_user;
        this.to_group = initTo_group;
        this.chat_time = initChat_time;
        this.chat_content = initChat_content;
        this.isDeleted = initIsDeleted;
        this.isDeletedAll = initIsDeletedAll;
    }
}
