package com.svs.learn.guser.web.controller;

import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.svs.learn.guser.service.GUserStorageService;
import com.svs.learn.guser.service.bean.GUserMedia;
import com.svs.learn.guser.web.beans.L7MediaResponse;
import com.svs.learn.guser.web.controller.util.MultipartFileSender;

@Controller
@RequestMapping("/api/media")
public class MediaController {

	@Autowired
	GUserStorageService storageService;

	@PostMapping("/upload")
	@ResponseBody
	public Object uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("tags") String tags) {

		return storageService.storeFile(file, tags);
	}

	@GetMapping("/read/{mediaKey}")
	public void readMediaByPath(@PathVariable("mediaKey") String mediaKey, HttpServletResponse resp) throws Exception {
		readMedia(mediaKey, resp);
	}

	@GetMapping("/read")
	public void readMedia(@RequestParam("mediaKey") String mediaKey, HttpServletResponse resp) throws Exception {

		L7MediaResponse mediaResp = storageService.getL7Media(mediaKey);
		GUserMedia media = mediaResp.getL7media();
		if (mediaResp.getMediaFile() == null) {
			resp.sendError(404);
		}

		resp.setContentLength((int) media.getSize());
		resp.setContentType(media.getContentType());

		FileInputStream mediaFis = new FileInputStream(mediaResp.getMediaFile());
		StreamUtils.copy(mediaFis, resp.getOutputStream());
		mediaFis.close();

		resp.flushBuffer();
	}

	@GetMapping("/video/{mediaKey}")
	public void getVideoStream(@PathVariable("mediaKey") String mediaKey, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		L7MediaResponse mediaResp = storageService.getL7Media(mediaKey);
		GUserMedia media = mediaResp.getL7media();

		response.setContentLength((int) media.getSize());
		response.setContentType(media.getContentType());

		MultipartFileSender.fromFile(mediaResp.getMediaFile()).with(request).with(response)
				.setContentType(media.getContentType()).serveResource();

	}

}
