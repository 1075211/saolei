import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
public class Incrementor {
    public static void main(String[] args) throws IOException {
            JPanel jp1=new JPanel();
            JFrame frame =new JFrame ("Incrementor");
            Container co=frame.getContentPane();
            frame.setSize(200, 160);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField a1=new JTextField(10);
            JButton btn1=new JButton ("Increment");
            JButton btn2=new JButton("Decrement");
            jp1.add(a1);
            jp1.add(btn1);
            jp1.add(btn2);
            co.add(jp1);
            btn1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String a=a1.getText();
                    int b=Integer.parseInt(a);
                   a1.setText(String.valueOf(++b));
                }
            });    
            btn2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String a=a1.getText();
                    int b=Integer.parseInt(a);
                    a1.setText(String.valueOf(--b));
                }
            });      
    }
}