import java.io.File;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public  static void main(String[] args) throws SQLException {
        //load the driver
        DriverManager.registerDriver( new oracle.jdbc.OracleDriver());
//Create a connection
        Connection conn =
                DriverManager.getConnection("jdbc:oracle:thin:@10.170.138.40:1521:orclE",
                        "sbd51162", "51162");
        conn.setAutoCommit(false);
        try {

    File file = new File("C:\\Users\\Ang3lExtreme\\Documents\\FCT\\2223\\2\\SBD\\pratica\\aula3\\src\\files\\usa_accidents_2001_2013.csv");
        int N = 1000;
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO ACCIDENTS VALUES(?,?,?,?,?,?,?,?,?)"
            );
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine(); //Skip headers
            int id = 1;
            long initBulk = System.currentTimeMillis();
            System.out.println(initBulk);
            int i = 0;
            while (scanner.hasNextLine() && id <= N ) {
                String[] splitted = scanner.nextLine().split(";");
                String YearColumn = splitted[47];
                String MonthColumn = splitted[2];
                String DayColumn = splitted[3];
                String HourColumn = splitted[4];
                String MinuteColumn = splitted[5];
                String LatitudeColumn = splitted[53];
                String LongitudeColumn = splitted[54];
                String VictimsColumn = splitted[41];

                //TODO



                insert.setInt(1, id);
                insert.setInt(2,Integer.parseInt(YearColumn));
                insert.setInt(3, Integer.parseInt(MonthColumn));
                insert.setInt(4,Integer.parseInt(DayColumn));
                insert.setInt(5, Integer.parseInt(HourColumn));
                insert.setInt(6, Integer.parseInt(MinuteColumn));
                insert.setFloat(7,Float.parseFloat(LatitudeColumn));
                insert.setFloat(8, Float.parseFloat(LongitudeColumn));
                insert.setLong(9,Long.parseLong(VictimsColumn));
                insert.addBatch();

                i++;
                if(i == 250) {
                    insert.executeBatch();
                    System.out.println("batch "+ i);
                    i=0;
                }

                id++;
                //asd
            }
            long endBulk = System.currentTimeMillis();
            System.out.println(TimeUnit.MILLISECONDS.toSeconds(endBulk-initBulk));
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



        }


        finally {
            conn.close();
        }

    }
}
