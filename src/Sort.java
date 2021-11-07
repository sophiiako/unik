import java.util.ArrayList;
import java.util.List;

public  class Sort {


    public List<FirmwareElement> sortElements(List<FirmwareElement> arr) {
        //List<String> result = new ArrayList<String>(); // think
        quickSort(arr, 0, arr.size() - 1);
        System.out.println("-----");
        for (FirmwareElement i : arr) {
            System.out.println(i.date.resultDate);
        }

        return arr;
    }


    public static void quickSort(List<FirmwareElement> array, int low, int high) {
        if (array.size() == 0)
            return;//завершить выполнение, если длина массива равна 0

        if (low >= high)
            return;//завершить выполнение если уже нечего делить

        // выбрать опорный элемент
        int middle = low + (high - low) / 2;
        Date opora = array.get(middle).date;

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = low, j = high;
        while (i <= j) {
            while (array.get(i).date.compareTo(opora) < 0) {
                i++;
            }

            while (array.get(j).date.compareTo(opora) > 0) {
                j--;
            }

            if (i <= j) {//меняем местами
                FirmwareElement temp = array.get(i);
                array.set(i, array.get(j));
                array.set(j, temp);
                i++;
                j--;
            }
        }

        // вызов рекурсии для сортировки левой и правой части
        if (low < j)
            quickSort(array, low, j);

        if (high > i)
            quickSort(array, i, high);
    }


    public static void main(String[] args) {
        List<FirmwareElement> arr = new ArrayList<FirmwareElement>();
        FirmwareElement a1 = new FirmwareElement();
        a1.date = new Date("01.01.2018");
        arr.add(a1);
        FirmwareElement a2 = new FirmwareElement();
        a2.date = new Date("03.06.2021");
        arr.add(a2);
        arr.add(a2);
        FirmwareElement a3 = new FirmwareElement();
        a3.date = new Date("05.05.2020");
        arr.add(a3);

       new Sort().sortElements(arr);
    }
}
