package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

	private Long id;
	private String itemName;
	private Integer price; // price가 안들어갈 수도 있어서 Integer, 가격이 0인건 이상하니까.
	private Integer quantity;

	public Item(){
	}

	public Item(String itemName, Integer price, Integer quantity) {
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
}
