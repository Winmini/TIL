package com.ecommerce.computer.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Builder
@ToString
public class Item {
	@Id
	private String id;

	private String name;
	private String description;
	private double price;
	private LocalDate releaseDate;

	@CreatedDate
	private final LocalDateTime createdTime;

	@LastModifiedDate
	private final LocalDateTime updatedTime;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Item item = (Item) o;
		return id.equals(item.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
