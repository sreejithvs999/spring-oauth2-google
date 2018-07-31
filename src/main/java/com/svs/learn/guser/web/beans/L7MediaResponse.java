package com.svs.learn.guser.web.beans;

import java.io.File;

import com.svs.learn.guser.service.bean.GUserMedia;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class L7MediaResponse {

	GUserMedia l7media;

	File mediaFile;
}
