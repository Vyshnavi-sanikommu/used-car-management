package com.tamuc.usedcarsmanagement.database_seq_service;

import com.tamuc.usedcarsmanagement.usedcarsPojo.UsedCarsDatabase;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class DatabaseSeq_Generator {
    private MongoOperations mongoOperations;

    @Autowired
    public DatabaseSeq_Generator(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
    public String generateSeq(String seqName){
        UsedCarsDatabase counter =  mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1),options().returnNew(true).upsert(true),
                UsedCarsDatabase.class);
        System.out.println(counter.getSeq());
        System.out.println(counter.getId());
        return String.valueOf(!Objects.isNull(counter) ? counter.getSeq() : 1);
    }
}
