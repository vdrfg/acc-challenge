package com.challenge

import java.util.regex.Pattern

class StringReplacer {
    static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

        print "Enter directory: "
        String dirInput = br.readLine()

        print "Enter searched text or pattern: "
        String textInput = br.readLine()

        print "Enter replacement text: "
        String replacementInput = br.readLine()

        replace(dirInput, textInput, replacementInput)
    }

    static def replace(String path, String originalText, String newText) {
        def fileContents = new File(path).text
        Pattern pattern = ~originalText
        fileContents = fileContents.replaceAll(pattern, newText)

        println fileContents

    }
}
