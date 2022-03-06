package hello.servlet.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemberRepositoryTest {

	MemberRepository memberRepository = MemberRepository.getInstance();

	@AfterEach
	void afterEach() {
		memberRepository.clearStore();
	}

	@Test
	void getInstance() {
	}

	@Test
	void save() {
		Member member = new Member("hello", 20);

		Member saveMember = memberRepository.save(member);

		Member findMember = memberRepository.findById(saveMember.getId());

		assertThat(findMember).isEqualTo(saveMember);
	}

	@Test
	void findById() {
	}

	@Test
	void findAll() {
	}

	@Test
	void clearStore() {
	}
}