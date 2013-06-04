package com.heys.dating.service.impl;

import org.springframework.stereotype.Service;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.service.SearchManager;

@Service("SearchManager")
public class GaeSearchManagerImpl implements SearchManager {

	private final Index profileIndex;

	public GaeSearchManagerImpl() {
		final IndexSpec profileIndexSpec = IndexSpec.newBuilder()
				.setName("myindex").build();
		profileIndex = SearchServiceFactory.getSearchService().getIndex(
				profileIndexSpec);
	}

	private void addToProfileIndex(final Document... docs) {
		try {
			profileIndex.put(docs);
		} catch (final PutException e) {
			if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
					.getCode())) {
				addToProfileIndex(docs);
			}
		}
	}

	private Field atom(final String name, final String value) {
		return Field.newBuilder().setName(name).setAtom(value).build();
	}

	private Document doc(final String id, final Field... fields) {
		final com.google.appengine.api.search.Document.Builder doc = Document
				.newBuilder().setId(id);
		for (final Field field : fields) {
			doc.addField(field);
		}
		return doc.build();
	}

	@Override
	public void index(final Profile profile) {
		final Document doc = doc("" + profile.getId(),
				atom("vanity", profile.getVanity()),
				atom("status", profile.getStatus().toString()),
				text("desc", profile.getDescription()));

		// Launch new thread to index document
		new Thread(new Runnable() {
			@Override
			public void run() {
				addToProfileIndex(doc);
			}
		}).start();
	}

	private Field text(final String name, final String value) {
		return Field.newBuilder().setName(name).setText(value).build();
	}

}
