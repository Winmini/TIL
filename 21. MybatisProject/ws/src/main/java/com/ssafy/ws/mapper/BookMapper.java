package com.ssafy.ws.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.ws.domain.Book;

@Mapper
@Repository
public interface BookMapper {

	Optional<Book> findById(int id);

	int save(Book book);

	List<Book> findAll();
}
