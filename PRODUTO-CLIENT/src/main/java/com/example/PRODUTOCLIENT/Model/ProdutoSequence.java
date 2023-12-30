package com.example.PRODUTOCLIENT.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "produto_sequence")
public class ProdutoSequence {

    @Id
    private String id;

    private Long sequenceValue;

    // Getters e setters
    // Construtores

    public Long getSequenceValue() {
        return sequenceValue;
    }

    public void setSequenceValue(Long sequenceValue) {
        this.sequenceValue = sequenceValue;
    }
}
