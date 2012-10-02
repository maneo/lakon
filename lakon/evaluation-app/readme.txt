Evaluation-app

Spis tresci:
1. Maven
2. Instalacja

1. Maven

Przed zbudowanie war'a nale¿y okreœliæ parametry po³¹czeniowe bazy danych, znajduj¹ siê
one w pliku conf.properties.utf8. Plik ten jest kodowany utf-8, po zbudowaniu projektu
zostanie przekszta³cony w conf.properties, obie wersje pliku zostan¹ skopiowane do 
WEB-INF/classes aplikacji webowej.

Aby zbudowaæ war'a nale¿y wpisaæ: mvn package, war zostanie utworzony w katalogu target.

2. Instalacja

Dla wszystkich baz z wyjatkiem hsqldb: hibernate sam tworzy schemat, o ile 
hibernate.hbm2ddl.auto jest ustawione na "update". Aby wszystko dzialalo ok, nalezy
dodac uzytkownika z prawa administratora:

insert into users values (1,'admin@admin.com','admin',10, now());

Mozna tez zarejestrowac sie, i w bazie zmienic wartosc pola  user_type na 10.

Dla HSQLDB wystarczy u¿yc bazy ktora jest dostarczona z projektem, jedyne co (oprocz
parametrow polaczeniowych) trzeba zmienic to wartosc hibernate.hbm2ddl.auto na validate. 
Z nieznanych mi blizej powodow, update nie dziala dla hsqldb.

Gotowa baze hsql z domyslnymi wartosciami i tekstami uzytymi w eksperymencie mozna
znalezc w src/main/sql/eval.script.