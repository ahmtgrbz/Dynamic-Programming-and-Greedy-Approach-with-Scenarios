import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Ahmet_Gurbuz_1509510002 {
    static ArrayList<int []> investment;
    static ArrayList<Integer> monnthdemand;
    static ArrayList<Integer> garagecost;

    public static int DPcost(int x,int[] income,int t,int p,int d) {
        int total=totaldemend(x);
        int Dpcosttable[][] = new int[x+1][total+1];
        int findmin=Integer.MAX_VALUE;
        for (int i =0;i<x+1;i++){
            for(int ttl=0;ttl<total+1;ttl++) {
                if (i == 0) {
                    if (ttl == 0) {
                        Dpcosttable[0][0] = 0;
                    } else {
                        Dpcosttable[i][ttl] = garagecost.get(ttl - 1);
                    }

                } else {
                    int need=monnthdemand.get(i-1)-p;
                    if(need>0){
                    for (int h = 0; h < need+1; h++) {
                            int gideranl�k = Dpcosttable[i - 1][h] + ((need-h+ttl)*d);
                            if(ttl!=0){
                                gideranl�k=gideranl�k+garagecost.get(ttl-1);//garaj maaliyetlerini ekliyorum.
                            }

                            if ( gideranl�k < findmin) {
                                findmin = gideranl�k;
                                Dpcosttable[i][ttl] = findmin;
                            }

                        }
                    }else{

                        for (int h = 0; h <monnthdemand.get(i)+1; h++) {

                            int gideranl�k = Dpcosttable[i - 1][h];
                            if(ttl!=0){
                                gideranl�k=gideranl�k+garagecost.get(ttl-1);//garaj maaliyetlerini ekliyorum.
                            }

                            if ( gideranl�k < findmin) {
                                findmin = gideranl�k;
                                Dpcosttable[i][ttl] = findmin;
                            }

                        }
                    }
                }
                findmin=Integer.MAX_VALUE;
            }

        }
        int o = Integer.MAX_VALUE;
        for(int n=0; n<total+1;n++) {
            if ( Dpcosttable[x][n]< o) {
                o = Dpcosttable[x][n];
            }
        }

        return o;
    }

    public static int DPProfit(int x,int c,int[] income,int t,int B) {
        int Dpprofittable[][] = new int[x][c];
        double tempincome = 0.00;

        for(int i=0; i<x;i++){
            for (int bank=0;bank<c;bank++){
                if(i==0){
                    Dpprofittable[i][bank]=(int)((double)income[i]+(((double)income[i]/100)*(investment.get(bank))[i]));
                    if(tempincome <Dpprofittable[i][bank]){
                        tempincome=Dpprofittable[i][bank];
                    }
                }else {
                    for (int h = 0; h < c; h++){
                        if (bank == h) {
                            double aylikgelir = (double) Dpprofittable[i - 1][h] + (double) income[i];
                            double a = (aylikgelir + (aylikgelir / 100 * (investment.get(bank))[i]));
                            if (tempincome < a) {
                                tempincome = a;
                                Dpprofittable[i][bank] = (int) tempincome;
                            }
                        } else {
                            double kesintisonras� = (double) Dpprofittable[i - 1][h] - ((double) Dpprofittable[i - 1][h] * t / 100);
                            double aylikgelir = kesintisonras� + (double) income[i];
                            double a = (aylikgelir + (aylikgelir / 100 * (investment.get(bank))[i]));
                            if (tempincome < a) {
                                tempincome = a;
                                Dpprofittable[i][bank] = (int) tempincome;
                            }
                        }
                    }
                }
                tempincome = 0.00;
            }
        }
        int o = 0;
        for(int n=0; n<c;n++) {
            if (o < Dpprofittable[x-1][n]) {
                o = Dpprofittable[x-1][n];
            }
        }
        return o + (monnthdemand.get(x-1)*B/2)  ;
    }


    public static int Greedycost(int x,int[] income,int t,int p,int d) {
        int total=totaldemend(x);
        int Greedycosttable[][] = new int[x+1][total+1];
        for(int i=0; i<x+1;i++) {              //burada yorum yaparak gitti�im i�in biraz fakl� bir yap� oldu yorum �u;
            for (int ttl = 0; ttl < 1; ttl++) {//gready maaliyet olmamas� ad�na ba�lang��ta hi� araba tutmayaca�� i�in ve �retim fazlas� ��kard���nda da anl�k d���n�rek garaj maaliyetinden ka�mak ad�na o tur i�in ek araba �retmedi�inden her seferinde garajda 0 arabam olur.bu sebeple loopun garajdaki t�m arabalar�(total demand say�sn�) gezmesine ihiya� yok.Garaj hep bo� olacak 0.sat�rda 0 stun'un 0 maaliyetli olmas� sebebi ile.
                if (i == 0) {
                    Greedycosttable[0][0] = 0;
                } else {
                    int need = monnthdemand.get(i - 1) - p;
                    if (need > 0) {
                        Greedycosttable[i][ttl] = Greedycosttable[i - 1][0] + (need * d); //ba�lang��ta hi� araba tutmad��� i�in ve �retim fazlas� ��kard���nda anl�k d���n�rek ek araba �retmedi�i i�in her seferinde garajda 0 arabam olur. ihtiyac� kadar stajyet al�r.

                    } else {
                        Greedycosttable[i][ttl] = Greedycosttable[i - 1][0];//�retim fazlas� yapabilir durumda oldu�unda da garaj maaliyetinden ka�may� se�ece�i i�in ve talebide kendi �retiminden kar��lad��� i�in bir �nceki ay ne gelirse onu s�rd�r�r.
                    }
                }
            }
        }
        return Greedycosttable[x][0];
    }


    public static int GreedyProfit(int x, int c, int[] income, int t, int B) {
        ArrayList<Integer>tempbesthistory=new ArrayList<>();
        ArrayList<Integer>bankhistory= new ArrayList<>();
        int Dpprofittable[][] = new int[x][c];
        double tempincome = 0.00;
        int tempbank=0;
        for(int i=0; i<x;i++){
            for (int bank=0;bank<c;bank++){
                if(i==0){
                    Dpprofittable[i][bank]=(int)((double)income[i]+(((double)income[i]/100)*(investment.get(bank))[i]));
                    if(tempincome <Dpprofittable[i][bank]){
                        tempincome=Dpprofittable[i][bank];
                        tempbank=bank;
                    }
                }else {
                      if (bank == bankhistory.get(i-1)) {
                            double aylikgelir = (double) tempbesthistory.get(i-1) + (double) income[i];
                          Dpprofittable[i][bank] = (int )(aylikgelir + (aylikgelir / 100 * (investment.get(bank))[i]));
                            if (tempincome < Dpprofittable[i][bank]) {
                                tempincome = Dpprofittable[i][bank];
                                tempbank=bank;
                            }
                        } else {
                            double kesintisonras� = (double) tempbesthistory.get(i-1) - ((double) tempbesthistory.get(i-1) * t / 100);
                            double aylikgelir = kesintisonras� + (double) income[i];
                            Dpprofittable[i][bank] =(int) (aylikgelir + (aylikgelir / 100 * (investment.get(bank))[i]));
                            if (tempincome < Dpprofittable[i][bank]) {
                                tempincome = Dpprofittable[i][bank];
                                tempbank=bank;

                            }
                        }

                }
            }
            tempbesthistory.add((int)tempincome);
            bankhistory.add(tempbank);
        }

        return tempbesthistory.get(x-1) + (monnthdemand.get(x-1)*B/2);
    }



    public static int[] mounthlydemand(int x,ArrayList<Integer> demeand,int B){
        int[] mountlydemand =new int[x];
        for(int i=0; i<x;i++){
            if(i==0){
                mountlydemand[i]=(demeand.get(i)*B)/2;
            }else{
                mountlydemand[i]=(demeand.get(i)*B)/2+(demeand.get(i-1)*B)/2; //profite son ay gelen yar�m paray� eklemeyi unutma.
            }
        }
        return mountlydemand;
    }


    public static int totaldemend(int x){
        int total=0;
        for(int i=0;i<x;i++){
            total=total+monnthdemand.get(i);
        }
    return total;
    }


    //You can add different functions here...
    public static void main(String[] args) throws IOException {
        int p = 2, d=2, x= 25, t = 2, B=100, c = 6;
        investment=new ArrayList<>();
        int[] c1,c2,c3,c4,c5,c6;
        c1 = new int[x];c2 = new int[x];c3 = new int[x];c4 = new int[x];c5 = new int[x];c6 = new int[x];
        garagecost = new ArrayList<>();
        monnthdemand = new ArrayList<>();

        String line = "";

        //Reading garage_cost.txt
        try(Scanner scanner = new Scanner(new FileReader("garage_cost.txt"))){
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String dosya = scanner.nextLine();

                String[] dosyaokuma = dosya.split("\t");
                garagecost.add(Integer.valueOf(dosyaokuma[1]));
            }


        } catch (FileNotFoundException ex) {
            System.out.println("Dosya bulunamad�");
        } catch (IOException ex) {
            System.out.println("I/O hatas�.");
        }
        //Reading month_demand.txt
        try(Scanner scanner = new Scanner(new FileReader("month_demand.txt"))){
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String dosya = scanner.nextLine();
                String[] dosyaokuma = dosya.split("\t");
                monnthdemand.add(Integer.valueOf(dosyaokuma[1]));
            }


        } catch (FileNotFoundException ex) {
            System.out.println("Dosya bulunamad�");
        } catch (IOException ex) {
            System.out.println("I/O hatas�.");
        }

        //Reading investment.txt
        try(Scanner scanner = new Scanner(new FileReader("investment.txt"))){
            scanner.nextLine();
            for(int i = 0; i<x; i++) {
                String dosya = scanner.nextLine();
                String[] dosyaokuma = dosya.split("\t");
                c1[i] = (Integer.valueOf(dosyaokuma[1]));
                c2[i] = (Integer.valueOf(dosyaokuma[2]));
                c3[i] = (Integer.valueOf(dosyaokuma[3]));
                c4[i] = (Integer.valueOf(dosyaokuma[4]));
                c5[i] = (Integer.valueOf(dosyaokuma[5]));
                c6[i] = (Integer.valueOf(dosyaokuma[6]));
            }


        } catch (FileNotFoundException ex) {
            System.out.println("Dosya bulunamad�");
        } catch (IOException ex) {
            System.out.println("I/O hatas�.");
        }

            investment.add(c1);
            investment.add(c2);
            investment.add(c3);
            investment.add(c4);
            investment.add(c5);
            investment.add(c6);

        final int[] income = mounthlydemand(x,monnthdemand,B);
        int Dpprofitt= DPProfit(x,c,income,t,B);
        int Greadyprofitt = GreedyProfit(x,c,income,t,B);
        int Dpcostt = DPcost(x,income,t,p,d);
        int Greedycostt = Greedycost(x,income,t,p,d);
        System.out.println("DP Results:" + ( Dpprofitt - Dpcostt ) );
        System.out.println("DP Results-Profit: "+Dpprofitt + " DP Results-Cost: "+ Dpcostt);
        System.out.println();
        System.out.println("Greedy Results:" + ( Greadyprofitt - Greedycostt ) );
        System.out.println("Greedy Results-Profit: "+Greadyprofitt + " Greedy Results-Cost: "+ Greedycostt);

        System.out.println("Hocam dobuble int d�n���mleri s�ras�nda olu�an kay�p yada kazan�tan dolay�, test dosyas�ndan �ok az farklar olu�abiliyor.");

    }
}









