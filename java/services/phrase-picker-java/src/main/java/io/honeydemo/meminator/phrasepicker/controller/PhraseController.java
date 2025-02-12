package io.honeydemo.meminator.phrasepicker.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhraseController {

    private static final List<String> PhraseList = Arrays.asList(
            "you're muted",
            "not dead yet",
            "Let them.",
            "Boiling Loves Company!",
            "Must we?",
            "SRE not-sorry",
            "Honeycomb at home",
            "There is no cloud",
            "This is fine",
            "It's a trap!",
            "Not Today",
            "You had one job",
            "bruh",
            "have you tried restarting?",
            "try again after coffee",
            "deploy != release",
            "oh, just the crimes",
            "not a bug, it's a feature",
            "test in prod",
            "who broke the build?",
            "it could be worse");

    @GetMapping("/phrase")
    public PhraseResult hello() {
        // choose a random phrase from the list
        String chosenPhrase = PhraseList.get((int) (Math.random() * PhraseList.size()));
        return new PhraseResult(chosenPhrase);
    }

    public static class PhraseResult {
        private String phrase;

        public PhraseResult(String phrase) {
            this.phrase = phrase;
        }

        public String getPhrase() {
            return phrase;
        }

        public void setPhrase(String phrase) {
            this.phrase = phrase;
        }
    }
}
