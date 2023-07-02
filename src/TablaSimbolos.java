import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class TablaSimbolos  {

    private final Map<String, Object> values = new HashMap<>();

    boolean existeIdentificador(String identificador){
        return values.containsKey(identificador);
    }

    Object obtener(String identificador) {
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        throw new RuntimeException("Variable no definida '" + identificador + "'.");
    }

    void asignar(String identificador, Object valor){
        values.put(identificador, valor);
    }

    @Override
    public TablaSimbolos clone() {
        //TablaSimbolos nuevaTabla = new TablaSimbolos();
        //nuevaTabla.values.putAll(values);
        //return nuevaTabla;
        return this;
    }

}
