package com.INGSW2223_V_06.ratatouille23.utils.LocalStorage;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.INGSW2223_V_06.ratatouille23.model.TipoUtente;
import com.INGSW2223_V_06.ratatouille23.model.Utente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtenteLocale {

    public static void setLocalUser(Context c, Utente utente) {
        Log.i("utentelocale", "Setting local user.");
        SharedPreferences sharedPreferences = c.getSharedPreferences("utente_locale", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idUtente", String.valueOf(utente.getId()));
        editor.putString("nome", utente.getNome());
        editor.putString("email", utente.getEmail());
        editor.putString("cognome", utente.getCognome());
        editor.putString("TipoUtente", utente.getTipoUtente().toString());
        editor.putString("DataPrimoAccesso", utente.getPrimoAccesso());
        editor.apply();
    }


    public static void deleteLocalUser(Context c) {
        Log.i("utentelocale", "Cancellazione local user.");
        SharedPreferences sharedPreferences = c.getSharedPreferences("utente_locale", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static String aggiungiSpazioTipoUtente(String tipoUtente) {
        String output = "";

        for (int i = 0; i < tipoUtente.length(); i++) {
            char c = tipoUtente.charAt(i);
            // Se il carattere è maiuscolo aggiungo uno spazio prima del carattere
            if (Character.isUpperCase(c)) {
                output += " " + c;
            } else {
                // Altrimenti aggiungo  il carattere alla stringa
                output += c;
            }
        }
        return output;
    }

    public static boolean ritornoTipoAmministratoreOSupervisoreUtente(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences("utente_locale", MODE_PRIVATE);
        TipoUtente tipoUtente = TipoUtente.valueOf(myPrefs.getString("TipoUtente", null));
        return tipoUtente.equals(TipoUtente.Amministratore) || tipoUtente.equals(TipoUtente.Supervisore);

    }

    public static String ritornoEmail(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences("utente_locale", MODE_PRIVATE);
        String email = myPrefs.getString("email", null);
        return email;
    }

    public static synchronized String DataPrimoAccesso(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences("utente_locale", MODE_PRIVATE);
        return myPrefs.getString("DataPrimoAccesso", null);
    }

    public static boolean ritornoTipoAddettoAllaSalaUtente(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences("utente_locale", MODE_PRIVATE);
        TipoUtente tipoUtente = TipoUtente.valueOf(myPrefs.getString("TipoUtente", null));
        return tipoUtente.equals(TipoUtente.AddettoAllaSala);

    }

    public static boolean ritornoTipoAddettoAllaCucinaUtente(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences("utente_locale", MODE_PRIVATE);
        TipoUtente tipoUtente = TipoUtente.valueOf(myPrefs.getString("TipoUtente", null));
        return tipoUtente.equals(TipoUtente.AddettoAllaCucina);

    }

    public static String tipoUtenteStringa(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences("utente_locale", MODE_PRIVATE);
        TipoUtente tipoUtente = TipoUtente.valueOf(myPrefs.getString("TipoUtente", null));
        return tipoUtente.toString();
    }

    public static boolean patternEmailVerifica(final String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean regexPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(passwordPattern);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static void messageTokenScaduto(Context c) {
        Log.i("token save", "Setting local token.");
        SharedPreferences sharedPreferences = c.getSharedPreferences("messageTokenScaduto", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("messageTokenScaduto", "Sessione utente scaduta. Effettuare il login");
        editor.apply();
    }

    public static String ritornoMessageTokenScaduto(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences("messageTokenScaduto", MODE_PRIVATE);
        return myPrefs.getString("messageTokenScaduto", null);
    }

    public static void deleteMessageTokenScaduto(Context c) {
        Log.i("token delete", "Deleteing token.");
        SharedPreferences sharedPreferences = c.getSharedPreferences("messageTokenScaduto", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}