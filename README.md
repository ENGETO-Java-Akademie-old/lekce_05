<p align="center">
  <img src="https://engeto.cz/wp-content/uploads/2019/01/engeto-square.png" width="200" height="200">
</p>

# Java - 5. lekce

## Co bychom měli mít hotové

Máme zmáklé proměnné, výrazy, funkce a třídy a i tušíme, jakou roli hrají v OOP. Náš kód už nemusí jen jednoduchou posloupností příkazů, protože už ovládáme i podmínky a cykly. Ideální základ, na kterém by už šel postavit nějaký jednoduchý projekt.

## Co nás čeká

Dnešní lekce se bude celá točit okolo našeho prvního projektu. Který si společně zpracujeme od A až do Z. Tedy od analýzy zadání až po zpracování možných rozšíření.
Použijeme skoro všechny znalosti, které jste nabyli za poslední 4 lekce a přidáme si k nim i pár novinek.

- [Projekt](#projekt)

A jeho jednotlivé části:

- [Zadání](#zadání)

- [Analýza zadání](#analýza-zadání)

- [Načítání dat](#načítání-dat)

- [Parsování dat](#parsování-dat)

- [Uložení dat](#uložení-dat)

- [Zpracování dat](#zpracování-dat)

- [Vypsání výsledku](#vypsání-výsledku)

- [Rozšíření](#rozšíření)

## Projekt

### Zadání

Úkolem je naprogramovat aplikaci, která vypíše všechny státy evropské unie, které mají daň vyšší, než 18%. Aktuální data o jedotlivých státech a jejich daních budou dodány vždy v souboru - vzorový soubor je ke stažení zde: [vat-eu.csv](https://github.com/ENGETO-Java-Akademie/lekce_05/blob/main/vat-eu.csv).

Na každém řádku v tomto souboru budou informace vždy o jednom státu a to tyto a v tomto pořadí:

- zkratka státu (AT)

- název státu (Austria)

- plná sazba daně z přidané hodnoty (20)

- snížená sazba daně z přidané hodbnoty (10)

- informace o tom, jestli země používá speciální sazbu DPH pro některé produkty (true)

Jednotlivé hodnoty jsou odděleny vždy tabulátorem.

A jako výsledek budeme vždy vypisovat jméno státu a za ním v závorce jeho sazbu DPH.

### Analýza zadání

Pokud vytváříme aplikaci pro někoho dalšího, tak pro nás práce začne analýzou zadání. Během ní je dobré si jasně definovat vstupy a výstupy, pokud jsme nedostali jejich přesnou specifikaci a aplikaci si rozdělit na jednotlivé problémy, které budeme muset řešit.

My víme, že budeme zpracovávat data, která dostaneme v nějakém souboru a proto bude první krok to, že si je budeme muset [načíst](#načítání-dat). Víme také, že s nimi budeme dále pracovat, což znamená, že jednotlivé řetězce, které budou představovat vždy jeden řádek ze souboru a tedy jeden záznam, budeme potřebovat převést do nějaké použitelnější podoby - [rozparsovat je](#parsování-dat), a když je rozparsujeme, tak je budeme muset nějak [uložit](#uložení-dat). Až budeme mít rozparsované a uložené všechny záznamy, tak můžeme provést samotné [zpracování dat](#zpracování-dat) a nakonec [vypsat výsledek](#vypsání výsledku) v požadovaném formátu.

### Načítání dat

Víme, že budeme načítat data ze souboru. To jde v Javě řešit mnoha způsoby. My pro načítání použijeme již předpřipravenou třídu <b>Scanner</b>.

Konstruktor třídy Scanner bere jako parametr vždy nějaký zdroj dat (soubor, cestu k souboru, přímo nějaký řetězec, atd.) a samotná třída pak nabízí metody pro načítání a kontrolu toho, jestli jsou ještě ve zdroji nějaká data k načtení.

Předtím si ale budeme muset vytvořit nějakou reprezentaci toho našeho souboru, ze které budeme data načítat. K tomu nám poslouží třída <b>File</b>. Parametrem jejího konstruktoru je cesta k tomu souboru s daty. Po vytvoření pak tento objekt předáme jako parametr konstruktoru právě třídě Scanner.

```java
File file = new File("Cesta/k/souboru/ktery/chete/nacist.txt");

Scanner scanner = new Scanner(file);
```
Nyní máme vytvořenou instanci třídy Scanner, připravenou ke čtení z toho našeho souboru.

Řekli jsme si, že ke čtení dat a kontrole toho, jestli je ještě co číst, má třída Scanner připravených spoustu metod. (jaké všechny to jsou mohou zvídaví studentí najít v dokumentaci [zde](https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html)).

Nám budou stačit pouze dvě.

Víme, že jeden řádek vždy představuje jeden záznam, a proto se nám bude hodit metoda, která nám načte vždy jeden řádek - tou je:

```java
scanner.nextLine();
```

A druhá metoda, která se nám bude hodit, je pak:

```java
scanner.hasNextLine();
```
Ta vrací true/false podle toho, jestli máme ještě nějaký řádek k přečtení.

Ve výsledku by to naše načítání mohlo vypadat nějak takto:

```java
File nasSouborSDaty = new File("cesta/k/nasemu/souboru/s/daty.txt");

Scanner scanner = new Scanner(nasSouborSDaty);

while (scanner.hasNextLine()) {
    String zaznamKRozparsovani = scanner.nextLine();
}

```

### Parsování dat

Teď, když máme kód, který nám postupně načte všechny řádky toho našeho souboru s daty, budeme potřebovat nějakou funkci, která je rozparsuje.

Na vstupu máme řetezec, který představuje záznam o jedné zemi, a chceme z něj dostat jednotlivé údaje.

O nich víme, že jsou v tomto řetězci odděleny tabulátorem a taky známe jejich přesné pořadí.

Bedeme tedy potřebat nějak rozdělit ten vstupní řetězec - a přesně k tomu slouží jedna metoda třídy String - "split".

A protože náš vstupní řetězec je právě instancí třídy String, tak tu metodu můžeme zavolat přímo na něm:

```java
vstupniRetezec.split("\t");
```

Tato metoda bere jako parametr regulární výraz popisující znak, či znaky, pomocí kterých bude řetězec rozdělen a vrací pole řetězců, které vzniknou po rozdělení toho řetězce, na kterém tu metodu zavoláme.

Popis jednotlivých konstruktů, které jde použít jako parametr, najdete [zde](https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html#sum). Nám stačí ale vědět, že pokud jde o běžný znak, tak ho stačí dát prostě do uvozovek:

```
String vstup = "Bruno,Alfonz,Tamas,Ulrich";

String[] jmena = vstup.split(",");
```
Pole jmena pak bude vypadat takto:

```java
["Bruno","Alfonz","Tamas","Ulrich"]
```
A v případě, že jde o speciální symbol, jako třeba náš tabulátor, je jeho zápis k dohledání v dokumentaci.

Teď máme víme, jak rozdělit vstupní řetězec na jednotlivé části a jsme schopni si na toto rozdělování vytvořit metodu:

```java

void rozparsujData(String data) {
   
   String[] rozparsovanaData = data.split("\t");
   
   // víme, že na první pozici bude zkratka státu (pozor, indexujeme od nuly, takže první pozice je na indexu 0)
   System.out.println("Zkratka státu: " + rozparsovanaData[0]);
   
   // víme, že na druhé pozici bude název státu
   System.out.println("Název státu: " + rozparsovanaData[1]);
   
   // víme, že na třetí pozici bude plná daň
   System.out.println("Plná daň: " + rozparsovanaData[2]);
   
   // víme, že na čtvrté pozi bude snížená daň
   System.out.println("Snížená daň: " + rozparsovanaData[3]);
   
   // víme, že na páte pozici bude informace o tom, jestli země používá pro některé produkty speciální sazbu
   System.out.println("Speciální sazba: " + rozparsovanaData[4]);
   
}

```

Tato metoda by nám data rozparsovala a vypsala - my s nimi ale budeme chtít dále pracovat, takže nám vypsání stačit nebude - pro to, abychom si je ale z metody mohli vrátit, nám chybí vhodná struktrura. Budeme si tedy nějakou muset vytvořit.

### Uložení dat

Jak už jsme si řekli několikrát v předchozích lekcích, tak ukládání nějakých složitějších datových struktur řešíme pomocí tříd.

Vytvoříme si tedy třídu, do které půjde uložit informaci o daňových sazbách jednoho státu a tuto třídu pak budeme moct použít i jako návratový typ naší metody k parsování dat.

Ta třída by mohla vypadat nějak takto:

```java
public class CountrysTaxes {

    private String countryAbbreviation;
    
    private String countryName;
    
    private Float fullTaxRate;
    
    private Float loweredTaxRate;
    
    private Boolean usesSpecialTaxRate;

    // konstruktor

    // gettery a settery

}
```

A naše upravená metoda na parsování takto:

```java
private CountrysTaxes rozparsujData(String data) {
   
   String[] rozparsovanaData = data.split("\t");
   
   CountrysTaxes result = new CountrysTaxes();
   
   result.setCountryAbbreviation(rozparsovanaData[0]);

   result.setCountryName(rozparsovanaData[1]);

   /*
    * po rozparsování jsou všechny hodnoty řetezce, takže když chceme
    * se sazbami DPH pracovat jako s desetinnými čísly, tak je musíme
    * nejdřív převést
    */
   result.setFullTaxRate(Float.valueOf(rozparsovanaData[2]));

   result.setLoveredTaxRate(Float.valueOf(rozparsovanaData[3]));

   result.setUsesSpecialTaxRate(Boolean.valueOf(rozparsovanaData[4]));
   
   return result;
   
}
```

Když tuto metodu budeme volat v tom našem cyklu, tak si postupně vybudujeme List všech těch danových sazeb.

```java
List<CountrysTaxes> countrysTaxesList = new ArrayList<>();
```

A v cyklu pak budeme do listu přidávat hodnoty:
```java
countrysTaxesList.add(rozparsujData(zaznamKRozparsovani));
```

### Zpracování dat

Máme připravený list daňových sazeb, které máme za úkol protřídit tak, aby zůstaly jen ty, co splňují zadanou podmínku - základní sazba DPH je větší, než 18%.

Protože je možné, že z daty budeme chtít provádět ještě nějaké další operace, tak nebudeme měnit ten původní list, ale vytvoříme si metodu, která ten původní list dostane jako parametr a jako výsledek vrátí nový list s vyfiltrovanými daňovými sazbami.

```java
public List<CountrysTaxes> filterCountrysWithRateHigherThan18(List<CountrysTaxes> countrysTaxesList) {
    List<CountrysTaxes> resultList = new ArrayList<>();
    for (CountrysTaxes ct : countrysTaxesList) {
        if (ct.getFullTaxRate() > 18.0F) {
            resultList.add(ct);
        }
    }
    return resultList;
}
```

### Vypsání výsledku

V předchozím bodě jsme si profiltrovali ty naše záznamy a získali jsme tak <b>List</b> sazeb, které vyhovovaly zadané podmínce.

Podle zadání máme vždy vypsat Jméno státu a v závorce plnou daňovou sazbu.

Připravíme si tedy metodu, která nám vypíše všechny sazby, které si předáme jako parametr:

```java
public void printTaxesToConsole(List<CountrysTaxes> countrysTaxesList) {

    for (CountrysTaxes ct : countrysTaxesList) {
    
        System.out.println(ct.getCountryName() + " (" + ct.getFullTaxRate().toString() + ")");
    
    }

}

```

### Rozšíření

Pro případ, že bychom hlavní část zadání stihli rychleji, než bylo v plánu, tu mám ještě dvě rozšíření:

1.) Přidej do programu ovládání skrze konzoli a možnost vypsat informace o sazbách v konkrétním státě.

Drobná nápověda - Scanner umí načítat i data z konzole a to takto:

```java
String oblibenaBarva;
// vytvoříme si Scanner, kterému tentokrát nepředáme konkrétní soubor, ale System.in, čímž mu řekneme, že budeme načítat z konzole
Scanner scanner = new Scanner(System.in);
// požádáme uživatele o zadání nějaké hodnoty
System.out.println("Zadej svoji oblíbenou barvu:");
// a tu hodnotu načteme do proměnné oblibenaBarva
oblibenaBarva = scanner.nextLine();
```

2.) Přidej možnost vypsání států seřazených podle jejich daňových sazeb.
