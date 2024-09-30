package com.sushma.gi.service;


public interface ViewerService {

	void load();

	int viewOptions();

	void viewAll();

	void addmusic();

	void editmusic();

	void removemusic();

	void searchByTitle();

	void searchByDescription();

	void searchByTitleOrDescription();

	void saveAndExit();

}
