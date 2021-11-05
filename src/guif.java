
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class guif extends JFrame {
    private static final service serviceUI = new service();
    private JPanel mainPanel;
    private JButton filterButton;
    private JList firmwareList;
    private JComboBox sortBox;
    private JLabel sortedLabel;
    private JPanel firmwarePanel;
    private JPanel infoPanel;
    private JTextArea infoTextArea;

    public guif(String title)  {
        super(title);
        initUI(this);
        ButtonsActivate(this);

    }

    private void ButtonsActivate(guif frame){
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltersDialog filters = new FiltersDialog(frame);
                filters.setVisible(true);
            }
        });
    }

        private void initUI(guif frame) {
            ImageIcon logo = new ImageIcon("src/logo.png");
            setIconImage(logo.getImage());
            createMenuBar(frame);
            ContentSettings();
            //this.setSize(1360, 1250);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setContentPane(mainPanel);
            pack();
        }

        private void createMenuBar(guif frame) {

            JMenuBar menuBar = new JMenuBar();

            JMenu fileMenu = new JMenu("File");
            JMenu editMenu = new JMenu("Edit");
            JMenu settingsMenu = new JMenu("Settings");

            JMenuItem newMenuItem = new JMenuItem("New firmware");
            newMenuItem.setToolTipText("Add new firmware");
            newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                    InputEvent.CTRL_DOWN_MASK));

            JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.setToolTipText("Exit application");
            exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                    InputEvent.CTRL_DOWN_MASK));

            JMenuItem settingsMenuItem1 = new JMenuItem("Add devices");
            settingsMenuItem1.setToolTipText("Add available devices");
            settingsMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                    InputEvent.CTRL_DOWN_MASK));

            JMenuItem settingsMenuItem2 = new JMenuItem("Add functionalities");
            settingsMenuItem2.setToolTipText("Add available functionalities");
            settingsMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                    InputEvent.CTRL_DOWN_MASK));

            exitMenuItem.addActionListener((event) -> System.exit(0));

            settingsMenuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DevicesDialog devicesDialog = new DevicesDialog(frame);
                    devicesDialog.setVisible(true);
                }
            });

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

        private void ContentSettings() {

            DefaultListModel listModel = new DefaultListModel();
            for (int i = 0; i<20; i++){
                listModel.addElement("item " + Integer.toString(i));
            }

            firmwareList.setModel(listModel);

            firmwareList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()) {
                        //System.out.println(firmwareList.getSelectedValue());
                        serviceUI.addInfoToPanel(infoTextArea, (String)firmwareList.getSelectedValue());
                    }
                }
            });
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