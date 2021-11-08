import java.util.ArrayList;
import java.util.List;

public  class Sort {


    public List<FirmwareElement> sortElements(List<FirmwareElement> arr) {
        quickSort(arr, 0, arr.size() - 1);
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


}
