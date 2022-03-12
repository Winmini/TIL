# @NotEmpty 는 String 변수에만 사용 가능



HV000030: No validator could be found for constraint 'javax.validation.constraints.NotEmpty' validating type...

라고 뜨며 트랜잭션이 롤백이 된다. NotEmpty라고 해놓고 분명 값을 넣었는데 이 부분에서 문제가 생긴다고 한다. validator가 없다는 뜻인거 같은데, 내가 문제를 일으킨 부분은 Name라는 객체 때문이였다.

```java
	@NotEmpty
	@Embedded
	private Name name;
```

Member객체에 firstName, lastName을 분리하여 넣었기 때문에 위와 같은 객체를 만들어 놓은게 문제다. Empty는 String변수에만 사용이 가능하며, 나머지변수에 사용하고 싶으면 NotNull을 쓰면 되고, 다음과 같이 고치면 된다.

```java
	@NotNull
	@Embedded
	private Name name;
```



