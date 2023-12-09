# Struktura binary heap v javě
Semestrální práce z předmětu Datové struktury zaměřená na tvorbu struktury **prioritní fronta**,
která bude realizována jako **levostranná halda na poli** s využitím

* Pole s prioritní frontou, do kterého se vkládají prvky ze struktury binary-tree 
* Struktury binary-tree, která bude využívat: 
  * Iterátor do šířky/hloubky
  * Vkládání do struktury pomocí comparable rozhraní
  * Prohlídka struktury dle **IN-ORDER**
  * Zásobník **LIFO/FIFO** interpretovaný jako **non-cyclic double-linked-list**
* **JavaFx** knihoven pro základní operaci s vytvořenou agendou **AgendaKraj** 
* **Generátoru** pro generaci testovacích dat
* **Data-loaderu** pro import/save operace s daty 

Aplikace je realizována tak, aby se v GUI dalo přepínat mezi implementovanými strukturami
### Vzorová ukázka binárního stromu naplněného testovacími daty
![Ukázka vzorových dat uložených do struktury bin-tree](doc/strom.png  "bin-tree")