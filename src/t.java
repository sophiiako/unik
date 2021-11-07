// Пример разработки менеджера вертикального расположения компонентов

import javax.swing.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

// Менеджер вертикального расположения компонентов
class VerticalLayout implements LayoutManager
{
    private Dimension size = new Dimension();

    // Следующие два метода не используются
    public void addLayoutComponent   (String name, Component comp) {}
    public void removeLayoutComponent(Component comp) {}

    // Метод определения минимального размера для контейнера
    public Dimension minimumLayoutSize(Container c) {
        return calculateBestSize(c);
    }
    // Метод определения предпочтительного размера для контейнера
    public Dimension preferredLayoutSize(Container c) {
        return calculateBestSize(c);
    }
    // Метод расположения компонентов в контейнере
    public void layoutContainer(Container container)
    {
        // Список компонентов
        Component list[] = container.getComponents();
        int currentX = 5;
        for (int i = 0; i < list.length; i++) {
            // Определение предпочтительного размера компонента
            Dimension pref = list[i].getPreferredSize();
            // Размещение компонента на экране
            list[i].setBounds(currentX, 5, pref.width, pref.height);
            // Учитываем промежуток в 5 пикселов
            currentX += 5;
            // Смещаем вертикальную позицию компонента
            currentX += pref.width;
        }
    }
    // Метод вычисления оптимального размера контейнера
    private Dimension calculateBestSize(Container c)
    {
        // Вычисление длины контейнера
        Component[] list = c.getComponents();
        int maxWidth = 0;
        for (int i = 0; i < list.length; i++) {
            int width = list[i].getWidth();
            // Поиск компонента с максимальной длиной
            if ( width > maxWidth )
                maxWidth = width;
        }
        // Размер контейнера в длину с учетом левого отступа
        size.height = maxWidth + 5;
        // Вычисление высоты контейнера
        int height = 0;
        for (int i = 0; i < list.length; i++) {
            height += 5;
            height += list[i].getHeight();
        }
        size.width = height;
        return size;
    }
}

public class t
{
    public static void main(String[] args)
    {
        // Создаем окно
        JFrame frame = new JFrame("VerticalLayoutTest");
        // Определяем размеры
        frame.setSize(260, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Создаем панель с менеджером вертикального расположения компонентов
        JPanel contents = new JPanel(new VerticalLayout());
        // Добавим кнопки и текстовое поле в панель
        contents.add(new JButton("Продукты"  ));
        contents.add(new JButton("Галантерея"));
        contents.add(new JTextField(20));
        // Размещаем панель в контейнере
        frame.setContentPane(contents);
        // Открываем окно
        frame.setVisible(true);
    }
}