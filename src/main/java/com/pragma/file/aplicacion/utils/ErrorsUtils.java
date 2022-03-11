package com.pragma.file.aplicacion.utils;

public class ErrorsUtils {

    //General
    private static final  String SIN_REGISTROS = "No hay registros";

    //File - cliente
    private static final String IDENTIFICACION_NO_REGISTRADA = "la identificacion %s no esta registrada";
    private static final String IDENTIFICACION_YA_REGISTRADA = "la identificacion %s ya tiene un archivo";
    private static final String IDENTIFICACION_YA_REGISTRADA_SIN_FILE = "la identificacion %s no tiene ningun archivo";

    public static String sinRegistros() {
        return SIN_REGISTROS;
    }

    public static String identificacionNoRegistrada(String numero) {
        return String.format(IDENTIFICACION_NO_REGISTRADA, numero);
    }

    public static String identificacionYaRegistrada(String numero) {
        return String.format(IDENTIFICACION_YA_REGISTRADA, numero);
    }

    public static String identificacionYaRegistradaSinFile(String numero) {
        return String.format(IDENTIFICACION_YA_REGISTRADA_SIN_FILE, numero);
    }
}
