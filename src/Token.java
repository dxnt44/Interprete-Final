public class Token {
    final TipoToken tipo;
    String lexema;
    Object literal;
    final int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }


    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.linea = 0;
    }

    public Token(TipoToken tipo, String lexema, Object literal){
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        if(this.tipo == ((Token)o).tipo){
            return true;
        }

        return false;
    }
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
            case CADENA:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case SUMA:
            case RESTA:
            case MULTIPLICACION:
            case DIVISION:
            case ASIGNACION:
            case MAYOR_QUE:
            case MAYOR_O_IGUAL_QUE:
            case MENOR_QUE:
            case MENOR_O_IGUAL_QUE:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case SI:
            case IMPRIMIR:
            case ADEMAS:
            case PARA:
            case MIENTRAS:
            case Y:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case SI:
            case ADEMAS:
            case MIENTRAS:
            case PARA:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    public int obtenerPrecedencia() {
        switch (this.tipo) {
            case MULTIPLICACION:
            case DIVISION:
                return 7;
            case SUMA:
            case RESTA:
                return 6;
            case MENOR_QUE:
            case MENOR_O_IGUAL_QUE:
            case MAYOR_QUE:
            case MAYOR_O_IGUAL_QUE:
                return 3;
            case IGUALDAD:
            case DISTINTO:
                return 4;
            case Y:
                return 5;
            case O:
                return 2;
            case ASIGNACION:
                return 1;
            default:
                return 0;
        }
    }


    public int aridad(){
        switch (this.tipo) {
            case MULTIPLICACION:
            case DIVISION:
            case SUMA:
            case O:
            case DISTINTO:
            case IGUALDAD:
            case RESTA:
            case ASIGNACION:
            case MAYOR_QUE:
            case MAYOR_O_IGUAL_QUE:
            case Y:
            case MENOR_QUE:
            case MENOR_O_IGUAL_QUE:
                return 2;
        }
        return 0;
    }



    public String toString(){
        return tipo + " " + lexema + " " + literal;
    }
}