package com.supershy.moviepedia.common.code;

import com.supershy.moviepedia.common.code.key.CodeKey;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "common_code")
@Getter
@EqualsAndHashCode(of = "codeKey")
@ToString
public class CommonCode {

	@EmbeddedId
	private CodeKey codeKey;

	private String codeName;

	private String codeDescription;

	@Column(name = "use_yn", nullable = false)
	private String useYn = "Y";

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "parent_code_id", referencedColumnName = "commonCodeId"),
			@JoinColumn(name = "parent_code", referencedColumnName = "commonCode")
	})
	private CommonCode parentCommonCode;

	@OneToMany(mappedBy = "parentCommonCode")
	private List<CommonCode> childCommonCodes;

	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column( nullable = false)
	private LocalDateTime modifiedAt = LocalDateTime.now();

	public void updateCodeKdy(CodeKey codeKey) {
		this.codeKey = codeKey;
	}

}
