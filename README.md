# KTS-NVT_Tim4 - Sbv Transport
Predmetni projekat iz KTS i NVT (Konstrukcija i testiranje softvera i Napredne web tehnologije)

#Članovi tima:
1. Srbislav Stojić SW83-2016
2. Vladimir Gajčin SW49-2014
3. Bojana Ćorilić SW39-2014

#Projektni zadatak
-Realizovati aplikaciju za digitalizaciju gradskog saobraćaja.

#Korišćene tehnologije:
1. Java + Spring Boot 
2. REST servisi
3. MySql baza podataka
4. Angular 7
5. Bootstrap

#Urađeni testovi:
1. Unit i integracioni svih service/controller u Spring Boot aplikaciji
2. Angular testovi service u Angular aplikaciji
3. Selenium testovi (e2e)

#Uputstvo za pokretanje projekta
1. Clone/Download projekat sa github-a sa 			https://github.com/ssrbislav/KTS-NVT_Tim4
2. Aplikacija sbv-transport:
  U Eclipse-u importujte projekat:
  - desni klik
  - import...
  - Existing Project
   Build projekat:
  - desni klik -> Run as -> Maven clean
  - desni klik -> Run as -> Maven build (u goals 	ukucajte package)
   Pokretanje projekta:
  - desni klik -> Run as -> Spring Boot App
  -U fajlu aplication.properties se nalaze podesavanja za aplikaciju:
  - spring.datasource.url = jdbc:mysql://localhost:3306/sbvtransport?useSSL=false&createDatabaseIfNotExist=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
  - spring.datasource.username=root
  - spring.datasource.password=root
  Da bi se aplikacija pokrenula potrebno je skinuti MySql bazu podataka sa sajta: https://www.mysql.com/downloads/
3.Aplikacija sbv-trancport-front:
   -otvori se cmd as Administrator 
   -pozicionira se na putanji projekta
   -ukuca je ng serve
   -nakon kompajliranja, ode se na Chrome pretraživač i ukuca http://localhost:4200  
4. Aplikacije treba da su pokrenute u isto vreme( prvo Spring Boot pa Angular)

#DODATNO
-U projektu postoji samo jedan admin koji se sam generiše pri pokretanju projekta(username: admin, password:admin)



