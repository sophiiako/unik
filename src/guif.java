
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class guif extends JFrame{

    private JPanel mainPanel;
    private JButton filterButton;
    private JList firmwareList;
    private JComboBox sortBox;
    private JLabel sortedLabel;
    private JPanel firmwarePanel;
    private JPanel infoPanel;
    private JTextArea textLlllllllllllllllllllllllllllllllllllllllllllllllllTextArea;

    public guif(String title)  {
            super(title);
            initUI();
            ButtonsActivate(this);
    }

    private void ButtonsActivate(guif frame){
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilterDialog filters = new FilterDialog(frame);
                filters.setVisible(true);
            }
        });
    }

    static class FilterDialog extends JDialog{

        public FilterDialog(guif frame){
            super(frame, "Filters", true);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            setBounds(dimension.width/2 - 250,dimension.height/2 - 150,500,300);
            InitFilterDialog();
        }

        private void InitFilterDialog(){
            JPanel mainFiltersPanel = new JPanel();
            mainFiltersPanel.setLayout(new GridBagLayout());
            setContentPane(mainFiltersPanel);
            GridBagConstraints constraints = new GridBagConstraints();
            mainFiltersPanel.add(new JCheckBox("with documentation"));
            mainFiltersPanel.add(new JCheckBox("tested"));
            JComboBox<String> devices = new JComboBox();
            devices.addItem("krm");
            devices.addItem("kam");
            mainFiltersPanel.add(devices);
        }
    }

        private void initUI() {
            ImageIcon logo = new ImageIcon("src/logo.png");
            this.setIconImage(logo.getImage());
            createMenuBar();
            ContentSettings();
            //this.setSize(1360, 1250);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setContentPane(mainPanel);
            this.pack();
        }

        private void createMenuBar() {

            var menuBar = new JMenuBar();

            var fileMenu = new JMenu("File");
            var editMenu = new JMenu("Edit");
            var settingsMenu = new JMenu("Settings");

            var newMenuItem = new JMenuItem("New firmware");
            newMenuItem.setToolTipText("Add new firmware");
            newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                    InputEvent.CTRL_DOWN_MASK));

            var exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.setToolTipText("Exit application");
            exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                    InputEvent.CTRL_DOWN_MASK));

            var settingsMenuItem1 = new JMenuItem("Add devices");
            settingsMenuItem1.setToolTipText("Add available devices");
            settingsMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                    InputEvent.CTRL_DOWN_MASK));

            var settingsMenuItem2 = new JMenuItem("Add functionalities");
            settingsMenuItem2.setToolTipText("Add available functionalities");
            settingsMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                    InputEvent.CTRL_DOWN_MASK));

            exitMenuItem.addActionListener((event) -> System.exit(0));

            editMenu.add(newMenuItem);
            fileMenu.addSeparator();
            fileMenu.add(exitMenuItem);
            settingsMenu.add(settingsMenuItem1);
            settingsMenu.add(settingsMenuItem2);

            menuBar.add(fileMenu);
            menuBar.add(editMenu);
            menuBar.add(settingsMenu);

            setJMenuBar(menuBar);
        }

        private class MenuItemAction extends AbstractAction {

            public MenuItemAction(String text, ImageIcon icon,
                                  Integer mnemonic) {
                super(text);

                putValue(SMALL_ICON, icon);
                putValue(MNEMONIC_KEY, mnemonic);
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(e.getActionCommand());
            }
        }

        private void ContentSettings(){

            String[] data1 = { "Чай" ,"Кофе"  ,"Минеральная","Морс"};
            DefaultListModel listModel = new DefaultListModel();
            for (int i = 0; i<200; i++){
                listModel.addElement("item " + Integer.toString(i));
            }

            firmwareList.setModel(listModel);

            //JScrollPane scroll = new JScrollPane(firmwareList);

            //firmwareList.add(scroll);
            //firmwareList.addElement("");
            //JList<String> list = new JList<String>(data1);
        }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch(Exception ignored){}
        EventQueue.invokeLater(() -> {

            var ex = new guif("The best application of your life!");
            ex.setVisible(true);
        });
    }
}