package clients.customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class CustomerView implements PropertyChangeListener
{
    private CustomerController controller = null;
    
    private JPanel mainPanel = new JPanel();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    
    private static final String IMAGE = "cog.jpg";
    
    public CustomerView()
    {
        cardPanel.add(new LoginPanel(), "Login");
        cardPanel.add(tabbedPane, "Main");
        tabbedPane.addTab("Home", new HomePanel());
        tabbedPane.addTab("Current Trade", new TradePanel());
        tabbedPane.addTab("Saved Products", new SavedPanel());
        tabbedPane.addTab("Trade History", new HistoryPanel());
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/resources/cog.png"));
            Image scaledImage = image.getScaledInstance(15, 15, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(scaledImage);
            tabbedPane.addTab("", icon, new AccountPanel());
        } catch (Exception e) {
            System.out.println("CustomerView::Constructor:: " + e);
        }     
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        JFrame frame = new JFrame("Customer MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
                    System.out.println("CustomerView:: loginButton clicked");
                    String user = userEntry.getText();
                    String pass = passEntry.getText();
                    controller.makeAccount(user, pass);
                        
                    if (controller.verifyAccount(user, pass)) {
                        controller.setState("Main");
                    }
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
    
    public class HomePanel extends JPanel {
        private JMenuBar menuBar;
        private JButton tradeButton;
        private JTextField isbnEntry;
        private JLabel isbnLabel;
        private JList tradeList;
    
        //Constructor 
        public HomePanel(){
            this.setSize(400,300);
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));
    
            tradeButton = new JButton();
            tradeButton.setBounds(148,94,90,35);
            tradeButton.setBackground(new Color(214,217,223));
            tradeButton.setForeground(new Color(0,0,0));
            tradeButton.setEnabled(true);
            tradeButton.setFont(new Font("sansserif",0,12));
            tradeButton.setText("Submit");
            tradeButton.setVisible(true);
    
            isbnEntry = new JTextField();
            isbnEntry.setBounds(109,48,267,29);
            isbnEntry.setBackground(new Color(255,255,255));
            isbnEntry.setForeground(new Color(0,0,0));
            isbnEntry.setEnabled(true);
            isbnEntry.setFont(new Font("sansserif",0,12));
            isbnEntry.setText("");
            isbnEntry.setVisible(true);
    
            isbnLabel = new JLabel();
            isbnLabel.setBounds(19,45,90,35);
            isbnLabel.setBackground(new Color(214,217,223));
            isbnLabel.setForeground(new Color(0,0,0));
            isbnLabel.setEnabled(true);
            isbnLabel.setFont(new Font("sansserif",0,12));
            isbnLabel.setText("Enter ISBN: ");
            isbnLabel.setVisible(true);
    
            tradeList = new JList();
            tradeList.setBounds(79,150,243,109);
            tradeList.setBackground(new Color(255,255,255));
            tradeList.setForeground(new Color(0,0,0));
            tradeList.setEnabled(true);
            tradeList.setFont(new Font("sansserif",0,12));
            tradeList.setVisible(true);
    
            //adding components to contentPane panel
            contentPane.add(tradeButton);
            contentPane.add(isbnEntry);
            contentPane.add(isbnLabel);
            contentPane.add(tradeList);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);
        }
    }
    
    public class TradePanel extends JPanel {
        private JLabel label;
        public TradePanel() {
            this.setSize(400,300);
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));
            
            label = new JLabel();
            label.setBounds(19,45,90,35);
            label.setBackground(new Color(214,217,223));
            label.setForeground(new Color(0,0,0));
            label.setEnabled(true);
            label.setFont(new Font("sansserif",0,12));
            label.setText("Trade");
            label.setVisible(true);
            
            contentPane.add(label);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);
            
        }
    }
    
    public class SavedPanel extends JPanel {
        private JLabel label;
        public SavedPanel() {
            this.setSize(400,300);
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192)); 

            label = new JLabel();
            label.setBounds(19,45,90,35);
            label.setBackground(new Color(214,217,223));
            label.setForeground(new Color(0,0,0));
            label.setEnabled(true);
            label.setFont(new Font("sansserif",0,12));
            label.setText("Saved");
            label.setVisible(true);
            
            contentPane.add(label);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);
        }
    }
    
    public class HistoryPanel extends JPanel {
        private JLabel label;
        public HistoryPanel() {
            this.setSize(400,300);
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));            

            label = new JLabel();
            label.setBounds(19,45,90,35);
            label.setBackground(new Color(214,217,223));
            label.setForeground(new Color(0,0,0));
            label.setEnabled(true);
            label.setFont(new Font("sansserif",0,12));
            label.setText("History");
            label.setVisible(true);
            
            contentPane.add(label);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);        
        }
    }
    
    public class AccountPanel extends JPanel {
        private JLabel label;
        public AccountPanel() {
            this.setSize(400,300);
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));            

            label = new JLabel();
            label.setBounds(19,45,90,35);
            label.setBackground(new Color(214,217,223));
            label.setForeground(new Color(0,0,0));
            label.setEnabled(true);
            label.setFont(new Font("sansserif",0,12));
            label.setText("Account");
            label.setVisible(true);
            
            contentPane.add(label);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);        
        }
    }
}
