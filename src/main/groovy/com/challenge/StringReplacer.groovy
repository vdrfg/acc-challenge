package com.challenge

import java.util.regex.Pattern
import groovy.io.FileType


class StringReplacer {
    static void main(String[] args) {
        // reading user's inputs
        // https://stackoverflow.com/questions/10184091/groovy-console-read-input

        def fileDir = args[0]
        def origText = args[1]
        def newText = args[2]
        def logDir = null
        if (args.length > 3) {
            logDir = args[3]
        }

        def files = getFiles(fileDir)

        replace(files, origText, newText, logDir)
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
            println file // TODO: remove once done
            list << file
        }
        return list
    }

    static replace(File[] files, String origText, String newText, String logDir) {
        // iterating through each file and replacing given text/pattern
        // https://www.tutorialspoint.com/groovy/groovy_file_io.htm

        for (File f in files) {
            Pattern pattern = ~origText
            def fileContent = f.text
            fileContent = fileContent.replaceAll(pattern, newText)
            f.withWriter { writer -> writer.writeLine(fileContent) }
        }
    }
}
