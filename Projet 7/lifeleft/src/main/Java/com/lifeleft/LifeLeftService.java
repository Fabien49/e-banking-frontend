package com.lifeleft;

import java.time.Year;

public class LifeLeftService {

    private static final Integer ESPERANCE_VIE_HOMMES = 79;
    private static final Integer ESPERANCE_VIE_FEMMES = 80;

    String homme = "homme";
    String femme = "femme";

    Integer evDeReference = 0;

    public String anneeRestanteAVivre (String prenom, String sexe, Integer anneedenaissance){
        if(sexe.equals(homme)) evDeReference = ESPERANCE_VIE_HOMMES;
        else evDeReference =ESPERANCE_VIE_FEMMES;

        Integer anneeRestantes = evDeReference - (Year.now().getValue() - anneedenaissance);

        return "Bonjour" + prenom + "Il vous reste à vivre" + anneeRestantes + "années";
    }
}