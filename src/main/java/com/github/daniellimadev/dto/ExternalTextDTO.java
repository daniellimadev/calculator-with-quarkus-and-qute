package com.github.daniellimadev.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalTextDTO {

    @JsonProperty("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
