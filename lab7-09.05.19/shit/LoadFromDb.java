package server;

import L3pc.Mumiboy;
import L3pc.MumiboyTreeSet;

import java.sql.*;


public class LoadFromDb
{
    public static void loadingStr(MumiboyTreeSet mumiboyTreeSet){
        ConnectBuilder connectBuilder = new ConnectBuilder("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/Lab7",
                "postgres", "postgres","SELECT * FROM JC_CONTACT" );
        try {
            Statement stmt = connectBuilder.getStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM JC_CONTACT");
            while(rs.next()) {
                String str = rs.getString("contact_id") + ":" + rs.getString(2) + "   charm: " + rs.getString(3);
                System.out.println(str);
                mumiboyTreeSet.add(rs.getString(2), rs.getShort(3));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}