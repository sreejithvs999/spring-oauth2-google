package com.svs.learn.guser.web.beans;

import java.io.File;

import com.svs.learn.guser.service.bean.GUserMedia;


public class L7MediaResponse {

	GUserMedia l7media;

	File mediaFile;

	public GUserMedia getL7media() {
		return l7media;
	}

	public void setL7media(GUserMedia l7media) {
		this.l7media = l7media;
	}

	public File getMediaFile() {
		return mediaFile;
	}

	public void setMediaFile(File mediaFile) {
		this.mediaFile = mediaFile;
	}
	
	
}
