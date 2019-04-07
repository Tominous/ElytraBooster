package com.github.zamponimarco.elytrabooster.manager;

public interface DataManager {

	/**
	 * Loads the data file and sets it in an instance variable
	 */
	public void loadDataFile();
	
	/**
	 * Loads the yaml configuration, sets it in an instance variable and initializes data structures
	 */
	public void loadDataYaml();
	
	/**
	 * Loads the data from the yaml configurations and puts it in his data structure
	 */
	public void loadData();
	
	public default void reloadData() {
		loadDataFile();
		loadDataYaml();
		loadData();
	}
	
}
