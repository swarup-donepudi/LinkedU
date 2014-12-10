/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import model.MessageBean;

/**
 *
 * @author skdonep
 */
public class MessagesDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public MessagesDAO() {
        super();
    }

    public int insertMessageIntoDB(MessageBean messageBean) throws IOException {
        int rowCount = 0;
        String insertQuery = "INSERT INTO LINKEDU.MESSAGES(FROM_ADDR,"
                + "TO_ADDR,"
                + "SUBJECT,"
                + "MESSAGE,"
                + "MSG_STATUS) "
                + "VALUES('"
                + messageBean.getFromAddress() + "','"
                + messageBean.getToAddress() + "','"
                + messageBean.getSubject() + "','"
                + messageBean.getMessageBody() + "','"
                + messageBean.getStatus() + "')";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            rowCount = stmt.executeUpdate(insertQuery);
            this.DBConn.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return rowCount;
    }
    public int deleteMessageFromDB(int msgID) throws IOException{
        int deleteCount=0;
        String deleteStatement="DELETE FROM LINKEDU.MESSAGES WHERE MSG_ID="+msgID;
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            deleteCount = stmt.executeUpdate(deleteStatement);
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return deleteCount;
    
    }
    public int fetchUnreadMsgsCountFromDB(String username) throws IOException {
        username = username.toLowerCase();
        int unreadCount = 0;
        String getunreadCountQuery = "SELECT COUNT(*) FROM LINKEDU.MESSAGES WHERE LOWER(TO_ADDR)='" + username + "' AND"
                + " MSG_STATUS='N'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(getunreadCountQuery);
            if (rs.next()) {
                unreadCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return unreadCount;
    }

    public ArrayList<MessageBean> fetchInoxItemsFromDB(String username) throws ParseException, IOException {
        ArrayList<MessageBean> inboxItems = new ArrayList<MessageBean>();
        username = username.toLowerCase();
        String getInboxQuery = "SELECT * FROM LINKEDU.MESSAGES WHERE LOWER(TO_ADDR)='" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(getInboxQuery);
            while (rs.next()) {
                MessageBean messageBean = new MessageBean();
                messageBean.setToAddress(username);
                messageBean.setFromAddress(rs.getString("FROM_ADDR"));
                messageBean.setSubject(rs.getString("SUBJECT"));
                messageBean.setMessageBody(rs.getString("MESSAGE"));
                messageBean.setStatus(rs.getString("MSG_STATUS").charAt(0));
                messageBean.setMsgId(rs.getInt("MSG_ID"));
                messageBean.setTimeStamp(new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH).parse(rs.getString("TIMESTAMP")));
                inboxItems.add(messageBean);
            }
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return inboxItems;
    }
}
