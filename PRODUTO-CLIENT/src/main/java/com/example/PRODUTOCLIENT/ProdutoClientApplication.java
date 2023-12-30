package com.example.PRODUTOCLIENT;

import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.boot.ApplicationRunner;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class ProdutoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdutoClientApplication.class, args);
	}

	@Autowired
	private MongoOperations mongoOperations;

	@Bean
	public ApplicationRunner initializeMongoDB() {
		return args -> {
			// Verificar se o documento existe antes de inserir
			Document counter = mongoOperations.getCollection("counters").find(new Document("_id", "produtoId")).first();
			if (counter == null) {
				Document document = new Document("_id", "produtoId").append("sequenceValue", 0);
				mongoOperations.getCollection("counters").insertOne(document);
			}
		};
	}
}