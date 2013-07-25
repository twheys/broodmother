package com.heys.dating.impl.app;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.heys.dating.deeplink.Deeplink;
import com.heys.dating.deeplink.DeeplinkAction;
import com.heys.dating.deeplink.DeeplinkRepository;
import com.heys.dating.deeplink.DeeplinkService;
import com.heys.dating.deeplink.InvalidDeeplinkException;
import com.heys.dating.member.Member;
import com.heys.dating.util.security.TwoWayHash;

@Service("DeeplinkService")
@Slf4j
public class DeeplinkServiceImpl implements DeeplinkService {

	private final TwoWayHash deeplinkHash = new TwoWayHash(
			"Or>55fH-:s,HZ[xRUXu1x0oEö7QqpsIxb9,Ku<M(X87Wkü+zNi[7GwU!*qBI-bLDloNFT0EDMu(N$vNIp6,ucyFCnv;o37UQDu[Yk)LN9lEY?}aVEUNa7,P)6lü66a]R");

	@Autowired
	private DeeplinkRepository deeplinkRepository;

	private Deeplink createDeeplink(final Member member,
			final DeeplinkAction action, final Date expiration) {
		final Deeplink deeplink = new Deeplink(action, expiration,
				Ref.create(member));
		return deeplinkRepository.save(deeplink);
	}

	@Override
	public String generate(final Member member, final DeeplinkAction action,
			final Date expiration) {
		final Deeplink deeplink = createDeeplink(member, action, expiration);
		return deeplinkHash.encryptBase64("" + deeplink.getId());
	}

	private boolean isExpired(final Deeplink deeplink) {
		final DateTime expiration = new DateTime(deeplink.getExpiration());
		return DateTime.now().isAfter(expiration);
	}

	@Override
	public Deeplink parse(final String deeplinkValue)
			throws InvalidDeeplinkException {
		try {
			final String decryptedId = deeplinkHash
					.decryptBase64(deeplinkValue);
			final long id = Long.valueOf(decryptedId);
			final Key<Deeplink> key = Key.create(Deeplink.class, id);
			final Deeplink deeplink = deeplinkRepository.findOne(key);

			if (null == deeplink) {
				log.info("Decrypted deeplink does not match a result.");
				throw new InvalidDeeplinkException(deeplinkValue);
			}

			if (isExpired(deeplink)) {
				log.info("Deeplink is no longer valid.");
				deeplinkRepository.delete(deeplink);
				throw new InvalidDeeplinkException(deeplinkValue);
			}

			log.info("Redeeming deeplink.");
			deeplinkRepository.delete(deeplink);
			return deeplink;
		} catch (final IllegalStateException e) {
			log.info("Deeplink does not decyrpt.", e);
		} catch (final NumberFormatException e) {
			log.info("Deeplink does not format as a number.", e);
		}
		throw new InvalidDeeplinkException(deeplinkValue);
	}
}
