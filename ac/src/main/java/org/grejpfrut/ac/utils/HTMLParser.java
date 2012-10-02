package org.grejpfrut.ac.utils;

import java.io.UnsupportedEncodingException;

import org.grejpfrut.ac.cleaners.ArticleCleaner;

/**
 * This class gets article from HTML, assuming that summary will be extracted during
 * RSS parsing, we get only body of article.
 * @author ad
 *
 */
public class HTMLParser {
    
    private static final String artStartTag = "<div id=\"artykul\">";
    private static final String artEndTag = "</div>";
    
    public String getArticle(String html) throws UnsupportedEncodingException{
    	html = new String(html.getBytes(),"ISO-8859-2");
        int artStart = html.indexOf(artStartTag);
        html = html.substring(artStart+artStartTag.length());
        int artEnd = html.indexOf(artEndTag);
        html = html.substring(0,artEnd);
        html = html.replace("èR&Oacute;D£O:", "");
        
        return ArticleCleaner.clean(html).trim();
        
    }

}
