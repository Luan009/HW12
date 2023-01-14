package de.uni.koeln.se.bookstore.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.uni.koeln.se.bookstore.datamodel.Book;
import de.uni.koeln.se.bookstore.service.BookService;



@RequestMapping("/bookStore")
@RestController
public class BookController {
	
	
	
	@Autowired
	BookService bookSer;
	
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllbooks() {
		
		List<Book> books = new ArrayList<Book> ();
		books=bookSer.findBooks();
		return new ResponseEntity<>(books,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/oldestBook")
	public ResponseEntity<Book> getOldestBook(String text) {
		
		List<Book> books = new ArrayList<Book>();
		books = bookSer.findBooks();
				
		int oldestBook = books.get(0).getDateYear();
		int oldestBookId = books.get(0).getId();
		for(int i = 1; i < books.size(); i++) {
			if(books.get(i).getDateYear() < oldestBook) {
				oldestBook = books.get(i).getDateYear();
				oldestBookId = books.get(i).getId();
			}
		}
				
		return new ResponseEntity<>(bookSer.fetchBook(oldestBookId).get(), HttpStatus.OK);

		
		
	}
	
	
	@GetMapping("/mostRecentBook")
	public ResponseEntity<Book> getMostRecentBook(String text) {
		

		List<Book> books = new ArrayList<Book>();
		books = bookSer.findBooks();
				
		int recentBook = books.get(0).getDateYear();
		int recentBookId = books.get(0).getId();
		for(int i = 1; i < books.size(); i++) {
			if(books.get(i).getDateYear() > recentBook) {
				recentBook = books.get(i).getDateYear();
				recentBookId = books.get(i).getId();
			}
		}
				
		return new ResponseEntity<>(bookSer.fetchBook(recentBookId).get(), HttpStatus.OK);

		
		
		
	}
	
	
	
	
	
	@PostMapping
	public ResponseEntity<Book> addBookt(@RequestBody Book book) {
		
		bookSer.addBook(book);
		
		return new ResponseEntity<>(book, HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Book> removeBookById(@PathVariable int id) {
		
		
		Book book=bookSer.fetchBook(id).get();
		
		if(bookSer.deleteBook(id)) {
			return new ResponseEntity<>(book,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(book,HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
	

}
