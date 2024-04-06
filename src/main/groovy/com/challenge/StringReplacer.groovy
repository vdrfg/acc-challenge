package com.challenge

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.regex.Pattern
import groovy.io.FileType


class StringReplacer {
    static void main(String[] args) {

        // reading input arguments
        def fileDir = args[0]
        def origText = args[1]
        def newText = args[2]

        // log directory optional, hence null by default
        def logPath = null
        if (args.length > 3) {
            logPath = args[3]
        }

        def files = getFiles(fileDir)

        // checking if given directory is valid and/or if there are any files
        if (files == null) {
            print "Not a valid directory. Please try again."
        } else if (files.length == 0) {
            print "Directory is empty. No files to modify."
        } else {
            replace(files, origText, newText, logPath)
        }
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
            list << file
        }

        return list
    }

    static replace(File[] files, String origText, String newText, String logPath) {

        def logFile = null

        // if optional argument passed, log file is created
        if (logPath != null) {

            logFile = new File(logPath)

            if (!logFile.exists()) {

                // checking if log directory exists and creating one if it doesn't yet
                def logDir = new File(logPath.substring(0, logPath.lastIndexOf("/")))
                if (!logDir.isDirectory()) {
                    logDir.mkdir()
                }

                logFile.createNewFile()
            }
        }

        // iterating through each file and replacing given text/pattern
        // https://www.tutorialspoint.com/groovy/groovy_file_io.htm
        for (File f in files) {
            Pattern pattern = ~origText
            def fileContent = f.text

            if (fileContent.contains(origText) && logFile != null) {
                logFile.append(new Date().toString() + ' - ' + f.absolutePath + '\n')
            }

            fileContent = fileContent.replaceAll(pattern, newText)
            f.withWriter { writer -> writer.writeLine(fileContent) }
        }
    }
}
