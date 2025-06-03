package com.mavrommatis.ebookshop.ebookshop;

import com.mavrommatis.ebookshop.ebookshop.dao.*;
import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class EbookshopApplication {

	private static final Logger logger = LoggerFactory.getLogger(EbookshopApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EbookshopApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(BookRepository bookRepository,
											   BookDetailsRepository bookDetailsRepository,
											   AuthorRepository authorRepository,
											   AuthorDetailsRepository authorDetailsRepository,
											   CustomerRepository customerRepository,
											   CustomerDetailsRepository customerDetailsRepository)  {

		return runner -> {

			logger.debug("Starting data creation...");

			//	Create detail entities
			AuthorDetails authorDetails = new AuthorDetails(
					"Stephen King is an American author of horror, supernatural fiction, " +
							"suspense, and fantasy novels.",
					LocalDate.of(1947, 9, 21),
					"https://www.stephenking.com/"
			);
			logger.debug("Created AuthorDetails: {}", authorDetails);


			BookDetails bookDetails = new BookDetails(
					new BigDecimal("0.75"),
					"Hardcover",
					"6 x 9 inches",
					"A horror novel about a shapeshifting evil in the town of Derry.",
					1138,
					LocalDate.of(1986, 9, 15),
					"978-1501142970"
			);
			logger.debug("Created BookDetails: {}", bookDetails);

			// Create main entities and connect them with details
			Author author = new Author("Stephen", "King");
			author.setAuthorDetails(authorDetails);
			authorDetails.setAuthor(author);

			logger.debug("Created Author and connected AuthorDetails.");

			Book book = new Book("It", "English", "Horror", "Novel", author);
			book.setBookDetails(bookDetails);
			bookDetails.setBook(book);

			logger.debug("Created Book and connected BookDetails.");

			// Connect book with author
			author.addBook(book);
			logger.debug("Added Book to Author's book list.");


			// Save the new records in database
			authorRepository.save(author);
			logger.debug("Saved Author (and cascaded Book, AuthorDetails, BookDetails) to database.");

			/**
			 * !!!Note: We only call authorRepository.save(author) because of the cascading relationships:
			 *
			 * - The Author entity has:
			 * @OneToOne(mappedBy = "author", cascade = CascadeType.ALL)
			 * private AuthorDetails authorDetails;
			 * @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
			 * private List<Book> books;
			 *
			 * - Each Book in turn has:
			 * @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
			 * private BookDetails bookDetails;
			 * These cascading settings mean that when we save the Author, all related entities
			 * (AuthorDetails, Book, BookDetails) are also persisted automatically by JPA.
			 */
		};
	}
}
