package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.CleaningAgent;

public class CleanerTest extends TestCase {

    private final static String wikiMarkup1 = "'''ActiveX''' – rodzaj "
            + "[[Komponent (informatyka)|komponentów]], kontrolek mo¿liwy do "
            + "u¿ycia w programach pisanych za pomoc¹ takich narzêdzi jak [[Delphi]],"
            + " [[Visual Basic]], [[C++]], [[Java]], [[Power Builder]] i wielu innych."
            + " Kontrolki s¹ du¿ym u³atwieniem dla programisty - pozwalaj¹ oszczêdziæ "
            + "czas który trzeba by poœwiêciæ na pisanie w³asnych kontrolek; maj¹c do "
            + "dyspozycji technologie ActiveX, mo¿na skorzystaæ z ju¿ gotowych kontrolek,"
            + " oferowanych przez licznych producentów.";

    private final static String text1 = "ActiveX – rodzaj "
            + "komponentów, kontrolek mo¿liwy do "
            + "u¿ycia w programach pisanych za pomoc¹ takich narzêdzi jak Delphi,"
            + " Visual Basic, C++, Java, Power Builder i wielu innych."
            + " Kontrolki s¹ du¿ym u³atwieniem dla programisty - pozwalaj¹ oszczêdziæ "
            + "czas który trzeba by poœwiêciæ na pisanie w³asnych kontrolek; maj¹c do "
            + "dyspozycji technologie ActiveX, mo¿na skorzystaæ z ju¿ gotowych kontrolek,"
            + " oferowanych przez licznych producentów.";

    private final static String wikiMarkup2 = "== Linki zewnêtrzne == "
            + " * [http://amigaos.pl Polska strona opisuj¹ca system AmigaOS 4] "
            + " * [http://os4.hyperion-entertainment.biz Oficjalna strona systemu AmigaOS 4] "
            + "[[Kategoria:Amiga]]" + "[[Kategoria:systemy operacyjne]]"
            + "[[Kategoria:Graficzne systemy operacyjne]]" + "[[ca:AmigaOS]]"
            + "[[da:AmigaOS]]";

    private final static String text2 = "== Linki zewnêtrzne == "
            + " * Polska strona opisuj¹ca system AmigaOS 4 "
            + " * Oficjalna strona systemu AmigaOS 4 " + "Amiga"
            + "systemy operacyjne"
            + "Graficzne systemy operacyjne";

    private final static String wikiMarkup3 = "'''Alleny''' (inaczej [[dien]]y skumulowane) to [[wêglowodór|wêglowodory]],"
            + " w których jeden z atomów wêgla jest zwi¹zany z dwoma innymi atomami wêgla [[wi¹zanie chemiczne|wi¹zaniami]] "
            + "podwójnymi (C=C=C)."
            + " &lt;br&gt;&lt;table&gt;&lt;tr&gt;&lt;td&gt;"
            + "[[Grafika:allen_propylowy.png]]"
            + "&lt;/tr&gt;&lt;/td&gt;&lt;tr&gt;&lt;td&gt;"
            + "allen propylowy (1,2-propadien)"
            + "&lt;/tr&gt;&lt;/td&gt;&lt;/table&gt;&lt;p&gt;";

    private final static String text3 = "Alleny (inaczej dieny skumulowane) to wêglowodory," +
            " w których jeden z atomów wêgla jest zwi¹zany z dwoma innymi atomami wêgla " +
            "wi¹zaniami podwójnymi (C=C=C). &lt;br&gt;&lt;table&gt;&lt;tr&gt;&lt;td&gt;" +
            "[[Grafika:allen_propylowy.png]]&lt;/tr&gt;&lt;/td&gt;&lt;tr&gt;&lt;td&gt;" +
            "allen propylowy (1,2-propadien)&lt;/tr&gt;&lt;/td&gt;&lt;/table&gt;&lt;p&gt;";

    
    private final static String wikiMarkup4 = "[[Kategoria:Alkany|*]]";
    private final static String text4 = "Alkany";
    
    private final static String wikiMarkup5 = "";
    private final static String text5 = "";
    
    public void testSimpleMarkup() {

        CleaningAgent cm = new CleaningAgent();
        String res = cm.cleanMarkup(wikiMarkup1);
     //   assertEquals(text1, res);

    }

    public void testSophMarkup() {

        CleaningAgent cm = new CleaningAgent();
        String res = cm.cleanMarkup(wikiMarkup2);
     //   assertEquals(text2, res);

    }
    
    public void testEmbHTML(){

        CleaningAgent cm = new CleaningAgent();
        String res = cm.cleanMarkup(wikiMarkup3);
      //  assertEquals(text3, res);
        
    }

    public void testShortWhirdCat(){

        CleaningAgent cm = new CleaningAgent();
        String res = cm.cleanMarkup(wikiMarkup4);
      //  assertEquals(text4, res);
        
    }

    public void testEmpty(){

        CleaningAgent cm = new CleaningAgent();
        String res = cm.cleanMarkup(wikiMarkup5);
       // assertEquals(text5, res);
        
    }

}
