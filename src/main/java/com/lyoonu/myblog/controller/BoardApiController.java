package com.lyoonu.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyoonu.myblog.model.Board;
import com.lyoonu.myblog.repository.BoardRepository;

// https://spring.io/guides/tutorials/rest/
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
// Table 3. Supported keywords inside method names

@RestController
@RequestMapping("/api")
class BoardApiController {

	@Autowired
	BoardRepository repository;

	@GetMapping("/boards")
	List<Board> all(@RequestParam(required = false, defaultValue="") String title,
					@RequestParam(required = false, defaultValue="") String content) {
		
		if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
			return repository.findAll();
		}else {
			return repository.findByTitleOrContent(title, content);
		}
		
	}

	@PostMapping("/boards")
	Board newEmployee(@RequestBody Board newBoard) {
		return repository.save(newBoard);
	}

	// Single item

	@GetMapping("/boards/{id}")
	Board one(@PathVariable Long id) {
		return repository.findById(id).orElse(null);
	}

	// Modify
	@PutMapping("/boards/{id}")
	Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

		return repository.findById(id)
				.map(board -> {
			        board.setTitle(newBoard.getTitle());
			        board.setContent(newBoard.getContent());
			        return repository.save(board);
		})
				.orElseGet(() -> {
					newBoard.setId(id);
					return repository.save(newBoard);
		});
	}

	// Delete
	@DeleteMapping("/boards/{id}")
	void deleteBoard(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
