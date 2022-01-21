# Singleton



디자인 패턴중 싱글턴이라는 패턴이 있다. 무거운 객체일 수록 생성하는 데 많은 비용을 치뤄야하므로 굳이 무거운 객체를 계속 생성하여 쓰는 것보다, 쓰던 객체를 계속 돌려쓰는 것이 시간적, 공간적 비용을 해결해준다. 싱글턴 패턴엔 많은 방법이 있겠지만 enum을 이용한 패턴을 이용하고자 한다.

enum을 이용하면 다소 어색해보일지 몰라도, private생성자를 이용하는 것 보다 안전하다. private생성자로 만들고 getInstance로 하는 방법은 권한이 있는 클라리언트가 리플렉션 api를 이용해 private 생성자를 호출할 수도 있다. private 생성자를 실행시킬 때 두번째 객체라면 오류를 내는 방법을 추가해줘야 한다. 또한 직렬화된 인스턴스를 역질렬화할 때마다 새로운 인스턴스가 만들어진다. 이를 해결하기 위해 또 다른 메서드를 만들어야 한다.

하지만 열거 타입은 훨씬 간결하고 추가 노력없이 직렬화가 가능하며 복잡한 공격도 완벽하게 막아준다. 그래서 상속을 사용하지 않아도 된다면 정말 좋은 방법이라 할 수 있다.

이를 이용해 가볍지만 가볍지 않은 실습예제 코드를 구현해보았다.



문제의 상황은 다음과 같다.

아이템클래스와 이 아이템 클래스를 상속받는 수주 아이템 클래스와 발주 아이템 클래스, 그리고 이 아이템들을 관리하는 매니저 클래스로 이뤄져 있다.



### 구현기능

1. 먼저 안내한다.

```java
[1] 품목등록
[2] 발주등록
[3] 수주등록
[4] 품목목록
[5] 발주목록
[6] 수주목록
[0] 종료하기
```

1번을 눌러 품목을 등록하면

```java
품목명을 입력하세요 : 로사
품목설명을 입력하세요 : 빨간색
```

위와 같이 입력하면 아이템은 등록되며 1로 돌아간다.

4번을 누르면 다음과 같이 볼 수 있다.

```java
======== 품목목록 ========
품목번호		품목이름		품목설명
1		로사		빨간색
```

그리고 3번으로 수주등록도 해보자.

```java
품목번호를 선택하세요 :1
수주거래처를 입력하세요 : 코카콜라
수주거래일을 입력하세요(예: 2021-05-05) : 2021-12-12
수주수량을 입력하세요 : 100
수주금액을 입력하세요 : 2000
```

입력한 후 6번으로 수주목록을 보면

```java
======== 수주목록 ========
품목번호		품목명		거래일		거래처		수주수량		수주금액		총수주금액(판매)
1		로사		2021-12-12		코카콜라		100		2000		200000
```

이렇게 목록을 볼 수 있다.

0을 눌러 종료한다.

다른 입력에 대한 에러들은 잡지 않았으며, 클린코드의 원칙은 지키지 않았다. 사실 이렇게 예제를 열심히 구현할 생각도 없었지만, 짜다보니 욕심이 생겨 클래스도 생각보다 많이 만들어서 분할하게 되었다. 클린코드를 생각하면 한 메서드는 한 가지일의 원칙이 지켜져야 하지만, 프로젝트를 짜는 것이 아닌 그저 싱글턴 연습을 위해 짠 코드이기에 생략했다.

다음은 그 예제 코드 내용이다.



**IoItem클래스**

```java
public class IoItem {
	private final int itemNumber;
	private final String itemName;
	private final String itemManual;

	public static class Builder {
		private final int itemNumber;
		private final String itemName;
		private final String itemManual;

		public Builder(int itemNumber, String itemName, String itemManual) {
			this.itemNumber = itemNumber;
			this.itemName = itemName;
			this.itemManual = itemManual;
		}
		public Builder(IoItem ioItem) {
			this.itemNumber = ioItem.itemNumber;
			this.itemName = ioItem.itemName;
			this.itemManual = ioItem.itemManual;
		}

		public IoItem build() {
			return new IoItem(this);
		}
	}

	IoItem(Builder builder) {
		itemNumber = builder.itemNumber;
		itemName = builder.itemName;
		itemManual = builder.itemManual;
	}

	public boolean searchByNumber(int itemNumber) {
		return this.itemNumber == itemNumber;
	}

	@Override
	public String toString() {
		return itemNumber + "\t" + itemName + "\t" + itemManual;
	}

	String getItemInfo() {
		return itemNumber + "\t" + itemName + "\t";
	}
}
```

**IoIncome 클래스**

```java
public class IoIncome extends IoItem {

	private final String incomeTrader;
	private final String incomeDate;
	private final int numberOfOrders;
	private final int price;
	private final int totalPrice;

	public static class Builder extends IoItem.Builder {
		private String incomeTrader = "없음";
		private String incomeDate = "없음";
		private int numberOfOrders = 0;
		private int price = 0;

		public Builder(int itemNumber, String itemName, String itemManual) {
			super(itemNumber, itemName, itemManual);
		}

		public Builder(IoItem ioItem) {
			super(ioItem);
		}

		public Builder buy(int numberOfOrders) {
			this.numberOfOrders = numberOfOrders;
			return this;
		}

		public Builder in(String incomeDate) {
			this.incomeDate = incomeDate;
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
	}

	private IoIncome(Builder builder) {
		super(builder);
		incomeTrader = builder.incomeTrader;
		incomeDate = builder.incomeDate;
		numberOfOrders = builder.numberOfOrders;
		price = builder.price;
		totalPrice = numberOfOrders * price;
	}

	@Override
	public String toString() {
		return super.getItemInfo() + this.incomeDate + "\t" +
			this.incomeTrader + "\t" + this.numberOfOrders + "\t" + this.price + "\t" + this.totalPrice;
	}
}

```

**IoOutcome클래스**

```java

public class IoOutcome extends IoItem {
	private final String outcomeTrader;
	private final String outcomeDate;
	private final int numberOfOrders;
	private final int price;
	private final int totalPrice;

	public static class Builder extends IoItem.Builder {
		private String outcomeTrader = "없음";
		private String outcomeDate = "없음";
		private int numberOfOrders = 0;
		private int price = 0;

		public Builder(int itemNumber, String itemName, String itemManual) {
			super(itemNumber, itemName, itemManual);
		}

		public Builder(IoItem ioItem) {
			super(ioItem);
		}

		public Builder sell(int numberOfOrders) {
			this.numberOfOrders = numberOfOrders;
			return this;
		}

		public Builder in(String outcomeDate) {
			this.outcomeDate = outcomeDate;
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
	}

	private IoOutcome(Builder builder) {
		super(builder);
		outcomeTrader = builder.outcomeTrader;
		outcomeDate = builder.outcomeDate;
		numberOfOrders = builder.numberOfOrders;
		price = builder.price;
		totalPrice = numberOfOrders * price;
	}

	@Override
	public String toString() {
		return super.getItemInfo() + this.outcomeDate + "\t" +
			this.outcomeTrader + "\t" + this.numberOfOrders + "\t" + this.price + "\t" + this.totalPrice;
	}
}


```

**IoMgr클래스**

```java
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public enum IoMgr {
	INSTANCE;

	private final Scanner sc = new Scanner(System.in);
	private final List<IoItem> ioItems = new LinkedList<>();
	private final List<IoIncome> ioIncomes = new LinkedList<>();
	private final List<IoOutcome> ioOutcomes = new LinkedList<>();
	private int number = 1;

	public void addItem() {
		System.out.print("품목명을 입력하세요 : ");
		String itemName = sc.nextLine();
		System.out.print("품목설명을 입력하세요 : ");
		String itemManual = sc.nextLine();
		System.out.println();
		ioItems.add(new IoItem.Builder(number++, itemName, itemManual).build());
	}

	public void addIncome() {
		System.out.print("품목번호를 선택하세요 :");
		int itemNumber = Integer.parseInt(sc.nextLine());
		System.out.print("발주거래처를 입력하세요 : ");
		String incomeTrader = sc.nextLine();
		System.out.print("발주거래일을 입력하세요(예: 2021-05-05) : ");
		String incomeDate = sc.nextLine();
		System.out.print("발주수량을 입력하세요 : ");
		int numberOfOrders = Integer.parseInt(sc.nextLine());
		System.out.print("발주금액을 입력하세요 : ");
		int price = Integer.parseInt(sc.nextLine());
		System.out.println();

		IoItem item = ioItems.stream().filter((it) -> it.searchByNumber(itemNumber)).findAny()
			.orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 품목 번호 없음"));

		IoIncome ioIncome = new IoIncome.Builder(item).buy(numberOfOrders)
			.at(price).from(incomeTrader).in(incomeDate).build();

		ioIncomes.add(ioIncome);
	}

	public void addOutcome() {
		System.out.print("품목번호를 선택하세요 :");
		int itemNumber = Integer.parseInt(sc.nextLine());
		System.out.print("수주거래처를 입력하세요 : ");
		String outcomeTrader = sc.nextLine();
		System.out.print("수주거래일을 입력하세요(예: 2021-05-05) : ");
		String outcomeDate = sc.nextLine();
		System.out.print("수주수량을 입력하세요 : ");
		int numberOfOrders = Integer.parseInt(sc.nextLine());
		System.out.print("수주금액을 입력하세요 : ");
		int price = Integer.parseInt(sc.nextLine());
		System.out.println();

		IoItem item = ioItems.stream().filter((it) -> it.searchByNumber(itemNumber)).findAny()
			.orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 품목 번호 없음"));

		IoOutcome ioOutcome = new IoOutcome.Builder(item).sell(numberOfOrders)
			.at(price).to(outcomeTrader).in(outcomeDate).build();

		ioOutcomes.add(ioOutcome);
	}

	public void printItemList() {
		System.out.println("======== 품목목록 ========");
		System.out.println("품목번호" + "\t" + "품목이름" + "\t" + "품목설명");
		ioItems.forEach(System.out::println);
		System.out.println();
	}

	public void printIncomeList() {
		System.out.println("======== 발주목록 ========");
		System.out.println("품목번호" + "\t" + "품목명" + "" + "거래일" + "\t" + "거래처" +
			"\t" + "발주수량" + "\t" + "발주금액" + "\t" + "총발주금액(구매)");
		ioIncomes.forEach(System.out::println);
		System.out.println();
	}

	public void printOutcomeList() {
		System.out.println("======== 수주목록 ========");
		System.out.println("품목번호" + "\t" + "품목명" + "\t" + "거래일" + "\t" + "거래처" +
			"\t" + "수주수량" + "\t" + "수주금액" + "\t" + "총수주금액(판매)");
		ioOutcomes.forEach(System.out::println);
		System.out.println();
	}
}
```

**IoMgrController 클래스** 

```java
import java.util.Arrays;
import java.util.Scanner;

import com.company.Button;

public enum IoMgrController implements Button {
	REGISTER_ITEM("[1] 품목등록", 1) {
		public void enter() {
			ioMgr.addItem();
			run();
		}
	},
	REGISTER_INCOME("[2] 발주등록", 2) {
		public void enter() {
			ioMgr.addIncome();
			run();
		}
	},
	REGISTER_OUTCOME("[3] 수주등록", 3) {
		public void enter() {
			ioMgr.addOutcome();
			run();
		}
	},
	ITEM_LIST("[4] 품목목록", 4) {
		public void enter() {
			ioMgr.printItemList();
			run();
		}
	},
	INCOME_LIST("[5] 발주목록", 5) {
		public void enter() {
			ioMgr.printIncomeList();
			run();
		}
	},
	OUTCOME_LIST("[6] 수주목록", 6) {
		public void enter() {
			ioMgr.printOutcomeList();
			run();
		}
	},
	QUIT("[0] 종료하기", 0) {
		public void enter() {

		}
	};

	private final String guideMessage;
	private final int number;
	private static final IoMgr ioMgr = IoMgr.INSTANCE;
	private static final Scanner sc = new Scanner(System.in);

	IoMgrController(String guideMessage, int number) {
		this.guideMessage = guideMessage;
		this.number = number;
	}

	public static void run() {
		printGuide();
		int number = Integer.parseInt(sc.nextLine());
		Arrays.stream(IoMgrController.values())
			.filter(order -> order.isNumber(number))
			.findAny()
			.orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 번호는 없습니다."))
			.enter();
	}

	public static void printGuide() {
		Arrays.stream(IoMgrController.values()).map(IoMgrController::getGuideMessage).forEach(System.out::println);
		System.out.println();
	}

	private String getGuideMessage() {
		return guideMessage;
	}

	private boolean isNumber(int number) {
		return this.number == number;
	}

	@Override
	public String toString() {
		return guideMessage;
	}

}
```

이를 실행하는 **main클래스**

```java
public class Main {
	public static void main(String[] args) {
		IoMgrController.run();
	}
}
```

리모콘을 실행하면 끝이다.