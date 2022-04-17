package hello.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.jar.JarEntry;

import javax.sql.DataSource;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
	Connection con = dataSource.getConnection();
	try{
		con.setAutoCommit(false); // 트랜잭션 시작
		//비즈니스 로직 수행
		Member fromMember = memberRepository.findById(con, fromId);
		Member toMember = memberRepository.findById(con, toId);

		memberRepository.update(con, fromId, fromMember.getMoney() - money);
		validate(toMember);
		memberRepository.update(con, toId, toMember.getMoney() + money);

		con.commit();
	}catch (Exception e){
		con.rollback();
		throw new IllegalStateException(e);
	}finally {
		if(con != null){
			try{
				con.setAutoCommit(true); // 커넥션 풀을 고려
				con.close();
			}catch (Exception e){
				log.error("error", e);
			}
		}
	}
}

	private final MemberRepositoryV2 memberRepository;
	private final DataSource dataSource;



	private void validate(Member toMember) {
		if(toMember.getMemberId().equals("ex")){
			throw new IllegalStateException("이체중 예외 발생");
		}
	}
}
