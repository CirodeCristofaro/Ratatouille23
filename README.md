# Ratatouille23
Ratatouille23 è un sistema finalizzato al supporto alla gestione e all’operatività di attività di  ristorazione. Il sistema consiste in un’applicazione performante e affidabile, attraverso cui gli utenti possono fruire delle funzionalità del sistema in modo intuitivo, rapido e piacevole. 

#Punti svolti:
* Un amministratore può creare utenze per i propri dipendenti (sia addetti alla sala, che addetti alla cucina, che supervisori). Al primo accesso, ogni utente deve re-impostare la password   inserita dall’amministratore, scegliendo una password diversa
* Un amministratore (o un supervisore) può personalizzare il menù dell’attività di ristorazione. In  particolare, l’utente può creare e/o eliminare elementi dal menù. Ciascun elemento è caratterizzato da un nome, un costo, una descrizione, e un elenco di allergeni comuni. Inoltre, è possibile organizzare gli elementi del menù in categorie personalizzabili (e.g.: primi, dessert, primi di pesce, bibite, etc.), e definire l’ordine con cui gli elementi compaiono nel menù. In fase di inserimento, è richiesto l’autocompletamento di alcuni prodotti (e.g.: bibite o  preconfezionati) utilizzando open data come quelli disponibili in https://it.openfoodfacts.org/data.
* Un addetto alla sala può registrare ordinazioni indicando l’identificativo del tavolo e gli elementi del menù desiderati
*  Un supervisore o un amministratore può inserire nel sistema degli avvisi, che vengono visualizzati da tutti i dipendenti. Ciascun dipendente può marcare un avviso come “visualizzato”  e nasconderlo.

Cosa è stato utilizzato per lo sviluppo:
* Android:
   * retrofix
   * interceptor class per la gestione del token e degli errori   
  * rxjava
  * fragment
* Backend Java:
  * springboot
  * jwt
  * mockito
  * junit
  * handler exception
  
