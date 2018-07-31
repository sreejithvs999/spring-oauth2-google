package com.svs.learn.guser.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.svs.learn.guser.service.bean.GUserMedia;

@Repository
public class GUserMediaDao {

	@Autowired
	MongoTemplate mongoTemplate;

	public GUserMedia saveGUserMedia(GUserMedia media) {

		mongoTemplate.save(media, "l7media");

		return media;
	}

	public GUserMedia getGUserMedia(String mediaId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("mediaId").is(mediaId));

		List<GUserMedia> l7medias = mongoTemplate.find(query, GUserMedia.class, "guser_media");
		return l7medias.isEmpty() ? null : l7medias.get(0);
	}
}
