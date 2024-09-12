package com.supershy.moviepedia.member.repository;

import com.supershy.moviepedia.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
