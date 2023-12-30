package com.example.PRODUTOCLIENT.Exception;

public class ProdutoNotFoundException extends  RuntimeException{

    public ProdutoNotFoundException() {
    }

    public ProdutoNotFoundException(String message) {
        super(message);
    }
}
