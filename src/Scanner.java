import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Scanner {

    private String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.Y);//
        palabrasReservadas.put("class", TipoToken.CLASE);//
        palabrasReservadas.put("else", TipoToken.ADEMAS);//
        palabrasReservadas.put("false", TipoToken.FALSO);//
        palabrasReservadas.put("for", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); // definir funciones
        palabrasReservadas.put("if", TipoToken.SI);//
        palabrasReservadas.put("null", TipoToken.NULO);//
        palabrasReservadas.put("or", TipoToken.O);//
        palabrasReservadas.put("print", TipoToken.IMPRIMIR);//
        palabrasReservadas.put("return", TipoToken.RETORNAR);//
        palabrasReservadas.put("super", TipoToken.SUPER);//
        palabrasReservadas.put("this", TipoToken.ESTE);
        palabrasReservadas.put("true", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); // definir variables
        palabrasReservadas.put("while", TipoToken.MIENTRAS);//
    }

    private static final Map<String, TipoToken> simbolos;

    static {
        simbolos = new HashMap<>();
        simbolos.put("(", TipoToken.PARENTESIS_IZQUIERDO);
        simbolos.put(")", TipoToken.PARENTESIS_DERECHO);
        simbolos.put("{", TipoToken.LLAVE_IZQUIERDA);
        simbolos.put("}", TipoToken.LLAVE_DERECHA);
        simbolos.put(",", TipoToken.COMA);
        simbolos.put(".", TipoToken.PUNTO);
        simbolos.put(";", TipoToken.PUNTO_Y_COMA);
        simbolos.put("-", TipoToken.RESTA);
        simbolos.put("+", TipoToken.SUMA);
        simbolos.put("*", TipoToken.MULTIPLICACION);
        simbolos.put("/", TipoToken.DIVISION);
        simbolos.put("!", TipoToken.NEGACION);
        simbolos.put("!=", TipoToken.DISTINTO);
        simbolos.put("=", TipoToken.ASIGNACION);
        simbolos.put("==", TipoToken.IGUALDAD);
        simbolos.put("<", TipoToken.MENOR_QUE);
        simbolos.put("<=", TipoToken.MENOR_O_IGUAL_QUE);
        simbolos.put(">", TipoToken.MAYOR_QUE);
        simbolos.put(">=", TipoToken.MAYOR_O_IGUAL_QUE);

    }

    private static final Map<String, TipoToken> identificadores;

    static {
        identificadores = new HashMap<>();
        identificadores.put("", TipoToken.IDENTIFICADOR);
        identificadores.put("", TipoToken.CADENA);
        identificadores.put("", TipoToken.NUMERO);

    }

    Scanner(String source) {
        this.source = source;

    }

    List<Token> scanTokens() {
        int b = source.length();

        int cont = 1;

        while (cont - 1 != source.length()) {
            char a = source.charAt(cont - 1);

            if (cont - 1 < source.length() && source.charAt(cont - 1) == '\t') {
                cont++;
            } else if (cont - 1 < source.length() && source.charAt(cont - 1) == '\r') {
                cont++;
                linea++;
            } else if (source.charAt(cont - 1) == '\n' || (cont - 1 < source.length() - 1
                    && source.charAt(cont - 1) == '/' && source.charAt(cont) == 'n')) {
                cont++;
                linea++;
            } else

            if (source.charAt(cont - 1) == '(') {
                tokens.add(new Token(TipoToken.PARENTESIS_IZQUIERDO, "(",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == ')') {
                tokens.add(new Token(TipoToken.PARENTESIS_DERECHO, ")",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == '{') {
                tokens.add(new Token(TipoToken.LLAVE_IZQUIERDA, "{",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == '}') {
                tokens.add(new Token(TipoToken.LLAVE_DERECHA, "}",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == ',') {
                tokens.add(new Token(TipoToken.COMA, ",",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == '.') {
                tokens.add(new Token(TipoToken.PUNTO, ".",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == ';') {
                tokens.add(new Token(TipoToken.PUNTO_Y_COMA, ";",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == '-') {
                tokens.add(new Token(TipoToken.RESTA, "-",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == '+') {
                tokens.add(new Token(TipoToken.SUMA, "+",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == '*') {
                tokens.add(new Token(TipoToken.MULTIPLICACION, "*",  linea));
                cont++;
            } else if (source.charAt(cont - 1) == '/') {
                if (cont < source.length() && source.charAt(cont) == '/') {
                    cont = cont + 2;

                    while (!(source.charAt(cont - 1) == '\r' && source.charAt(cont) == '\n')) {
                        cont++;
                    }
                    cont = cont + 4;
                } else if (cont + 1 < source.length() && source.charAt(cont) == '*') {
                    cont = cont + 2;

                    while (cont + 1 < source.length() && !(source.charAt(cont) == '*')
                            && !(source.charAt(cont + 1) == '/')) {
                        cont++;

                    }
                    cont += 3;
                } else {
                    tokens.add(new Token(TipoToken.DIVISION, "/",  linea));
                    cont++;
                }
            } else

            if (source.charAt(cont - 1) == '!') {
                if (cont < source.length() && source.charAt(cont) == '=') {
                    tokens.add(new Token(TipoToken.DISTINTO, "!=",  linea));
                    cont = cont + 2;
                } else {
                    tokens.add(new Token(TipoToken.NEGACION, "!",  linea));
                    cont++;
                }

            } else if (source.charAt(cont - 1) == '=') {
                // tokens.add(new Token(TipoToken.ASIGNACION, "=", "simbolos", linea++));

                if (cont < source.length() && source.charAt(cont) == '=') {
                    tokens.add(new Token(TipoToken.IGUALDAD, "==",  linea));
                    cont = cont + 2;
                } else {
                    tokens.add(new Token(TipoToken.ASIGNACION, "=",  linea));
                    cont++;
                }
            } else

            if (source.charAt(cont - 1) == '<') {
                if (cont < source.length() && source.charAt(cont) == '=') {
                    tokens.add(new Token(TipoToken.MENOR_O_IGUAL_QUE, "<=",  linea));
                    cont = cont + 2;
                } else
                    tokens.add(new Token(TipoToken.MENOR_QUE, "<",  linea));
                cont++;
            } else

            if (source.charAt(cont - 1) == '>') {
                if (cont < source.length() && source.charAt(cont) == '=') {
                    tokens.add(new Token(TipoToken.MAYOR_O_IGUAL_QUE, ">=",  linea));
                    cont = cont + 2;
                } else
                    tokens.add(new Token(TipoToken.MAYOR_QUE, ">",  linea));
                cont++;
            } else if (source.charAt(cont - 1) == '"') {
                String aux = "";
                cont++;
                while (cont - 1 < source.length() && source.charAt(cont - 1) != '"') {
                    aux = aux.concat(String.valueOf(source.charAt(cont - 1)));
                    cont++;
                }

                if (cont - 1 >= source.length()) {
                    System.out.print("Error: no se cerraron las comillas ");
                } else {
                    tokens.add(new Token(TipoToken.CADENA, aux, aux, linea));
                    cont = cont + 1;
                }
            }
            if (cont - 1 < source.length() && source.charAt(cont - 1) == ' ') {
                cont++;
            } else if (cont - 1 < source.length() && Character.isLetter(source.charAt(cont - 1))) {
                String aux = "";

                if (source.length() - cont > 1 && source.charAt(cont - 1) == 'a' && source.charAt(cont) == 'n'
                        && source.charAt(cont + 1) == 'd') {
                    tokens.add(new Token(TipoToken.Y, "and",  linea));
                    cont = cont + 3;

                } else if (source.length() - cont > 2 && source.charAt(cont - 1) == 'e' && source.charAt(cont) == 'l'
                        && source.charAt(cont + 1) == 's' && source.charAt(cont + 2) == 'e') {
                    tokens.add(new Token(TipoToken.ADEMAS, "else",  linea));
                    cont = cont + 4;
                } else if (source.length() - cont > 3 && source.charAt(cont - 1) == 'c' && source.charAt(cont) == 'l'
                        && source.charAt(cont + 1) == 'a' && source.charAt(cont + 2) == 's'
                        && source.charAt(cont + 3) == 's') {
                    tokens.add(new Token(TipoToken.CLASE, "class",  linea));
                    cont = cont + 5;
                } else if (source.length() - cont > 3 && source.charAt(cont - 1) == 'f' && source.charAt(cont) == 'a'
                        && source.charAt(cont + 1) == 'l' && source.charAt(cont + 2) == 's'
                        && source.charAt(cont + 3) == 'e') {
                    tokens.add(new Token(TipoToken.FALSO, "false",  linea));
                    cont = cont + 5;
                } else if (source.length() - cont > 1 && source.charAt(cont - 1) == 'f' && source.charAt(cont) == 'u'
                        && source.charAt(cont + 1) == 'n') {
                    tokens.add(new Token(TipoToken.FUN, "fun",  linea));
                    cont = cont + 3;
                } else if (source.length() - cont > 0 && source.charAt(cont - 1) == 'i' && source.charAt(cont) == 'f') {
                    tokens.add(new Token(TipoToken.SI, "if",  linea));
                    cont = cont + 2;
                } else if (source.length() - cont > 2 && source.charAt(cont - 1) == 'n' && source.charAt(cont) == 'u'
                        && source.charAt(cont + 1) == 'l' && source.charAt(cont + 2) == 'l') {
                    tokens.add(new Token(TipoToken.NULO, "null",  linea));
                    cont = cont + 4;
                } else if (source.length() - cont > 0 && source.charAt(cont - 1) == 'o' && source.charAt(cont) == 'r') {
                    tokens.add(new Token(TipoToken.O, "or",  linea));
                    cont = cont + 2;
                } else if (source.length() - cont > 3 && source.charAt(cont - 1) == 'p' && source.charAt(cont) == 'r'
                        && source.charAt(cont + 1) == 'i' && source.charAt(cont + 2) == 'n'
                        && source.charAt(cont + 3) == 't') {
                    tokens.add(new Token(TipoToken.IMPRIMIR, "print",  linea));
                    cont = cont + 5;
                } else if (source.length() - cont > 4 && source.charAt(cont - 1) == 'r' && source.charAt(cont) == 'e'
                        && source.charAt(cont + 1) == 't' && source.charAt(cont + 2) == 'u'
                        && source.charAt(cont + 3) == 'r' && source.charAt(cont + 4) == 'n') {
                    tokens.add(new Token(TipoToken.RETORNAR, "return",  linea));
                    cont = cont + 6;
                } else if (source.length() - cont > 3 && source.charAt(cont - 1) == 's' && source.charAt(cont) == 'u'
                        && source.charAt(cont + 1) == 'p' && source.charAt(cont + 2) == 'e'
                        && source.charAt(cont + 3) == 'r') {
                    tokens.add(new Token(TipoToken.SUPER, "super",  linea));
                    cont = cont + 5;
                } else if (source.length() - cont > 0 && source.charAt(cont - 1) == 'f' && source.charAt(cont) == 'o'
                        && source.charAt(cont + 1) == 'r') {
                    tokens.add(new Token(TipoToken.PARA, "for",  linea));
                    cont = cont + 3;
                } else if (source.length() - cont > 2 && source.charAt(cont - 1) == 't' && source.charAt(cont) == 'r'
                        && source.charAt(cont + 1) == 'u' && source.charAt(cont + 2) == 'e') {
                    tokens.add(new Token(TipoToken.VERDADERO, "true",  linea));
                    cont = cont + 4;
                } else if (source.length() - cont > 2 && source.charAt(cont - 1) == 't' && source.charAt(cont) == 'h'
                        && source.charAt(cont + 1) == 'i' && source.charAt(cont + 2) == 's') {
                    tokens.add(new Token(TipoToken.ESTE, "this",  linea));
                    cont = cont + 4;
                } else if (source.length() - cont > 1 && source.charAt(cont - 1) == 'v' && source.charAt(cont) == 'a'
                        && source.charAt(cont + 1) == 'r' && source.charAt(cont + 2) == ' ') {
                    tokens.add(new Token(TipoToken.VAR, "var",  linea));
                    cont = cont + 4;
                } else if (source.length() - cont > 3 && source.charAt(cont - 1) == 'w' && source.charAt(cont) == 'h'
                        && source.charAt(cont + 1) == 'i' && source.charAt(cont + 2) == 'l'
                        && source.charAt(cont + 3) == 'e') {
                    tokens.add(new Token(TipoToken.MIENTRAS, "while",  linea));
                    cont = cont + 5;
                } else

                    while ((cont - 1 < source.length()) && (Character.isLetter(source.charAt(cont - 1))
                            || Character.isDigit(source.charAt(cont - 1)))) {
                        aux = aux.concat(String.valueOf(source.charAt(cont - 1)));
                        if (cont == source.length()) { // Verifica si el número es el último carácter de la cadena de
                            // origen
                            cont++;
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux,  linea));
                            cont++;
                            break;
                        } else if (cont < source.length() && source.charAt(cont) == ' ') {
                            cont++;
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux,  linea));
                            cont++;
                            break;
                        } else if (!(Character.isDigit(source.charAt(cont))
                                || Character.isLetter(source.charAt(cont)))) { // Verifica si el siguiente carácter no
                            // es un número

                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux,  linea));
                            cont++;
                            break;
                        }

                        cont++;
                    }

            } else if (cont - 1 < source.length() && Character.isDigit(source.charAt(cont - 1))) {
                String aux = "";
                Double auxD= (double) 0;
                while ((cont - 1 < source.length()) && Character.isDigit(source.charAt(cont - 1))) {
                    aux = aux.concat(String.valueOf(source.charAt(cont - 1)));
                    if (cont == source.length()) {
                        auxD= Double.parseDouble(aux);
                        tokens.add(new Token(TipoToken.NUMERO, aux, auxD, linea));
                        cont++;
                        break;
                    } else if ((cont < source.length() && source.charAt(cont) == '.')
                            || (cont < source.length() && source.charAt(cont) == ',')) {
                        cont++;
                        aux = aux.concat(String.valueOf(source.charAt(cont - 1)));
                    } else if (!Character.isDigit(source.charAt(cont))) {
                        // número
                        auxD= Double.parseDouble(aux);
                        tokens.add(new Token(TipoToken.NUMERO, aux, auxD, linea));
                        cont++;
                        break;
                    }

                    cont++;

                }

            }

        }
        tokens.add(new Token(TipoToken.EOF, "",  linea));
        return tokens;
    }
}

/*

public class Scanner {

    final private  String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.Y);
        palabrasReservadas.put("class", TipoToken.CLASE);
        palabrasReservadas.put("else", TipoToken.ADEMAS);
        palabrasReservadas.put("false", TipoToken.FALSO);
        palabrasReservadas.put("for", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN);
        palabrasReservadas.put("if", TipoToken.SI);
        palabrasReservadas.put("null", TipoToken.NULO);
        palabrasReservadas.put("or", TipoToken.O);
        palabrasReservadas.put("print", TipoToken.IMPRIMIR);
        palabrasReservadas.put("return", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("this", TipoToken.ESTE);
        palabrasReservadas.put("true", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put("while", TipoToken.MIENTRAS);
    }

    private static final Map<String, TipoToken> simbolos;

    static {
        simbolos = new HashMap<>();
        simbolos.put("(", TipoToken.PARENTESIS_IZQUIERDO);
        simbolos.put(")", TipoToken.PARENTESIS_DERECHO);
        simbolos.put("{", TipoToken.LLAVE_IZQUIERDA);
        simbolos.put("}", TipoToken.LLAVE_DERECHA);
        simbolos.put(",", TipoToken.COMA);
        simbolos.put(".", TipoToken.PUNTO);
        simbolos.put(";", TipoToken.PUNTO_Y_COMA);
        simbolos.put("-", TipoToken.RESTA);
        simbolos.put("+", TipoToken.SUMA);
        simbolos.put("*", TipoToken.MULTIPLICACION);
        simbolos.put("/", TipoToken.DIVISION);
        simbolos.put("!", TipoToken.NEGACION);
        simbolos.put("!=", TipoToken.DISTINTO);
        simbolos.put("=", TipoToken.ASIGNACION);
        simbolos.put("==", TipoToken.IGUALDAD);
        simbolos.put("<", TipoToken.MENOR_QUE);
        simbolos.put("<=", TipoToken.MENOR_O_IGUAL_QUE);
        simbolos.put(">", TipoToken.MAYOR_QUE);
        simbolos.put(">=", TipoToken.MAYOR_O_IGUAL_QUE);

    }

    private static final Map<String, TipoToken> identificadores;

    static {
        identificadores = new HashMap<>();

        identificadores.put("", TipoToken.IDENTIFICADOR);
        identificadores.put("", TipoToken.CADENA);
        identificadores.put("", TipoToken.NUMERO);

    }


    Scanner(String source) {
        this.source = source;


    }

    List<Token> scanTokens() {

        int cont = 1;

        while (cont - 1 != source.length()) {


            if (source.charAt(cont - 1) == ' ') {
                cont++;
            } else if (source.charAt(cont - 1) == '\t') {
                cont++;
            } else if (source.charAt(cont - 1) == '\n' || source.charAt(cont - 1) == '\r') {
                cont++;
                linea++;
            }else

            if (source.charAt(cont - 1) == '(') {
                tokens.add(new Token(TipoToken.PARENTESIS_IZQUIERDO, "(", cont ));
                cont++;
            }else

            if (source.charAt(cont-1) == ')') {
                tokens.add(new Token(TipoToken.PARENTESIS_DERECHO, ")", cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == '{') {
                tokens.add(new Token(TipoToken.LLAVE_IZQUIERDA, "{",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == '}') {
                tokens.add(new Token(TipoToken.LLAVE_DERECHA, "}",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == ',') {
                tokens.add(new Token(TipoToken.COMA, ",",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == '.') {
                tokens.add(new Token(TipoToken.PUNTO, ".",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == ';') {
                tokens.add(new Token(TipoToken.PUNTO_Y_COMA, ";",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == '-') {
                tokens.add(new Token(TipoToken.RESTA, "-",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == '+') {
                tokens.add(new Token(TipoToken.SUMA, "+",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == '*') {
                tokens.add(new Token(TipoToken.MULTIPLICACION, "*",  cont ));
                cont++;
            } else
            if (source.charAt(cont-1) == '/') {
                if(cont<source.length() &&source.charAt(cont) == '/'){
                    cont=cont+2;

                    while(cont<source.length() &&!(source.charAt(cont) == '/') && (source.charAt(cont+1) == 'n')){
                        cont++;
                    }
                    cont=cont+4;
                }else
                if(cont+1<source.length() &&source.charAt(cont) == '*'){
                    cont=cont+2;

                    while(cont+1<source.length() && !(source.charAt(cont) == '*') && !(source.charAt(cont+1) == '/')){
                        cont++;

                    }
                    tokens.add(new Token(TipoToken.COMENTARIO, "COMENTARIO", cont));
                    cont++;
                }else

                    tokens.add(new Token(TipoToken.DIVISION, "/",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == '!') {
                if (cont < source.length() && source.charAt(cont)=='=') {
                    tokens.add(new Token(TipoToken.DISTINTO, "!=",  cont ));
                    cont=cont+2;
                } else {
                    tokens.add(new Token(TipoToken.NEGACION, "!",  cont ));
                    cont++;
                }

            } else
            if (source.charAt(cont-1) == '=') {


                if (cont < source.length() && source.charAt(cont)=='=') {
                    tokens.add(new Token(TipoToken.IGUALDAD, "==",  cont ));
                    cont=cont+2;
                }else
                {
                    tokens.add(new Token(TipoToken.ASIGNACION, "=",  cont ));
                    cont++;}
            } else

            if (source.charAt(cont-1) == '<') {
                if (cont < source.length() && source.charAt(cont)=='=') {
                    tokens.add(new Token(TipoToken.MENOR_O_IGUAL_QUE, "<=",  cont ));
                    cont=cont+2;
                } else
                    tokens.add(new Token(TipoToken.MENOR_QUE, "<",  cont ));
                cont++;
            } else

            if (source.charAt(cont-1) == '>') {
                if (cont < source.length() && source.charAt(cont)=='=') {
                    tokens.add(new Token(TipoToken.MAYOR_O_IGUAL_QUE, ">=",  cont ));
                    cont=cont+2;
                } else
                    tokens.add(new Token(TipoToken.MAYOR_QUE, ">",  cont ));
                cont++;
            }else
            if (source.charAt(cont - 1) == '"') {
                String aux = " ";
                cont++;
                while (cont - 1 < source.length() && source.charAt(cont - 1) != '"') {
                    aux = aux.concat(String.valueOf(source.charAt(cont - 1)));
                    cont++;
                }

                if (cont - 1 >= source.length()) {
                    System.out.print("Error: no se cerraron las comillas ");
                } else {
                    tokens.add(new Token(TipoToken.CADENA, aux, cont ));
                    cont = cont + 1;
                }
            }
            else
            if(Character.isLetter(source.charAt(cont-1))){
                String aux = "";


                if (source.length() - cont>1 &&source.charAt(cont-1) == 'a' && source.charAt(cont) == 'n' && source.charAt(cont+1) == 'd') {
                    tokens.add(new Token(TipoToken.Y, "and",  cont ));
                    cont = cont + 3;

                }else

                if (source.length() - cont>2&&source.charAt(cont-1) == 'e' && source.charAt(cont) == 'l' && source.charAt(cont+1) == 's' && source.charAt(cont+2) == 'e') {
                    tokens.add(new Token(TipoToken.ADEMAS, "else",  cont ));
                    cont = cont + 4;
                }else

                if (source.length() - cont>3&&source.charAt(cont-1) == 'c' && source.charAt(cont) == 'l' && source.charAt(cont+1) == 'a' && source.charAt(cont+2) == 's' && source.charAt(cont+3) == 's') {
                    tokens.add(new Token(TipoToken.CLASE, "class", cont ));
                    cont=cont+5;
                }else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>3&&source.charAt(cont-1)  == 'f' && source.charAt(cont) == 'a' && source.charAt(cont+1) == 'l' && source.charAt(cont+2) == 's' && source.charAt(cont+3) == 'e') {
                    tokens.add(new Token(TipoToken.FALSO, "false",  cont ));
                    cont = cont + 5;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>1&&source.charAt(cont-1) == 'f' && source.charAt(cont) == 'u' && source.charAt(cont+1) == 'n') {
                    tokens.add(new Token(TipoToken.FUN, "fun",  cont ));
                    cont = cont + 3;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>0&&source.charAt(cont-1) == 'i' && source.charAt(cont) == 'f') {
                    tokens.add(new Token(TipoToken.SI, "if",  cont ));
                    cont = cont + 2;
                }else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>2&&source.charAt(cont-1) == 'n' && source.charAt(cont) == 'u' && source.charAt(cont+1) == 'l' && source.charAt(cont+2) == 'l') {
                    tokens.add(new Token(TipoToken.NULO, "null",  cont ));
                    cont = cont + 4;
                }
                else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>0&&source.charAt(cont-1) == 'o' && source.charAt(cont) == 'r') {
                    tokens.add(new Token(TipoToken.O, "or",  cont ));
                    cont = cont + 2;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>3&&source.charAt(cont-1) == 'p' && source.charAt(cont) == 'r' && source.charAt(cont+1) == 'i' && source.charAt(cont+2) == 'n' && source.charAt(cont+3) == 't') {
                    tokens.add(new Token(TipoToken.IMPRIMIR, "print",  cont ));
                    cont = cont + 5;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>4&&source.charAt(cont-1) == 'r' && source.charAt(cont) == 'e' && source.charAt(cont+1) == 't' && source.charAt(cont+2) == 'u' && source.charAt(cont+3) == 'r' && source.charAt(cont+4) == 'n') {
                    tokens.add(new Token(TipoToken.RETORNAR, "return",  cont ));
                    cont = cont + 6;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>3&&source.charAt(cont-1) == 's' && source.charAt(cont) == 'u' && source.charAt(cont+1) == 'p' && source.charAt(cont+2) == 'e' && source.charAt(cont+3) == 'r') {
                    tokens.add(new Token(TipoToken.SUPER, "super",  cont ));
                    cont = cont + 5;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else
                if (source.length() - cont>1&&source.charAt(cont-1) == 'f' && source.charAt(cont) == 'o' && source.charAt(cont+1) == 'r') {
                    tokens.add(new Token(TipoToken.PARA, "for",  cont ));
                    cont = cont + 3;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>2&&source.charAt(cont-1) == 't' && source.charAt(cont) == 'r' && source.charAt(cont+1) == 'u' && source.charAt(cont+2) == 'e') {
                    tokens.add(new Token(TipoToken.VERDADERO, "true",  cont ));
                    cont = cont + 4;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>1&&source.charAt(cont-1) == 'v' && source.charAt(cont) == 'a' && source.charAt(cont+1) == 'r' && source.charAt(cont+2) == ' ') {
                    tokens.add(new Token(TipoToken.VAR, "var",  cont ));
                    cont = cont + 3;
                }
                else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>3&&source.charAt(cont-1) == 'w' && source.charAt(cont) == 'h' && source.charAt(cont+1) == 'i' && source.charAt(cont+2) == 'l' && source.charAt(cont+3) == 'e') {
                    tokens.add(new Token(TipoToken.MIENTRAS, "while", cont ));
                    cont = cont + 5;
                } else

                    while((cont-1 < source.length()) && Character.isLetter(source.charAt(cont-1))){
                        aux = aux.concat(String.valueOf(source.charAt(cont-1)));
                        if (cont == source.length()) {
                            cont++;
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux,  cont ));
                            cont++;
                            break;
                        } else
                        if(cont  < source.length() && source.charAt(cont) == ' ') {
                            cont++;
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux,  cont ));
                            cont++;
                            break;
                        }else if (!Character.isLetter(source.charAt(cont))) {

                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux,  cont ));
                            cont++;
                            break;
                        }
                        cont++;
                    }

            }else
            if ( Character.isDigit(source.charAt(cont-1)) && cont-1 < source.length()) {
                String aux="";
                while((cont-1 < source.length()) && Character.isDigit(source.charAt(cont-1))) {
                    aux = aux.concat(String.valueOf(source.charAt(cont-1)));
                    if (cont == source.length()) {

                        tokens.add(new Token(TipoToken.NUMERO, aux, cont ));
                        cont++;
                        break;
                    }
                    else if((cont  < source.length() && source.charAt(cont) == '.')|| (cont  < source.length() && source.charAt(cont) == ',')) {
                        cont++;
                        aux = aux.concat(String.valueOf(source.charAt(cont - 1)));
                    }
                    else if (!Character.isDigit(source.charAt(cont))) {

                        tokens.add(new Token(TipoToken.NUMERO, aux, cont ));
                        cont++;
                        break;
                    }

                    cont++;

                }

            }


        }

        tokens.add(new Token(TipoToken.EOF, "",  cont ));
        return tokens;
    }
}
*/

