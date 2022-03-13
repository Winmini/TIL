package hello.itemservice.web.basic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

	private final ItemRepository itemRepository;

	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "basic/items";
	}

	@GetMapping("/{itemId}")
	public String item(@PathVariable long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}

	@GetMapping("/add")
	public String addForm() {
		return "basic/addForm";
	}

//	@PostMapping("/add")
	public String addItemV1(@RequestParam String itemName,
							@RequestParam int price,
							@RequestParam Integer quantity,
							Model model) {

		Item item = new Item(itemName, price, quantity);
		itemRepository.save(item);

		model.addAttribute("item", item);

		return "basic/item";
	}

	// @PostMapping("/add")
	public String addItemV2(@ModelAttribute Item item) {
		//@ModelAttribute("item") 이렇게 이름을 지정해주지 않으면 받는 클래스 Item의 첫글자만 소문자로 바꿔서 수행된다.
		itemRepository.save(item);
//		model.addAttribute("item", item); 생략가능 자동 추가
		return "basic/item";
	}

	@PostMapping("/add")
	public String addItemV3(Item item) {
		//@ModelAttribute 를 지정안해도 우리가 만든 객체는 알아서 지정됨
		itemRepository.save(item);
		return "basic/item";
	}

	/**
	 * 테스트용 데이터 추가
	 */
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
}
