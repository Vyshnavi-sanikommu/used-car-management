package com.tamuc.usedcarsmanagement.usedcarsPojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="database_sequences")
public class UsedCarsDatabase {
    @Id
    private String id;
    private long seq;

    public UsedCarsDatabase() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
