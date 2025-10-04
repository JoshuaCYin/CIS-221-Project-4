public class Reference implements Comparable<Reference> {
    private String word;                            // Word in book
    private CollectionInterface<String> verses;     // Verses containing the word

    public Reference(String newWord) {
        word = newWord;
        verses = new LinkedCollection<>();
    }

    public String getWordIs(){ return word; }
    public String getVerses(){ return verses.toString(); }

    // Adds a verse to the collection of verses containing the word
    public void addVerse(String verse) {
        verses.add(verse);
    }

    // Compare words (alphabetical order)
    public int compareTo(Reference other) {
        return this.word.compareTo(other.word);
    }

    public String toString() {
        return("Word: " + word + "\n" +
               "Verses: " + verses);
    }
}