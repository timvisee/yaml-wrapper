# Yaml Wrapper
A YAML wrapper for Java to make it much easier to use these configuration files.

## Features
* Load/save YAML from/to a file or string.
* Get, set and check YAML keys and/or sections in the preferred data type.
* Easily format/parse lists or objects.
* Use a default value if the requested key doesn't exist.
* Set a base YAML configuration to use as fallback, if a key doesn't exist.
* Use _isolated_ sub-sections of a YAML configuration, as if you're using a full YAML file.
* Various useful helper methods to streamline your YAML configuration usage.

## What is this YAML wrapper
This YAML wrapper library makes using YAML configurations very easy in your Java project.
The wrapper uses the SnakeYaml library as it's backend for interacting with YAML files,
and provides a nice, clean and easy to use set of classes as abstraction.
This library is perfect to use for your own project if you'd like to implement YAML configuration file support.

## Usage / Example
A runnable usage example is included in the [Main.java](src/main/java/com/timvisee/yamlwrapper/Main.java) file.

## Contributing
1. Fork the project.
2. Create a branch: `git checkout -b my_feature_branch`
3. Commit your changes: `git commit -m "Add new specific feature"`
4. Push to the branch: `git push origin my_feature_branch`
5. Open a [Pull Request](https://github.com/timvisee/yaml-wrapper/compare).
6. Enjoy a refreshing Diet Coke and wait.
