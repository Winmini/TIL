package study.datajpa.entity;

import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Team {

	@Id
	@GeneratedValue
	@Column(name = "team_id")
	private Long id;
	private String name;

	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();

	public Team(String name) {
		this.name = name;
	}
}
