import java.util.NoSuchElementException;

/**
 * A special form of a String with only a few allowable operations. Internally, an NCString is modeled as a String,
 * but it only exposes a few methods for us to use, which let us ask the length, first, last, and middle characters
 * of the string. We cannot ask if two NCStrings are equal directly.
 */
public class NCString {

    /** The internal String representation. */
    private final String string;

    /**
     * Constructs a new NCString with the given String.
     *
     * @param string The base String to use.
     */
    public NCString(final String string){
        if (string == null){
            throw new IllegalArgumentException("String cannot be null.");
        }
        this.string = string;
    }

    /**
     * Returns the number of characters in the NCString.
     *
     * @return The number of characters in this NCString.
     */
    public int getLength(){
        return this.string.length();
    }

    /**
     * Returns the first character of the NCString. The NCString is expected to be nonempty, or else
     * a NoSuchElementException will be thrown. Note that the first character may also be the last character if
     * the NCString only has length 1.
     *
     * @return The first character in this NCString, if it exists.
     */
    public char getFirstChar(){
        if (this.string.length() == 0){
            throw new NoSuchElementException("The NCString is empty!");
        }
        return this.string.charAt(0);
    }

    /**
     * Returns the last character of the NCString. The NCString is expected to be nonempty, or else
     * a NoSuchElementException will be thrown. Note that the last character may also be the first character if
     * the NCString only has length 1.
     *
     * @return The last character in this NCString, if it exists.
     */
    public char getLastChar(){
        if (this.string.length() == 0){
            throw new NoSuchElementException("The NCString is empty!");
        }
        return this.string.charAt(this.string.length()-1);
    }

    /**
     * Returns the characters in between the first and last characters of the NCString. If the NCString has length 2,
     * this will be the empty string. If the NCString is empty or has only one character, there is no middle and so
     * a NoSuchElementException will be thrown.
     *
     * @return The middle characters of this NCString as a new NCString.
     */
    public NCString getMiddleChars(){
        if (this.string.length() <= 1){
            throw new NoSuchElementException("The NCString has no middle.");
        }
        return new NCString(this.string.substring(1,this.string.length()-1));
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode(){
        return this.string.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return "<NCString with value: \"" + this.string + "\">";
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean equals(final Object obj){
        throw new UnsupportedOperationException("You cannot directly compare NCString objects!");
    }

}
