import javax.swing.*;
import java.awt.*;

public class o extends JFrame{
    private JPanel main;
    private JPanel q;
    private JPanel w;
    private JPanel e;
    private JButton qButton;
    private JButton wButton;
    private JButton eButton;

    public o()
    {


        setSize(500,200);

        // Размещаем нашу панель в панели содержимого
        setContentPane(main);
        // Устанавливаем оптимальный размер окна

        main.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        main.add(q, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        main.add(w, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        main.add(e, c);

        q.setBorder(
                BorderFactory.createLineBorder(Color.ORANGE));
        w.setBorder(
                BorderFactory.createLineBorder(Color.RED));
        e.setBorder(
                BorderFactory.createLineBorder(Color.GREEN));
        // Terminate the program when the user closes the application.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);






        //pack();
        // Открываем окно
        setVisible(true);
    }
    public static void main(String[] args) {
        new o();
    }
}
