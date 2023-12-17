package com.INGSW2223_V_06.ratatouille23.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FiltroInputBigDecimalPrezzo implements InputFilter {

    private Pattern pattern;

    /**
     * ^ indica l'inizio della stringa.
     * \\d* corrisponde a zero o più cifre.
     * \\.? corrisponde a un punto opzionale.
     * \\d{0,2} corrisponde a zero, uno o due cifre dopo il punto.
     * $ indica la fine della stringa.
     */
    public FiltroInputBigDecimalPrezzo() {
        pattern = Pattern.compile("^\\d*\\.?\\d{0,2}$");
    }
    /**
     originalString.substring(0, dstart): Restituisce una sottostringa di originalString che va dall'inizio fino all'indice dstart. Questa parte corrisponde al testo precedente alla posizione in cui è stato inserito il nuovo testo.
     source.subSequence(start, end): Restituisce una sottosequenza di source che va dall'indice start all'indice end. Questa parte corrisponde al nuovo testo che è stato inserito.
     originalString.substring(dend): Restituisce una sottostringa di originalString che va dall'indice dend fino alla fine della stringa. Questa parte corrisponde al testo dopo la posizione in cui è stato inserito il nuovo testo.
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        String stringaOriginale = dest.toString();
        String stringaFinale = stringaOriginale.substring(0, dstart) + source.subSequence(start, end) + stringaOriginale.substring(dend);

        // Aggiungo uno zero all'inizio se viene inserito un punto come primo carattere
        if (stringaFinale.equals(".") && dstart == 0) {
            return "0.";
        }
        Matcher matcher = pattern.matcher(stringaFinale);
        if (!matcher.matches())
            return "";//stringa rifiutata
        return null; //stringa accettata

    }

}