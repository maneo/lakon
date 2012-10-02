package org.grejpfrut.ac.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Item {

    private String link;

    private String title;

    private String category;

    private String summary;

    private String article;
    
    private String date;

    public Date getDate() {
    	Date dat;
		try {
			dat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z",Locale.US).parse(this.date.trim());
		} catch (ParseException e) {
			return new Date();
		}
		return dat;
	}
    
    public String getDateString() {
    	if ( this.date != null ) return this.date.trim();
    	return null;
    }

	public String getArticle() {
        return article;
    }

    public String getSummary() {
        return summary;
    }

    public String getCategory() {
        return category;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void appendCategory(String cat) {
        if (this.category == null)
            this.category = new String(cat);
        else
            this.category = this.category + cat;
    }

    public void appendLink(String link) {
        if (this.link == null)
            this.link = new String(link);
        else
            this.link = this.link + link;
    }

    public void appendTitle(String tit) {
        if (this.title == null)
            this.title = new String(tit);
        else
            this.title = this.title + tit;
    }

    public void appendSummary(String summ) {
        if (this.summary == null)
            this.summary = new String(summ);
        else
            this.summary = this.summary + summ;
    }

    public void appendArticle(String art) {
        if (this.article == null)
            this.article = new String(art);
        else
            this.article = this.article + art;
    }
    
    public void appendDate(String dat){
    	if (this.date == null)
    		  this.date = new String(dat);
    	else 
    		  this.date = this.date + dat;
    }

    /**
     * Gets identifier used to create file name for this item.
     * 
     * @return id
     */
    public String getIdentifier() {
        int lastslash = link.lastIndexOf("/");
        int dothtml = link.indexOf(".html");
        return link.substring(lastslash + 1, dothtml);
    }

    /**
     * Gets rid off all whitespaces, tabs.
     */
    public void cleanData() {

        if (category != null)
            category = category.trim();
        if (link != null)
            link = link.trim();
        if (title != null)
            title = title.trim();
        if (summary != null) {
            summary = summary.trim();
            summary = summary.replace("\n\t\t\t\t", " ");
        }
        if (article != null)
            article = this.article.trim();

    }

}
