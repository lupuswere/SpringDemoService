package net.lilifei.SpringDemoService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRecordResponse {

    private String recordId;
}
