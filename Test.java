import javax.swing.*;
import java.sql.*;

public class Test extends JFrame{
    JComboBox jc = new JComboBox();
    JPanel panel = new JPanel();
    Connection con;
    Statement st;
    ResultSet rs;
    public Test()
    {
    this.setSize(400, 400);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    try{
      con = DriverManager.getConnection("jdbc:mysql://localhost/optica","root","");
      st = con.createStatement();
      String s = "select nombre from marca";
      rs = st.executeQuery(s);
        while(rs.next())
        {
            jc.addItem(rs.getString(1));
        }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, "ERROR");
    }finally{
        try{
            st.close();
            rs.close();
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR CLOSE");
        }
    }
    panel.add(jc);
    this.getContentPane().add(panel);
    this.setVisible(true);
    }

public static void main(String[] args){
            new Test();
  }
}