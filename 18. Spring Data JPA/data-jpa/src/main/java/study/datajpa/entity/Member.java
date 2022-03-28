package study.datajpa.entity;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String username;
	private int age;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	public Member(String username, int age, Team team) {
		this.username = username;
		this.age = age;
		if (team != null) {
			changeTeam(team);
		}
	}

	public Member(String username) {
		this.username = username;
	}

	public void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}
}
