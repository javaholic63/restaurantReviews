package com.powerreviews.project.errors;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

public class CustomErrorResponse {
	
    @Setter
	@Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    @Setter
	@Getter
    private int status;
    @Setter
	@Getter
    private String error;

}
