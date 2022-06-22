package hello.aop.exam;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ExamRepository {

	private static int seq = 0;

	public void save(String itemId) {
		seq++;

		if(seq % 5 == 0) {
			throw new IllegalStateException("예외");
		}
	}
}
