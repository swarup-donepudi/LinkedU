/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.MessageBean;

/**
 *
 * @author skdonep
 */
public class MessagesDAO extends AppDBInfoDAO{
    private Connection DBConn;
    
    public MessagesDAO(){
        super();
    }
    
    public int insertMessageIntoDB(MessageBean messageBean){
        int rowCount=0;

        String insertQuery = "INSERT INTO LINKEDU.MESSAGES(FROMADDRESS,"
                + "TOADDRESS,"
                + "SUBJECT,"
                + "MESSAGEBODY,"
                + "STATUS) "
                + "VALUES('"
                + messageBean.getFromAddress()+ "','"
                + messageBean.getToAddress() + "','"
                + messageBean.getSubject() + "','"
                + messageBean.getMessageBody()+ "','"
                + messageBean.getStatus() + "')";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            rowCount = stmt.executeUpdate(insertQuery);
            this.DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return rowCount;
    }
    
    public int fetchUnreadMsgsCountFromDB(String username){
        int unreadCount=0;
        String getunreadCountQuery = "SELECT COUNT(*) FROM LINKEDU.MESSAGES WHERE TOADDRESS='" + username + "' AND"
                + " STATUS='N'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(getunreadCountQuery);
            if (rs.next()) {
                unreadCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return unreadCount;
    }
}
