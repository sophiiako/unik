
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.ListView;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    private DefaultListModel listModel;
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JButton filterButton;
    private JList firmwareList;
    private JComboBox sortBox;
    private JLabel sortedLabel;
    private JPanel firmwarePanel;
    private JPanel toolPanel;
    private JButton addFirmwareButton;
    private JButton addPlatformsButton;
    private JButton updateButton;
    private JPanel firmInfoPanel;
    private JTextPane infoTextPane;
    private JLabel firmwareInfoLabel;
    private JTextPane theFieldForAnotherTextPane;
    private JPanel anotherInfoPanel;
    //private JTextArea infoTextArea;
    private Service serviceUI;

    public Gui(Service serviceModel, String title)  {
        super(title);
        serviceUI = serviceModel;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width/2 - 500,dimension.height/2 - 300,1000,600);

        initUI(this);
    }

    private void initUI(Gui frame) {
        ImageIcon logo = new ImageIcon("src/logo.png");
        setIconImage(logo.getImage());
        createToolBar(frame);
        loadFirmwareList();
        ButtonsActivate(this);
        activateLayout();
        ContentSettings();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
    }

    private void activateLayout() {
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.07;
        c.weighty = 0.5;
        //c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(toolPanel, c);

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.2;
        c.weighty = 0.5;
        //c.gridx = 2;
        c.gridy = 0;
        mainPanel.add(firmwarePanel, c);

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.4;
        c.weighty = 0.5;
        //c.gridx = 3;
        c.gridy = 0;
        mainPanel.add(infoPanel, c);


    }

    private void changeListView(java.util.List<String> newModel) {
        listModel.clear();
        for (String n : newModel) {

            listModel.addElement(n);
        }
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
                    //for(String n : )
                    java.util.List<String> newModel = serviceUI.sortItems((String)item);
                    changeListView(newModel);
                    infoTextPane.setText(serviceUI.resetInfoPanel());

                }
            }
        });
    }

        public void loadFirmwareList() {
            listModel = new DefaultListModel();
            firmwareList.setModel(listModel);

            //load all firmwares and sort by sortbox now
            Object item = sortBox.getSelectedItem();
            java.util.List<String> newModel = serviceUI.sortItems((String)item);
            changeListView(newModel);

            // reset info panel because no selected elements after

            infoTextPane.setText(serviceUI.resetInfoPanel());

        }

        private void updateFirmwareList() {
            loadFirmwareList();
        }

        private void createToolBar(Gui frame) {
            updateButton.setToolTipText("Update firmware list");
            addFirmwareButton.setToolTipText("Add new firmware");
            addPlatformsButton.setToolTipText("Add new available platform");

            //exitMenuItem.addActionListener((event) -> System.exit(0));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateFirmwareList();
                }
            });

            addPlatformsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DevicesDialog devicesDialog = new DevicesDialog(frame, serviceUI);
                    devicesDialog.setVisible(true);
                }
            });

            addFirmwareButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NewFirmwareDialog firmwareDialog = new NewFirmwareDialog(listModel, frame, serviceUI);
                    firmwareDialog.setVisible(true);
                }
            });

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
                        infoTextPane.setText(detailedDataAboutFirmware);

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
                            infoTextPane.setText(serviceUI.resetInfoPanel());
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