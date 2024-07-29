package turkishairlines;


//Gerekli Importlar
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.SpinnerDateModel;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.sql.SQLException;
import java.util.*;



// Ana Frame
public class AirlineReservationGUI extends JFrame implements ActionListener {
    private JComboBox<String> fromComboBox, toComboBox;
    private JSpinner dateSpinner;
    private JComboBox<String> classComboBox;
    
    public AirlineReservationGUI() {
        setTitle("Havayolu Rezervasyon Sistemi");
        setSize(1920, 1080); // Frame boyutu 1920x1080
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Sayfanin tamamini kaplamasi isin
        setLocationRelativeTo(null);

        // Bilet Paneli, arka plan resmi ve bilet alma kısmı
        JPanel backPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("arkaplan.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                
            }
            
            
        };
        backPanel.setLayout(new BorderLayout());
        //backPanel.setBorder(BorderFactory.createTitledBorder("backPanel"));
                
        // TicketPanel
        JPanel ticketPanel = new JPanel();
        ticketPanel.setLayout(new BorderLayout());

        ticketPanel.setPreferredSize(new Dimension(500, 400)); // 500x500 boyutunda bir panel oluşturur
        ticketPanel.setOpaque(false);
        //ticketPanel.setBorder(BorderFactory.createTitledBorder("ticketPanel"));


        // Bilet alma panelini ayarlamak için gerekli paneller
        JPanel rightPanel = new JPanel();
        //rightPanel.setBorder(BorderFactory.createTitledBorder("Sağ Panel"));
        JPanel leftPanel = new JPanel();
        //leftPanel.setBorder(BorderFactory.createTitledBorder("Sol Panel"));
        
        //Sol, sağ panelin ayarları
        leftPanel.setPreferredSize(new Dimension(400, 200));
        rightPanel.setPreferredSize(new Dimension(400, 200));
        leftPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        
        //centerpanel
        JPanel centerPanel = new JPanel();
        //centerPanel.setBorder(BorderFactory.createTitledBorder("Orta Panel"));
        centerPanel.setBackground(Color.WHITE);
        
        
        //inputPanel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //inputPanel.setBorder(BorderFactory.createTitledBorder("inputPanel"));


        // Nereden ve Nereye ComboBox'ları
        String[] cities = {"İstanbul", "Ankara", "İzmir", "Paris", "Madrid", "Berlin"};
        fromComboBox = new JComboBox<>(cities);
        toComboBox = new JComboBox<>(cities);
        fromComboBox.setBackground(Color.WHITE);
        toComboBox.setBackground(Color.WHITE);
        Font inputFont = new Font("Arial", Font.PLAIN, 16);
        toComboBox.setFont(inputFont);
        fromComboBox.setFont(inputFont);

        // Tarih için JSpinner
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date initialDate = calendar.getTime();

        SpinnerDateModel dateModel = new SpinnerDateModel(initialDate, null, null, Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(dateModel);

        // JSpinner.DateEditortarih formatını ayarlamak için
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
       
        // Tarih Spinner'ını ekleme ve yeni özellikler ekleme
        dateSpinner.setFont(inputFont);
        inputPanel.add(dateSpinner);
        
        // Sınıf secimi
        classComboBox = new JComboBox<>(new String[]{"Business", "Economy"});
        classComboBox.setForeground(Color.BLACK);
        classComboBox.setBackground(Color.WHITE);
        classComboBox.setFont(inputFont);

        //Uçuş Aramak için JButton
        JButton searchButton = new JButton("Uçuş Ara");
        searchButton.addActionListener(this);
        searchButton.setBackground(Color.RED); 
        searchButton.setForeground(Color.WHITE); 
       
        //inputPanel'e gerekli bileşenleri ekleme
        inputPanel.add(fromComboBox);
        inputPanel.add(toComboBox);
        inputPanel.add(dateSpinner);
        inputPanel.add(classComboBox);
        inputPanel.add(searchButton);
        
        //Bileşen boyutlarını ayarlama
        fromComboBox.setPreferredSize(new Dimension(200, 70)); // 200 genişlik, 30 yükseklik
        toComboBox.setPreferredSize(new Dimension(200, 70)); // 200 genişlik, 30 yükseklik
        dateSpinner.setPreferredSize(new Dimension(200, 70)); // 200 genişlik, 30 yükseklik
        classComboBox.setPreferredSize(new Dimension(200, 70)); // 200 genişlik, 30 yükseklik
        searchButton.setPreferredSize(new Dimension(200, 70));

        // Radio Butonları
        JRadioButton tekYönRadio = new JRadioButton("Tek Yön", true);
        JRadioButton gidişDönüşRadio = new JRadioButton("Gidiş-Dönüş");
        gidişDönüşRadio.setEnabled(false);
  
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(tekYönRadio);
        radioButtonGroup.add(gidişDönüşRadio);
        
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //radioPanel.setBorder(BorderFactory.createTitledBorder("radioPanel"));
        Font radioButtonFont = new Font("Arial", Font.PLAIN, 18);
        gidişDönüşRadio.setFont(radioButtonFont);
        tekYönRadio.setFont(radioButtonFont);
        radioPanel.add(tekYönRadio);
        radioPanel.add(gidişDönüşRadio);
        
        //Orta Panele hem radioPaneli hem de inputPaneli ekleme
        centerPanel.add(radioPanel, BorderLayout.CENTER);
        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        
        //radioPanel ve inputPanel icin boyut ve renk ayarlama
        inputPanel.setPreferredSize(new Dimension(1100, 100));
        radioPanel.setPreferredSize(new Dimension(1100, 50));
        inputPanel.setBackground(Color.WHITE);
        radioPanel.setBackground(Color.WHITE);
        gidişDönüşRadio.setBackground(Color.WHITE);
        tekYönRadio.setBackground(Color.WHITE);
 
        //Bilet alma panelini ayarlamak için gerekli bos panel
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        //bottomPanel.setBorder(BorderFactory.createTitledBorder("Alt Panel"));
        bottomPanel.setPreferredSize(new Dimension(1920, 180));

        // Ic paneli olusturma ve boyutunu ayarlama
        JPanel inner = new JPanel(new GridLayout(1, 0, 5, 0)); // 10 piksel boşluk bırakarak
        inner.setPreferredSize(new Dimension(1120, 120));
        inner.setBackground(Color.WHITE);

        // Ic panele altı ikonlar
        String[] metinler = {"Ekstra Bagaj", "Otel Rezervasyonu", "Araç Kiralama", "Otopark", "Seyahat Sigortası", "Hediye Kart"}; // Kullanıcı tarafından belirlenen metinler
        for (int i = 0; i < 6; i++) { // Toplam 6 resim ve metin
            JPanel imagePanel = new JPanel(new BorderLayout());
            JLabel imageLabel = new JLabel(new ImageIcon("resim" + (i+1) + ".png")); 
            imageLabel.setPreferredSize(new Dimension(32, 32));
            imageLabel.setHorizontalAlignment(JLabel.CENTER); 
            imagePanel.add(imageLabel, BorderLayout.CENTER); 

            JLabel textLabel = new JLabel(metinler[i]); 
            textLabel.setHorizontalAlignment(JLabel.CENTER); 
            imagePanel.add(textLabel, BorderLayout.SOUTH); 
            imagePanel.setBackground(Color.WHITE);
            imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
            inner.add(imagePanel);
        }

        // GridBagLayout kullanarak iç paneli bottomPanel içine tam ortaya yerleştirme
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Tam ortaya hizalama
        bottomPanel.add(inner, gbc);
        
        //ticketPanele gerekli bileşenleri ekledik
        ticketPanel.setLayout(new BorderLayout());
        ticketPanel.add(rightPanel, BorderLayout.EAST);
        ticketPanel.add(leftPanel, BorderLayout.WEST);
        ticketPanel.add(centerPanel, BorderLayout.CENTER);
        ticketPanel.add(bottomPanel, BorderLayout.SOUTH);

        // ticketPanel'i backPanel'in icinde asagisina yerkestirme
        backPanel.add(ticketPanel, BorderLayout.SOUTH);

        // leftPanel icine üst ve alt panel ekleme
        JPanel leftTopPanel = new JPanel();
        JPanel leftBottomPanel = new JPanel();

        //leftTopPanel.setBorder(BorderFactory.createTitledBorder("Sol Üst Panel"));
        //leftBottomPanel.setBorder(BorderFactory.createTitledBorder("Sol Alt Panel"));

        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        
        // rightPanel içine üst ve alt panel ekleme
        JPanel rightTopPanel = new JPanel();
        JPanel rightBottomPanel = new JPanel();

        //rightTopPanel.setBorder(BorderFactory.createTitledBorder("Sağ Üst Panel"));
        //rightBottomPanel.setBorder(BorderFactory.createTitledBorder("Sağ Alt Panel"));

        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(rightTopPanel, BorderLayout.NORTH);
        rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);
        
        
        //Sol ve Sağ panel içine eklenen panellerden üst olanları transparent yapma
        rightBottomPanel.setPreferredSize(new Dimension(100, 100));
        leftBottomPanel.setPreferredSize(new Dimension(100, 100));
        leftTopPanel.setOpaque(false);
        rightTopPanel.setOpaque(false);

        //Panellere gerekli rengin verilmesi
        leftBottomPanel.setBackground(new Color(242, 242, 242));
        rightBottomPanel.setBackground(new Color(242, 242, 242));
        bottomPanel.setBackground(new Color(242, 242, 242));

        
        
        
        // Navbar
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS)); // Yatay hizalama için BoxLayout kullan

        //menuPanel.setBorder(BorderFactory.createTitledBorder("Menü Paneli"));
        menuPanel.setBackground(new Color(35, 43, 56)); 

        // Logo ekleme
        ImageIcon logoIcon = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logoIcon);

        JButton planButton = new JButton("PLANLA & UÇ");
        JButton helpButton = new JButton("YARDIM");
        JButton signUpButton = new JButton("ÜYE OL");
        JButton loginButton = new JButton("GİRİŞ YAP");
        
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignUpFrame();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginFrame(); 
            }
        });
        
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelpFrame(); 
            }
        });
        
        // Butonların arka plan rengi
        planButton.setBackground(new Color(35, 43, 56));
        helpButton.setBackground(new Color(35, 43, 56));
        signUpButton.setBackground(new Color(35, 43, 56));
        loginButton.setBackground(new Color(35, 43, 56));

        // Butonların yazı rengi
        planButton.setForeground(Color.WHITE);
        helpButton.setForeground(Color.WHITE);
        signUpButton.setForeground(Color.WHITE);
        loginButton.setForeground(Color.WHITE);

        // Butonların kenarlık ozelliklerini kaldırmak  icin
        planButton.setBorder(null);
        helpButton.setBorder(null);
        signUpButton.setBorder(null);
        loginButton.setBorder(null);

        // Butonların yazı stilleri icin
        Font buttonFont = new Font(planButton.getFont().getName(), Font.BOLD, planButton.getFont().getSize() + 2);
        planButton.setFont(buttonFont);
        helpButton.setFont(buttonFont);
        signUpButton.setFont(buttonFont);
        loginButton.setFont(buttonFont);

        // Butonların cursor ayarı
        planButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        menuPanel.add(logoLabel); // Logoyu ekleme
        menuPanel.add(Box.createHorizontalGlue()); // Logoyu sola hizalama
        menuPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Boşluk ekleme
        menuPanel.add(planButton);
        menuPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Boşluk ekleme
        menuPanel.add(helpButton);
        menuPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Boşluk ekleme
        menuPanel.add(signUpButton);
        menuPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Boşluk ekleme
        menuPanel.add(loginButton);

        
        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(backPanel, BorderLayout.CENTER);
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        

    }
   
    private void showSignUpFrame() {
    JFrame signUpFrame = new JFrame("Üye Ol");
    signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    signUpFrame.setSize(300, 300);
    signUpFrame.setLocationRelativeTo(this); // Ana pencerenin tam ortasında gösterme

    // Ana panel oluştur
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 4 satır, 1 sütunlu bir GridLayout kullanma

    // İsim giriş kutusu ve etiketi oluştur
    JTextField nameField = new JTextField();
    JLabel nameLabel = new JLabel("İsim: ");
    JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Bileşenleri sola hizalama
    namePanel.add(nameLabel);
    namePanel.add(nameField);

    // Soyisim giriş kutusu ve etiketi oluştur
    JTextField surnameField = new JTextField();
    JLabel surnameLabel = new JLabel("Soyisim: ");
    JPanel surnamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Bileşenleri sola hizalama
    surnamePanel.add(surnameLabel);
    surnamePanel.add(surnameField);

    // E-posta giriş kutusu ve etiketi oluştur
    JTextField emailField = new JTextField();
    JLabel emailLabel = new JLabel("E-posta: ");
    JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Bileşenleri sola hizalama
    emailPanel.add(emailLabel);
    emailPanel.add(emailField);
    
    
    
    JPasswordField passwordField = new JPasswordField();
    JLabel passwordLabel = new JLabel("Şifre: ");
    JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Bileşenleri sola hizalama
    passwordPanel.add(passwordLabel);
    passwordPanel.add(passwordField);
    
    

    // Kayıt ol butonu oluştur
    JButton signUpButton = new JButton("Kayıt Ol");
    signUpButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Textfield'ların değerlerini al
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Textfield'ların boş olup olmadığını kontrol et
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                // Herhangi bir alan boşsa kullanıcıya hata mesajı göster
                JOptionPane.showMessageDialog(signUpFrame, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
            } else {
                TurkishAirlines.saveUser(name, surname, email, password);
                JOptionPane.showMessageDialog(signUpFrame, "Kayıt işlemi başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                signUpFrame.dispose(); // Frame'i kapat
            }
        }
    });

    // Buton paneli olusturma
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(signUpButton);

    // Textfield'ların boyutunu ayarlama
    nameField.setPreferredSize(new Dimension(200, 30));
    surnameField.setPreferredSize(new Dimension(200, 30));
    emailField.setPreferredSize(new Dimension(200, 30));
    passwordField.setPreferredSize(new Dimension(200, 30));

    mainPanel.add(namePanel);
    mainPanel.add(surnamePanel);
    mainPanel.add(emailPanel);
    mainPanel.add(passwordPanel);
    mainPanel.add(buttonPanel);

    signUpFrame.add(mainPanel);
    signUpFrame.setVisible(true);
}
    
    private void showLoginFrame() {
    JFrame loginFrame = new JFrame("Giriş Yap");
    loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    loginFrame.setSize(300, 150);
    loginFrame.setLocationRelativeTo(this); // Ana pencerenin ortasında göster

    // Ana panel oluştur
    JPanel mainPanel = new JPanel(new BorderLayout());

    // E-posta giriş kutusu ve etiketi oluştur
    JTextField emailField = new JTextField();
    JLabel emailLabel = new JLabel("E-posta: ");
    JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Bileşenleri sola hizala
    emailPanel.add(emailLabel);
    emailPanel.add(emailField);

    // Şifre giriş kutusu ve etiketi oluştur
    JPasswordField passwordField = new JPasswordField();
    JLabel passwordLabel = new JLabel("Şifre: ");
    JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Bileşenleri sola hizala
    passwordPanel.add(passwordLabel);
    passwordPanel.add(passwordField);

    // Giris yap butonu olusturma
    JButton loginButton = new JButton("Giriş Yap");
    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Textfield'ların değerlerini al
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Textfield'ların boş olup olmadığını kontrol et
            if (email.isEmpty() || password.isEmpty()) {
                // Herhangi bir alan boşsa kullanıcıya hata mesajı göster
                JOptionPane.showMessageDialog(loginFrame, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
            } else {
                // Veritabanında kullanıcıyı kontrol et
                if (TurkishAirlines.checkUser(email, password)) {
                    // Kullanıcı doğruysa ana pencereyi göster
                    JOptionPane.showMessageDialog(loginFrame, "Giriş başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    loginFrame.dispose(); // Frame'i kapat
                } else {
                    // Kullanıcı yoksa veya şifre yanlışsa hata mesajı göster
                    JOptionPane.showMessageDialog(loginFrame, "Geçersiz e-posta veya şifre.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    });

    // Buton paneli oluştur ve butonu ekleyerek alt kısmı tamamla
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(loginButton);

    // Textfield'ların boyutunu ayarla
    emailField.setPreferredSize(new Dimension(200, 30));
    passwordField.setPreferredSize(new Dimension(200, 30));

    // Ana panelin içeriğini oluşturulan bileşenlerle doldur
    mainPanel.add(emailPanel, BorderLayout.NORTH);
    mainPanel.add(passwordPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Ana paneli frame'e ekle ve frame'i görünür hale getir
    loginFrame.add(mainPanel);
    loginFrame.setVisible(true);
}


    private void showHelpFrame() {
    JFrame helpFrame = new JFrame("Yardım");
    helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    helpFrame.setSize(300, 100);
    helpFrame.setLocationRelativeTo(this);
    helpFrame.setVisible(true);
    
    JPanel comPanel = new JPanel();
    comPanel.setBackground(Color.WHITE);
    
    JLabel iletisim = new JLabel("Destek ve önerileriniz için:");
    JLabel mail = new JLabel("destek@turkishairlines.com");
    helpFrame.add(comPanel);
    comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.Y_AXIS));
    comPanel.add(iletisim);
    comPanel.add(mail);
    
    iletisim.setPreferredSize(new Dimension(100, 50));
    mail.setPreferredSize(new Dimension(100, 50)); 
    
    iletisim.setAlignmentX(Component.CENTER_ALIGNMENT);
    mail.setAlignmentX(Component.CENTER_ALIGNMENT);
    comPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

}




    
    
    // Şehre göre kod oluşturma
    public class CityConverter {
    private HashMap<String, String> cityPairsMap;

    public CityConverter() {
        cityPairsMap = new HashMap<>();
        cityPairsMap.put("istanbul-ankara", "FTR1");
        cityPairsMap.put("istanbul-izmir", "FTR2");
        cityPairsMap.put("ankara-izmir", "FTR3");
        cityPairsMap.put("ankara-istanbul", "FTR4");
        cityPairsMap.put("izmir-istanbul", "FTR5");
        cityPairsMap.put("izmir-ankara", "FTR6");
        cityPairsMap.put("istanbul-berlin", "FDE1");
        cityPairsMap.put("berlin-istanbul", "FDE2");
        cityPairsMap.put("istanbul-paris", "FFR1");
        cityPairsMap.put("paris-istanbul", "FFR2");
        cityPairsMap.put("istanbul-madrid", "FES1");
        cityPairsMap.put("madrid-istanbul", "FES2");
    }

    public String convertToId(String from, String to) {
        String key = from.toLowerCase() + "-" + to.toLowerCase();
        return cityPairsMap.getOrDefault(key, "Unknown");
    }
}
    
    // actionPerformed
    
    public void actionPerformed(ActionEvent e) {
        if (!TurkishAirlines.isLoggedIn) {
            JOptionPane.showMessageDialog(null, "Önce giriş yapmalısınız!", "Hata", JOptionPane.ERROR_MESSAGE);
        } else {
    if (e.getActionCommand().equals("Uçuş Ara")) {
        String from = (String) fromComboBox.getSelectedItem();
        String to = (String) toComboBox.getSelectedItem();
        Date selectedDate = (Date) dateSpinner.getValue();
        String classChoice = (String) classComboBox.getSelectedItem();

        CityConverter converter = new CityConverter();
        String flywayId = converter.convertToId(from, to);
        
        
        
        if (flywayId.equals("Unknown")) {
            // Eğer uçuş bulunamazsa, hata mesajı göster
            JOptionPane.showMessageDialog(null, "Uçuş bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
         } else {
            
            
            // Uçuş bulunduğunda işlemleri yap
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedDate);
            String combinedValue = flywayId + formattedDate;
            

        
        //ResultFrame
        JFrame resultFrame = new JFrame("Ödeme Ekranı");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(720, 360);
        resultFrame.setBackground(Color.WHITE);
        
        // Ana panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        
        // Sol panel 
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // İsim giriş kutusu ve etiketi oluşturuluyor
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 60)); // Metin giriş kutusunun boyutunu ayarla
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Sol panel içindeki bileşenlerin sola hizalanmasını sağlar
        JLabel nameLabel = new JLabel("İsim: "); // İsim etiketi oluşturuluyor
        namePanel.add(nameLabel);   
        namePanel.add(nameField);

        // Soyisim giriş kutusu ve etiketi oluşturuluyor
        
        JTextField surnameField = new JTextField();
        surnameField.setPreferredSize(new Dimension(200, 60)); // Metin giriş kutusunun boyutunu ayarla
        JPanel surnamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Sol panel içindeki bileşenlerin sola hizalanmasını sağlar
        JLabel surnameLabel = new JLabel("Soyisim: "); // Soyisim etiketi oluşturuluyor
        surnamePanel.add(surnameLabel);
        surnamePanel.add(surnameField);
        
        String fName = nameField.getText(); // İsim giriş kutusundan metni al
        String fSurname = surnameField.getText(); // Soyisim giriş kutusundan metni al


        // Telefon giriş kutusu ve etiketi oluşturuluyor
        
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(200, 60)); // Metin giriş kutusunun boyutunu ayarla
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Sol panel içindeki bileşenlerin sola hizalanmasını sağlar
        JLabel phoneLabel = new JLabel("Telefon: "); // Telefon etiketi oluşturuluyor
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);

        // Mail giriş kutusu ve etiketi oluşturuluyor
       
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 60)); // Metin giriş kutusunun boyutunu ayarla
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Sol panel içindeki bileşenlerin sola hizalanmasını sağlar
        JLabel emailLabel = new JLabel("E-posta: "); // E-posta etiketi oluşturuluyor
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        
        nameField.setBackground(Color.WHITE);
        surnameField.setBackground(Color.WHITE);
        phoneField.setBackground(Color.WHITE);
        emailField.setBackground(Color.WHITE);
        namePanel.setBackground(Color.WHITE);
        surnamePanel.setBackground(Color.WHITE);
        phonePanel.setBackground(Color.WHITE);
        emailPanel.setBackground(Color.WHITE);
        
        

        // Oluşturulan tüm panel ve bileşenler sol panele ekleniyor
        leftPanel.add(namePanel);
        leftPanel.add(surnamePanel);
        leftPanel.add(phonePanel);
        leftPanel.add(emailPanel);

        
        
        //right panel
        JPanel rightPanel = new JPanel(new GridLayout(3, 1)); // 3 sıra, 1 sütunlu bir GridLayout kullanıyoruz

        rightPanel.setBackground(Color.WHITE);

        
        
        
        JPanel firstUpperSubPanel = new JPanel(); // 2 sıra, 1 sütunlu bir GridLayout kullanıyoruz
        firstUpperSubPanel.setLayout(new BoxLayout(firstUpperSubPanel, BoxLayout.Y_AXIS)); // Dikey hizalama için BoxLayout kullanıyoruz
        firstUpperSubPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
     // İlk üst paneldeki ilk alt panel oluşturuluyor
     JPanel firstTextFieldPanel = new JPanel();
     firstTextFieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortalama hizalama için
        JLabel firstUpperLabel = new JLabel("Kart Üzerindeki İsim Soyisim: "); // İlk üst panel etiketi
        firstTextFieldPanel.add(firstUpperLabel);
     JTextField firstTextField = new JTextField();
     firstTextField.setPreferredSize(new Dimension(300, 25)); // Boyutu ayarlanıyor
     firstTextFieldPanel.add(firstTextField);

     // İlk üst paneldeki ikinci alt panel oluşturuluyor
     JPanel secondTextFieldPanel = new JPanel();
     secondTextFieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortalama hizalama için
        JLabel secondUpperLabel = new JLabel("Kart Numarası: "); // İkinci üst panel etiketi
        secondTextFieldPanel.add(secondUpperLabel);
     JTextField secondTextField = new JTextField();
     secondTextField.setPreferredSize(new Dimension(300, 25)); // Boyutu ayarlanıyor
     secondTextFieldPanel.add(secondTextField);

     // İlk üst paneldeki alt paneller ekleniyor
     firstUpperSubPanel.add(firstTextFieldPanel);
     firstUpperSubPanel.add(secondTextFieldPanel);
     
     firstUpperSubPanel.setBackground(Color.WHITE);
     firstTextFieldPanel.setBackground(Color.WHITE);
     secondTextFieldPanel.setBackground(Color.WHITE);

     // Sağ paneldeki en üstteki panele alt paneller ekleniyor
     rightPanel.add(firstUpperSubPanel);
    
     
     
     
     
     
        JPanel secondSubPanel = new JPanel();
       secondSubPanel.setLayout(new GridBagLayout());
       secondSubPanel.setBackground(Color.WHITE);

       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(5, 5, 5, 5);

       // Son Kullanma Tarihi etiketi ve text alanı
       JLabel expiryLabel = new JLabel("Son Kullanım Yılı");
       gbc.gridx = 0;
       secondSubPanel.add(expiryLabel, gbc);

       JTextField expiryTextField = new JTextField();
       expiryTextField.setPreferredSize(new Dimension(145, 40));
       gbc.gridx = 1;
       secondSubPanel.add(expiryTextField, gbc);

       // CVV etiketi ve text alanı
       JLabel cvvLabel = new JLabel("CVV");
       gbc.gridx = 0;
       gbc.gridy = 1; // İkinci satıra geçiyoruz
       secondSubPanel.add(cvvLabel, gbc);

       JTextField cvvTextField = new JTextField();
       cvvTextField.setPreferredSize(new Dimension(145, 40));
       gbc.gridx = 1;
       secondSubPanel.add(cvvTextField, gbc);

       rightPanel.add(secondSubPanel, BorderLayout.CENTER);


 
     


     // Sağ paneldeki üçüncü alt panel oluşturuluyor
     JPanel thirdSubPanel = new JPanel();
     thirdSubPanel.setBackground(Color.WHITE);
     thirdSubPanel.setLayout(new GridBagLayout()); // GridBagLayout kullanarak butonu ortalamak için

     // Buton oluşturuluyor
     JButton paymentButton = new JButton("Ödeme Yap");

     // Butonun boyutunu ayarlamak için bir GridBagConstraints oluşturuluyor
     GridBagConstraints gdc = new GridBagConstraints();
     gbc.gridx = 0;
     gbc.gridy = 0;
     gbc.insets = new Insets(10, 10, 10, 10); // Butonun kenarlardan boşlukları

     // Buton thirdSubPanel'e ekleniyor
     thirdSubPanel.add(paymentButton, gdc);
     
     paymentButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() ||
                        phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                        firstTextField.getText().isEmpty() || secondTextField.getText().isEmpty() ||
                        expiryTextField.getText().isEmpty() || cvvTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(resultFrame, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
        String fName = nameField.getText(); // İsim giriş kutusundan metni al
        String fSurname = surnameField.getText(); // Soyisim giriş kutusundan metni al
        
        // Ödeme alındı uyarısı
        TurkishAirlines.insertFlightInfo(combinedValue, fName, fSurname, classChoice);
        JOptionPane.showMessageDialog(resultFrame, "Ödemeniz alındı!", "Ödeme Başarılı", JOptionPane.INFORMATION_MESSAGE);

        resultFrame.dispose();
         }
    }
});


        // Alt paneller sağ panele ekleniyor
        rightPanel.add(firstUpperSubPanel);
        rightPanel.add(secondSubPanel);
        rightPanel.add(thirdSubPanel);
        
        

        
        // Ana panel içerisine sol ve sağ paneller ekleniyor
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        // Ana paneli frame'e ekliyoruz
        resultFrame.add(mainPanel);
        resultFrame.setVisible(true);
        resultFrame.setLocationRelativeTo(null);
            }  
           

        }
    }
  
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AirlineReservationGUI gui = new AirlineReservationGUI();
            gui.setVisible(true);
            
        });
    }
}
