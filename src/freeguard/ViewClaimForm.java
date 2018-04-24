package freeguard;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Kyle Clabough COSC 412
 *
 */
public class ViewClaimForm extends JFrame
{

    private JFrame resultFrame; //declare variable
    private JTable resultTable;
    DatabaseManager db = new DatabaseManager();
    // database URL
    String DB_URL = db.JDBC_DB_URL;

    //  Database credentials
     String USER = db.username;
     String PASS = db.password;
    
    protected static final String String = null;
    static final int ROWNUM = 2;

    public ViewClaimForm(String username)
    {
        
        resultFrame = new JFrame();      //create object of JPanel
        resultFrame.setTitle("View Claims");                                                                                                //set title
        resultFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);                        //set close operation                                                                          
        resultFrame.setSize(450, 300);                                      //set contentPane border

        resultFrame.setAlwaysOnTop(true);


        //string builder used to format and store the resulting tables
        int columnNum = 0;
        
        JLabel lblTitle = new JLabel("Claims");
        lblTitle.setBounds(70, 25, 86, 14);

        String SQuery = "select db412.SSClaims.idSSClaims,"
                + "db412.SSClaims.Moneys \n"
                + "FROM db412.SSClaims, db412.accounts\n"
                + "WHERE '" + username + "' = accounts.email AND SSClaims.CustomerSSN = accounts.SSN;";
        System.out.println("Connecting to a selected database...");
        System.out.println(SQuery);

        Wrapper conn = null;                                                                //Create wrapper object and define it null
        try                                                                                                                                                          //try block
        {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);                            // Open a connection
            System.out.println("Connected database successfully...");
            Statement stmt = ((Connection) conn).createStatement();
            ResultSet rs = stmt.executeQuery(SQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            columnNum = rsmd.getColumnCount();                                                          //Get number of Columns
            Object [] [] cellData = new Object [columnNum] [ROWNUM];
            String [] columnNames = new String [columnNum];
            for (int i = 0; i< ROWNUM; i++)
            {
                 columnNames [i] = rsmd.getColumnName(i+1);
            }
            int i = 0;
            rs.next();
            while (rs.next())
            {
                for (int j = 0; j < ROWNUM; j++)   //for loop nested in while loop to retrieve the results table
                {
                    cellData [i] [j] = rs.getString(j+1);
                }
                i++;
            }
            // JOptionPane.showMessageDialog(null, SMessage, "Message", JOptionPane.PLAIN_MESSAGE);
            //close connection
            ((java.sql.Connection) conn).close();
            resultTable =  new JTable (cellData, columnNames);
            resultFrame.add(new JScrollPane(resultTable));
            resultFrame.setVisible(true);
        }
        catch (SQLException se)
        {
            //handle errors for JDBC
            se.printStackTrace();
        }
        catch (Exception a) //catch block
        {
            a.printStackTrace();
        }

    }

//    public static void main(String[] args)
//    {
//        try  //try block
//        {
//            //create NewUser frame object
//            Login frame = new Login();
//            //set NewUser frame visible
//            frame.setVisible(true);
//        }
//        catch (Exception e) //catch block
//        {
//            e.printStackTrace();
//        }
//    }
}
