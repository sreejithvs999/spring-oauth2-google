package com.svs.learn.guser.service.bean;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GUserMedia {

	private String mediaId;

	private String fileName;

	private String contentType;

	private String tags;

	private long size;

	private String type;

	private String status;

	private LocalDateTime createdDt;
}
