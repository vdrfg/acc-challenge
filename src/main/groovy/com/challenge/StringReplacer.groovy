package com.challenge

import java.util.regex.Pattern
import groovy.io.FileType


class StringReplacer {
    static void main(String[] args) {

        // assigning input arguments
        def fileDir = args[0]
        def origText = args[1]
        def newText = args[2]
        def optLogFile = createLogFile(args)

        // getting all files from given directory
        def files = getFiles(fileDir)

        // checking if given directory is valid and/or if there are any files
        if (files == null) {
            print "Not a valid directory. Please try again."
        } else if (files.length == 0) {
            print "Directory is empty. No files to modify."
        } else {
            replace(files, origText, newText, optLogFile)
        }
    }

    static File[] getFiles(String path) {
        // getting a list of files in given directory and subdirectories via recursive method
        // https://stackoverflow.com/questions/3953965/get-a-list-of-all-the-files-in-a-directory-recursive

        def list = []

        def dir = new File(path)
        if (!dir.isDirectory()) {
            // returning null in case path is not valid
            return null
        }

        dir.eachFileRecurse(FileType.FILES) { file ->
            list << file
        }

        return list
    }

    static replace(File[] files, String origText, String newText, File logFile) {

        // if optional argument passed, log file is created
        if (logFile != null) {

            if (!logFile.exists()) {

                // checking if log directory exists and creating one if it doesn't yet
                def logDir = new File(logFile.absolutePath.substring(0, logFile.absolutePath.lastIndexOf("/")))
                if (!logDir.isDirectory()) {
                    logDir.mkdir()
                }

                logFile.createNewFile()
            }
        }

        // iterating through each file and replacing given text/pattern
        for (File f in files) {
            Pattern pattern = ~origText
            def fileContent = f.text

            // logging modified files
            if (fileContent.contains(origText) && logFile != null) {
                logFile.append(String.format("%s - %s\n", new Date().toString(), f.absolutePath))
                fileContent = fileContent.replaceAll(pattern, newText)
                f.withWriter { writer -> writer.writeLine(fileContent) }
            }
        }
    }

    // returning null if optional argument wasn't passed
    static def createLogFile(String[] args) {
        args.length > 3 ? new File(args[3]) : null
    }

    static def getFileDirectory(File file) {
        file.absolutePath.substring(0, file.absolutePath.lastIndexOf("/"))
    }
}
