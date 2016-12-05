package hu.uni.miskolc.webtech.librarian.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hu.uni.miskolc.webtech.librarian.controller.dto.BookAssembler;
import hu.uni.miskolc.webtech.librarian.controller.dto.BookDTO;
import hu.uni.miskolc.webtech.librarian.model.Author;
import hu.uni.miskolc.webtech.librarian.model.Book;
import hu.uni.miskolc.webtech.librarian.persist.AuthorNotFoundException;
import hu.uni.miskolc.webtech.librarian.service.BookManagerService;



@Controller
public class IndexController {
	
	private static final Logger LOG = LogManager.getLogger();
	
	@Autowired
	private BookManagerService manager;
	
	@RequestMapping(value={"", "/", "/index"})
	public ModelAndView indexPage(){
		ModelAndView result = new ModelAndView("index");
		return result;
	}
	
	@RequestMapping("/home")
	public ModelAndView homePage() {
		ModelAndView result = new ModelAndView("home");
		return result;
	}
	
	@RequestMapping("/about")
	public ModelAndView aboutPage() {
		ModelAndView result = new ModelAndView("about");
		return result;
	}

	@RequestMapping("/hello")
	public @ResponseBody String sayHello(){
		return "Hello World";
	}
	
	@RequestMapping("/listauthors")
	public ModelAndView listOfAuthors() {
		return new ModelAndView("authorList");
	}

	@RequestMapping(value={"/newauthor", "/updateauthor"})
	public ModelAndView modifyAuthor() {
		ModelAndView result = new ModelAndView("authorForm");
		return result;
	}
	
	@RequestMapping("/listbooks")
	public ModelAndView listOfBooks() {
		return new ModelAndView("bookList");
	}
	
	@RequestMapping(value={"/newbook", "/updatebook"})
	public ModelAndView modifyBook() {
		ModelAndView result = new ModelAndView("bookForm");
		return result;
	}
	
	@RequestMapping(value={"/book/insert"}, method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public void insertBook(@RequestBody BookDTO book) {
		try {
			manager.addBook(BookAssembler.assembleAuthor(book));
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
	}
	
	@RequestMapping(value={"/book/update"}, method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public void updateBook(@RequestBody BookDTO book) {
		try {
			manager.updateBook(BookAssembler.assembleAuthor(book));
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
	}
	
	@RequestMapping("/books/{id}")
	public @ResponseBody Collection<Book> listBooksOfAuthor(@PathVariable Integer id) throws AuthorNotFoundException {
		/*List<Author> authors = manager.queryAuthors().stream().filter(a -> a.getAuthorID() == id).collect(Collectors.toList());
		if (authors.size() == 1)
			return manager.queryBooks(authors.get(0));*/
		for (Author author : manager.queryAuthors()) {
			if (author.getAuthorID() == id)
				return manager.queryBooks(author);
		}
		throw new AuthorNotFoundException();
	}
	
}
