package clients.customer;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class CustomerView implements PropertyChangeListener {
    private CustomerController controller = null;
    
    private JPanel mainPanel = new JPanel();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);

    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel homePanel;
    private JPanel tradePanel;
    private JPanel savedPanel;
    private JPanel historyPanel;
    
    private static final String IMAGE = "cog.jpg";
    
    public CustomerView() {
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(registerPanel, "Register");
        cardPanel.add(tabbedPane, "Main");

        homePanel = new HomePanel();
        tradePanel = new TradePanel();
        savedPanel = new SavedPanel();
        historyPanel = new HistoryPanel();
        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Current Trade", tradePanel);
        tabbedPane.addTab("Saved Products", savedPanel);
        tabbedPane.addTab("Trade History", historyPanel);
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
    public void propertyChange(@NotNull PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case "state":
                cardLayout.show(cardPanel, (String) event.getNewValue());
                break;

            case "output":
                JTextArea output = (JTextArea) homePanel.getClientProperty("output");
                output.setText((String) event.getNewValue());
                break;

            case "tradePrice":
                JLabel priceLabel = (JLabel) tradePanel.getClientProperty("priceLabel");
                priceLabel.setText(String.format("Total Price: £%5.2f", (float) event.getNewValue()));
                break;

            case "tradeList":
                JList<String> tradeList = (JList<String>) tradePanel.getClientProperty("tradeList");
                tradeList.setModel((DefaultListModel) event.getNewValue());
                break;

            case "savedList":
                JList<String> savedList = (JList<String>) savedPanel.getClientProperty("savedList");
                savedList.setModel((DefaultListModel) event.getNewValue());
                break;
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
            loginButton.addActionListener(
                    e -> controller.login_loginButtonClicked(
                            userEntry.getText(),
                            passEntry.getText()
                    )
            );

            registerButton = new JButton();
            registerButton.setBounds(69,260,90,35);
            registerButton.setBackground(new Color(214,217,223));
            registerButton.setForeground(new Color(0,0,0));
            registerButton.setEnabled(true);
            registerButton.setFont(new Font("sansserif",0,12));
            registerButton.setText("Register");
            registerButton.setVisible(true);
            registerButton.addActionListener(
                    e -> controller.login_registerButtonClicked()
            );
    
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
            confirmButton.addActionListener(
                    e -> controller.register_confirmButtonClicked(
                            userEntry.getText(),
                            passEntry.getText(),
                            passConfirmEntry.getText(),
                            postcodeEntry.getText(),
                            emailEntry.getText()
                    )
            );

            cancelButton = new JButton();
            cancelButton.setBounds(69,260,90,35);
            cancelButton.setBackground(new Color(214,217,223));
            cancelButton.setForeground(new Color(0,0,0));
            cancelButton.setEnabled(true);
            cancelButton.setFont(new Font("sansserif",0,12));
            cancelButton.setText("Cancel");
            cancelButton.setVisible(true);
            cancelButton.addActionListener(
                    e -> controller.register_cancelButtonClicked()
            );

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
        private JButton submitButton;
        private JTextField isbnEntry;
        private JLabel isbnLabel;
        public JTextArea output;
    
        //Constructor 
        public HomePanel(){
            this.setSize(400,300);
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));
    
            submitButton = new JButton();
            submitButton.setBounds(148,94,90,35);
            submitButton.setBackground(new Color(214,217,223));
            submitButton.setForeground(new Color(0,0,0));
            submitButton.setEnabled(true);
            submitButton.setFont(new Font("sansserif",0,12));
            submitButton.setText("Submit");
            submitButton.setVisible(true);
            submitButton.addActionListener(
                    e -> controller.home_submitButtonClicked(isbnEntry.getText())
            );

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
            this.putClientProperty("output", output);
    
            //adding components to contentPane panel
            contentPane.add(submitButton);
            contentPane.add(isbnEntry);
            contentPane.add(isbnLabel);
            contentPane.add(output);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);
        }
    }
    
    public class TradePanel extends JPanel {
        private JList<String> tradeList;
        private JScrollPane scrollPane;
        private JLabel priceLabel;
        private JButton tradeButton;
        private JButton saveButton;

        private JPopupMenu popupMenu;
        private JMenuItem savePopup;
        private JMenuItem deletePopup;

        public TradePanel() {
            this.setSize(400,300);
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));

            tradeList = new JList<String>();
            tradeList.setBounds(50, 25, 300, 150);
            tradeList.setBackground(new Color(255,255,255));
            tradeList.setForeground(new Color(0,0,0));
            tradeList.setEnabled(true);
            tradeList.setFont(new Font("sansserif",0,12));
            tradeList.setVisible(true);
            this.putClientProperty("tradeList", tradeList);

            scrollPane = new JScrollPane();
            scrollPane.setBounds(50, 25, 300, 150);
            scrollPane.getViewport().add(tradeList);

            priceLabel = new JLabel();
            priceLabel.setBounds(147,200,160,35);
            priceLabel.setBackground(new Color(214,217,223));
            priceLabel.setForeground(new Color(0,0,0));
            priceLabel.setEnabled(true);
            priceLabel.setFont(new Font("sansserif",0,12));
            priceLabel.setText("Total Price: £0.00");
            priceLabel.setVisible(true);
            this.putClientProperty("priceLabel", priceLabel);

            tradeButton = new JButton();
            tradeButton.setBounds(210,245,160,35);
            tradeButton.setBackground(new Color(214,217,223));
            tradeButton.setForeground(new Color(0,0,0));
            tradeButton.setEnabled(true);
            tradeButton.setFont(new Font("sansserif",0,12));
            tradeButton.setText("Confirm Trade");
            tradeButton.setVisible(true);
            tradeButton.addActionListener(
                    e -> controller.trade_tradeButtonClicked()
            );

            saveButton = new JButton();
            saveButton.setBounds(30,245,160,35);
            saveButton.setBackground(new Color(214,217,223));
            saveButton.setForeground(new Color(0,0,0));
            saveButton.setEnabled(true);
            saveButton.setFont(new Font("sansserif",0,12));
            saveButton.setText("Save For Later");
            saveButton.setVisible(true);
            saveButton.addActionListener(
                    e -> controller.trade_saveButtonClicked()
            );

            popupMenu = new JPopupMenu("Trade");

            savePopup = new JMenuItem("Save for later");
            savePopup.addActionListener(
                    e -> controller.trade_savePopupClicked(
                            tradeList.getSelectedValuesList()
                    )
            );

            deletePopup = new JMenuItem("Delete");
            deletePopup.addActionListener(
                    e -> controller.trade_deletePopupClicked(
                            tradeList.getSelectedValuesList()
                    )
            );

            popupMenu.add(savePopup);
            popupMenu.add(deletePopup);
            tradeList.setComponentPopupMenu(popupMenu);
            contentPane.setComponentPopupMenu(popupMenu);
            contentPane.add(scrollPane);
            contentPane.add(priceLabel);
            contentPane.add(tradeButton);
            contentPane.add(saveButton);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);
        }
    }
    
    public class SavedPanel extends JPanel {
        private JList<String> savedList;
        private JScrollPane scrollPane;

        public SavedPanel() {
            this.setSize(400,300);

            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));

            savedList = new JList<String>();
            savedList.setBounds(50, 25, 300, 150);
            savedList.setBackground(new Color(255,255,255));
            savedList.setForeground(new Color(0,0,0));
            savedList.setEnabled(true);
            savedList.setFont(new Font("sansserif",0,12));
            savedList.setVisible(true);
            this.putClientProperty("savedList", savedList);

            scrollPane = new JScrollPane();
            scrollPane.setBounds(50, 25, 300, 150);
            scrollPane.getViewport().add(savedList);

            contentPane.add(scrollPane);
    
            //adding panel to JFrame and setting of window position and close operation
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