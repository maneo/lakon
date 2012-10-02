org.grejpfut.ac - article collector 

1. wymagania: maven2, opis pozostalych zależności pom.xml
2. maven: 
       mvn eclipse:eclipse    - deskryptory Eclipse'owe
	   mvn assembly:directory - w katalogu target/ac-jar gotowy do uruchomienia jar z zaleznosciami
	   mvn assembly:assembly  - w katalogu target wersja dystrybucyjna .zip
	   
3. konfiguracja - src/main/resources/conf.properties

	work.dir 			 - katalog roboczy
	id.loader.class 	 - klasa wczytujaca id juz zapisanych artykulow 
	cache.size 			 - ile identyfikatorow juz zapisanych artow ma byc wczytanych
	article.storer.class - klasa zapisujaca sciagniety artykul
	target.url 			 - adres rss z ktorego arty beda zapisywane
	http.*				 - wszystkie propertiesy zaczynajace sie od http zostana dolaczone do 
						   naglowka http (np. http.User-Agent wartosc zostanie przypisana do 
						   pola User-Agent naglowka)
	min.delay 			 - minimalna przerwa miedzy sprawdzaniem czy sa nowe wpisy w rss
	
4. dzialanie

    Program laczy sie co jakis czas z  target.url i sprawdza czy sa nowe artykuly, jezeli
    sa zapisuje je na dysku. Aby wyjsc w konsoli w ktorej jest uruchomiony wpisac: exit.
    
    Jako argument na starcie mozna podac plik konfiguracyjny, zmieniajac w tym pliku katalog
    docelowy i url, mozemy jednoczesnie sciagac kilka rss.
		    
5. todo	 

    1.proste gui pozwalające przeglądać, ewentualnie przeszukiwać zgromadzone xml'e.   
    