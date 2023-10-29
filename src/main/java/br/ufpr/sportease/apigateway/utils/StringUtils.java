package br.ufpr.sportease.apigateway.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils(){/*vazio*/}

    public static String extrairMensagemDeErro(String jsonString) {
        Pattern pattern = Pattern.compile("message\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(jsonString);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
