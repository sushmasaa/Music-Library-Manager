package com.sushma.gi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sushma.gi.model.Book;


public class ViewerServiceImpl implements ViewerService {

	BookService musicService = new MusicServiceImpl();
	private List<Music> musics;
	private Scanner sc = new Scanner(System.in);

	
	@Override
	public void load() {
		musics = musicService.loadAll();

		if (musics.size() > 0)
			System.out.printf("Loaded [%d] books into the library.\n", musics.size());
		else
			System.out.println("There aren't any books available to load.");
	}

	/**
	 * Display Book Manager Options
	 * 
	 * @return - User Choice
	 */
	@Override
	public int viewOptions() {

		StringBuilder sb = new StringBuilder();

		sb.append("\n===== Book Manager =====\n\n");
		sb.append("\t1) View all musics\n");
		sb.append("\t2) Add a musics\n");
		sb.append("\t3) Edit a music\n");
		sb.append("\t4) Remove a music\n");
		sb.append("\t5) Search for a music by title\n");
		sb.append("\t6) Search for a music by description\n");
		sb.append("\t7) Search for a music by title and description\n");
		sb.append("\t8) Save and exit\n");
		sb.append("\t9) Exit without save\n\n");
		sb.append("Choose [1-9]: ");

		System.out.print(sb);

		String selection = sc.next();
		while (!selection.matches("[1-9]")) {
			System.out.print("Please choose correct choice [1-9]: ");
			selection = sc.next();
		}

		int choice = Integer.parseInt(selection);
		return choice;
	}

	/**
	 * View all books in the database
	 * 
	 * - List the ID and title of each book
	 * 
	 * - Allow the user to see details of a particular book
	 * 
	 */
	@Override
	public void viewAll() {
		StringBuilder sb = new StringBuilder();
		System.out.println("\n===== View Books =====\n");
		if (musics.size() > 0) {
			for (Music music : musics) {
				sb.append("\t[" + music.getId() + "] " + music.getTitle() + "\n");
			}
			System.out.println(sb);
			System.out.println("To view details enter the music ID, to return press <Enter>.");
			sc.nextLine();
			System.out.print("\nMusic ID: ");
			String id = sc.nextLine();
			while (!id.isEmpty()) {
				while (id.matches("[^\\d]+")) {
					System.out.print("\tPlease enter correct music id; to return press <Enter>.\n\n");
					System.out.print("music ID: ");
					id = sc.nextLine();
				}
				if (!id.isEmpty()) {
					Music music = musicService.get(musics, Integer.parseInt(id));

					if (music != null) {
						System.out.print("\n\tID: " + music.getId());
						System.out.print("\n\tTitle: " + music.getTitle());
						
						System.out.print("\n\tDescription: " + music.getDescription() + "\n");

					} else {
						System.out.println("\tThere is no music with ID [" + id + "] in the listed musics.");
					}

					System.out.println("\nTo view details enter the music ID, to return press <Enter>.");
					System.out.print("\nMusic ID: ");
					id = sc.nextLine();
				}
			}
		} else {
			System.out.println("\tThere aren't any musics available.");
		}
	}

	
	public void addBook() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== Add a Music =====\n");
		sb.append("\nPlease enter the following information:\n\n");
		System.out.print(sb);
		sc.nextLine();
		System.out.print("\tTitle: ");
		String title = sc.nextLine();
		
		System.out.print("\tDescription: ");
		String description = sc.nextLine();
		String status = musicService.save(new Book(title, description));
		System.out.println("\n" + status);
	}

	
	@Override
	public void editmusic() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== Edit a music=====\n\n");
		for (Music music : musics) {
			sb.append("\t[" + music.getId() + "] " + music.getTitle() + "\n");
		}
		sb.append("\nEnter the music ID of the music you want to edit; to return press <Enter>.\n\n");
		System.out.print(sb);
		sc.nextLine();
		System.out.print("music ID: ");
		String id = sc.nextLine();
		while (id.matches("[^\\d]+") && !id.isEmpty()) {
			System.out.print("\tPlease enter correct music id; to return press <Enter>.\n\n");
			System.out.print("music ID: ");
			id = sc.nextLine();
		}
		if (!id.isEmpty()) {
			// sc.nextLine();
			Book oldMusic = musicService.get(Integer.parseInt(id));
			if (oldBook != null) {
				System.out.print("\nInput the following information. To leave a field unchanged, hit <Enter>.\n\n");

				System.out.print("\tTitle: [" + oldMusic.getTitle() + "]: ");
				String title = sc.nextLine();
				
				System.out.print("\tDescription: [" + oldMusic.getDescription() + "]: ");
				String description = sc.nextLine();

				if (title.isEmpty() && description.isEmpty()) {
					System.out.println("\nThere are not any changes for music with ID [" + id + "]");
				} else {
					String status = musicService.update(oldMusic.getId(), title, description);
					System.out.println("\n" + status);
				}
			} else {
				System.out.println("\tThere is no music with ID [" + id + "]");
			}
		}
	}

	/**
	 * Remove an existing book
	 * 
	 * - Display a list of available books
	 * 
	 * - Allow the user to select a book to delete
	 * 
	 * - Delete the book from the database
	 */
	@Override
	public void removeBook() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== Remove a Book =====\n");
		for (Book book : books) {
			sb.append("\t[" + book.getId() + "] " + book.getTitle() + "\n");
		}
		sb.append("\nEnter the book ID of the book you want to remove; to return press <Enter>.\n\n");
		System.out.print(sb);
		sc.nextLine();
		System.out.print("Book ID: ");
		String id = sc.nextLine();
		while (id.matches("[^\\d]+") && !id.isEmpty()) {
			System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
			System.out.print("Book ID: ");
			id = sc.nextLine();
		}
		if (!id.isEmpty()) {
			Book book = bookService.get(Integer.parseInt(id));
			if (book != null) {
				System.out.print("\tAre you sure you want to remove " + book
						+ "? \n\tEnter y for confirmation; to return press <Enter>: ");

				String title = sc.nextLine();
				if (!title.isEmpty() && title.equalsIgnoreCase("y")) {
					String status = bookService.delete(book.getId());
					System.out.println("\n" + status);
				}
			} else {
				System.out.println("\tThere is no book with ID [" + id + "]");
			}
		}
	}

	/**
	 * Search for books using keywords for specific field
	 * 
	 * @param by
	 */
	public void search(String by) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== Search by " + by + " =====\n");

		sb.append("\nType in one or more keywords to search for.\n\n");
		System.out.print(sb);
		sc.nextLine();
		System.out.print("\tSearch: ");
		String keywords = sc.nextLine();
		while (keywords.isEmpty()) {
			System.out.print("Type in one or more keywords to search for.\n\n");
			System.out.print("\tSearch: ");
			keywords = sc.nextLine();
		}
		if (!keywords.isEmpty()) {
			// sc.nextLine();
			List<Book> books = new ArrayList<>();
			if (by.equals("title"))
				books = bookService.getByTitle(keywords);
			else if (by.equals("description"))
				books = bookService.getByDescription(keywords);
			else if (by.equals("titleOrDescription"))
				books = bookService.getByTitleOrDescription(keywords);

			if (books.size() > 0) {
				System.out.print(
						"\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n\n");

				for (Book book : books) {
					System.out.print("\t[" + book.getId() + "] " + book.getTitle() + "\n");
				}
				System.out.print("\nBook ID: ");
				String id = sc.nextLine();
				while (!id.isEmpty()) {
					while (id.matches("[^\\d]+")) {
						System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
						System.out.print("Book ID: ");
						id = sc.nextLine();
					}
					if (!id.isEmpty()) {
						Book book = bookService.get(books, Integer.parseInt(id));

						if (book != null) {
							System.out.print("\n\tID: " + book.getId());
							System.out.print("\n\tTitle: " + book.getTitle());
							System.out.print("\n\tAuthor: " + book.getAuthor());
							System.out.print("\n\tDescription: " + book.getDescription() + "\n");

						} else {
							System.out.println(
									"\tThere aren't anyn't any books with ID [" + id + "] in the matched books.");
						}
					}
					System.out.print("\nBook ID: ");
					id = sc.nextLine();
				}
			} else {
				System.out.println("\t\tThere aren't any books matched your query for keywords [" + keywords + "].");
			}
		}
	}

	/**
	 * Search for books using keywords at title field
	 */
	@Override
	public void searchByTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== Search by title =====\n");

		sb.append("\nType in one or more keywords to search for.\n\n");
		System.out.print(sb);
		sc.nextLine();
		System.out.print("\tSearch: ");
		String keywords = sc.nextLine();
		while (keywords.isEmpty()) {
			System.out.print("Type in one or more keywords to search for.\n\n");
			System.out.print("\tSearch: ");
			keywords = sc.nextLine();
		}
		if (!keywords.isEmpty()) {
			List<Book> books = bookService.getByTitle(keywords);
			if (books.size() > 0) {
				System.out.print(
						"\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n\n");

				for (Book book : books) {
					System.out.print("\t[" + book.getId() + "] " + book.getTitle() + "\n");
				}
				System.out.print("\nBook ID: ");
				String id = sc.nextLine();
				while (!id.isEmpty()) {
					while (id.matches("[^\\d]+")) {
						System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
						System.out.print("Book ID: ");
						id = sc.nextLine();
					}
					if (!id.isEmpty()) {
						Book book = bookService.get(books, Integer.parseInt(id));

						if (book != null) {
							System.out.print("\n\tID: " + book.getId());
							System.out.print("\n\tTitle: " + book.getTitle());
							System.out.print("\n\tAuthor: " + book.getAuthor());
							System.out.print("\n\tDescription: " + book.getDescription() + "\n");

						} else {
							System.out.println("\tThere aren't any books with ID [" + id + "] in the matched books.");
						}

						System.out.print("\nBook ID: ");
						id = sc.nextLine();
					}

				}
			} else {
				System.out.println("\t\tThere aren't any books matched your query for keywords [" + keywords + "]");
			}
		}
	}

	/**
	 * Search for books using keywords at description field
	 */
	@Override
	public void searchByDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== Search by description =====\n");

		sb.append("\nType in one or more keywords to search for.\n\n");
		System.out.print(sb);
		sc.nextLine();
		System.out.print("\tSearch: ");
		String keywords = sc.nextLine();
		while (keywords.isEmpty()) {
			System.out.print("Type in one or more keywords to search for.\n\n");
			System.out.print("\tSearch: ");
			keywords = sc.nextLine();
		}
		if (!keywords.isEmpty()) {
			List<Book> books = bookService.getByDescription(keywords);
			if (books.size() > 0) {
				System.out.print(
						"\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n\n");

				for (Book book : books) {
					System.out.print("\t[" + book.getId() + "] " + book.getTitle() + "\n");
				}
				System.out.print("\nBook ID: ");
				String id = sc.nextLine();
				while (!id.isEmpty()) {
					while (id.matches("[^\\d]+")) {
						System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
						System.out.print("Book ID: ");
						id = sc.nextLine();
					}
					if (!id.isEmpty()) {
						Book book = bookService.get(books, Integer.parseInt(id));

						if (book != null) {
							System.out.print("\n\tID: " + book.getId());
							System.out.print("\n\tTitle: " + book.getTitle());
							System.out.print("\n\tAuthor: " + book.getAuthor());
							System.out.print("\n\tDescription: " + book.getDescription() + "\n");

						} else {
							System.out.println("\tThere aren't any books with ID [" + id + "] in the matched books.");
						}

						System.out.print("\nBook ID: ");
						id = sc.nextLine();
					}

				}
			} else {
				System.out.println("\t\tThere aren't any books matched your query for keywords[" + keywords + "].");
			}
		}
	}

	/**
	 * Search for books using keywords at title or description fields
	 */
	@Override
	public void searchByTitleOrDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== Search by title or description =====\n");

		sb.append("\nType in one or more keywords to search for.\n\n");
		System.out.print(sb);
		sc.nextLine();
		System.out.print("\tSearch: ");
		String keywords = sc.nextLine();
		while (keywords.isEmpty()) {
			System.out.print("Type in one or more keywords to search for.\n\n");
			System.out.print("\tSearch: ");
			keywords = sc.nextLine();
		}
		if (!keywords.isEmpty()) {
			List<Book> books = bookService.getByTitleOrDescription(keywords);
			if (books.size() > 0) {
				System.out.print(
						"\nThe following books matched your query. Enter the book ID to see more details, or <Enter> to return.\n\n");

				for (Book book : books) {
					System.out.print("\t[" + book.getId() + "] " + book.getTitle() + "\n");
				}
				System.out.print("\nBook ID: ");
				String id = sc.nextLine();
				while (!id.isEmpty()) {
					while (id.matches("[^\\d]+")) {
						System.out.print("\tPlease enter correct book id; to return press <Enter>.\n\n");
						System.out.print("Book ID: ");
						id = sc.nextLine();
					}
					if (!id.isEmpty()) {
						Book book = bookService.get(books, Integer.parseInt(id));

						if (book != null) {
							System.out.print("\n\tID: " + book.getId());
							System.out.print("\n\tTitle: " + book.getTitle());
							System.out.print("\n\tAuthor: " + book.getAuthor());
							System.out.print("\n\tDescription: " + book.getDescription() + "\n");

						} else {
							System.out.println("\tThere aren't any books with ID [" + id + "] in the matched books.");
						}
						System.out.print("\nBook ID: ");
						id = sc.nextLine();
					}

				}
			} else {
				System.out.println("\t\tThere aren't any books matched your query for keywords[" + keywords + "].");
			}
		}
	}

	/**
	 * Save and Exit
	 * 
	 * - Write the database of books to disk, upon exiting the application
	 */
	@Override
	public void saveAndExit() {
		if (bookService.save()) {
			System.out.println("\nLibrary saved.");
			System.exit(0);
		} else
			System.out.println("Sorry, Something has error while saving.");
	}

}
