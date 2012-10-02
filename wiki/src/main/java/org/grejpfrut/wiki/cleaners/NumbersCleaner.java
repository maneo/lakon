package org.grejpfrut.wiki.cleaners;

/**
 * Do numbers have any meaning when we are looking for topic?
 *  
 * @author Adam Dudczak
 */
public class NumbersCleaner extends BasicCleanerImpl {
    
    private final static String NUMBER_REGEXP = "\\s[\\d]+\\s";

    public NumbersCleaner() {
        super(NUMBER_REGEXP);
    }
    
    @Override
    public String processTokens(String token) {
        return " ";
    }
}
