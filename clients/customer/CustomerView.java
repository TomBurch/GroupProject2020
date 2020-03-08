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
    
    private class LoginPanel extends JPanel {    
        public LoginPanel() {
            setPreferredSize(new Dimension(400, 300));
            setLayout(new GridBagLayout());
            
            JTextField userEntry = new JTextField("Username: ", SwingConstants.TOP);
            JPasswordField passEntry = new JPasswordField("Password: ", SwingConstants.BOTTOM);
            
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String user = "abc";
                    String pass = "123";
                    controller.makeAccount(user, pass);
                    boolean success = controller.verifyAccount(user, pass);
                    
                    //controller.setState("Trade");
                }
            });
            
            add(userEntry);
            add(passEntry);
            add(loginButton, SwingConstants.CENTER);
            
            setBorder(BorderFactory.createTitledBorder("Enter login details"));
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
