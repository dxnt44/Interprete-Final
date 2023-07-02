public class Arbol {
    private final Nodo raiz;
    private final TablaSimbolos tas;

    public Arbol(Nodo raiz, TablaSimbolos tas){
        this.raiz = raiz;
        this.tas = tas;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()) {
            Token t = n.getValue();
            if (t != null) {
                switch (t.tipo) {
                    // Operadores aritméticos
                    case SUMA:
                    case RESTA:
                    case MULTIPLICACION:
                    case DIVISION:
                    case O:
                    case Y:
                    case MAYOR_QUE:
                    case MAYOR_O_IGUAL_QUE:
                    case MENOR_QUE:
                    case MENOR_O_IGUAL_QUE:
                    case IGUALDAD:
                    case NEGACION:
                    case DISTINTO:
                        SolverAritmetico solver = new SolverAritmetico(n, tas);
                        Object res = solver.resolver();
                        System.out.println(res);
                        break;
                    case VAR:
                        if (n.getHijos() != null && n.getHijos().size() >= 2) {
                            String Var = n.getHijos().get(0).getValue().lexema;
                            Nodo valorNodo = n.getHijos().get(1);

                            Object valorVar = new SolverAritmetico(valorNodo, tas).resolver();
                            tas.asignar(Var, valorVar);
                        } else {
                            System.out.println("Error: No se pudo declarar la variable");
                        }
                        break;
                    case IMPRIMIR:
                        if (n.getHijos() != null && !n.getHijos().isEmpty()) {
                            for (Nodo hijo : n.getHijos()) {
                                evaluarNodo(hijo);
                            }
                        } else {
                            System.out.println("Error: El comando PRINT no tiene ninguna expresión asociada.");
                        }
                        break;

                    case SI:
                        break;
                    default:
                        System.out.println("Token no reconocido " + t.lexema);
                }
            }
        }
    }

    private void evaluarNodo(Nodo nodo) {
        Token token = nodo.getValue();
        if (token != null) {
            switch (token.tipo) {
                case CADENA:
                    System.out.println(token.literal);
                    break;
                case IDENTIFICADOR:
                    String varNombre = token.lexema;
                    if (tas.existeIdentificador(varNombre)) {
                        Object varValor = tas.obtener(varNombre);
                        System.out.println(varValor);
                    } else {
                        System.out.println("Error: La variable '" + varNombre + "' no existe.");
                    }
                    break;
                default:
                    SolverAritmetico solver = new SolverAritmetico(nodo, tas);
                    Object valor = solver.resolver();
                    System.out.println(valor);
                    break;
            }
        }
    }

}

