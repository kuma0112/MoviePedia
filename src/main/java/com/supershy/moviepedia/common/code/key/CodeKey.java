package com.supershy.moviepedia.common.code.key;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
@NoArgsConstructor
public class CodeKey implements Serializable {

	private String commonCodeId;
	private String commonCode;

	public CodeKey(String commonCodeId, String commonCode) {
		this.commonCodeId = commonCodeId;
		this.commonCode = commonCode;
	}
}

