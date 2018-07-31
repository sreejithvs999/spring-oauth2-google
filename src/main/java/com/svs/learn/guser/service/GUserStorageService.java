package com.svs.learn.guser.service;

import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.svs.learn.guser.dao.GUserMediaDao;
import com.svs.learn.guser.exception.GUserAppException;
import com.svs.learn.guser.service.bean.GUserMedia;
import com.svs.learn.guser.web.beans.L7MediaResponse;

@Service
public class GUserStorageService {

	Logger log = LoggerFactory.getLogger(GUserStorageService.class);

	@Autowired
	SecurityServices secServices;

	@Autowired
	GUserMediaDao mediaDao;

	public GUserMedia storeFile(MultipartFile file, String tags) {

		String uuid = UUID.randomUUID().toString();

		storeFileLocal(file, uuid);

		GUserMedia l7Media = new GUserMedia();
		l7Media.setMediaId(uuid);
		l7Media.setFileName(file.getOriginalFilename());
		l7Media.setContentType(file.getContentType());
		l7Media.setSize(file.getSize());
		l7Media.setStatus("UPLOAD");
		l7Media.setTags(tags);
		mediaDao.saveGUserMedia(l7Media);
		log.info("Saved media data. id={}", uuid);
		return l7Media;
	}

	public L7MediaResponse getL7Media(String mediaKey) {

		String[] keys = mediaKey.split("_");

		log.info("Reading GUserMedia for mediaId={}", keys[0]);
		GUserMedia l7media = mediaDao.getGUserMedia(keys[0]);

		L7MediaResponse resp = new L7MediaResponse();
		resp.setL7media(l7media);
		resp.setMediaFile(getMediaFile(l7media));
		return resp;
	}

	private File getMediaFile(GUserMedia media) {
		File f = new File(storageDir, media.getMediaId());
		if (f.exists()) {
			return f;
		} else {
			return null;
		}
	}

	private String storeFileLocal(MultipartFile file, String uuid) {

		try {

			File storeLoc = new File(storageDir, uuid);
			file.transferTo(storeLoc);
			return storeLoc.getAbsolutePath();
		} catch (Exception e) {
			log.error("File store to local path", e);
			throw new GUserAppException("Could not upload the file");
		}

	}

	@Value("${mylitmus7.storage.dir}")
	private String storageDir;
}
