# mekwars-planet-converter

A simple, quick and dirty planet converter from the MekWars 0.3.7 style `planets` directory with
lots of `*.dat` files inside to a single MekWars 0.4 style `planets.xml` file.

Nothing fancy and ignores quite a bit of data that's not used in the files in the default distribution.
It should work fine with any reasonably up to data Java 8 installation and doesn't require any external libraries.

Pull requests and comments welcome.

**Building**

See Maven documentation. In Eclipse, right-click on the `pom.xml` file and pick "Run As > Maven install".
The result will be in the `target` directory.

**Usage (command line)**

`java -jar planetconv-(version).jar [directory_path]`

If the directory path is not given, defaults to `planets` in the working directory.

Writes into the `planets.xml` file in the working directory. If such a file exists, it gets overwritten.
Errors while parsing get printed to the standard output, but don't stop the parsing.
