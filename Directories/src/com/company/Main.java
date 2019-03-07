package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

//HOW TO READ EXISTING DIRECTORY CONTENTS
public class Main {

    public static void main(String[] args) {
        //Reading the entries in our directory
        //using the files.newDirectoryStream method
        Path directory = FileSystems.getDefault().getPath("FileTree/Dir2");
        try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory, "*.dat")) {
            //DirectoryStream is an interface that implements the
            //Iterable interface
            //Consequently, you can iterate over the return paths
            //to get the contents of the Dir2 folder
            for(Path file: contents) {
                System.out.println(file.getFileName());
            }

            //third version of newDirectoryStream method
            //writing your own filters!
            //Accepts a DirectoryStream.Filter parameter
            //Example of a filter that only returns files and not directories
            //Note: DirectoryStream.Filter interface only has one method called accept
            //Now when accept returns true for a path, path will be
            //included in the directory stream results
            //Also, notice how there is an anonymous class that
            //implements the interface on the fly.
            //You cannot create an instance of an interface
            //You must replace the second glob parameter in line 14
            //with "filter"
            //Then, the console will spit back out the regular files
            //of DIr2: file1.txt, file2.txt, file3.dat
            DirectoryStream.Filter<Path> filter =
                    new DirectoryStream.Filter<Path>() {
                public boolean accept(Path path) throws IOException {
                    return (Files.isRegularFile(path));
                }
                    };
            //Now since there is only method to implement
            //We could have used a lambda expression:
            //DirectoryStream.Filter<Path> filter = p -> Files.isREgularFile(p);


        } catch (IOException |DirectoryIteratorException e) {
            System.out.println(e.getMessage());
        }





        /*try {
         Path fileToCreate = FileSystems.getDefault().getPath("FileTree", "file2.txt");
         Files.createFile(fileToCreate);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/


    }
}

//What if you wanted to work on specific files in a directory

//newDirectoryStream method accepts a filter as a second parameter
//The second filtering parameter is referred to as a "glob"
//A glob is similar to a regular expression, but the syntax is
//more geared towards paths
//Here are some examples:
// * character matches any string
// *.dat will match any path with the .dat extension
// *.{dat,txt} will match any path that has the extension .dat or .txt
//? symbol matches exactly one character. For example, the glob ??? would
//match any path that contains exactly three characters.
//myfile* matches any paths that begin with myfile
//b?*.txt matches any paths that are at least two characters long
//and begin with the character b(the ? forces a second character, and the
//* matches 0 or more characters).
//For more documentation, check out getPatchMatcher
//https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystem.html#getPathMatcher-java.lang.String-


