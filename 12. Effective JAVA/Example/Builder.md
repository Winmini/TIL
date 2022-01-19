# Builder



### 빌더의 기본

책정보를 저장할 클래스를 생성한다.

여기서 책의 속성은 책번호, 책제목, 작가, 출판사, 가격, 비고 이렇게 주어진다고 하자. 이런 클래스를 만들면 책클래스를 만들 때, 매개변수가 너무 많아진다. 매개변수가 많은 것은 보기 좋지 않고 실수할 여력이 늘어나므로 빌더 패턴을 이용하여 설계하는 것이 좋다.

```java
public class Book {
	private final int bookNumber;
	private final String title;
	private final String author;
	private final String publisher;
	private final int price;
	private final String disc;

	public static class Builder {
      // 반드시 필요한 부분
		private final String title;
		private final String author;
		private final int price;
      
      // 선택할 부분
		private int bookNumber = 0;
		private String publisher = "없음";
		private String disc = "없음";

		public Builder(String title, String author, int price) {
			this.title = title;
			this.author = author;
			this.price = price;
		}

		public Builder bookNumber(int bookNumber) {
			this.bookNumber = bookNumber;
			return this;
		}

		public Builder publisher(String publisher) {
			this.publisher = publisher;
			return this;
		}

		public Builder disc(String disc) {
			this.disc = disc;
			return this;
		}

		public Book build() {
			return new Book(this);
		}
	}

	private Book(Builder builder) {
		bookNumber = builder.bookNumber;
		title = builder.title;
		author = builder.author;
		publisher = builder.publisher;
		price = builder.price;
		disc = builder.disc;
	}

	@Override
	public String toString() {
		return this.bookNumber + "\t |" + title + "\t |" + author +
			"\t |" + publisher + "\t |" + price + "\t |" + disc;
	}
}
```

toString은 무조건 하는 것이 좋기도 한데다가, 여기서는 특히 더 쓸일이 많다. 그래서 출력한다.

main에서 이를 이용하여 결과를 보자.

```java
import java.util.LinkedList;

public class Main {
	public static void main(String[] args) {
		LinkedList<Book> books = new LinkedList<>();
		Book javaBook = new Book.Builder("Java Pro", "김하나", 15000)
			.bookNumber(21424).disc("Java 기본 문법").publisher("jaen.kr").build();
		Book designBook = new Book.Builder("분석설계", "소나무", 30000)
			.bookNumber(35355).disc("SW 모델링").publisher("jaen.kr").build();

		books.add(javaBook);
		books.add(designBook);
		System.out.println("******************도서목록********************");
		books.forEach(System.out::println);
	}
}
```

Book 클래스를 생성할 때, 실수할 여지도 줄어들었으며 가독성또한 매우 향상된 것을 볼 수 있다.

이렇게 하면 결과는 다음과 같다.

```java
******************도서목록********************
21424	 |Java Pro	 |김하나	 |jaen.kr	 |15000	 |Java 기본 문법
35355	 |분석설계	 |소나무	 |jaen.kr	 |30000	 |SW 모델링
```



---

### 빌더와 계측적으로 설계된 클래스

품목을 관리하기 위해 품목번호, 품목명, 품목 설명을 속성으로 하는 클래스를 만들 것이다. 3개는 빌더하기 애매하지 않나? 라고 할 수 있지만 이미 3개인데 언제 추가될지도 모르기 때문에 빌더로 하는 것도 좋다. 그리고 약간 연습을 위한 예제기는 하지만 이 품목을 발주, 수주하는 클래스도 만든다고 해보자.

이 클래스들은 거래처, 거래일, 수량, 금액, 거래처를 추가로 매개변수로 받는다 하자. 그럼 정말 매개변수만 너무나 많아졌다. 빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기 좋다. 

아무튼 다음과 같이 구현할 수 있다.



부모 클래스, 품목 클래스

```java
public abstract class IoItem {
	private final int itemNumber;
	protected final String itemName;
	private final String itemManual;

	abstract static class Builder<T extends Builder<T>>{
		private final int itemNumber;
		private final String itemName;
		private final String itemManual;

		public Builder(int itemNumber, String itemName, String itemManual) {
			this.itemNumber = itemNumber;
			this.itemName = itemName;
			this.itemManual = itemManual;
		}

		abstract IoItem build();

		protected abstract T self();
	}

	IoItem(Builder<?> builder) {
		itemNumber = builder.itemNumber;
		itemName = builder.itemName;
		itemManual = builder.itemManual;
	}
}

```

자식 클래스, 수주 클래스

```java
public class IoOutcome extends IoItem {
	private final String outcomeTrader;
	private final String tradeDate;
	private final int numberOfOrders;
	private final int price;
	private final int totalPrice;

	public static class Builder extends IoItem.Builder<Builder> {
		private String outcomeTrader = "없음";
		private String tradeDate = "없음";
		private int numberOfOrders = 0;
		private int price = 0;

		public Builder(int itemNumber, String itemName, String itemManual) {
			super(itemNumber, itemName, itemManual);
		}

		public Builder sell(int numberOfOrders) {
			this.numberOfOrders = numberOfOrders;
			return this;
		}

		public Builder in(String tradeDate) {
			this.tradeDate = tradeDate;
			return this;
		}

		public Builder to(String outcomeTrader) {
			this.outcomeTrader = outcomeTrader;
			return this;
		}

		public Builder at(int price) {
			this.price = price;
			return this;
		}

		@Override
		public IoOutcome build() {
			return new IoOutcome(this);
		}

		@Override
		protected Builder self() {
			return this;
		}
	}

	private IoOutcome(Builder builder) {
		super(builder);
		outcomeTrader = builder.outcomeTrader;
		tradeDate = builder.tradeDate;
		numberOfOrders = builder.numberOfOrders;
		price = builder.price;
		totalPrice = numberOfOrders * price;
	}

	@Override
	public String toString() {
		return "아이템: " + this.itemName + "\t |" + "개수: " + this.numberOfOrders + "\t |" +
			"가격: " + this.price + "\t |" + "거래일: " + this.tradeDate + "\t |" + "판매처: " + this.outcomeTrader +
			"\t |" + "총금액: " + totalPrice;
	}
}
```

자식클랫스, 발주 클래스

위와 비슷하다.

```JAVA
public class IoIncome extends IoItem {

	private final String incomeTrader;
	private final String tradeDate;
	private final int numberOfOrders;
	private final int price;
	private final int totalPrice;

	public static class Builder extends IoItem.Builder<Builder> {
		private String incomeTrader = "없음";
		private String tradeDate = "없음";
		private int numberOfOrders = 0;
		private int price = 0;

		public Builder(int itemNumber, String itemName, String itemManual) {
			super(itemNumber, itemName, itemManual);
		}

		public Builder buy(int numberOfOrders) {
			this.numberOfOrders = numberOfOrders;
			return this;
		}

		public Builder in(String tradeDate) {
			this.tradeDate = tradeDate;
			return this;
		}

		public Builder from(String incomeTrader) {
			this.incomeTrader = incomeTrader;
			return this;
		}

		public Builder at(int price) {
			this.price = price;
			return this;
		}

		@Override
		public IoIncome build() {
			return new IoIncome(this);
		}

		@Override
		protected Builder self() {
			return this;
		}
	}

	private IoIncome(Builder builder) {
		super(builder);
		incomeTrader = builder.incomeTrader;
		tradeDate = builder.tradeDate;
		numberOfOrders = builder.numberOfOrders;
		price = builder.price;
		totalPrice = numberOfOrders * price;
	}

	@Override
	public String toString() {
		return "아이템: " + this.itemName + "\t |" + "개수: " + this.numberOfOrders + "\t |" +
			"가격: " + this.price + "\t |" + "거래일: " + this.tradeDate + "\t |" + "구매처: " + this.incomeTrader +
			"\t |" + "총금액: " + totalPrice;
	}
}

```

main에서 확인을 해보자.

```java
public class Main {
	public static void main(String[] args) {
		IoIncome ioIncome = new IoIncome.Builder(100, "콜라", "마실 것")
			.buy(100).at(1100).from("Cocacola").in("2022").build();
		IoOutcome ioOutcome = new IoOutcome.Builder(200, "사이다", "마실 것")
			.sell(200).at(1300).to("sprite").in("2023").build();


		System.out.println("******************발주목록********************");
		System.out.println(ioIncome);
		System.out.println();
		System.out.println("******************수주목록********************");
		System.out.println(ioOutcome);

	}
}
```

실행 결과는 다음과 같다.

```java
******************발주목록********************
아이템: 콜라	 |개수: 100	 |가격: 1100	 |거래일: 2022	 |구매처: Cocacola	 |총금액: 110000

******************수주목록********************
아이템: 사이다	 |개수: 200	 |가격: 1300	 |거래일: 2023	 |판매처: sprite	 |총금액: 260000
```

