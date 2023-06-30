public class Arbol {
    private final Nodo raiz;
    private final TablaSimbolos tas;

    public Arbol(Nodo raiz, TablaSimbolos tas){
        this.raiz = raiz;
        this.tas = tas;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case SUMA:
                case RESTA:
                case MULTIPLICACION:
                case DIVISION:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                    break;

                case VAR:
                    if(n.getHijos() != null && n.getHijos().size() >=2){
                        String Var = n.getHijos().get(0).getValue().lexema;
                        Nodo valorNodo = n.getHijos().get(1);

                        Object valorVar = new SolverAritmetico(valorNodo);
                        tas.asignar(Var, valorVar);
                    }else {
                        System.out.println("Error: No se pudo declarar la variable");
                    }
                    break;
                case SI:
                    break;

            }
        }
    }


}

