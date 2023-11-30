# Struktura binary heap v javě
Semestrální práce z předmětu Datové struktury zaměřená na tvorbu struktury **prioritní fronta**,
která bude realizována jako **levostranná haldy na poli** s využitím

* Pole do kterého se budou dle prioritní fronty vkládat prvky ze struktury binary-tree 
* Struktury binary-tree, která bude využívat: 
  * Iterátor do šířky/hloubky
  * Vkládání do struktury pomocí comparable rozhraní
  * Prohlídka struktury dle **IN-ORDER**
  * Zásobník **LIFO/FIFO** interpretovaný jako **non-cyclic double-linked-list**
* **JavaFx** knihoven pro základní operaci s vytvořenou agendou **AgendaKraj** 
* **Generátoru** pro generaci testovacích dat
* **Data-loaderu** pro import/save operace s daty 

### Vzorová ukázka binárního stromu naplněného testovacími daty
![Ukázka vzorových dat uložených do struktury bin-tree](doc/strom.png  "bin-tree")