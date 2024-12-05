package org.example.Model;

public class groupChatMemberModel {
    private int group_chat_id;
    private int group_member_id;
    private int isAdminOfGroup;

    public groupChatMemberModel(int initGroup_chat_id, int initGroup_member_id, int initIsAdminOfGroup) {
        this.group_chat_id = initGroup_chat_id;
        this.group_member_id = initGroup_member_id;
        this.isAdminOfGroup = initIsAdminOfGroup;
    }
}
