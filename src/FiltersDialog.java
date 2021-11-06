import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FiltersDialog extends JDialog {
    private JPanel mainFiltersPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JCheckBox rollingCheckBox;
    private JCheckBox anotherBranchesCheckBox;
    private JCheckBox masterCheckBox;
    private JCheckBox withDocumentationCheckBox;
    private JCheckBox useAllPlatformsCheckBox;
    private JComboBox platformsBox;
    private JButton addButton;
    private JPanel platformsPanel;
    private JPanel docPanel;
    private JPanel versionPanel;
    private JPanel testPanel;
    private JList selectedDevicesList;
    private JPanel datePanel;
    private JRadioButton useAllDateRadioButton;
    private JRadioButton defineDateIntervalRadioButton;
    private JCheckBox testedCheckBox;
    private JCheckBox notTestedCheckBox;
    private JTextField textField1;
    private JTextField textField2;
    private Service serviceUI;
    private Filter tempFilter;

    public FiltersDialog(Gui frame, Service serviceModule) {
        super(frame, "Filters", true);
        tempFilter = new Filter();
        serviceUI = serviceModule;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width/2 - 350,dimension.height/2 - 300,700,600);
        InitFilterDialog();
        ActivateButtons();
    }

    private void ActivateButtons() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // place to listen boxes
        //tempFilter.doc = ..



        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceUI.changeFilters(tempFilter);
            }
        });
    }

    private void InitFilterDialog() {
        ButtonGroup dateButtonsGroup = new ButtonGroup();
        dateButtonsGroup.add(useAllDateRadioButton);
        dateButtonsGroup.add(defineDateIntervalRadioButton);

        Border versionBorder = BorderFactory.createTitledBorder("version");
        versionPanel.setBorder(versionBorder);
        Border documentationBorder = BorderFactory.createTitledBorder("documentation");
        docPanel.setBorder(documentationBorder);
        Border testBorder = BorderFactory.createTitledBorder("test");
        testPanel.setBorder(testBorder);
        Border dateBorder = BorderFactory.createTitledBorder("date");
        datePanel.setBorder(dateBorder);
        Border platformBorder = BorderFactory.createTitledBorder("platform");
        platformsPanel.setBorder(platformBorder);

        DefaultListModel selectedDevicesModel = new DefaultListModel();

        for (String item : serviceUI.devices.allDevices()) {
            platformsBox.addItem(item);
        }
        //mainFiltersPanel.add(devices);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var selectedDevice = platformsBox.getSelectedItem();
                if (!hasElement(selectedDevice, selectedDevicesList)) {
                    selectedDevicesModel.addElement(platformsBox.getSelectedItem());
                }
            }
        });

        selectedDevicesList.setModel(selectedDevicesModel);

        selectedDevicesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem del = new JMenuItem("Delete");
                popup.add(del);
                del.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedDevicesModel.removeElement(selectedDevicesList.getSelectedValue());
                    }
                });
                selectedDevicesList.setComponentPopupMenu(popup);
            }
        });

        setContentPane(mainFiltersPanel);

    }

    private boolean hasElement(Object searched, JList list) {
        for (int a = 0; a < list.getModel().getSize(); a++) {
            Object element = list.getModel().getElementAt(a);
            if (element.equals(searched))  {
                return true;
            }
        }
        return false;
    }
}