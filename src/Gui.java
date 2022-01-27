
import javax.print.ServiceUI;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.html.ListView;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JButton filterButton;
    private JComboBox sortBox;
    private JLabel sortedLabel;
    private JPanel firmwarePanel;
    private JPanel toolPanel;
    private JButton addFirmwareButton;
    private JButton addPlatformsButton;
    private JButton updateButton;
    private JTable firmTable;
    private Service serviceUI; // Служебный модуль

    public Gui(Service serviceModel, String title)  {
        super(title);
        serviceUI = serviceModel;
        tableModel = serviceUI.getTable();

        // создаем окно
        initUI(this);
    }

    private void initUI(Gui frame) {
        // размеры окна
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width/2 - 500,dimension.height/2 - 300,1000,600);
        // логотип программы
        ImageIcon logo = new ImageIcon("src/logo.png");
        setIconImage(logo.getImage());
        createToolBar(frame);
        loadFirmwareTable();
        activateChangeFilters(this);
        activateSortBox();
        activateLayout();
        activateTablePopup();
        activateTableSelection();
        // доп настройки для окна
        setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                serviceUI.finish();
            }
        });
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
        c.gridy = 0;
        mainPanel.add(toolPanel, c);

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.4;
        c.weighty = 0.5;
        c.gridy = 0;
        mainPanel.add(firmwarePanel, c);
    }

    private void activateChangeFilters(Gui frame) {
        // Активация кнопки 'Change filters'
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltersDialog filters = new FiltersDialog(frame, serviceUI, serviceUI.getCurrentFilter());
                filters.setVisible(true);
            }
        });
    }

    private void activateSortBox() {
        // Активация нажатий на список всех прошивок для получения доп инфы
        sortBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Object item = event.getItem();
                    if (((String)item).equals("latest first")) {
                        serviceUI.changeSortAction(false);
                    }
                    else {
                        serviceUI.changeSortAction(true);
                    }
                    // перезапись таблицы
                    updateFirmwareList();
                }
            }
        });
    }

    public void loadFirmwareTable() {
        firmTable.setModel(tableModel);
        // это для сортировки
        Object item = sortBox.getSelectedItem();
        updateFirmwareList();
        }

    public void updateFirmwareList() {
            tableModel = serviceUI.showItems();
            //loadFirmwareTable();
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
                    NewFirmwareDialog firmwareDialog = new NewFirmwareDialog(tableModel, frame, serviceUI);
                    firmwareDialog.setVisible(true);
                }
        });



    }

    private void activateTablePopup() {
        // удаление, редактирование при нажатии на правую конпку мыши
        firmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = firmTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem del = new JMenuItem("Delete");
                //JMenuItem edit = new JMenuItem("Edit");
                popup.add(del);
                //popup.add(edit);
                del.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Integer rowToDelete = firmTable.getSelectedRow();
                        String md5ToRemove = (String)tableModel.getValueAt(rowToDelete, 1);
                        // отдельно удаляем из БД
                        serviceUI.deleteFirmware(md5ToRemove);
                        updateFirmwareList();
                        // Отдельно из таблы
                        //System.out.println(columnToRemove);
                        //firmTable.removeColumn(firmTable.getColumn(new Object[table]));
                    }
                });
                /*
                edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        serviceUI.editFirmwareData(firmTable.getSelectedColumn());
                    }
                });

                 */
                firmTable.setComponentPopupMenu(popup);
            }
        });

    }

    public void activateTableSelection() {
        firmTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    int selectedRow = firmTable.getSelectionModel().getAnchorSelectionIndex();
                    DetailedInfo dInfo = new DetailedInfo(serviceUI ,selectedRow);
                    dInfo.setVisible(true);
                }
            }
        });
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

}