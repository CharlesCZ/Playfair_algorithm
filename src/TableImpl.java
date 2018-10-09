import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TableImpl implements Table {

    private final char[][] table = new char[5][5];

    private String keyword;

    private String message;

    private String toFileMessage;



    private final String alph="ABCDEFGHIKLMNOPQRSTUVWXYZ";


    public class Coord{
        int x;
        int y;

        public Coord() {
            this.x = -1;
            this.y = -1;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
    public char[][] getTable() {
        return table;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void initFromTerminal() {

        System.out.println("wiadomosc");
        Scanner in = new Scanner(System.in);
        message = in.nextLine();

        System.out.println("klucz");
        in = new Scanner(System.in);
        keyword = in.nextLine();


        //message scan & prepare
        messageScanAndPrepare();

//matrix prepare
        matrixInit();


        //   print();
    }

    private void messageScanAndPrepare(){

        message=message.toUpperCase();
        keyword= keyword.toUpperCase();
        char helpletter = message.charAt(0);
        if (message.charAt(0) == message.charAt(message.length() - 1))
            message = new StringBuilder(message).insert(0, 'X').toString();
        for (int i = 1; i < message.length(); ++i) {
            if (helpletter == message.charAt(i))
                message = new StringBuilder(message).insert(i, 'X').toString();
            helpletter = message.charAt(i);
        }
        if(message.length()%2==1 ) // if odd
            message = new StringBuilder(message).insert(message.length(), 'X').toString();

        //    System.out.println("formatted message= "+message);


    }

    private void matrixInit() {
        String alphabet=new String(alph);
        //deleting the same letters of alphabet and keyword
        for(int l=0;l<keyword.length();++l)
            for(int k=0;k<alphabet.length();++k)
                if(keyword.charAt(l)==alphabet.charAt(k))
                    alphabet= new StringBuilder(alphabet).deleteCharAt(k).toString();
        //System.out.println("keyword= "+keyword);
//System.out.println("wyciety alfabet= "+alphabet);
        int temp=0,temp2=0;
        for (int i = 0; i < table.length; ++i)
            for (int j = 0; j < table.length; ++j) {

                // after keyword was written into matrix
                if(temp==keyword.length())
                {
                    table[i][j] =alphabet.charAt(temp2);
                    ++temp2;

                }

                //writing keyword
                if(keyword.length()>temp) {
                    table[i][j] = keyword.charAt(temp);
                    temp++;
                }

            }


    }


    @Override
    public void initFromFile() {
        System.out.println("sciezka wiadomosci    wpisz src/....txt");
        Scanner in = new Scanner(System.in);
        String messagePath = in.nextLine();
        Path file = Paths.get(messagePath);
        System.out.println(file.getFileName());
        byte[] fileArray=new byte[10000];
        try {
            fileArray = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        message=new String(fileArray);
        System.out.println(message+"         size=  "+message.length());


        System.out.println("sciezka klucza  wpisz src/....txt");
        in = new Scanner(System.in);
        messagePath = in.nextLine();
        file = Paths.get(messagePath);
        System.out.println(file.getFileName());
        fileArray=new byte[10000];
        try {
            fileArray = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        keyword=new String(fileArray);
        System.out.println(keyword+"         size=  "+keyword.length());



        //message scan & prepare
        messageScanAndPrepare();

//matrix prepare
        matrixInit();


        //   print();
    }

    @Override
    public void toFile() {

        System.out.println("sciezka wyjsciowa wiadomosci   src/...");
        Scanner in = new Scanner(System.in);
        String messagePath = in.nextLine();
        Path file = Paths.get(messagePath);
        System.out.println(file.getFileName());
        byte[] fileArray=toFileMessage.getBytes();
        try {
            Files.write(file,fileArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public String encript() {

        char[] pair=new char[2];
        String encriptmessage=new String();



        for(int p=0;p<message.length();p=p+2) {
            pair[0] = message.charAt(p);
            pair[1] = message.charAt(p + 1);

            Coord pPair0=new Coord();
            Coord pPair1= new Coord();

            //System.out.println(message+"       "+new String(pair));
            for (int i = 0; i < table.length; ++i)
                for (int j = 0; j < table.length; ++j) {

                    if(pair[0]==table[i][j] ) {
                        pPair0.y = i;
                        pPair0.x = j;


                        //System.out.print("para0   ");
                        //System.out.println(pair[0]+"    "+table[pPair0.y][pPair0.x]);
                    }
                    if( pair[1]==table[i][j]) {
                        pPair1.y = i;
                        pPair1.x = j;


                        //System.out.print("para1   ");
                        //System.out.println(pair[1]+"    "+table[pPair1.y][pPair1.x]);
                    }


//case 1 the same column
                    if(pPair0.y!=pPair1.y && pPair0.x==pPair1.x && pPair0.y>-1 && pPair1.y>-1 ){

                        if(pPair0.y!=table.length-1)
                            ++pPair0.y;
                        else
                            pPair0.y=0;

                        if(pPair1.y!=table.length-1)
                            ++pPair1.y;
                        else
                            pPair1.y=0;


                        encriptmessage += table[pPair0.y][pPair0.x];
                        encriptmessage += table[pPair1.y][pPair1.x];

                        i=table.length;
                        j=table.length;

                        //System.out.println("przypadek pierwszy=   "+encriptmessage);


                    }

                    //case 2 the same row

                    if(pPair0.y==pPair1.y && pPair0.x!=pPair1.x && pPair0.y>-1 && pPair1.y>-1 ){

                        if(pPair0.x<table.length-1)
                            ++pPair0.x;
                        else
                            pPair0.x=0;

                        if(pPair1.x<table.length-1)
                            ++pPair1.x;
                        else
                            pPair1.x=0;




                        encriptmessage += table[pPair0.y][pPair0.x];
                        encriptmessage += table[pPair1.y][pPair1.x];

                        i=table.length;
                        j=table.length;

                        //System.out.println("przypadek drugi=   "+encriptmessage);
                    }


                    //case 3 rectangle



                    if(pPair0.y!=pPair1.y && pPair0.x!=pPair1.x  && pPair0.y>-1 && pPair1.y>-1 )
                    {
                        int pPairtempx=pPair0.x;

                        pPair0.x=pPair1.x;
                        pPair1.x=pPairtempx;


                        encriptmessage += table[pPair0.y][pPair0.x];
                        encriptmessage += table[pPair1.y][pPair1.x];

                        i=table.length;
                        j=table.length;

                        //System.out.println("przypadek trzeci=   "+encriptmessage);
                    }



                }

        }
        toFileMessage=encriptmessage;
        return encriptmessage ;
    }

    @Override
    public String decript() {

        char[] pair=new char[2];
        String decriptmessage=new String();



        for(int p=0;p<message.length();p=p+2) {
            pair[0] = message.charAt(p);
            pair[1] = message.charAt(p + 1);

            Coord pPair0=new Coord();
            Coord pPair1= new Coord();

            //System.out.println(message+"       "+new String(pair));
            for (int i = 0; i < table.length; ++i)
                for (int j = 0; j < table.length; ++j) {

                    if(pair[0]==table[i][j] ) {
                        pPair0.y = i;
                        pPair0.x = j;


                        //System.out.print("para0   ");
                        //System.out.println(pair[0]+"    "+table[pPair0.y][pPair0.x]);
                    }
                    if( pair[1]==table[i][j]) {
                        pPair1.y = i;
                        pPair1.x = j;


                        //System.out.print("para1   ");
                        //System.out.println(pair[1]+"    "+table[pPair1.y][pPair1.x]);
                    }


//case 1 the same column
                    if(pPair0.y!=pPair1.y && pPair0.x==pPair1.x && pPair0.y>-1 && pPair1.y>-1 ){

                        if(pPair0.y!=0)
                            --pPair0.y;
                        else
                            pPair0.y=table.length-1;

                        if(pPair1.y!=0)
                            --pPair1.y;
                        else
                            pPair1.y=table.length-1;


                        decriptmessage += table[pPair0.y][pPair0.x];
                        decriptmessage += table[pPair1.y][pPair1.x];

                        i=table.length;
                        j=table.length;

                        //  //System.out.println("przypadek pierwszy=   "+decriptmessage);


                    }

                    //case 2 the same row

                    if(pPair0.y==pPair1.y && pPair0.x!=pPair1.x && pPair0.y>-1 && pPair1.y>-1 ){

                        if(pPair0.x!=0)
                            --pPair0.x;
                        else
                            pPair0.x=table.length-1;

                        if(pPair1.x!=0)
                            --pPair1.x;
                        else
                            pPair1.x=table.length-1;




                        decriptmessage += table[pPair0.y][pPair0.x];
                        decriptmessage += table[pPair1.y][pPair1.x];

                        i=table.length;
                        j=table.length;

                        // //System.out.println("przypadek drugi=   "+decriptmessage);
                    }


                    //case 3 rectangle



                    if(pPair0.y!=pPair1.y && pPair0.x!=pPair1.x  && pPair0.y>-1 && pPair1.y>-1 )
                    {
                        int pPairtempx=pPair0.x;

                        pPair0.x=pPair1.x;
                        pPair1.x=pPairtempx;


                        decriptmessage += table[pPair0.y][pPair0.x];
                        decriptmessage += table[pPair1.y][pPair1.x];

                        i=table.length;
                        j=table.length;

                        //  //System.out.println("przypadek trzeci=   "+decriptmessage);
                    }



                }

        }
        toFileMessage=decriptmessage ;
        return decriptmessage ;
    }

    @Override
    public void print() {
        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table.length; ++j) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }




}