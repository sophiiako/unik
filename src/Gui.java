
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    private DefaultListModel listModel;
    private JPanel mainPanel;
    private JButton filterButton;
    private JList firmwareList;
    private JComboBox sortBox;
    private JLabel sortedLabel;
    private JPanel firmwarePanel;
    private JPanel infoPanel;
    private JTextArea infoTextArea;
    private Service serviceUI;

    public Gui(Service serviceModel, String title)  {
        super(title);
        serviceUI = serviceModel;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width/2 - 500,dimension.height/2 - 300,1000,600);
        initUI(this);
        ButtonsActivate(this);

    }

    private void ButtonsActivate(Gui frame) {
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltersDialog filters = new FiltersDialog(frame, serviceUI);
                filters.setVisible(true);
            }
        });

        sortBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Object item = event.getItem();
                    serviceUI.sortItems((String)item);
                }
            }
        });
    }

        private void initUI(Gui frame) {
            listModel = new DefaultListModel();
            firmwareList.setModel(listModel);
            ImageIcon logo = new ImageIcon("src/logo.png");
            setIconImage(logo.getImage());
            createMenuBar(frame);
            ContentSettings();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setContentPane(mainPanel);
        }

        private void createMenuBar(Gui frame) {

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

            JMenuItem settingsMenuItem1 = new JMenuItem("Add platforms");
            settingsMenuItem1.setToolTipText("Add available platforms");
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
                    DevicesDialog devicesDialog = new DevicesDialog(frame, serviceUI);
                    devicesDialog.setVisible(true);
                }
            });

            newMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NewFirmwareDialog firmwareDialog = new NewFirmwareDialog(listModel, frame, serviceUI);
                    firmwareDialog.setVisible(true);
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
/*
        private void addToListPanel(FirmwareElement f) {
            listModel.addElement(f.name);

        }

 */

        private void ContentSettings() {

            firmwareList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()) {
                        //System.out.println(firmwareList.getSelectedValue());
                        String detailedDataAboutFirmware = serviceUI.getInfoToPanel((String)firmwareList.getSelectedValue());
                        infoTextArea.setText(detailedDataAboutFirmware);

                    }
                }
            });

            firmwareList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem del = new JMenuItem("Delete");
                    JMenuItem edit = new JMenuItem("Edit");
                    popup.add(del);
                    popup.add(edit);
                    del.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String firmwareNameToDelete = (String)firmwareList.getSelectedValue();
                            serviceUI.deleteFirmware(firmwareNameToDelete);
                            listModel.removeElement(firmwareNameToDelete);
                            infoTextArea.setText(serviceUI.flushInfoPanel());
                        }
                    });
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            serviceUI.editFirmwareData((String)firmwareList.getSelectedValue());
                        }
                    });
                    firmwareList.setComponentPopupMenu(popup);
                }
            });
        }


}