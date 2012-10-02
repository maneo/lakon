org.grejpfut.wiki 

1. wymagania: maven2, opis pozostałych zależności pom.xml

2. maven: 
       mvn eclipse:eclipse    - tworzy deskryptory Eclipse'owe
	   mvn assembly:directory - w katalogu target/wiki-jar gotowy do uruchomienia jar z zależnościami
	   mvn assembly:assembly  - w katalogu target wersja dystrybucyjna .zip
	   
3. konfiguracja - src/main/resources/conf.properties


    lucene.index.dir 	     - katalog w którym znajduję się index zawierąjacy strony z wikipedii
    
    clean.wiki.markup 		 - wlasność używana przy wybieraniu artykułów 
     						   z xml wikipedii, czy czyścic markup wikipedii
    used.cleaners			 - lista używanych cleanerów,  tylko nazwy klas
                               (wszystkie muszą być z pakietu org.grejpfrut.wiki.cleaners )
    desperate.cleaner.chars  - znaki, lub ciagi znakow ktore maja byc po prostu wyciete, 
    					       konfiguracja DesperateCleaner
    ignored.pages  	         - lista (oddzielnonych przecinkami wyrażeń  regularnych ) 
                               tytułów artykułów wikipedii które mają być pominiete 
                               przy indeksowaniu
    data.dump.xml 			 - ścieżka do testowych plików z zrzutami bazy danych
    indexer.ignored.words    - stop words
	indexer.putStems.insteadOf.articles - czy artykuły mają być przerabiane na stemy 
	                                      i potem wrzucane do indeksu
    indexer.stemmer.class    - klasa stemmer'a, klasa powinna rozszerzać 
                               klasę org.grejpfrut.wiki.utils.Stemmer
	number.of.keywords 	     - domyslna liczba slow kluczowych uzywanych w zapytaniu porownawczym
	number.of.doc.in.result  - domyslna liczba zwracanych artykulow
	min.tfidf.value 	     - minimalna wartosc tfidf
	
4. dzialanie

	org.grejpfrut.wiki.SearchingApp 

	 Jako parametr przyjmuje nazwę pliku (UTF-8) z artykułem. Korzysta tylko z domyślnej konfiguracji.(TODO)
	 
	org.grejpfrut.wiki.SwingSearchingGui 
	
	 Jako parametr można podac plik z własną konfigurację, jezeli nie zostanie podany uruchamiana jest z domyslna.

	org.grepjfrut.wiki.IndexingApp 
	
		usage: org.grejpfrut.wiki.indexer.IndexingApp [OPTIONS]
		
	    -dd,--data-dump <file>           ścieżka do pliku z zrzutem bazy danych.
        -cp,--config-properties <file>   ścieżka do pliku z konfiguracją.
        -a                               przełącznik który mówi: indeksujemy abstract'y.
        -o,--index <dir>                 katalog w wyjściowy dla wygenerowanego indeksu.
        -p                               przełącznik który mówi: indeksujemy pełne artykuły.
		    
5. todo
	
	2. Niezalezna konfiguracja Cleanerów 
	3. Zapisywanie wyników dzialania GUI do xml (??)
	4. Dac mozliwosc pogladania tego co wypluja cleanery i stemmer.
	5. commandline'owe sterowanie GUI, w linii polecen wpisujemy co ma zrobic, jakie otworzyc pliki, jakie parametry
	   na zakladkach. otwieranie za kazdym razem pliku/ustawianie parametrow zeby sprawdzic czy dziala jest meczace.
	6. przejsc z propertiesami na xml, niech zyje utf-8 w properties. 
	
6. Błedy

	1. wygląda na to, że tfidf jest źle liczone powinno być względem liczby
	wszystkich wyrazów, a nie liczby unikalnych wyrazów.