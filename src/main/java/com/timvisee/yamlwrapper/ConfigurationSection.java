package com.timvisee.yamlwrapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationSection {

    /**
     * Parent configuration section.
     */
    private ConfigurationSection parent;

    /**
     * Key of the current configuration section.
     */
    private String key;

    /**
     * Value of the current configuration section.
     */
    private Object value;
	
	/**
	 * Constructor.
     *
	 * @param key Section key.
	 * @param value Section value.
	 */
	public ConfigurationSection(String key, Object value) {
		this.parent = null;
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Constructor.
     *
	 * @param parent Parent section.
	 * @param key Section key.
	 * @param value Section value.
	 */
	public ConfigurationSection(ConfigurationSection parent, String key, Object value) {
		this.parent = parent;
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Get the parent configuration section.
     * Null will be returned if this is the root section.
     *
	 * @return Parent section.
	 */
	public ConfigurationSection getParent() {
		return this.parent;
	}
	
	/**
	 * Check whether this configuration section is the root.
     *
	 * @return True if this section is the root, false if not.
	 */
	public boolean isRoot() {
		return (this.parent == null);
	}
	
	/**
	 * Get the root configuration section this section is part of.
     *
	 * @return Root section.
	 */
	public ConfigurationSection getRoot() {
		// Check whether this section is the root, return it in that case
		if(isRoot())
			return this;
		
		// Return the parent root
		return this.parent.getRoot();
	}
	
	/**
	 * Get the path of the configuration section.
     *
	 * @return Section path.
	 */
	public String getPath() {
		// Return an empty string if this is the root
		if(isRoot())
			return "";
		
		// If the parent configuration section is the root, return the key of the current
		if(this.parent.isRoot())
			return this.key;

        // Create a string builder for the section path
		StringBuilder path = new StringBuilder();

		// Append the parent path if his isn't the root
		if(!isRoot())
			path.append(this.parent.getPath()).append(".");

		// Append the current path
		path.append(this.key);

		// Return the path
		return path.toString();
	}
	
	/**
	 * Get the name of this configuration section.
     * Alias for {@code getKey()}.
     *
	 * @return Section name.
	 */
	public String getName() {
		return getKey();
	}
	
	/**
	 * Get the configuration section key.
     *
	 * @return Section key.
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Get the raw configuration section value.
     *
	 * @param path Path of the section.
     *
	 * @return Value to get.
	 */
	public Object get(String path) {
		return get(path, null);
	}
	
	/**
	 * Get the raw configuration section value for the given path.
     *
	 * @param path Path of the section.
	 * @param def Default value if the section wasn't found.
     *
	 * @return Raw section value.
	 */
	public Object get(String path, Object def) {
		// Make sure the path is not null
		if(path == null)
			return def;
		
		// Trim the path
		path = path.trim();
		
		// Is the path leading to this section
		if(path.equals(""))
			return this.value;
		
		// Get the section this path is leading to
		final ConfigurationSection section = getConfigurationSection(path);
		
		// Make sure the section is not null
		if(section == null)
			return def;
					
		// Return the value
		return section.get("");
	}
	
	/**
	 * Get a string value at the given path.
     *
	 * @param path Path of the section.
     *
	 * @return String value.
	 */
	public String getString(String path) {
		return getString(path, "");
	}
	
	/**
	 * Get a string value at the given path.
     *
	 * @param path Path of the value.
	 * @param def Default value if the path doesn't exist.
     *
	 * @return String value.
	 */
	public String getString(String path, String def) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return def;
		
		// The value has to be an instance of a boolean
		if(value instanceof String)
			return (String) value;
		return def;
	}
	
	/**
	 * Check whether the value at the given path is a string.
     *
	 * @param path Path of the value.
     *
	 * @return True if the value is a string, false if not.
	 */
	public boolean isString(String path) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return false;
		
		// Is the value an instance of a string
		return (value instanceof String);
	}
	
	/**
	 * Get an integer value at the given path.
     *
	 * @param path Path of the value.
     *
	 * @return Integer value.
	 */
	public int getInt(String path) {
		return getInt(path, 0);
	}
	
	/**
	 * Get an integer value at the given path.
     *
	 * @param path Path of the value.
	 * @param def Default value if the path doesn't exist.
     *
	 * @return Integer value.
	 */
	public int getInt(String path, int def) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return def;
		
		// Return the value if it's an integer, return the default if not
		if(value instanceof Integer)
			return (Integer) value;
		return def;
	}
	
	/**
	 * Check whether the value at the given path is an integer.
     *
	 * @param path Path of the value.
     *
	 * @return True if the value at the given path is an integer, false if not.
	 */
	public boolean isInt(String path) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return false;
		
		// Is the value an instance of a integer
		return (value instanceof Integer);
	}
	
	/**
	 * Get a boolean value at the given path.
     *
	 * @param path Path of the value
     *
	 * @return Boolean value.
	 */
	public boolean getBoolean(String path) {
		return getBoolean(path, false);
	}
	
	/**
	 * Get a boolean value at the given path.
     *
	 * @param path Path of the value.
	 * @param def Default value if the key doesn't exist.
     *
	 * @return Boolean value.
	 */
	public boolean getBoolean(String path, boolean def) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return def;
		
		// The value has to be an instance of a boolean
		if(value instanceof Boolean)
			return (Boolean) value;
		return def;
	}
	
	/**
	 * Check whether the value at the given path is a boolean.
     *
	 * @param path Path to the value.
     *
	 * @return True if the value at the given path is a boolean, false if not.
	 */
	public boolean isBoolean(String path) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return false;
		
		// Is the value an instance of a boolean
		return (value instanceof Boolean);
	}
	
	/**
	 * Get a double value at the given path.
     *
	 * @param path Path of the value.
     *
	 * @return Double value.
	 */
	public double getDouble(String path) {
		return getDouble(path, 0);
	}
	
	/**
	 * Get a double value at the given path.
     *
	 * @param path Path of the value.
	 * @param def Default value if the key doesn't exist.
     *
	 * @return Double value.
	 */
	public double getDouble(String path, double def) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return def;
		
		// The value has to be an instance of a boolean
		if(value instanceof Double)
			return (Double) value;
		return def;
	}
	
	/**
	 * Check whether the value at the given path is a double.
     *
	 * @param path Path of the value.
     *
	 * @return True if the value at the given path is a double.
	 */
	public boolean isDouble(String path) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return false;
		
		// Is the value an instance of a double
		return (value instanceof Double);
	}
	
	/**
	 * Get a float value at the given path.
     *
	 * @param path Path of the value.
     *
	 * @return Float value.
	 */
	public float getFloat(String path) {
		return getFloat(path, 0);
	}
	
	/**
	 * Get a float value at the given path.
     *
	 * @param path Path of the value.
	 * @param def Default value if the key doesn't exist.
     *
	 * @return Float value.
	 */
	public float getFloat(String path, float def) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return def;
		
		// The value has to be an instance of a boolean
		if(value instanceof Float)
			return (Float) value;
		return def;
	}
	
	/**
	 * Check whether the value at the given path is a float.
     *
	 * @param path Path of the value.
     *
	 * @return True if the value at the given pat his a float.
	 */
	public boolean isFloat(String path) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return false;
		
		// Is the value an instance of a double
		return (value instanceof Float);
	}
	
	/**
	 * Get a long value at the given path.
     *
	 * @param path Path of the value.
     *
	 * @return Long value.
	 */
	public long getLong(String path) {
		return getLong(path, 0);
	}
	
	/**
	 * Get a long value at the given path.
     *
	 * @param path Path of the value.
	 * @param def Default value if the path doesn't exist.
     *
	 * @return Long value.
	 */
	public long getLong(String path, long def) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return def;
		
		// The value has to be an instance of a long
		if(value instanceof Long)
			return (Long) value;
		return def;
	}
	
	/**
	 * Check whether the value at the given path is a long.
     *
	 * @param path Path of the value.
     *
	 * @return True if the value at the given path is a long.
	 */
	public boolean isLong(String path) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return false;
		
		// Is the value an instance of a long
		return (value instanceof Long);
	}
	
	/**
	 * Get a list of values at the given path.
     *
	 * @param path Path of the value containing the list.
     *
	 * @return List of values.
	 */
	public List<?> getList(String path) {
		return getList(path, null);
	}
	
	/**
	 * Get a list of values at the given path.
     *
	 * @param path Path of the value containing the list.
	 * @param def Default value if the path doesn't exist.
     *
	 * @return List of values.
	 */
	public List<?> getList(String path, List<?> def) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return def;
		
		// The value has to be an instance of a boolean
		if(value instanceof List)
			return (List<?>) value;
		return def;
	}
	
	/**
	 * Check whether the value at the given path is a list of values.
     *
	 * @param path Path of the value containing the list.
     *
	 * @return True if the value at the given path is a list of value.
	 */
	public boolean isList(String path) {
		// Get the value
		final Object value = get(path);
		
		// Make sure the value is not null
		if(value == null)
			return false;
		
		// Is the value an instance of a list
		return (value instanceof List);
	}
	
	/**
	 * Return a list of keys that are inside the given configuration section.
     *
	 * @param path Path of the section to get the keys for.
     *
	 * @return List of keys.
	 */
	public List<String> getKeys(String path) {
		// Make sure the path is not null
		if(path == null)
			return new ArrayList<String>();
		
		// Trim the path
		path = path.trim();
		
		// Make sure this configuration section exists
		if(!isConfigurationSection(path))
			return new ArrayList<String>();
		
		// Get the configuration sections to get the keys from
		final ConfigurationSection section = getConfigurationSection(path);
		
		// Make sure the configuration section holds other configuration sections
		if(!section.isHoldingConfigurationSections())
			return new ArrayList<String>();
		
		// Return the list of keys
		@SuppressWarnings("unchecked")
		final List<ConfigurationSection> sections = (List<ConfigurationSection>) section.get("");
		final List<String> keys = new ArrayList<>();

		// Create a list of keys
		for(ConfigurationSection entry : sections)
			keys.add(entry.getKey());

		// Return the list of keys
		return keys;
	}
	
	/**
	 * Get a configuration section.
     *
	 * @param path Path of the section.
     *
	 * @return Configuration section.
	 */
	public ConfigurationSection getSection(String path) {
		return getConfigurationSection(path);
	}
	
	/**
	 * Get a configuration section.
     *
	 * @param path Path of the section.
     *
	 * @return Configuration section.
	 */
	public ConfigurationSection getConfigurationSection(String path) {
		// Make sure the path param is not null
		if(path == null)
			return null;
		
		// Trim the path
		path = path.trim();
		
		// Is the path locating to this configuration section
		if(path.equals(""))
			return this;
		
		// Make sure the value of the current section is set
		if(!isSet(""))
			return null;
		
		// Does the path contain any sub-paths
		if(!path.contains(".")) {
			// Make sure the path is locating to a configuration section
			if(!isConfigurationSection(path))
				return null;
			
			// Get and return the configuration section
			if(this.value instanceof List) {
				// Get the configuration section
				try {
					@SuppressWarnings("unchecked")
					List<ConfigurationSection> sections = (List<ConfigurationSection>) this.value;
					for(ConfigurationSection section : sections) {
						if(section == null) 
							continue;

						// Is this the section we are searching for
						if(section.getKey().equals(path))
							return section;
					}
				} catch(ClassCastException e) { }
				return null;
				
			} else
				return null;
			
		} else {
			
			// Get the keys
			String[] keys = path.split("\\.");
			String key = path;
			if(keys.length > 0)
				key = keys[0];
			String subPath = "";
			if(keys.length > 1) {
				subPath = keys[1];
				for(int i = 2; i < keys.length; i++)
					subPath += "." + keys[i];
			}
			
			// Make sure the key is not empty
			if(key.equals(""))
				return this;
			
			// Get the configuration section
			ConfigurationSection section = getConfigurationSection(key);
			
			// Make sure the section is not null
			if(section == null)
				return null;
			
			// Get the value from the child section
			return section.getConfigurationSection(subPath);
		}
	}

	/**
	 * Create a new configuration section
	 * @param path Configuration section
	 * @return New configuration section, null if path was invalid.<br>
	 * If path is an empty string it will return the section the method was called on.
	 */
	public ConfigurationSection createSection(String path) {
		return createConfigurationSection(path);
	}
	
	/**
	 * Create a new configuration section
	 * @param path Configuration section
	 * @return New configuration section, null if path was invalid.<br>
	 * If path is an empty string it will return the section the method was called on.
	 */
	public ConfigurationSection createConfigurationSection(String path) {
		// Make sure the path is not null
		if(path == null)
			return null;
		
		// Trim the path
		path = path.trim();
		
		// Is the path leading to the current section
		if(path.equals("")) {
			// Return this section without resetting the value
			return this;
		}
		
		// Get the keys
		String[] keys = path.split("\\.");
		String key = path;
		if(keys.length > 0)
			key = keys[0];
		String subPath = "";
		if(keys.length > 1) {
			subPath = keys[1];
			for(int i = 2; i < keys.length; i++)
				subPath += "." + keys[i];
			subPath = subPath.trim();
		}
		
		// Is the first key of the path leading to an already exsisting section
		if(isConfigurationSection(key)) {
			// Get the section
			ConfigurationSection section = getConfigurationSection(key);
			
			// Are there any subkeys
			if(subPath.length() == 0) {
				// Return the section
				return section;
			}
			
			// Create the sub key sections in the section and return the result
			return section.createConfigurationSection(subPath);
		
		} else {
			
			// Create a section
			//ConfigurationSection section = new ConfigurationSection(this, key, null);
			if(this.value instanceof List) {
				try {
					@SuppressWarnings("unchecked")
					List<ConfigurationSection> sections = (List<ConfigurationSection>) this.value;
					ConfigurationSection section = new ConfigurationSection(this, key, null);
					sections.add(section);
					this.value = sections;
					
					// Are there any subkeys
					if(subPath.length() == 0) {
						// Return the section
						return section;
					}
					
					// Create the sub key sections in the section and return the result
					return section.createConfigurationSection(subPath);
					
				} catch(ClassCastException ex) { }
			}
				
			// Create a new section
			ConfigurationSection section = new ConfigurationSection(this, key, null);
			List<ConfigurationSection> sections = new ArrayList<ConfigurationSection>();
			sections.add(section);
			this.value = sections;
			
			// Are there any subkeys
			if(subPath.length() == 0) {
				// Return the section
				return section;
			}
			
			// Create the sub key sections in the section and return the result
			return section.createConfigurationSection(subPath);
		}
	}
	
	/**
	 * Set the value
	 * @param val Value
	 */
	public void set(String path, Object val) {
		// Make sure the path is not null
		if(path == null)
			return;
		
		// Trim the path
		path = path.trim();
		
		// Is the path leading to this section
		if(path.equals("")) {
			this.value = val;
			return;
		}
		
		// Get the keys
		String[] keys = path.split("\\.");
		String key = path;
		if(keys.length > 0)
			key = keys[0];
		String subPath = "";
		if(keys.length > 1) {
			subPath = keys[1];
			for(int i = 2; i < keys.length; i++)
				subPath += "." + keys[i];
		}
		
		// Is there any section this key leads to
		if(isConfigurationSection(key)) {
			// Get the section
			ConfigurationSection section = getConfigurationSection(key);
			section.set(subPath, val);
			return;
		
		} else {
			// Create a section
			ConfigurationSection section = new ConfigurationSection(this, key, null);
			if(this.value instanceof List) {
				try {
					@SuppressWarnings("unchecked")
					List<ConfigurationSection> sections = (List<ConfigurationSection>) this.value;
					sections.add(section);
					
				} catch(ClassCastException ex) {
					// Create a new section
					List<ConfigurationSection> sections = new ArrayList<ConfigurationSection>();
					sections.add(section);
					this.value = sections;
				}
			} else {
				// Create a new section
				List<ConfigurationSection> sections = new ArrayList<ConfigurationSection>();
				sections.add(section);
				this.value = sections;
			}
			
			// Set the value in the new section
			section.set(subPath, val);
		}
	}
	
	/**
	 * Is the value set
	 * @return True if the value was set
	 */
	public boolean isSet(String path) {
		// Make sure the path param is not null
		if(path == null)
			return false;
		
		// Get the section the path is leading to
		ConfigurationSection section = getConfigurationSection(path);
		
		// Make sure the section is not null
		if(section == null)
			return false;
		
		// Is the value of the section null
		return (section.get("") != null);
	}
	
	/**
	 * Is the value set
	 * @return True if the value was set
	 */
	public boolean isHoldingConfigurationSections() {
		// Is the current value null
		if(this.value == null)
			return false;
		
		try {
    		@SuppressWarnings("unchecked")
			List<ConfigurationSection> sections = (List<ConfigurationSection>) this.value;
    		if(sections.size() <= 0)
    			return false;
    		
    		return (sections.get(0) instanceof ConfigurationSection);
    	} catch(ClassCastException e) {
    		return false;
    	}
	}
	
	/**
	 * Is the value a configuration section
	 * @return True if the value is a configuration section
	 */
	public boolean isConfigurationSection(String path) {
		// Make sure the path is not null
		if(path == null)
			return false;
		
		// Trim the path
		path = path.trim();
		
		// Is the path leading to this section
		if(path.equals(""))
			return true;
		
		// Is the value of this configuration section a list instance, if not it can't hold any sub sections (so return false)
		if(!(this.value instanceof List))
			return false;
		
		// Get the list of configuration sections
		try {
			@SuppressWarnings("unchecked")
			List<ConfigurationSection> sections = (List<ConfigurationSection>) this.value;
			for(ConfigurationSection section : sections) {
				// Make sure the current entry is not null
				if(section == null)
					continue;
				
				// Get the keys
				String[] keys = path.split("\\.");
				String key = path;
				if(keys.length > 0)
					key = keys[0];
				String subPath = "";
				if(keys.length > 1) {
					subPath = keys[1];
					for(int i = 2; i < keys.length; i++)
						subPath += "." + keys[i];
				}
					
				// Make sure the key of the current entry equals
				if(!section.getKey().equals(key))
					continue;
				
				return section.isConfigurationSection(subPath);
			}
			
			return false;
			
		} catch(ClassCastException e) {
			return false;
		}
	}
	
	/**
	 * Get all the values from the section as a Map list
	 * @return Values from the section as a Map list
	 */
	public Map<String, Object> getValues() {
        // Define a map list to store the values in
		Map<String, Object> out = new LinkedHashMap<String, Object>();
        
		// Make sure the value is not null
		if(this.key == null)
			return out;
		
		// Add the values to the 
        if(this.value instanceof List) {
        	try {
        		@SuppressWarnings("unchecked")
				List<ConfigurationSection> sections = (List<ConfigurationSection>) this.value;
        		//Map<String, Object> values = new LinkedHashMap<String, Object>();
        		for(ConfigurationSection entry : sections) {
        			if(entry.isHoldingConfigurationSections())
            			out.put(entry.getKey(), entry.getValues());
        			else
        				out.put(entry.getKey(), entry.get(""));
        		}
        		//out.put(getKey(), values);
        	} catch(ClassCastException e) {
        		out.put(getKey(), this.value);
        	}
        } else
        	out.put(getKey(), this.value);
        
        // Return the output
        return out;
	}
}
