package com.heys.dating.util;

import static org.apache.commons.lang.StringUtils.trim;
import static org.apache.commons.lang.StringUtils.upperCase;

import java.util.List;

import com.google.appengine.labs.repackaged.com.google.common.base.Function;
import com.google.appengine.labs.repackaged.com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

public class DatastoreUtil {
	public static String c(final String value) {
		return trim(upperCase(value));
	}

	public static <TType> Iterable<Key<TType>> keys(final Iterable<TType> list) {
		return Iterables.transform(list, new Function<TType, Key<TType>>() {
			@Override
			public Key<TType> apply(final TType value) {
				return Key.create(value);
			}
		});
	}

	public static <TType> Iterable<Ref<TType>> refs(final Iterable<TType> list) {
		return Iterables.transform(list, new Function<TType, Ref<TType>>() {
			@Override
			public Ref<TType> apply(final TType value) {
				return Ref.create(value);
			}
		});
	}

	public static <TType> Iterable<TType> values(final List<Ref<TType>> list) {
		return Iterables.transform(list, new Function<Ref<TType>, TType>() {
			@Override
			public TType apply(final Ref<TType> member) {
				return member.get();
			}
		});
	}
}
