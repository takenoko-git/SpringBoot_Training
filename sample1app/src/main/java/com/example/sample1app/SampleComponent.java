package com.example.sample1app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SampleComponent {
    
    @Value("${samplespp.samplecomponent.message}")
    private String message;

    public SampleComponent() {
        super();
    }

    public String message() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
