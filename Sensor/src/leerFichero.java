import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class leerFichero {
  public static void main(String[] args) {
    String valorAnterior = "4500";
    int led = 200;
    int valor = 200;
    File fichero = new File(System.getProperty("user.dir") + "/sensor1.txt");
    String lectura = "";


      try {
        FileReader fr = new FileReader(fichero);
        BufferedReader br = new BufferedReader(fr);

        String linea;
        while((linea = br.readLine()) != null) {
          if(linea.contains(valorAnterior)) {
            linea = linea.replace(valorAnterior, Integer.toString(valor));
          }
          lectura += linea + ",";
        }
        br.close();
        fr.close();

        String[] escritura = lectura.split(",");
        FileWriter fw = new FileWriter(fichero, false);
        for(String s: escritura) {
          fw.write(s);
          fw.write("\n");
          fw.flush();
        }

        fw.close();
    } catch(Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }
}
