package com.challenge

import java.util.regex.Pattern
import groovy.io.FileType


class StringReplacer {
    static void main(String[] args) {
        // reading user's inputs
        // https://stackoverflow.com/questions/10184091/groovy-console-read-input

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

        print "Enter directory: "
        String dirInput = br.readLine()
        def files = getFiles(dirInput)

        while (files == null || files.length == 0) {
            if (files == null) {
                print "Not a valid directory. Please try again: "
            } else if (files.length == 0) {
                print "This directory is empty. Please enter a different one: "
            }
            dirInput = br.readLine()
            files = getFiles(dirInput)
        }

        print "Enter searched text or pattern: "
        String textInput = br.readLine()

        print "Enter replacement text: "
        String replacementInput = br.readLine()

        replace(files, textInput, replacementInput)
    }


    static File[] getFiles(String path) {
        // getting a list of files in given directory and subdirectories via recursive method
        // https://stackoverflow.com/questions/3953965/get-a-list-of-all-the-files-in-a-directory-recursive

        def list = []

        def dir = new File(path)
        if (!dir.isDirectory()) {
            return null
        }
        dir.eachFileRecurse(FileType.FILES) { file ->
            println file
            list << file
        }
        return list
    }

    static def replace(File[] files, String originalText, String newText) {
        // iterating through each file and replacing given text/pattern
        // https://www.tutorialspoint.com/groovy/groovy_file_io.htm

        for (File f in files) {
            Pattern pattern = ~originalText
            def fileContent = f.text
            fileContent = fileContent.replaceAll(pattern, newText)
            f.withWriter { writer -> writer.writeLine(fileContent) }
        }
    }
}
