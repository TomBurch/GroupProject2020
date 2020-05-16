package clients.customer;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class CustomerView implements PropertyChangeListener {
    private CustomerController controller;

    private JFrame frame = new JFrame("Customer MVC");
    private JPanel mainPanel = new JPanel();
    private JTabbedPane tabbedPane = new JTabbedPane();
    //private CardLayout cardLayout = new CardLayout();
    private CardLayout cardLayout = new PageViewer();
    private JPanel cardPanel = new JPanel(cardLayout);

    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel homePanel;
    private JPanel tradePanel;
    private JPanel savedPanel;
    private JPanel historyPanel;
    private JPanel accountPanel;
    
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
        accountPanel = new AccountPanel();

        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Current Trade", tradePanel);
        tabbedPane.addTab("Saved Products", savedPanel);
        tabbedPane.addTab("Trade History", historyPanel);
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/resources/cog.png"));
            Image scaledImage = image.getScaledInstance(15, 15, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(scaledImage);
            tabbedPane.addTab("", icon, accountPanel);
        } catch (Exception e) {
            System.out.println("CustomerView::Constructor:: " + e);
        }

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);

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
                frame.pack();
                break;

            case "homeOutput":
                JTextArea output = (JTextArea) homePanel.getClientProperty("output");
                output.setText((String) event.getNewValue());
                break;

            case "tradePrice":
                JLabel priceLabel = (JLabel) tradePanel.getClientProperty("priceLabel");
                priceLabel.setText(String.format("Total Price: £%5.2f", (float) event.getNewValue()));
                break;

            case "tradeList":
                JList<?> tradeList = (JList<?>) tradePanel.getClientProperty("tradeList");
                tradeList.setModel((DefaultListModel) event.getNewValue());
                break;

            case "savedList":
                JList<?> savedList = (JList<?>) savedPanel.getClientProperty("savedList");
                savedList.setModel((DefaultListModel) event.getNewValue());
                break;

            case "accountEmail":
                JTextField emailEntry = (JTextField) accountPanel.getClientProperty("emailEntry");
                emailEntry.setText((String) event.getNewValue());
                break;

            case "accountPostcode":
                JTextField postcodeEntry = (JTextField) accountPanel.getClientProperty("postcodeEntry");
                postcodeEntry.setText((String) event.getNewValue());
                break;

            case "resetView":
                tabbedPane.setSelectedIndex(0);
                ((JTextField) homePanel.getClientProperty("isbnEntry")).setText("");
                ((JTextArea) homePanel.getClientProperty("output")).setText("");
                break;
        }
    }

    /**
     * Panel for logging in to an existing account
     */
    public class LoginPanel extends JPanel {
        private JLabel title;
        private JTextField userEntry;
        private JLabel userLabel;
        private JPasswordField passEntry;
        private JLabel passLabel;
        private JButton loginButton;
        private JButton registerButton;

        public LoginPanel() {
            this.setSize(400,195);
            
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,195));
            contentPane.setBackground(new Color(192,192,192));
    
            title = new JLabel();
            title.setBounds(146,6,108,34);
            title.setBackground(new Color(214,217,223));
            title.setForeground(new Color(0,0,0));
            title.setEnabled(true);
            title.setFont(new Font("sansserif", Font.PLAIN,12));
            title.setText("Login or Register");
            title.setVisible(true);
    
            userEntry = new JTextField();
            userEntry.setBounds(146,50,214,33);
            userEntry.setBackground(new Color(255,255,255));
            userEntry.setForeground(new Color(0,0,0));
            userEntry.setEnabled(true);
            userEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            userEntry.setText("");
            userEntry.setVisible(true);
    
            userLabel = new JLabel();
            userLabel.setBounds(48,50,90,35);
            userLabel.setBackground(new Color(214,217,223));
            userLabel.setForeground(new Color(0,0,0));
            userLabel.setEnabled(true);
            userLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            userLabel.setText("Username: ");
            userLabel.setVisible(true);

            passEntry = new JPasswordField();
            passEntry.setBounds(146,92,214,33);
            passEntry.setBackground(new Color(255,255,255));
            passEntry.setForeground(new Color(0,0,0));
            passEntry.setEnabled(true);
            passEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            passEntry.setVisible(true);

            passLabel = new JLabel();
            passLabel.setBounds(48,92,90,35);
            passLabel.setBackground(new Color(214,217,223));
            passLabel.setForeground(new Color(0,0,0));
            passLabel.setEnabled(true);
            passLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            passLabel.setText("Password:");
            passLabel.setVisible(true);

            loginButton = new JButton();
            loginButton.setBounds(231,145,90,35);
            loginButton.setBackground(new Color(214,217,223));
            loginButton.setForeground(new Color(0,0,0));
            loginButton.setEnabled(true);
            loginButton.setFont(new Font("sansserif", Font.PLAIN,12));
            loginButton.setText("Login");
            loginButton.setVisible(true);
            loginButton.addActionListener( e -> {
                controller.login_loginButtonClicked(userEntry.getText(), passEntry.getText());
                userEntry.setText("");
                passEntry.setText("");
            });

            registerButton = new JButton();
            registerButton.setBounds(69,145,90,35);
            registerButton.setBackground(new Color(214,217,223));
            registerButton.setForeground(new Color(0,0,0));
            registerButton.setEnabled(true);
            registerButton.setFont(new Font("sansserif", Font.PLAIN,12));
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

    /**
     * Panel for registering a new account
     */
    public class RegisterPanel extends JPanel {
        private JLabel title;
        private JLabel userLabel;
        private JTextField userEntry;
        private JLabel passLabel;
        private JPasswordField passEntry;
        private JLabel passConfirmLabel;
        private JPasswordField passConfirmEntry;
        private JLabel postcodeLabel;
        private JTextField postcodeEntry;
        private JLabel emailLabel;
        private JTextField emailEntry;
        private JCheckBox ageCheckbox;
        private JButton confirmButton;
        private JButton cancelButton;

        public RegisterPanel() {
            this.setSize(400,350);

            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,350));
            contentPane.setBackground(new Color(192,192,192));

            title = new JLabel();
            title.setBounds(126,6,200,34);
            title.setBackground(new Color(214,217,223));
            title.setForeground(new Color(0,0,0));
            title.setEnabled(true);
            title.setFont(new Font("sansserif", Font.PLAIN,12));
            title.setText("Register a new account");
            title.setVisible(true);

            userEntry = new JTextField();
            userEntry.setBounds(146,50,214,33);
            userEntry.setBackground(new Color(255,255,255));
            userEntry.setForeground(new Color(0,0,0));
            userEntry.setEnabled(true);
            userEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            userEntry.setText("");
            userEntry.setVisible(true);

            userLabel = new JLabel();
            userLabel.setBounds(48,50,90,35);
            userLabel.setBackground(new Color(214,217,223));
            userLabel.setForeground(new Color(0,0,0));
            userLabel.setEnabled(true);
            userLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            userLabel.setText("Username: ");
            userLabel.setVisible(true);

            passEntry = new JPasswordField();
            passEntry.setBounds(146,92,214,33);
            passEntry.setBackground(new Color(255,255,255));
            passEntry.setForeground(new Color(0,0,0));
            passEntry.setEnabled(true);
            passEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            passEntry.setVisible(true);

            passLabel = new JLabel();
            passLabel.setBounds(48,92,90,35);
            passLabel.setBackground(new Color(214,217,223));
            passLabel.setForeground(new Color(0,0,0));
            passLabel.setEnabled(true);
            passLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            passLabel.setText("Password:");
            passLabel.setVisible(true);

            passConfirmEntry = new JPasswordField();
            passConfirmEntry.setBounds(146,134,214,33);
            passConfirmEntry.setBackground(new Color(255,255,255));
            passConfirmEntry.setForeground(new Color(0,0,0));
            passConfirmEntry.setEnabled(true);
            passConfirmEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            passConfirmEntry.setVisible(true);

            passConfirmLabel = new JLabel();
            passConfirmLabel.setBounds(30,134,140,35);
            passConfirmLabel.setBackground(new Color(214,217,223));
            passConfirmLabel.setForeground(new Color(0,0,0));
            passConfirmLabel.setEnabled(true);
            passConfirmLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            passConfirmLabel.setText("Confirm Password:");
            passConfirmLabel.setVisible(true);

            postcodeEntry = new JTextField();
            postcodeEntry.setBounds(146,176,214,33);
            postcodeEntry.setBackground(new Color(255,255,255));
            postcodeEntry.setForeground(new Color(0,0,0));
            postcodeEntry.setEnabled(true);
            postcodeEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            postcodeEntry.setVisible(true);

            postcodeLabel = new JLabel();
            postcodeLabel.setBounds(48,176,140,35);
            postcodeLabel.setBackground(new Color(214,217,223));
            postcodeLabel.setForeground(new Color(0,0,0));
            postcodeLabel.setEnabled(true);
            postcodeLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            postcodeLabel.setText("Postcode:");
            postcodeLabel.setVisible(true);

            emailEntry = new JTextField();
            emailEntry.setBounds(146,218,214,33);
            emailEntry.setBackground(new Color(255,255,255));
            emailEntry.setForeground(new Color(0,0,0));
            emailEntry.setEnabled(true);
            emailEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            emailEntry.setVisible(true);

            emailLabel = new JLabel();
            emailLabel.setBounds(55,218,140,35);
            emailLabel.setBackground(new Color(214,217,223));
            emailLabel.setForeground(new Color(0,0,0));
            emailLabel.setEnabled(true);
            emailLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            emailLabel.setText("E-Mail:");
            emailLabel.setVisible(true);

            ageCheckbox = new JCheckBox();
            ageCheckbox.setBounds(145,265,120,35);
            ageCheckbox.setBackground(new Color(192, 192, 192));
            ageCheckbox.setForeground(new Color(0,0,0));
            ageCheckbox.setEnabled(true);
            ageCheckbox.setFont(new Font("sansserif", Font.PLAIN,12));
            ageCheckbox.setText("I am over 18");
            ageCheckbox.setVisible(true);

            confirmButton = new JButton();
            confirmButton.setBounds(231,305,90,35);
            confirmButton.setBackground(new Color(214,217,223));
            confirmButton.setForeground(new Color(0,0,0));
            confirmButton.setEnabled(true);
            confirmButton.setFont(new Font("sansserif", Font.PLAIN,12));
            confirmButton.setText("Confirm");
            confirmButton.setVisible(true);
            confirmButton.addActionListener(e -> {
                    String result = controller.register_confirmButtonClicked(
                            ageCheckbox.isSelected(),
                            userEntry.getText(),
                            passEntry.getText(),
                            passConfirmEntry.getText(),
                            postcodeEntry.getText(),
                            emailEntry.getText()
                    );
                    if (!result.equals("success")) {
                        JOptionPane.showMessageDialog(contentPane, result);
                    } else {
                        userEntry.setText("");
                        passEntry.setText("");
                        passConfirmEntry.setText("");
                        postcodeEntry.setText("");
                        emailEntry.setText("");
                    }
            });

            cancelButton = new JButton();
            cancelButton.setBounds(69,305,90,35);
            cancelButton.setBackground(new Color(214,217,223));
            cancelButton.setForeground(new Color(0,0,0));
            cancelButton.setEnabled(true);
            cancelButton.setFont(new Font("sansserif", Font.PLAIN,12));
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
            contentPane.add(ageCheckbox);
            contentPane.add(confirmButton);
            contentPane.add(cancelButton);

            this.add(contentPane);
            this.setVisible(true);
        }
    }

    /**
     * Panel for entering ISBNs to view products, and adding them to the Trade basket
     */
    public class HomePanel extends JPanel {
        private JButton checkButton;
        private JButton tradeButton;
        private JTextField isbnEntry;
        private JLabel isbnLabel;
        public JTextArea output;

        public HomePanel(){
            this.setSize(400,300);
    
            //pane with null layout
            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));

            checkButton = new JButton();
            checkButton = new JButton();
            checkButton.setBounds(65,94,120,35);
            checkButton.setBackground(new Color(214,217,223));
            checkButton.setForeground(new Color(0,0,0));
            checkButton.setEnabled(true);
            checkButton.setFont(new Font("sansserif", Font.PLAIN,12));
            checkButton.setText("Check Product");
            checkButton.setVisible(true);
            checkButton.addActionListener(
                    e -> controller.home_checkButtonClicked(isbnEntry.getText())
            );
    
            tradeButton = new JButton();
            tradeButton.setBounds(215,94,120,35);
            tradeButton.setBackground(new Color(214,217,223));
            tradeButton.setForeground(new Color(0,0,0));
            tradeButton.setEnabled(true);
            tradeButton.setFont(new Font("sansserif", Font.PLAIN,12));
            tradeButton.setText("Add To Trade");
            tradeButton.setVisible(true);
            tradeButton.addActionListener(
                    e -> controller.home_tradeButtonClicked(isbnEntry.getText())
            );

            isbnEntry = new JTextField();
            isbnEntry.setBounds(109,48,267,29);
            isbnEntry.setBackground(new Color(255,255,255));
            isbnEntry.setForeground(new Color(0,0,0));
            isbnEntry.setEnabled(true);
            isbnEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            isbnEntry.setText("");
            isbnEntry.setVisible(true);
            this.putClientProperty("isbnEntry", isbnEntry);
    
            isbnLabel = new JLabel();
            isbnLabel.setBounds(19,45,90,35);
            isbnLabel.setBackground(new Color(214,217,223));
            isbnLabel.setForeground(new Color(0,0,0));
            isbnLabel.setEnabled(true);
            isbnLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            isbnLabel.setText("Enter ISBN: ");
            isbnLabel.setVisible(true);
    
            output = new JTextArea();
            output.setBounds(79,150,243,109);
            output.setBackground(new Color(255,255,255));
            output.setForeground(new Color(0,0,0));
            output.setEnabled(true);
            output.setFont(new Font("sansserif", Font.PLAIN,12));
            output.setText("");
            output.setVisible(true);
            this.putClientProperty("output", output);

            contentPane.add(checkButton);
            contentPane.add(tradeButton);
            contentPane.add(isbnEntry);
            contentPane.add(isbnLabel);
            contentPane.add(output);

            this.add(contentPane);
            this.setVisible(true);
        }
    }

    /**
     * Panel for viewing the Trade basket
     */
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

            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));

            tradeList = new JList<>();
            tradeList.setBounds(50, 25, 300, 150);
            tradeList.setBackground(new Color(255,255,255));
            tradeList.setForeground(new Color(0,0,0));
            tradeList.setEnabled(true);
            tradeList.setFont(new Font("sansserif", Font.PLAIN,12));
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
            priceLabel.setFont(new Font("sansserif", Font.PLAIN,12));
            priceLabel.setText("Total Price: £0.00");
            priceLabel.setVisible(true);
            this.putClientProperty("priceLabel", priceLabel);

            tradeButton = new JButton();
            tradeButton.setBounds(210,245,160,35);
            tradeButton.setBackground(new Color(214,217,223));
            tradeButton.setForeground(new Color(0,0,0));
            tradeButton.setEnabled(true);
            tradeButton.setFont(new Font("sansserif", Font.PLAIN,12));
            tradeButton.setText("Confirm Trade");
            tradeButton.setVisible(true);
            tradeButton.addActionListener(e -> {
                String result = controller.trade_tradeButtonClicked();
                JOptionPane.showMessageDialog(contentPane, result);
            });

            saveButton = new JButton();
            saveButton.setBounds(30,245,160,35);
            saveButton.setBackground(new Color(214,217,223));
            saveButton.setForeground(new Color(0,0,0));
            saveButton.setEnabled(true);
            saveButton.setFont(new Font("sansserif", Font.PLAIN,12));
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

            this.add(contentPane);
            this.setVisible(true);
        }
    }

    /**
     * Panel for viewing the Saved basket
     */
    public class SavedPanel extends JPanel {
        private JList<String> savedList;
        private JScrollPane scrollPane;

        private JPopupMenu popupMenu;
        private JMenuItem tradePopup;
        private JMenuItem deletePopup;

        public SavedPanel() {
            this.setSize(400,300);

            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));

            savedList = new JList<>();
            savedList.setBounds(50, 25, 300, 150);
            savedList.setBackground(new Color(255,255,255));
            savedList.setForeground(new Color(0,0,0));
            savedList.setEnabled(true);
            savedList.setFont(new Font("sansserif", Font.PLAIN,12));
            savedList.setVisible(true);
            this.putClientProperty("savedList", savedList);

            scrollPane = new JScrollPane();
            scrollPane.setBounds(50, 25, 300, 150);
            scrollPane.getViewport().add(savedList);

            popupMenu = new JPopupMenu("Saved");

            tradePopup = new JMenuItem("Add to trade");
            tradePopup.addActionListener(
                    e -> controller.saved_tradePopupClicked(
                            savedList.getSelectedValuesList()
                    )
            );

            deletePopup = new JMenuItem("Delete");
            deletePopup.addActionListener(
                    e -> controller.saved_deletePopupClicked(
                            savedList.getSelectedValuesList()
                    )
            );

            popupMenu.add(tradePopup);
            popupMenu.add(deletePopup);
            savedList.setComponentPopupMenu(popupMenu);
            contentPane.setComponentPopupMenu(popupMenu);

            contentPane.add(scrollPane);

            this.add(contentPane);
            this.setVisible(true);
        }
    }

    /**
     * Panel for viewing the Trade history (to be made)
     */
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
            label.setFont(new Font("sansserif", Font.PLAIN,12));
            label.setText("History");
            label.setVisible(true);
            
            contentPane.add(label);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);        
        }
    }

    /**
     * Panel for viewing and editing account details (to be made)
     */
    public class AccountPanel extends JPanel {
        private JTextField emailEntry;
        private JButton updateEmailButton;
        private JTextField postcodeEntry;
        private JButton updatePostcodeButton;
        private JButton deleteAccountButton;
        private JButton logoutButton;

        public AccountPanel() {
            this.setSize(400,300);

            JPanel contentPane = new JPanel(null);
            contentPane.setPreferredSize(new Dimension(400,300));
            contentPane.setBackground(new Color(192,192,192));

            emailEntry = new JTextField();
            emailEntry.setBounds(20,30,210,35);
            emailEntry.setBackground(new Color(255,255,255));
            emailEntry.setForeground(new Color(0,0,0));
            emailEntry.setEnabled(true);
            emailEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            emailEntry.setText("");
            emailEntry.setVisible(true);
            this.putClientProperty("emailEntry", emailEntry);

            updateEmailButton = new JButton();
            updateEmailButton.setBounds(245,30,120,35);
            updateEmailButton.setBackground(new Color(214,217,223));
            updateEmailButton.setForeground(new Color(0,0,0));
            updateEmailButton.setEnabled(true);
            updateEmailButton.setFont(new Font("sansserif", Font.PLAIN,12));
            updateEmailButton.setText("Update E-Mail");
            updateEmailButton.setVisible(true);
            updateEmailButton.addActionListener( e -> {
                String result = controller.account_updateEmailButtonClicked(emailEntry.getText());
                JOptionPane.showMessageDialog(contentPane, result);
            });

            postcodeEntry = new JTextField();
            postcodeEntry.setBounds(20,75,210,35);
            postcodeEntry.setBackground(new Color(255,255,255));
            postcodeEntry.setForeground(new Color(0,0,0));
            postcodeEntry.setEnabled(true);
            postcodeEntry.setFont(new Font("sansserif", Font.PLAIN,12));
            postcodeEntry.setText("");
            postcodeEntry.setVisible(true);
            this.putClientProperty("postcodeEntry", postcodeEntry);

            updatePostcodeButton = new JButton();
            updatePostcodeButton.setBounds(245,75,135,35);
            updatePostcodeButton.setBackground(new Color(214,217,223));
            updatePostcodeButton.setForeground(new Color(0,0,0));
            updatePostcodeButton.setEnabled(true);
            updatePostcodeButton.setFont(new Font("sansserif", Font.PLAIN,12));
            updatePostcodeButton.setText("Update Postcode");
            updatePostcodeButton.setVisible(true);
            updatePostcodeButton.addActionListener( e -> {
                String result = controller.account_updatePostcodeButtonClicked(postcodeEntry.getText());
                JOptionPane.showMessageDialog(contentPane, result);
            });

            deleteAccountButton = new JButton();
            deleteAccountButton.setBounds(50,245,120,35);
            deleteAccountButton.setBackground(new Color(214,217,223));
            deleteAccountButton.setForeground(new Color(0,0,0));
            deleteAccountButton.setEnabled(true);
            deleteAccountButton.setFont(new Font("sansserif", Font.PLAIN,12));
            deleteAccountButton.setText("Delete Account");
            deleteAccountButton.setVisible(true);
            deleteAccountButton.addActionListener( e -> {
                String result = controller.account_deleteAccountButtonClicked();
                JOptionPane.showMessageDialog(contentPane, result);
            });

            logoutButton = new JButton();
            logoutButton.setBounds(230,245,120,35);
            logoutButton.setBackground(new Color(214,217,223));
            logoutButton.setForeground(new Color(0,0,0));
            logoutButton.setEnabled(true);
            logoutButton.setFont(new Font("sansserif", Font.PLAIN,12));
            logoutButton.setText("Log out");
            logoutButton.setVisible(true);
            logoutButton.addActionListener(
                    e -> controller.account_logoutButtonClicked()
            );

            contentPane.add(emailEntry);
            contentPane.add(updateEmailButton);
            contentPane.add(postcodeEntry);
            contentPane.add(updatePostcodeButton);
            contentPane.add(deleteAccountButton);
            contentPane.add(logoutButton);
    
            //adding panel to JFrame and seting of window position and close operation
            this.add(contentPane);
            this.setVisible(true);        
        }
    }

    // Credit: https://stackoverflow.com/questions/23881651/cardlayout-with-different-sizes
    public class PageViewer extends CardLayout {

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Component current = findCurrentComponent(parent);
            if (current != null) {
                Insets insets = parent.getInsets();
                Dimension pref = current.getPreferredSize();
                pref.width += insets.left + insets.right;
                pref.height += insets.top + insets.bottom;
                return pref;
            }
            return super.preferredLayoutSize(parent);
        }

        public Component findCurrentComponent(Container parent) {
            for (Component comp : parent.getComponents()) {
                if (comp.isVisible()) {
                    return comp;
                }
            }
            return null;
        }
    }
}