package chatting_application;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Client implements ActionListener {
    JTextField text;
    static JPanel a1;

    static Box vertical = Box.createVerticalBox(); // vertical is called in the main method hence static

    static JFrame f = new JFrame(); // validate method is called in the main method

    static DataOutputStream dout;

    Client() {

        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(0, 0, 255));
        p1.setBounds(0, 0, 500, 70);
        p1.setLayout(null);
        f.add(p1);

        // back button
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3); // using JLabel to add icon on the panel as icon cannot be directly set on the
                                      // panel
        back.setBounds(5, 23, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        // profilepic1
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/man.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6); // using JLabel to add icon on the panel as icon cannot be directly set on the
                                         // panel
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        // call
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/call.png"));
        Image i8 = i7.getImage().getScaledInstance(27, 27, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel call = new JLabel(i9); // using JLabel to add icon on the panel as icon cannot be directly set on the
                                      // panel
        call.setBounds(320, 10, 50, 50);
        p1.add(call);

        // video call
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i11 = i10.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel video = new JLabel(i12); // using JLabel to add icon on the panel as icon cannot be directly set on the
                                        // panel
        video.setBounds(380, 10, 50, 50);
        p1.add(video);

        // morevert
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/more.png"));
        Image i14 = i13.getImage().getScaledInstance(23, 23, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15); // using JLabel to add icon on the panel as icon cannot be directly set on
                                           // the panel
        morevert.setBounds(440, 10, 50, 50);
        p1.add(morevert);

        // name
        JLabel name = new JLabel("Hamzah");
        name.setBounds(100, 5, 100, 50);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("TIMES_NEW_ROMAN", Font.BOLD, 23));
        p1.add(name);

        // active status
        JLabel status = new JLabel("online");
        status.setBounds(102, 35, 100, 30);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("TIMES_NEW_ROMAN", Font.ITALIC, 15));
        p1.add(status);

        // msg area
        a1 = new JPanel();
        a1.setBounds(5, 75, 490, 655);
        f.add(a1);

        // text box
        text = new JTextField();
        text.setBounds(5, 740, 400, 50);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 25));
        f.add(text);

        // send button
        JButton send = new JButton("Send");
        send.setBounds(415, 740, 80, 50);
        send.setBackground(new Color(0, 0, 255));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("ALGERIA", Font.ITALIC, 16));

        send.addActionListener(this);
        f.add(send);

        f.setSize(500, 800);
        f.setLocation(1200, 100);
        f.getContentPane().setBackground(Color.WHITE);
        f.setUndecorated(true);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        try {

            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);

            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        // msg designing
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(0, 0, 255));
        output.setForeground(Color.WHITE);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 15));

        panel.add(output);

        // Time of the msg
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime())); // to set dynamic time

        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Client();

        // creating socket
        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {

                a1.setLayout(new BorderLayout());

                String msg = din.readUTF();

                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);

                f.validate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
