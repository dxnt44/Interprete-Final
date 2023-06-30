public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
    }

    public String toString(){
        return tipo + " " + lexema + " " + (literal == null ? " " : literal.toString());
    }

    // Métodos auxiliares
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
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
            case MAYOR_O_IGUAL_QUE:L:
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
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case SI:
            case ADEMAS:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
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
                return 5;
            case IGUALDAD:
            case DISTINTO:
                return 4;
            case Y:
                return 3;
            case O:
                return 2;
            case ASIGNACION:
                return 1;
        }
        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case MULTIPLICACION:
            case DIVISION:
            case SUMA:
            case RESTA:
            case IGUALDAD:
            case DISTINTO:
            case O:
            case ASIGNACION:
            case MAYOR_QUE:
            case MAYOR_O_IGUAL_QUE:
            case MENOR_QUE:
            case MENOR_O_IGUAL_QUE:
            case Y:
                return 2;
        }
        return 0;
    }
}
