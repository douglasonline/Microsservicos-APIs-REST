package com.example.PRODUTOCLIENT.Configuration;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ProdutoIdService {

    private final MongoOperations mongoOperations;

    @Autowired
    public ProdutoIdService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
        initializeSequence();
    }

    private void initializeSequence() {
        Query query = new Query(Criteria.where("_id").is("produtoId"));
        Update update = new Update().setOnInsert("sequenceValue", 0L);

        mongoOperations.upsert(query, update, "counters");
    }

    public Long getNextSequenceId() {
        Query query = new Query(Criteria.where("_id").is("produtoId"));
        Update update = new Update().inc("sequenceValue", 1);

        Document result = mongoOperations.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                Document.class,
                "counters"
        );

        return result != null ? result.getLong("sequenceValue") : null; // Retornar null se não houver sequência encontrada
    }
}