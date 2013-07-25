package com.heys.dating.profile;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.heys.dating.AbstractEntity;
import com.heys.dating.member.Gallery;
import com.heys.dating.member.Gender;
import com.heys.dating.member.Member;
import com.heys.dating.picture.Picture;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Profile extends AbstractEntity {
	private static final long serialVersionUID = 3370733434142254463L;

	@Parent
	@NotNull
	private Key<Member> owner;

	@Index
	@NotNull(message = "notnull")
	private String vanity;

	@NotNull(message = "notnull")
	private String country;

	private String description;

	private RelationshipStatus status;

	@Pattern(regexp = "^\\d+$", message = "pattern")
	@Length(min = 4, max = 6, message = "length 4 6")
	private String zipCode;

	private Gender gender;

	private List<Gender> partnerGenders = Lists.newArrayList();

	@Min(value = 18, message = "min 18")
	private Integer partnerAgeMin;

	@Max(value = 120, message = "min 120")
	private Integer partnerAgeMax;

	private boolean isOnline;

	private Ref<Picture> profilePicture;

	private List<Ref<Gallery>> galleries = Lists.newArrayList();

	public Profile(final Key<Member> owner, final String vanity,
			final Gender gender, final String country) {
		super();
		this.owner = owner;
		this.vanity = vanity;
		this.gender = gender;
		this.country = country;
	}
}
