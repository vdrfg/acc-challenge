package com.challenge

class StringReplacer {
    static void main(String[] args) {
        String filePath = "file/path"
        String fileContents = new File(filePath).text

        println fileContents

        def regex = "input string"
        def pattern = ~regex

        fileContents = fileContents.replaceFirst(pattern, "replacement")

        println fileContents
    }
}
