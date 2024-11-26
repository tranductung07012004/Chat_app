package org.example.Model;
import java.sql.Timestamp;

public class blockModel {

    private int block_id;
    private int block_by_id;
    private int target_user_id;

    public blockModel(int initBlock_id, int initBlock_by_id, int initTarget_user_id) {

        this.block_id = initBlock_id;
        this.block_by_id = initBlock_by_id;
        this.target_user_id = initTarget_user_id;
    }
}
