package net.lilifei.SpringDemoService.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateRecordRequest {

    private String someProperty;

    @JsonCreator
    public CreateRecordRequest(@JsonProperty("someProperty") final String property) {
        this.someProperty = property;
    }
}
