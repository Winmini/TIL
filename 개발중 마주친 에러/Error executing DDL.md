# Error executing DDL



**DB 테이블 생성시, like라는 변수는 사용할 수 없다.**

게시판기능을 생성하다 보면 게시판에 좋아요. 라는 속성을 추가하고 싶다. 때문에 도메인에 Post라는 클래스를 만들고, 안에 변수값으로 like를 넣어두었다. 그리고 스프링을 돌리면, 이상하게 다른 엔티티들은 모두 테이블에 있는데 콘솔창에 다음과 같은 메시지만 남기고 Post를 생성하지 않는다. Post의 클래스는 다음과 같다.

```java
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post {

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	@Id
	@GeneratedValue
	@Column(name = "post_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@NotEmpty
	@Column(name = "post_title")
	private String title;

	@Column(name = "post_content")
	private String content;

	@Column(name = "post_like")
	private Boolean like;
}

```

여기서 문제가 된 부분은 like이다. 이를 변경하지 않고 실행시키면 다음과 같은 에러를 만난다.

```java
CommandAcceptanceException: Error executing DDL
```

구글링으로 알아본 결과, like는 데이터 베이스의 예약어이기 때문에 만들 수 없던 것이다.

하지만 위 상단에 like로 한정지은 것은, 가장 많이 실수할 부분이라 생각하여 작성해두었다. 예약어를 변수로 사용하는 것은 당연히 모두 안된다.