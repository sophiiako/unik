import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FiltersDialog extends JDialog {
    private JPanel mainFiltersPanel;
    private JButton ClearButton;
    private JButton okButton;
    private JButton cancelButton;
    private Service serviceUI;
    private Filter tempFilter;

    public FiltersDialog(Gui frame, Service serviceModule) {
        super(frame, "Filters", true);
        tempFilter = new Filter();
        serviceUI = serviceModule;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width/2 - 250,dimension.height/2 - 150,500,300);
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
        //JPanel mainFiltersPanel = new JPanel();
        mainFiltersPanel.setLayout(new GridBagLayout());
        setContentPane(mainFiltersPanel);
        GridBagConstraints constraints = new GridBagConstraints();
        JCheckBox docBox = new JCheckBox("with documentation");
        docBox.setSelected(serviceUI.filter.withDocumentation);
        mainFiltersPanel.add(docBox);
        JCheckBox testedBox = new JCheckBox("tested");
        testedBox.setSelected(serviceUI.filter.tested);
        mainFiltersPanel.add(testedBox);
        JComboBox<String> devices = new JComboBox();
        for (String item : serviceUI.devices.allDevices()) {
            devices.addItem(item);
        }
        mainFiltersPanel.add(devices);

        JList selectedDevices = new JList();

        DefaultListModel selectedDevicesModel = new DefaultListModel();

        devices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var selectedDevice = devices.getSelectedItem();
                if (!hasElement(selectedDevice, selectedDevices)) {
                    selectedDevicesModel.addElement(devices.getSelectedItem());
                    System.out.println(devices.getSelectedItem());
                }
            }
        });

        selectedDevices.setModel(selectedDevicesModel);
        mainFiltersPanel.add(selectedDevices);
        /*
        JButton filtersClear = new JButton("Clear");
        JButton filtersCancel = new JButton("Cancel");
        JButton filtersOk = new JButton("Ok");



        mainFiltersPanel.add(filtersCancel);
        mainFiltersPanel.add(filtersClear);
        mainFiltersPanel.add(filtersOk);

         */

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