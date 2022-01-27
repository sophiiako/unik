import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class FiltersDialog extends JDialog {
    private JPanel mainFiltersPanel;
    private JButton okButton;
    private JButton cancelButton;
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
    private JTextField fromField;
    private JTextField toField;
    private JLabel errDateMin;
    private JLabel errDateMax;
    private JRadioButton allBranchesRadioButton;
    private JRadioButton onlyRollingRadioButton;
    private JRadioButton onlyMasterRadioButton;
    private JRadioButton specifyBranchRadioButton;
    private JTextField branch;
    private Service serviceUI;
    private selectFilter tempFilter;
    private selectFilter activeFilter;
    private JCheckBox onlyWithDocumentationCheckBox;
    private JRadioButton onlyDeveloperBranchesRadioButton;
    private JLabel errSelectedPlatforms;
    private JLabel errBranch;
    private static final String branchRegex = "(\\#\\d+)";
    private boolean result;
    private DefaultListModel<String> selectedPlatformsModel;

    public FiltersDialog(Gui frame, Service serviceModule, selectFilter currentFilter) {
        super(frame, "Filters", true);
        result = true;
        serviceUI = serviceModule;
        tempFilter = new selectFilter(serviceUI.getAllPlatforms());
        activeFilter = tempFilter;
        selectedPlatformsModel = new DefaultListModel<String>();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width/2 - 350,dimension.height/2 - 300,700,700);
        InitFilterDialog();
        ButtonsToCurrentState(currentFilter);
        ActivateButtons(frame);
    }

    private void ButtonsToCurrentState(selectFilter currentFilter) {

        // doc buttons
        onlyWithDocumentationCheckBox.setSelected(currentFilter.onlyWithDocumentation);

        // date buttons
        useAllDateRadioButton.setSelected(currentFilter.dateMax.resultDate.equals((new fwDate("")).getDateMax().resultDate) &&
                currentFilter.dateMin.resultDate.equals((new fwDate("")).getDateMin().resultDate));
        boolean dateIsEditable = !(currentFilter.dateMax.resultDate.equals((new fwDate("")).getDateMax().resultDate) &&
                currentFilter.dateMin.resultDate.equals((new fwDate("")).getDateMin().resultDate));
        fromField.setEnabled(dateIsEditable);
        toField.setEnabled(dateIsEditable);
        defineDateIntervalRadioButton.setSelected(dateIsEditable);

        if (fromField.isEnabled() && toField.isEnabled()) {
            fromField.setText(currentFilter.dateMin.resultDate);
            toField.setText(currentFilter.dateMax.resultDate);
        }

        //platforms buttons
        boolean allPlatforms = currentFilter.usingPlatforms.equals(serviceUI.getAllPlatforms());
        useAllPlatformsCheckBox.setSelected(allPlatforms);
        addButton.setEnabled(!allPlatforms);
        platformsBox.setEnabled(!allPlatforms);
        selectedDevicesList.setEnabled(!allPlatforms);
        if (!allPlatforms) {
            for (String plat : currentFilter.usingPlatforms) {
                selectedPlatformsModel.addElement(plat);
            }
        }

        // version buttons
        branch.setEnabled(!currentFilter.ownBranch.equals(""));
        if (branch.isEnabled()) {
            branch.setText(currentFilter.ownBranch);
        }
        allBranchesRadioButton.setSelected(currentFilter.allBranches);
        onlyRollingRadioButton.setSelected(currentFilter.onlyRolling);
        onlyMasterRadioButton.setSelected(currentFilter.onlyMaster);
        onlyDeveloperBranchesRadioButton.setSelected(currentFilter.onlyAnotherBranches);
        specifyBranchRadioButton.setSelected(!currentFilter.ownBranch.equals(""));

    }

    private void ActivateButtons(Gui frame) {
        // Активация Cancel Button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // при нажатиии на 'Cancel' - закрывается диалоговое окно
                dispose();
            }
        });

        // Активация Use documentation
        onlyWithDocumentationCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onlyWithDocumentationCheckBox.isSelected()) {
                    tempFilter.onlyWithDocumentation = true;
                }
                else {
                    tempFilter.onlyWithDocumentation = false;
                }
            }
        });

        // Активация All branches Radio Button
        allBranchesRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (allBranchesRadioButton.isSelected()) {
                    branch.setEnabled(false);
                    tempFilter.setAllBranches();
                    errBranch.setText("");
                }
            }
        });

        // Активация Master Radio Button
        onlyMasterRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onlyMasterRadioButton.isSelected()) {
                    branch.setEnabled(false);
                    tempFilter.setOnlyMaster();
                    errBranch.setText("");
                }
            }
        });

        // Активация Rolling Radio Button
        onlyRollingRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onlyRollingRadioButton.isSelected()) {
                    branch.setEnabled(false);
                    tempFilter.setOnlyRolling();
                    errBranch.setText("");
                }
            }
        });

        // Активация Specify branch radio box
        specifyBranchRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (specifyBranchRadioButton.isSelected()) {
                    branch.setEnabled(true);
                }
                else {
                    branch.setEnabled(false);
                }
            }
        });
        // Активация  Only Developer branches
        onlyDeveloperBranchesRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onlyDeveloperBranchesRadioButton.isSelected()) {
                    branch.setEnabled(false);
                    tempFilter.setOnlyAnotherBranches();
                    errBranch.setText("");
                }
            }
        });

        // Активация  Use all date radio button
        useAllDateRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (useAllDateRadioButton.isSelected()) {
                    fromField.setEnabled(false);
                    toField.setEnabled(false);
                    errDateMin.setText("");
                    errDateMax.setText("");
                    // задаем фильтру дефолтный диапазон дат
                    tempFilter.toDefaultDateInterval();
                }
            }
        });

        // Активация Define Date Interval Radio Button
        defineDateIntervalRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (defineDateIntervalRadioButton.isSelected()) {
                    fromField.setEnabled(true);
                    toField.setEnabled(true);
                }
            }
        });

        // Активация Use all platforms Check box
        useAllPlatformsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (useAllPlatformsCheckBox.isSelected()) {
                    errSelectedPlatforms.setText("");
                    addButton.setEnabled(false);
                    selectedDevicesList.setEnabled(false);
                    platformsBox.setEnabled(false);
                    tempFilter.usingPlatforms = serviceUI.getAllPlatforms();
                }
                else {
                    addButton.setEnabled(true);
                    selectedDevicesList.setEnabled(true);
                    platformsBox.setEnabled(true);

                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var selectedDevice = platformsBox.getSelectedItem();
                if (!hasElement(selectedDevice, selectedDevicesList)) {
                    selectedPlatformsModel.addElement((String)platformsBox.getSelectedItem());
                }
            }
        });

        selectedDevicesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem del = new JMenuItem("Delete");
                popup.add(del);
                del.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedPlatformsModel.removeElement(selectedDevicesList.getSelectedValue());
                    }
                });
                selectedDevicesList.setComponentPopupMenu(popup);
            }
        });




        // Активация Ok Button
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // при нажатиии на ok временный фильтр становится активным и отправляется запрос
                // сначала проверим введенные данные
                result = true;

                if (specifyBranchRadioButton.isSelected()) {
                    Pattern r = Pattern.compile(branchRegex);
                    Matcher m = r.matcher(branch.getText());
                    if (m.find()) {
                        tempFilter.setOwnBranch(branch.getText());
                        errBranch.setText("");
                    } else {
                        result = false;
                        errBranch.setText("Wrong format. Must be \'#\\d+\'");
                    }
                }


                // Проверка платформ
                if (!useAllPlatformsCheckBox.isSelected()) {
                    if (selectedPlatformsModel.getSize() == 0) {
                        result = false;
                        errSelectedPlatforms.setText("Specify something!");
                    }

                    else {
                        errSelectedPlatforms.setText("");
                        List<String> platforms = new ArrayList<String>();
                        for (int i = 0; i < selectedPlatformsModel.getSize(); ++i) {
                            platforms.add((String) selectedPlatformsModel.getElementAt(i));
                        }
                        tempFilter.usingPlatforms = platforms;
                    }

                }

                // Провека дат
                if (defineDateIntervalRadioButton.isSelected()) {
                    fromField.setEnabled(true);
                    toField.setEnabled(true);
                    tempFilter.dateMin = new fwDate(fromField.getText());

                    // если неправильно введена дата
                    if (tempFilter.dateMin.resultDate.equals("")) {
                        // вывод сообщения об ошибке
                        result = false;
                        errDateMin.setText("Wrong format. Must be dd.mm.yyyy");
                    }
                    else {
                        errDateMin.setText("");
                    }
                    tempFilter.dateMax = new fwDate(toField.getText());
                    if (tempFilter.dateMax.resultDate.equals("")) {
                        // вывод сообщения об ошибке
                        result = false;
                        errDateMax.setText("Wrong format. Must be dd.mm.yyyy");
                    }
                    else {
                        errDateMax.setText("");
                    }

                }

                //если все даннные корректны - создаем фильтр!
                System.out.println(result);
                if (result) {
                    activeFilter = tempFilter;
                    dispose();
                    serviceUI.changeFilters(activeFilter);
                    frame.updateFirmwareList();
                }
            }
        });
    }

    private void InitFilterDialog() {
        ButtonGroup dateButtonsGroup = new ButtonGroup();
        dateButtonsGroup.add(useAllDateRadioButton);
        dateButtonsGroup.add(defineDateIntervalRadioButton);

        ButtonGroup versionButtonsGroup = new ButtonGroup();
        versionButtonsGroup.add(onlyMasterRadioButton);
        versionButtonsGroup.add(onlyRollingRadioButton);
        versionButtonsGroup.add(specifyBranchRadioButton);
        versionButtonsGroup.add(allBranchesRadioButton);
        versionButtonsGroup.add(onlyDeveloperBranchesRadioButton);

        Border versionBorder = BorderFactory.createTitledBorder("version");
        versionPanel.setBorder(versionBorder);
        Border documentationBorder = BorderFactory.createTitledBorder("documentation");
        docPanel.setBorder(documentationBorder);
        Border testBorder = BorderFactory.createTitledBorder("test");
        //testPanel.setBorder(testBorder);
        Border dateBorder = BorderFactory.createTitledBorder("date");
        datePanel.setBorder(dateBorder);
        Border platformBorder = BorderFactory.createTitledBorder("platform");
        platformsPanel.setBorder(platformBorder);
        selectedDevicesList.setModel(selectedPlatformsModel);

        for (String item : serviceUI.devices.allDevices()) {
            platformsBox.addItem(item);
        }

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