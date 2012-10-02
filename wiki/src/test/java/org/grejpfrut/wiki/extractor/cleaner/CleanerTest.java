package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.CleaningAgent;

public class CleanerTest extends TestCase {

    private final static String wikiMarkup1 = "'''ActiveX''' � rodzaj "
            + "[[Komponent (informatyka)|komponent�w]], kontrolek mo�liwy do "
            + "u�ycia w programach pisanych za pomoc� takich narz�dzi jak [[Delphi]],"
            + " [[Visual Basic]], [[C++]], [[Java]], [[Power Builder]] i wielu innych."
            + " Kontrolki s� du�ym u�atwieniem dla programisty - pozwalaj� oszcz�dzi� "
            + "czas kt�ry trzeba by po�wi�ci� na pisanie w�asnych kontrolek; maj�c do "
            + "dyspozycji technologie ActiveX, mo�na skorzysta� z ju� gotowych kontrolek,"
            + " oferowanych przez licznych producent�w.";

    private final static String text1 = "ActiveX � rodzaj "
            + "komponent�w, kontrolek mo�liwy do "
            + "u�ycia w programach pisanych za pomoc� takich narz�dzi jak Delphi,"
            + " Visual Basic, C++, Java, Power Builder i wielu innych."
            + " Kontrolki s� du�ym u�atwieniem dla programisty - pozwalaj� oszcz�dzi� "
            + "czas kt�ry trzeba by po�wi�ci� na pisanie w�asnych kontrolek; maj�c do "
            + "dyspozycji technologie ActiveX, mo�na skorzysta� z ju� gotowych kontrolek,"
            + " oferowanych przez licznych producent�w.";

    private final static String wikiMarkup2 = "== Linki zewn�trzne == "
            + " * [http://amigaos.pl Polska strona opisuj�ca system AmigaOS 4] "
            + " * [http://os4.hyperion-entertainment.biz Oficjalna strona systemu AmigaOS 4] "
            + "[[Kategoria:Amiga]]" + "[[Kategoria:systemy operacyjne]]"
            + "[[Kategoria:Graficzne systemy operacyjne]]" + "[[ca:AmigaOS]]"
            + "[[da:AmigaOS]]";

    private final static String text2 = "== Linki zewn�trzne == "
            + " * Polska strona opisuj�ca system AmigaOS 4 "
            + " * Oficjalna strona systemu AmigaOS 4 " + "Amiga"
            + "systemy operacyjne"
            + "Graficzne systemy operacyjne";

    private final static String wikiMarkup3 = "'''Alleny''' (inaczej [[dien]]y skumulowane) to [[w�glowod�r|w�glowodory]],"
            + " w kt�rych jeden z atom�w w�gla jest zwi�zany z dwoma innymi atomami w�gla [[wi�zanie chemiczne|wi�zaniami]] "
            + "podw�jnymi (C=C=C)."
            + " &lt;br&gt;&lt;table&gt;&lt;tr&gt;&lt;td&gt;"
            + "[[Grafika:allen_propylowy.png]]"
            + "&lt;/tr&gt;&lt;/td&gt;&lt;tr&gt;&lt;td&gt;"
            + "allen propylowy (1,2-propadien)"
            + "&lt;/tr&gt;&lt;/td&gt;&lt;/table&gt;&lt;p&gt;";

    private final static String text3 = "Alleny (inaczej dieny skumulowane) to w�glowodory," +
            " w kt�rych jeden z atom�w w�gla jest zwi�zany z dwoma innymi atomami w�gla " +
            "wi�zaniami podw�jnymi (C=C=C). &lt;br&gt;&lt;table&gt;&lt;tr&gt;&lt;td&gt;" +
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
