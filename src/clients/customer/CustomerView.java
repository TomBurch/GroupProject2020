package clients.customer;

import trade.Product;

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
        cardPanel.add(new RegisterPanel(), "Register");
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
        private JLabel title;
        private JTextField userEntry;
        private JLabel userLabel;
        private JPasswordField passEntry;
        private JLabel passLabel;
        private JButton loginButton;
        private JButton registerButton;

        //Constructor 
        public LoginPanel() {
            this.setSize(400,300);
            
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));
    
            title = new JLabel();
            title.setBounds(146,6,108,34);
            title.setBackground(new Color(214,217,223));
            title.setForeground(new Color(0,0,0));
            title.setEnabled(true);
            title.setFont(new Font("sansserif",0,12));
            title.setText("Login or Register");
            title.setVisible(true);
    
            userEntry = new JTextField();
            userEntry.setBounds(146,50,214,33);
            userEntry.setBackground(new Color(255,255,255));
            userEntry.setForeground(new Color(0,0,0));
            userEntry.setEnabled(true);
            userEntry.setFont(new Font("sansserif",0,12));
            userEntry.setText("");
            userEntry.setVisible(true);
    
            userLabel = new JLabel();
            userLabel.setBounds(48,50,90,35);
            userLabel.setBackground(new Color(214,217,223));
            userLabel.setForeground(new Color(0,0,0));
            userLabel.setEnabled(true);
            userLabel.setFont(new Font("sansserif",0,12));
            userLabel.setText("Username: ");
            userLabel.setVisible(true);

            passEntry = new JPasswordField();
            passEntry.setBounds(146,92,214,33);
            passEntry.setBackground(new Color(255,255,255));
            passEntry.setForeground(new Color(0,0,0));
            passEntry.setEnabled(true);
            passEntry.setFont(new Font("sansserif",0,12));
            passEntry.setVisible(true);

            passLabel = new JLabel();
            passLabel.setBounds(48,92,90,35);
            passLabel.setBackground(new Color(214,217,223));
            passLabel.setForeground(new Color(0,0,0));
            passLabel.setEnabled(true);
            passLabel.setFont(new Font("sansserif",0,12));
            passLabel.setText("Password:");
            passLabel.setVisible(true);

            loginButton = new JButton();
            loginButton.setBounds(231,260,90,35);
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

                    if (controller.verifyAccount(user, pass)) {
                        controller.setState("Main");
                    }
                }
            });

            registerButton = new JButton();
            registerButton.setBounds(69,260,90,35);
            registerButton.setBackground(new Color(214,217,223));
            registerButton.setForeground(new Color(0,0,0));
            registerButton.setEnabled(true);
            registerButton.setFont(new Font("sansserif",0,12));
            registerButton.setText("Register");
            registerButton.setVisible(true);
            registerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("CustomerView:: registerButton clicked");
                    controller.setState("Register");
                }
            });
    
            //adding components to contentPane panel
            contentPane.add(title);
            contentPane.add(userEntry);
            contentPane.add(userLabel);
            contentPane.add(passEntry);
            contentPane.add(passLabel);
            contentPane.add(loginButton);
            contentPane.add(registerButton);
    
            this.add(contentPane);
            this.setVisible(true);
        }  
    }  
    
    public class RegisterPanel extends JPanel{
        private JButton confirmButton;
        private JButton cancelButton;
        private JLabel title;
        private JTextField userEntry;
        private JLabel userLabel;
        private JPasswordField passEntry;
        private JLabel passLabel;
        private JPasswordField passConfirmEntry;
        private JLabel passConfirmLabel;
        private JTextField postcodeEntry;
        private JLabel postcodeLabel;
        private JTextField emailEntry;
        private JLabel emailLabel;

        public RegisterPanel() {
            this.setSize(400,300);

            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));

            title = new JLabel();
            title.setBounds(126,6,200,34);
            title.setBackground(new Color(214,217,223));
            title.setForeground(new Color(0,0,0));
            title.setEnabled(true);
            title.setFont(new Font("sansserif",0,12));
            title.setText("Register a new account");
            title.setVisible(true);

            userEntry = new JTextField();
            userEntry.setBounds(146,50,214,33);
            userEntry.setBackground(new Color(255,255,255));
            userEntry.setForeground(new Color(0,0,0));
            userEntry.setEnabled(true);
            userEntry.setFont(new Font("sansserif",0,12));
            userEntry.setText("");
            userEntry.setVisible(true);

            userLabel = new JLabel();
            userLabel.setBounds(48,50,90,35);
            userLabel.setBackground(new Color(214,217,223));
            userLabel.setForeground(new Color(0,0,0));
            userLabel.setEnabled(true);
            userLabel.setFont(new Font("sansserif",0,12));
            userLabel.setText("Username: ");
            userLabel.setVisible(true);

            passEntry = new JPasswordField();
            passEntry.setBounds(146,92,214,33);
            passEntry.setBackground(new Color(255,255,255));
            passEntry.setForeground(new Color(0,0,0));
            passEntry.setEnabled(true);
            passEntry.setFont(new Font("sansserif",0,12));
            passEntry.setVisible(true);

            passLabel = new JLabel();
            passLabel.setBounds(48,92,90,35);
            passLabel.setBackground(new Color(214,217,223));
            passLabel.setForeground(new Color(0,0,0));
            passLabel.setEnabled(true);
            passLabel.setFont(new Font("sansserif",0,12));
            passLabel.setText("Password:");
            passLabel.setVisible(true);

            passConfirmEntry = new JPasswordField();
            passConfirmEntry.setBounds(146,134,214,33);
            passConfirmEntry.setBackground(new Color(255,255,255));
            passConfirmEntry.setForeground(new Color(0,0,0));
            passConfirmEntry.setEnabled(true);
            passConfirmEntry.setFont(new Font("sansserif",0,12));
            passConfirmEntry.setVisible(true);

            passConfirmLabel = new JLabel();
            passConfirmLabel.setBounds(30,134,140,35);
            passConfirmLabel.setBackground(new Color(214,217,223));
            passConfirmLabel.setForeground(new Color(0,0,0));
            passConfirmLabel.setEnabled(true);
            passConfirmLabel.setFont(new Font("sansserif",0,12));
            passConfirmLabel.setText("Confirm Password:");
            passConfirmLabel.setVisible(true);

            postcodeEntry = new JTextField();
            postcodeEntry.setBounds(146,176,214,33);
            postcodeEntry.setBackground(new Color(255,255,255));
            postcodeEntry.setForeground(new Color(0,0,0));
            postcodeEntry.setEnabled(true);
            postcodeEntry.setFont(new Font("sansserif",0,12));
            postcodeEntry.setVisible(true);

            postcodeLabel = new JLabel();
            postcodeLabel.setBounds(48,176,140,35);
            postcodeLabel.setBackground(new Color(214,217,223));
            postcodeLabel.setForeground(new Color(0,0,0));
            postcodeLabel.setEnabled(true);
            postcodeLabel.setFont(new Font("sansserif",0,12));
            postcodeLabel.setText("Postcode:");
            postcodeLabel.setVisible(true);

            emailEntry = new JTextField();
            emailEntry.setBounds(146,218,214,33);
            emailEntry.setBackground(new Color(255,255,255));
            emailEntry.setForeground(new Color(0,0,0));
            emailEntry.setEnabled(true);
            emailEntry.setFont(new Font("sansserif",0,12));
            emailEntry.setVisible(true);

            emailLabel = new JLabel();
            emailLabel.setBounds(48,218,140,35);
            emailLabel.setBackground(new Color(214,217,223));
            emailLabel.setForeground(new Color(0,0,0));
            emailLabel.setEnabled(true);
            emailLabel.setFont(new Font("sansserif",0,12));
            emailLabel.setText("E-Mail:");
            emailLabel.setVisible(true);

            confirmButton = new JButton();
            confirmButton.setBounds(231,260,90,35);
            confirmButton.setBackground(new Color(214,217,223));
            confirmButton.setForeground(new Color(0,0,0));
            confirmButton.setEnabled(true);
            confirmButton.setFont(new Font("sansserif",0,12));
            confirmButton.setText("Confirm");
            confirmButton.setVisible(true);
            confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("CustomerView:: confirmButton clicked");
                    String user = userEntry.getText();
                    String pass = passEntry.getText();
                    String passConfirm = passConfirmEntry.getText();
                    String postcode = postcodeEntry.getText();
                    String email = emailEntry.getText();

                    controller.makeAccount(user, pass, passConfirm, postcode, email);
                    if (controller.verifyAccount(user, pass)) {
                        controller.setState("Main");
                    }
                }
            });

            cancelButton = new JButton();
            cancelButton.setBounds(69,260,90,35);
            cancelButton.setBackground(new Color(214,217,223));
            cancelButton.setForeground(new Color(0,0,0));
            cancelButton.setEnabled(true);
            cancelButton.setFont(new Font("sansserif",0,12));
            cancelButton.setText("Cancel");
            cancelButton.setVisible(true);
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("CustomerView:: cancelButton clicked");
                    controller.setState("Login");
                }
            });

            contentPane.add(title);
            contentPane.add(userEntry);
            contentPane.add(userLabel);
            contentPane.add(passEntry);
            contentPane.add(passLabel);
            contentPane.add(passConfirmEntry);
            contentPane.add(passConfirmLabel);
            contentPane.add(postcodeEntry);
            contentPane.add(postcodeLabel);
            contentPane.add(emailEntry);
            contentPane.add(emailLabel);
            contentPane.add(confirmButton);
            contentPane.add(cancelButton);

            this.add(contentPane);
            this.setVisible(true);
        }
    }

    public class HomePanel extends JPanel {
        private JButton tradeButton;
        private JTextField isbnEntry;
        private JLabel isbnLabel;
        private JTextArea output;
    
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
            tradeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("CustomerView:: tradeButton clicked");
                    String ISBN = isbnEntry.getText();
                    Product product = controller.getProduct(ISBN);

                    if (product != null) {
                        controller.addProductToBasket(product);
                        output.setText(product.getDetails());
                    } else {
                        output.setText("Product is not currently required");
                    }
                }
            });

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
    
            output = new JTextArea();
            output.setBounds(79,150,243,109);
            output.setBackground(new Color(255,255,255));
            output.setForeground(new Color(0,0,0));
            output.setEnabled(true);
            output.setFont(new Font("sansserif",0,12));
            output.setText("");
            output.setVisible(true);
    
            //adding components to contentPane panel
            contentPane.add(tradeButton);
            contentPane.add(isbnEntry);
            contentPane.add(isbnLabel);
            contentPane.add(output);
    
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
