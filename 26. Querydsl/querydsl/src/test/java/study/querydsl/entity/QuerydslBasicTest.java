package study.querydsl.entity;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.*;
import static study.querydsl.domain.QMember.*;
import static study.querydsl.domain.QTeam.*;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import study.querydsl.domain.Member;
import study.querydsl.domain.QMember;
import study.querydsl.domain.Team;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

	@PersistenceContext
	EntityManager em;
	JPAQueryFactory queryFactory;

	@BeforeEach
	public void before() {
		queryFactory = new JPAQueryFactory(em);

		Team teamA = Team.builder().name("teamA").build();
		Team teamB = Team.builder().name("teamB").build();
		em.persist(teamA);
		em.persist(teamB);

		Member member1 = Member.builder().username("member1").age(10).team(teamA).build();
		Member member2 = Member.builder().username("member2").age(20).team(teamA).build();
		Member member3 = Member.builder().username("member3").age(30).team(teamB).build();
		Member member4 = Member.builder().username("member4").age(40).team(teamB).build();
		Member member5 = Member.builder().age(100).team(teamA).build();
		Member member6 = Member.builder().username("member6").age(100).team(teamB).build();


		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
		em.persist(member5);
		em.persist(member6);
	}

	@Test
	void JpqlTest() {
		Member findByJPQL = em.createQuery("select m from Member m where m.username =: username", Member.class)
			.setParameter("username", "member1")
			.getSingleResult();

		assertThat(findByJPQL.getUsername()).isEqualTo("member1");
	}

	@Test
	void querydslTest() {
		Member findByQuerydsl = queryFactory
			.select(member)
			.from(member)
			.where(member.username.eq("member1"))
			.fetchOne();

		assert findByQuerydsl != null;
		assertThat(findByQuerydsl.getUsername()).isEqualTo("member1");
	}

	@Test
	void search(){
		Member findMember = queryFactory
			.selectFrom(member)
			.where(member.username.eq("member1").and(member.age.eq(10)))
			.fetchOne();

		assert findMember != null;
		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	void search2(){
		Member findMember = queryFactory
			.selectFrom(member)
			.where(
				member.username.eq("member1"),
				member.age.eq(10)
			)
			.fetchOne();

		assert findMember != null;
		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	void resultFetch(){
		List<Member> fetch = queryFactory
			.selectFrom(member)
			.fetch();
	}

	@Test
	void sortTest(){
		List<Member> members = queryFactory
			.selectFrom(member)
			.orderBy(member.username.asc().nullsLast(), member.age.desc())
			.fetch();

		members.forEach(System.out::println);
	}

	@Test
	void pagingTest(){
		List<Member> members = queryFactory
			.selectFrom(member)
			.orderBy(member.username.desc())
			.offset(1)
			.limit(2)
			.fetch();
	}

	@Test
	void aggregation() {
		Tuple tuple = queryFactory
			.select(member.count(), member.age.sum(), member.age.max())
			.from(member)
			.fetchOne();

		assert tuple != null;
		System.out.println(tuple.get(member.count()));
	}

	@Test
	void groupTest() {
		List<Tuple> result = queryFactory
			.select(team, member.age.avg())
			.from(member)
			.join(member.team, team)
			.groupBy(team.name)
			.fetch();

		Tuple teamA = result.get(0);
		Tuple teamB = result.get(1);

		assertThat(Objects.requireNonNull(teamA.get(team.name))).isEqualTo("teamA");
		System.out.println(teamA.get(member.age.avg()));
		assertThat(Objects.requireNonNull(teamB.get(team.name))).isEqualTo("teamB");
		System.out.println(teamB.get(member.age.avg()));
	}

	@Test
	void joinTest(){
		List<Member> results = queryFactory
			.selectFrom(member)
			.join(member.team, team)
			.where(team.name.eq("teamA"))
			.fetch();

		assertThat(results)
			.extracting("username")
			.containsExactly("member1", "member2", null);
	}

	/**
	 * 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
	 */
	@Test
	void onTest() {
		List<Tuple> teamA = queryFactory
			.select(member, team)
			.from(member)
			.join(member.team, team)
			.on(team.name.eq("teamA"))
			.fetch();

		for (Tuple tuple : teamA) {
			System.out.println("tuple = " + tuple);
		}
	}

	@Test
	void noRelationTest() {
		List<Tuple> result = queryFactory
			.select(member, team)
			.from(member)
			.leftJoin(team).on(member.username.eq(team.name))
			.fetch();
	}

	@Test
	void fetchJoinTest() {
		Member member = queryFactory.selectFrom(QMember.member)
			.join(QMember.member.team, team)
			.fetchJoin()
			.where(QMember.member.username.eq("member1"))
			.fetchOne();
	}

	/**
	 * 나이가 가장 많은 회원 조회
	 */
	@Test
	void subQueryTest() {
		QMember memberSub = new QMember("memberSub");

		List<Member> fetch = queryFactory
			.selectFrom(member)
			.where(member.age.eq(
				select(memberSub.age.max())
				.from(memberSub))
			)
			.fetch();

		assertThat(fetch).extracting("age")
			.contains(100);
	}

	@Test
	void caseTest() {
		List<String> fetch = queryFactory
			.select(member.age
				.when(10).then("열살")
				.when(20).then("스무살")
				.otherwise("기타"))
			.from(member)
			.fetch();
	}

	@Test
	void concatTest(){
		List<String> fetch = queryFactory
			.select(member.username.concat("_").concat(member.age.stringValue()))
			.from(member)
			.fetch();
	}

}
