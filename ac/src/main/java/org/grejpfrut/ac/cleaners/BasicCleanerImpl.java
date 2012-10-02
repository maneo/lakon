package org.grejpfrut.ac.cleaners;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: Javadoc?
 * 
 * @author Adam Dudczak
 * @author Dawid Weiss (reimplemented pattern matchin loop)
 */
public abstract class BasicCleanerImpl implements MarkupCleaner {

    /**
     * Regular expression used by this cleaner.
     */
    private final Pattern pattern;

    /**
     * Create a cleaner instance and precompile the regular
     * expression for use later.
     */
    protected BasicCleanerImpl(String regularExpression) {
        this.pattern = Pattern.compile(regularExpression);
    }

    /**
     * Override to process a token.
     */
    public abstract String processTokens(String token);

    /**
     * Process the text in a loop, replacing
     * matches with whatever is returned from {@link #processTokens(String)}.
     */
    public String processMatchingMarkup(String text) {
        final Matcher m = pattern.matcher(text);
        final StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, 
                    Matcher.quoteReplacement(processTokens(m.group())));
        }
        m.appendTail(sb);
        
        sb.trimToSize();
        return sb.toString();
    }
}
