package org.grejpfrut.wiki.data;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * Representation of a separate wiki page.
 * 
 * @author Adam Dudczak
 */
public final class WikiSite {
    private StringBuffer title = new StringBuffer();
    private StringBuffer article = new StringBuffer();

    /**
     * Sets the current content to the given string.
     */
    public void setArticle(String newContent) {
        this.article.setLength(0);
        this.article.append(newContent);
    }

    /**
     * Appends the string to the current content of 
     * the article.
     */
    public void appendArticle(String string) {
        this.article.append(string);
    }

    /**
     * Appends a subarray of characters to the article content.
     */
    public void appendArticle(char [] charArray, int offset, int len) {
        this.article.append(charArray, offset, len);
    }

    /**
     * @return Returns the content of the article.
     */
    public String getArticle() {
        this.article.trimToSize();
        return this.article.toString();
    }

    /**
     * Appends a given string to the current title of
     * the page.
     */
    public void appendTitle(String string) {
        this.title.append(string);
    }
    
    /**
     * Appends a given subarray of characters to the title.
     */
    public void appendTitle(char [] charArray, int offset, int len) {
        this.title.append(charArray, offset, len);
    }

    /**
     * Sets the current content of the title to the
     * given string.
     */
    public void setTitle(String title) {
        this.title.setLength(0);
        this.title.append(title);
    }

    /**
     * @return Returns the title.
     */
    public String getTitle() {
        this.title.trimToSize();
        return title.toString();
    }

    /**
     * Returns a new Lucene document corresponding to this
     * wiki page. The document has two fields: <code>title</code>
     * and <code>text</code>.
     */
    public Document getLuceneDocument() {
        final Document doc = new Document();
        doc.add(new Field("title", this.getTitle(), Field.Store.YES,
                Field.Index.UN_TOKENIZED, Field.TermVector.NO));
        doc.add(new Field("text", this.getArticle(), Field.Store.NO,
                Field.Index.TOKENIZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
        return doc;
    }
}
