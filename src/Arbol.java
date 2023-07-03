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
                        //System.out.println(res);
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
                        if (n.getHijos() != null && n.getHijos().size() <= 3) {
                            Nodo condicionNodo = n.getHijos().get(0);
                            //Nodo bloqueVerdaderoNodo = new Nodo(null);
                            //bloqueVerdaderoNodo.insertarHijos(n.getHijos());
                            Nodo bloqueVerdaderoNodo = new Nodo(null);
                            //bloqueVerdaderoNodo.insertarHijos(n.getHijos());
                            bloqueVerdaderoNodo.insertarSiguienteHijo(n.getHijos().get(1));

                            // Nodo bloqueVerdaderoNodo = n.getHijos().get(1);

                            SolverAritmetico condicionSolver = new SolverAritmetico(condicionNodo, tas);
                            Object condicion = condicionSolver.resolver();

                            if (condicion instanceof Boolean) {
                                boolean condicionBool = (Boolean) condicion;

                                if (condicionBool) {
                                    // Ejecutar el bloque verdadero
                                    TablaSimbolos nuevaTas = tas.clone();
                                    Arbol bloqueVerdaderoArbol = new Arbol(bloqueVerdaderoNodo, nuevaTas);
                                    bloqueVerdaderoArbol.recorrer();
                                }else {
                                    //Nodo bloqueAdemasNodo = new Nodo(null);
                                    //bloqueAdemasNodo.insertarHijos(n.getHijos());
                                    if (n.getHijos() != null && n.getHijos().size() == 2) {
                                        break;
                                    }
                                    Nodo bloqueAdemasNodo = n.getHijos().get(2);
                                    TablaSimbolos nuevaTas = tas.clone();
                                    Arbol bloqueAdemasArbol = new Arbol(bloqueAdemasNodo, nuevaTas);
                                    bloqueAdemasArbol.recorrer();
                                }
                            } else {
                                System.out.println("Error: La condición del IF debe ser una expresión booleana.");
                            }
                        } else {
                            System.out.println("Error: Estructura incorrecta para el nodo IF.");
                        }
                        break;
                    case ADEMAS:
                        if (n.getHijos() != null && n.getHijos().size() == 1) {
                            Nodo bloqueElseNodo = new Nodo(null);
                            bloqueElseNodo.insertarHijos(n.getHijos());

                            // Ejecutar el bloque del nodo ELSE
                            TablaSimbolos nuevaTas = tas.clone();
                            Arbol bloqueElseArbol = new Arbol(bloqueElseNodo, nuevaTas);
                            bloqueElseArbol.recorrer();
                        } else {
                            System.out.println("Error: Estructura incorrecta para el nodo ELSE.");
                        }
                        break;
                    case PARA:
                        if (n.getHijos() != null && n.getHijos().size() > 3) {
                            Nodo temp = new Nodo(null);
                            temp.insertarHijos(n.getHijos());
                            Nodo inicializacionNodo = new Nodo(null);
                            inicializacionNodo.insertarSiguienteHijo(n.getHijos().get(0));
                            //Nodo inicializacionNodo = n.getHijos().get(0);
                            //Nodo condicionNodo = new Nodo(null);
                            //condicionNodo.insertarSiguienteHijo(n.getHijos().get(1));
                            Nodo condicionNodo = n.getHijos().get(1);
                            Nodo incrementoNodo = new Nodo(null);
                            incrementoNodo.insertarSiguienteHijo(n.getHijos().get(2));
                            //TablaSimbolos nuevaTas = tas.clone();
                            //Nodo bloqueForNodo = n.getHijos().get(3);

                            // Ejecutar la inicialización del FOR
                            Arbol inicializacionArbol = new Arbol(inicializacionNodo, tas);
                            inicializacionArbol.recorrer();
                            TablaSimbolos nuevaTas = tas.clone();
                            // Evaluar la condición del FOR
                            SolverAritmetico solverCondicion = new SolverAritmetico(condicionNodo, nuevaTas);
                            boolean condicion = (boolean) solverCondicion.resolver();

                            // Ejecutar el bloque del FOR mientras la condición sea verdadera
                            while (condicion) {
                                // Ejecutar el bloque del FOR
                                for(int i = 3; i < n.getHijos().size(); i++){
                                    //Nodo bloqueForNodo = n.getHijos().get(i);
                                    Nodo bloqueForNodo = new Nodo(null);
                                    bloqueForNodo.insertarSiguienteHijo(n.getHijos().get(i));
                                    Arbol bloqueForArbol = new Arbol(bloqueForNodo, nuevaTas);
                                    bloqueForArbol.recorrer();
                                }
                                // Ejecutar el incremento del FOR

                                Arbol incrementoArbol = new Arbol(incrementoNodo, nuevaTas);
                                incrementoArbol.recorrer();

                                // Volver a evaluar la condición del FOR
                                condicion = (boolean) solverCondicion.resolver();
                            }
                        } else {
                            System.out.println("Error: Estructura incorrecta para el nodo FOR.");
                        }
                        break;
                    case ASIGNACION:
                        // Reasignación de variables
                        if (n.getHijos() != null && n.getHijos().size() >= 2) {
                            String nombreVariable = n.getHijos().get(0).getValue().lexema;
                            Nodo valorNodo = n.getHijos().get(1);
                            if (tas.existeIdentificador(nombreVariable)) {
                                Object nuevoValor = new SolverAritmetico(valorNodo, tas).resolver();
                                tas.asignar(nombreVariable, nuevoValor);
                            } else {
                                throw new RuntimeException("Error: La variable '" + nombreVariable + "' no existe.");
                            }
                        } else {
                            System.out.println("Error: La reasignación de la variable está mal formada.");
                        }
                        break;
                    case MIENTRAS:
                        if (n.getHijos() != null && n.getHijos().size() == 2) {
                            Nodo condicionNodo = n.getHijos().get(0);
                            Nodo bloqueWhileNodo = n.getHijos().get(1);

                            // Evaluar la condición del WHILE
                            SolverAritmetico solverCondicion = new SolverAritmetico(condicionNodo, tas);
                            boolean condicion = (boolean) solverCondicion.resolver();

                            // Ejecutar el bloque del WHILE mientras la condición sea verdadera
                            while (condicion) {
                                Arbol bloqueWhileArbol = new Arbol(bloqueWhileNodo, tas);
                                bloqueWhileArbol.recorrer();

                                // Volver a evaluar la condición del WHILE
                                condicion = (boolean) solverCondicion.resolver();
                            }
                        } else {
                            System.out.println("Error: Estructura incorrecta para el nodo WHILE.");
                        }
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

