package com.sushma.gi.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.sushma.gi.connection;
import com.sushma.gi.model.Music;


public class serviceImpl implements MusicService {

	File file = FileConnection.getFile();

	private List<music> musics = new ArrayList<>();

	/**
	 * Load all books from the disk file
	 *
	 * @return List of loaded books
	 */
	@Override
	public List<music> loadAll() {

		try {
			Scanner reader = new Scanner(file);
			// reader.skip("[\r\n]*");
			reader.useDelimiter(" , |\n");

			int id;
			String title, description;

			// For each book in the file's list
			while (reader.hasNext()) {

				// id = reader.nextInt();
				id = Integer.parseInt(reader.next());
				reader.skip("[\r\n]*");
				title = reader.next();
				
				description = reader.next().replaceAll("[\r\n]+", "");
				books.add(new Book(id, title, description));
			}

		} catch (FileNotFoundException e) {
			System.out.println("Sorry, Error: " + e);
		}

		return books;
	}

	/**
	 * Return all books
	 */
	@Override
	public List<Music> getAll() {
		return musics;
	}

	/**
	 * Add a new book
	 */
	@Override
	public String save(Music music) {

		int id = books.size() > 0 ? books.get(books.size() - 1).getId() + 1 : 1;

		book.setId(id);

		books.add(book);

		return "Book [" + book.getId() + "] Saved";
	}

	/**
	 * Edit an existing book
	 */
	@Override
	public String update(int id, String title, String description) {
		for (Music music : musics) {
			if (music.getId() == id) {
				if (!title.isEmpty())
					music.setTitle(title);
				
				if (!description.isEmpty())
					music.setDescription(description);
				break;
			}
		}

		return "Music [" + id + "] Updated";

	}

	/**
	 * Remove an existing book
	 */
	@Override
	public String delete(int id) {
		for (Music music : musics) {
			if (music.getId() == id) {
				musics.remove(music);
				break;
			}
		}

		return "Music [" + id + "] Deleted";

	}

	
	public Music get1(int id) {
		for (Music music : musics) {
			if (music.getId() == id) {
				return music;
			}
		}
		return null;
	}

	
	@Override
	public Book get(int id) {
		return musics.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
	}

	
	public List<Music> getByTitle(String keyword) {
		return musics.stream().filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
				.collect(Collectors.toList());
	}

	
	@Override
	public List<Music> getByDescription(String keyword) {
		return musics.stream().filter(b -> b.getDescription().toLowerCase().contains(keyword.toLowerCase()))
				.collect(Collectors.toList());
	}

	
	@Override
	public List<Music> getByTitleOrDescription(String keyword) {
		return musics.stream()
				.filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase())
						|| b.getDescription().toLowerCase().contains(keyword.toLowerCase()))
				.collect(Collectors.toList());
	}

	
	@Override
	public Music get(List<Music> musics, int id) {
		return musics.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
	}

	
	public boolean save() {

		if (books.isEmpty() || !file.exists()) {
			return false;
		} else {
			PrintWriter writer;
			try {
				writer = new PrintWriter(file);

				for (Music music : musics) {
					writer.println(music.toRecord());
				}
				writer.flush();
				writer.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

}
