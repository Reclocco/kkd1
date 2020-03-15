import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    static byte[] dane;
    static int[] byteocc = new int[256];
    static int[][] byteoccAft = new int[256][256];

    public static void main(String[] args) throws IOException {
        String path = args[0];

        dane = Files.readAllBytes(Paths.get(path));

        //czyt
        for(int i=0; i<256; i++){
            byteocc[i] = 0;
        }

        //zerowanie
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                byteoccAft[i][j] = 0;
            }
        }

        //kod

        for(int i = 0; i< dane.length; i++){
            if(i == 0)
                byteoccAft[128][dane[0]+128]++;
            else
                byteoccAft[dane[i-1]+128][dane[i]+128]++;
            byteocc[dane[i]+128]++;
        }

        System.out.println(entropia());
    }

    public static String entropia(){

        double entropianor = 0;
        double entropiawar = 0;
        double omega = dane.length;

        for(int i=0; i<256; i++){

            double prawd = byteocc[i]*1.0 / omega;

            if(prawd != 0)
                entropianor += prawd * Math.log(prawd) / Math.log(2);

        }

        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                double omega_wyst_x_przed = byteocc[i]*1.0;
                double prawd_x_przed = byteocc[i]*1.0 / omega;
                double prawd_war = byteoccAft[i][j] / omega_wyst_x_przed;

                if(prawd_war != 0 && prawd_x_przed !=0)
                    entropiawar += prawd_x_przed * prawd_war * Math.log(prawd_war) / Math.log(2);
            }

        }
        return ("entropia " + -entropianor + "\nwarunkowa " + -entropiawar);
    }
}