import java.util.Stack;

public class SolverAritmetico {

    private final Nodo nodo;
    private final TablaSimbolos tablaSimbolos;

    public SolverAritmetico(Nodo nodo, TablaSimbolos tablaSimbolos) {
        this.nodo = nodo;
        this.tablaSimbolos = tablaSimbolos;
    }

    public Object resolver() {
        return resolver(nodo);
    }

    private Object resolver(Nodo n) {
        // No tiene hijos, es un operando
        if (n.getHijos() == null || n.getHijos().isEmpty()) {
            if (n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA) {
                return n.getValue().literal;
            } else if (n.getValue().tipo == TipoToken.IDENTIFICADOR) {
                // Ver la tabla de símbolos
                String varNombre = n.getValue().lexema;
                if (tablaSimbolos.existeIdentificador(varNombre)) {
                    return tablaSimbolos.obtener(varNombre);
                } else {
                    throw new RuntimeException("Error: La variable '" + varNombre + "' no existe.");
                }
            } else if (n.getValue().tipo == TipoToken.VERDADERO) {
                return true;
            } else if (n.getValue().tipo == TipoToken.FALSO) {
                return false;
            }
        } else {  // Operación binaria, esperamos al menos dos hijos
            if (n.getHijos().size() < 2) {
                throw new RuntimeException("Error: se esperaban al menos dos hijos para el nodo.");
            }

            Nodo izq = n.getHijos().get(0);
            Nodo der = n.getHijos().get(1);

            Object resultadoIzquierdo = resolver(izq);
            Object resultadoDerecho = resolver(der);

            if (resultadoIzquierdo instanceof Token && ((Token) resultadoIzquierdo).tipo == TipoToken.IDENTIFICADOR) {
                // El resultado izquierdo es un identificador
                String nombreIdentificadorIzquierdo = ((Token) resultadoIzquierdo).lexema;
                if (tablaSimbolos.existeIdentificador(nombreIdentificadorIzquierdo)) {
                    Object valorIdentificadorIzquierdo = tablaSimbolos.obtener(nombreIdentificadorIzquierdo);
                    if (valorIdentificadorIzquierdo instanceof String) {
                        resultadoIzquierdo = resolver(new Nodo(new Token(TipoToken.CADENA, nombreIdentificadorIzquierdo, valorIdentificadorIzquierdo)));
                    } else if (valorIdentificadorIzquierdo instanceof Double) {
                        resultadoIzquierdo = resolver(new Nodo(new Token(TipoToken.NUMERO, nombreIdentificadorIzquierdo, valorIdentificadorIzquierdo)));
                    } else {
                        throw new RuntimeException("Error: El tipo del identificador '" + nombreIdentificadorIzquierdo + "' no es compatible.");
                    }
                } else {
                    throw new RuntimeException("Error: El identificador '" + nombreIdentificadorIzquierdo + "' no existe en la tabla de símbolos.");
                }
            }

            if (resultadoDerecho instanceof Token && ((Token) resultadoDerecho).tipo == TipoToken.IDENTIFICADOR) {
                // El resultado derecho es un identificador
                String nombreIdentificadorDerecho = ((Token) resultadoDerecho).lexema;
                if (tablaSimbolos.existeIdentificador(nombreIdentificadorDerecho)) {
                    Object valorIdentificadorDerecho = tablaSimbolos.obtener(nombreIdentificadorDerecho);
                    if (valorIdentificadorDerecho instanceof String) {
                        resultadoDerecho = resolver(new Nodo(new Token(TipoToken.CADENA, nombreIdentificadorDerecho, valorIdentificadorDerecho)));
                    } else if (valorIdentificadorDerecho instanceof Double) {
                        resultadoDerecho = resolver(new Nodo(new Token(TipoToken.NUMERO, nombreIdentificadorDerecho, valorIdentificadorDerecho)));
                    } else {
                        throw new RuntimeException("Error: El tipo del identificador '" + nombreIdentificadorDerecho + "' no es compatible.");
                    }
                } else {
                    throw new RuntimeException("Error: El identificador '" + nombreIdentificadorDerecho + "' no existe en la tabla de símbolos.");
                }
            }

            // Obtener aridad y precedencia del operador actual
            int aridad = n.getValue().aridad();
            int precedencia = n.getValue().obtenerPrecedencia();

            // Verificar la aridad y precedencia
            if (aridad == 2 && precedencia > 0) {
                if (resultadoIzquierdo instanceof Integer || resultadoDerecho instanceof Integer) {
                    if (resultadoIzquierdo instanceof Integer) {
                        double resultadoIzquierdoDouble = ((Integer) resultadoIzquierdo).doubleValue();
                        // Usa el resultadoIzquierdoDouble convertido a double según sea necesario
                    }
                    if (resultadoDerecho instanceof Integer) {
                        double resultadoDerechoDouble = ((Integer) resultadoDerecho).doubleValue();
                        // Usa el resultadoDerechoDouble convertido a double según sea necesario
                    }
                }

                if (resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double) {
                    switch (n.getValue().tipo) {
                        case SUMA:
                            return ((Double) resultadoIzquierdo + (Double) resultadoDerecho);
                        case RESTA:
                            return ((Double) resultadoIzquierdo - (Double) resultadoDerecho);
                        case MULTIPLICACION:
                            return ((Double) resultadoIzquierdo * (Double) resultadoDerecho);
                        case DIVISION:
                            return ((Double) resultadoIzquierdo / (Double) resultadoDerecho);
                        case MAYOR_QUE:
                            return ((Double) resultadoIzquierdo > (Double) resultadoDerecho);
                        case MAYOR_O_IGUAL_QUE:
                            return ((Double) resultadoIzquierdo >= (Double) resultadoDerecho);
                        case MENOR_QUE:
                            return ((Double) resultadoIzquierdo < (Double) resultadoDerecho);
                        case MENOR_O_IGUAL_QUE:
                            return ((Double) resultadoIzquierdo <= (Double) resultadoDerecho);
                        case IGUALDAD:
                            return resultadoIzquierdo.equals(resultadoDerecho);
                        case DISTINTO:
                            return !resultadoIzquierdo.equals(resultadoDerecho);
                    }
                } else if (resultadoIzquierdo instanceof String && resultadoDerecho instanceof String) {
                    if (n.getValue().tipo == TipoToken.SUMA) {
                        // Ejecutar la concatenación
                        return resultadoIzquierdo.toString() + resultadoDerecho.toString();
                    } else if (n.getValue().tipo == TipoToken.MAYOR_QUE || n.getValue().tipo == TipoToken.MAYOR_O_IGUAL_QUE
                            || n.getValue().tipo == TipoToken.MENOR_QUE || n.getValue().tipo == TipoToken.MENOR_O_IGUAL_QUE
                            || n.getValue().tipo == TipoToken.IGUALDAD || n.getValue().tipo == TipoToken.DISTINTO) {
                        throw new RuntimeException("Error: Operaciones relacionales no válidas con operandos no numéricos.");
                    }
                } else if (resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean) {
                    if (n.getValue().tipo == TipoToken.Y) {
                        return ((Boolean) resultadoIzquierdo && (Boolean) resultadoDerecho);
                    } else if (n.getValue().tipo == TipoToken.O) {
                        return ((Boolean) resultadoIzquierdo || (Boolean) resultadoDerecho);
                    } else if (n.getValue().tipo == TipoToken.MAYOR_QUE || n.getValue().tipo == TipoToken.MAYOR_O_IGUAL_QUE
                            || n.getValue().tipo == TipoToken.MENOR_QUE || n.getValue().tipo == TipoToken.MENOR_O_IGUAL_QUE
                            || n.getValue().tipo == TipoToken.IGUALDAD || n.getValue().tipo == TipoToken.DISTINTO) {
                        throw new RuntimeException("Error: Operaciones relacionales no válidas con operandos booleanos.");
                    }
                } else {
                    // Error por diferencia de tipos
                    throw new RuntimeException("Error: Diferencia de tipos en la operación aritmética, concatenación o lógica.");
                }
            } else if (aridad == 1 && precedencia > 0) {
                // Operador unario
                if (resultadoIzquierdo instanceof Double) {
                    switch (n.getValue().tipo) {
                        case NEGACION:
                            return -((Double) resultadoIzquierdo);
                    }
                } else if (resultadoIzquierdo instanceof Boolean) {
                    if (n.getValue().tipo == TipoToken.DISTINTO) {
                        return !((Boolean) resultadoIzquierdo);
                    } else {
                        throw new RuntimeException("Error: Operación unaria no válida con operandos no booleanos.");
                    }
                } else {
                    // Error por diferencia de tipos
                    throw new RuntimeException("Error: Diferencia de tipos en la operación aritmética o lógica unaria.");
                }
            } else {
                // Error en la aridad
                throw new RuntimeException("Error: Aridad inválida para el operador '" + n.getValue().lexema + "'.");
            }
        }

        return null; // Agrega esta línea para evitar el error
    }
    private TipoToken getTipoTokenFromValue(Object value) {
        if (value instanceof Integer || value instanceof Double) {
            return TipoToken.NUMERO;
        } else if (value instanceof String) {
            return TipoToken.CADENA;
        } else if (value instanceof Boolean) {
            return (Boolean) value ? TipoToken.VERDADERO : TipoToken.FALSO;
        }
        throw new RuntimeException("Error: Tipo de token desconocido para el valor " + value);
    }

}

