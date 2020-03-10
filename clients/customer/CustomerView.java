package clients.customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class CustomerView implements PropertyChangeListener
{
    private CustomerController controller = null;
    
    private JPanel mainPanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    
    public CustomerView()
    {
        cardPanel.add(new LoginPanel(), "Login");
        cardPanel.add(new TradePanel(), "Trade");
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);
    } 
    
    public void setVisible(boolean bool) {
        if (bool == true) {
            JFrame frame = new JFrame("Customer MVC");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mainPanel);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    public void setController(CustomerController controller) {
        this.controller = controller;
    }   
    
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("state")) {
            cardLayout.show(cardPanel, (String) event.getNewValue());
        }
    }
    
    public class LoginPanel extends JPanel {

        private JMenuBar menuBar;
        private JButton loginButton;
        private JPasswordField passEntry;
        private JLabel passLabel;
        private JButton registerButton;
        private JLabel title;
        private JTextField userEntry;
        private JLabel userLabel;

        //Constructor 
        public LoginPanel(){
            this.setSize(400,300);
            //menu generate method
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));
    
    
            loginButton = new JButton();
            loginButton.setBounds(231,200,90,35);
            loginButton.setBackground(new Color(214,217,223));
            loginButton.setForeground(new Color(0,0,0));
            loginButton.setEnabled(true);
            loginButton.setFont(new Font("sansserif",0,12));
            loginButton.setText("Login");
            loginButton.setVisible(true);
            
            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String user = "abc";
                    String pass = "123";
                    controller.makeAccount(user, pass);
                    boolean success = controller.verifyAccount(user, pass);
                        
                    controller.setState("Trade");
                }
            });
    
            passEntry = new JPasswordField();
            passEntry.setBounds(146,119,214,33);
            passEntry.setBackground(new Color(255,255,255));
            passEntry.setForeground(new Color(0,0,0));
            passEntry.setEnabled(true);
            passEntry.setFont(new Font("sansserif",0,12));
            passEntry.setVisible(true);
    
            passLabel = new JLabel();
            passLabel.setBounds(48,119,90,35);
            passLabel.setBackground(new Color(214,217,223));
            passLabel.setForeground(new Color(0,0,0));
            passLabel.setEnabled(true);
            passLabel.setFont(new Font("sansserif",0,12));
            passLabel.setText("Password:");
            passLabel.setVisible(true);
    
            registerButton = new JButton();
            registerButton.setBounds(69,200,90,35);
            registerButton.setBackground(new Color(214,217,223));
            registerButton.setForeground(new Color(0,0,0));
            registerButton.setEnabled(true);
            registerButton.setFont(new Font("sansserif",0,12));
            registerButton.setText("Register");
            registerButton.setVisible(true);
    
            title = new JLabel();
            title.setBounds(146,16,108,34);
            title.setBackground(new Color(214,217,223));
            title.setForeground(new Color(0,0,0));
            title.setEnabled(true);
            title.setFont(new Font("sansserif",0,12));
            title.setText("Login or Register");
            title.setVisible(true);
    
            userEntry = new JTextField();
            userEntry.setBounds(146,77,214,33);
            userEntry.setBackground(new Color(255,255,255));
            userEntry.setForeground(new Color(0,0,0));
            userEntry.setEnabled(true);
            userEntry.setFont(new Font("sansserif",0,12));
            userEntry.setText("");
            userEntry.setVisible(true);
    
            userLabel = new JLabel();
            userLabel.setBounds(48,77,90,35);
            userLabel.setBackground(new Color(214,217,223));
            userLabel.setForeground(new Color(0,0,0));
            userLabel.setEnabled(true);
            userLabel.setFont(new Font("sansserif",0,12));
            userLabel.setText("Username: ");
            userLabel.setVisible(true);
    
            //adding components to contentPane panel
            contentPane.add(loginButton);
            contentPane.add(passEntry);
            contentPane.add(passLabel);
            contentPane.add(registerButton);
            contentPane.add(title);
            contentPane.add(userEntry);
            contentPane.add(userLabel);
    
            this.add(contentPane);
            this.setVisible(true);
        }  
    }  
    
    private class TradePanel extends JPanel {
        public TradePanel() {
            setPreferredSize(new Dimension(400, 300));
            setLayout(new GridBagLayout());
            JButton tradeButton = new JButton("Trade");
            tradeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.setState("Login");
                }
            });
            add(tradeButton, SwingConstants.CENTER);
            setBorder(BorderFactory.createTitledBorder("Trade"));
        }
    }
}
