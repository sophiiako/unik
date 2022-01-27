import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DetailedInfo extends JDialog{
    private JPanel panel1;
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JPanel docPanel;
    private JPanel saveFirmwarePanel;
    private JTextField firmwarePath;
    private JButton saveButton;
    private JTextPane infoTextPane;
    private JButton openButtonPdf;
    private JButton openButtonTxt;
    private JButton saveButtonPdf;
    private JButton saveButtonTxt;
    private JLabel statusPdf;
    private JLabel statusTxt;
    private JLabel PdfLabel;
    private JLabel txtLabel;
    private int row;
    private Service serviceUI;

    public DetailedInfo(Service serviceModule, int selectedRow)  {
        setContentPane(mainPanel);
        row = selectedRow;
        serviceUI = serviceModule;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 250, dimension.height / 2 - 200, 500, 400);
        setResizable(false);
        setTitle("Detailed info");

        Border firmwareBorder = BorderFactory.createTitledBorder("about firmware");
        infoTextPane.setBorder(firmwareBorder);
        Border documentationBorder = BorderFactory.createTitledBorder("documentation");
        docPanel.setBorder(documentationBorder);
        Border saveBorder = BorderFactory.createTitledBorder("options");
        saveFirmwarePanel.setBorder(saveBorder);

        activateSaveButton();

        showInfo();
    }

    public void showInfo() {
        DefaultTableModel tableModel = serviceUI.getTable();
        String allInfo = String.format("name: %s\nmd5: %s \nplatform: %s\ndate: %s\nbranch: %s\nstage: %s",
                (String)tableModel.getValueAt(row, 0),
                (String)tableModel.getValueAt(row, 1),
                (String)tableModel.getValueAt(row, 2),
                (String)tableModel.getValueAt(row, 3),
                (String)tableModel.getValueAt(row, 4),
                (String)tableModel.getValueAt(row, 5));

        infoTextPane.setText(allInfo);
    }

    private void activateSaveButton() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String pathToSave = firmwarePath.getText();

                    byte[] frm = serviceUI.getRequest().getFirmwareByMD5((String)serviceUI.getTable().getValueAt(row, 1));
                    try {
                        System.out.write(frm);
                    }
                    catch (IOException ioEx) {

                    }
                    File file = new File(pathToSave);
                    //byte[] firmwareBytes = new byte[(int)frm.length()];
                    try {
                        RandomAccessFile raf = new RandomAccessFile(file, "rw");
                        try {
                            raf.write(frm);
                            //raf.readFully(firmwareBytes);
                            raf.close();
                            //tempFirmwareElement.binData = firmwareBytes;

                            JOptionPane.showMessageDialog(new JFrame(), "Saved!", "Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch (IOException ioErr) {
                            String errorMsg = "Cannot read file with firmware because" + ioErr;
                            JOptionPane.showMessageDialog(new JFrame(), errorMsg, "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch (FileNotFoundException notFoundErr) {
                        String  errorMsg = "Cannot open file with firmware because " + notFoundErr;
                        JOptionPane.showMessageDialog(new JFrame(), errorMsg, "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    
                }

        });
    }
}
