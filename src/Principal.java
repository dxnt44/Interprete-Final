import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Principal {

    static boolean existenErrores = false;
    static TablaSimbolos tas = new TablaSimbolos();

    public static void main(String[] args) throws IOException {

        if(args.length > 1) {
            System.out.println("Uso correcto: interprete [script]");

            // Convención defininida en el archivo "system.h" de UNIX
            System.exit(64);
        } else if(args.length == 1){
            ejecutarArchivo(args[0], tas);
        } else{
            ejecutarPrompt(tas);
        }
        ejecutarPrompt(tas);
    }

    private static void ejecutarArchivo(String path, TablaSimbolos tas) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes, Charset.defaultCharset()), tas);

        // Se indica que existe un error
        if(existenErrores) System.exit(65);
    }

    private static void ejecutarPrompt(TablaSimbolos tas) throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print(">>> ");
            String linea = reader.readLine();
            if(linea == null) break; // Presionar Ctrl + D
            ejecutar(linea, tas);
            existenErrores = false;
        }
    }

    private static void ejecutar(String source, TablaSimbolos tas){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        /*for(Token token : tokens){
            System.out.println(token);
        }*/

        // Para este ejemplo no vamos a utilizar un parser
        /*Parser parser = new Parser(tokens);
        parser.parse();*/

        GeneradorPostfija gpf = new GeneradorPostfija(tokens);
        List<Token> postfija = gpf.convertir();

        /*for(Token token : postfija){
            System.out.println(token);
        }*/

        GeneradorAST gast = new GeneradorAST(postfija, tas);
        Arbol programa = gast.generarAST();
        programa.recorrer();
    }

    /*
    El método error se puede usar desde las distintas clases
    para reportar los errores:
    Interprete.error(....);
     */
    static void error(int linea, String mensaje){
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje){
        System.err.println(
                "[linea " + linea + "] Error " + donde + ": " + mensaje
        );
        existenErrores = true;
    }

}