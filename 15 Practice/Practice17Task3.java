import java.util.Scanner;

// Узел кольцевого односвязного списка.
class StudentTask3 {
    private String name;
    private int age;
    StudentTask3 next;

    public void readFromConsole(Scanner scanner) {
        do {
            System.out.print("Имя студента: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Имя не должно быть пустым.");
            }
        } while (name.isEmpty());
        age = Practice17Task3.readInt(scanner, "Возраст: ", 1, 150);
    }

    public void print() {
        System.out.println("Имя: " + name + ", возраст: " + age);
    }
}

class StudentListTask3 {
    private StudentTask3 tail;
    private int size;

    public StudentListTask3() {
        clear();
    }

    public boolean isEmpty() {
        return tail == null;
    }

    public void add(StudentTask3 student) {
        if (isEmpty()) {
            student.next = student;
        } else {
            student.next = tail.next;
            tail.next = student;
        }
        tail = student;
        size++;
    }

    public boolean remove(int position) {
        if (position < 1 || position > size) {
            return false;
        }
        StudentTask3 previous = tail;
        for (int i = 1; i < position; i++) {
            previous = previous.next;
        }
        StudentTask3 removed = previous.next;
        if (size == 1) {
            tail = null;
        } else {
            previous.next = removed.next;
            if (removed == tail) {
                tail = previous;
            }
        }
        removed.next = null;
        size--;
        return true;
    }

    public boolean printElement(int position) {
        if (position < 1 || position > size) {
            return false;
        }
        StudentTask3 current = tail.next;
        for (int i = 1; i < position; i++) {
            current = current.next;
        }
        current.print();
        return true;
    }

    public void printAll() {
        if (isEmpty()) {
            System.out.println("Картотека пуста.");
            return;
        }
        StudentTask3 current = tail.next;
        for (int position = 1; position <= size; position++) {
            System.out.print(position + ". ");
            current.print();
            current = current.next;
        }
    }

    public void clear() {
        if (tail != null) {
            tail.next = null; 
        }
        tail = null;
        size = 0;
    }
}

public class Practice17Task3 {
    static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Введите целое число от " + min + " до " + max + ".");
        }
    }

    public static void main(String[] args) {
        StudentListTask3 students = new StudentListTask3();
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            while (true) {
                System.out.println("\nКартотека студентов");
                System.out.println("1. Добавить студента");
                System.out.println("2. Удалить студента по номеру");
                System.out.println("3. Вывести студента по номеру");
                System.out.println("4. Вывести всю картотеку");
                System.out.println("5. Очистить картотеку (создать пустой список)");
                System.out.println("6. Проверить, пуста ли картотека");
                System.out.println("0. Выход");
                int choice = readInt(scanner, "Выберите действие: ", 0, 6);
                switch (choice) {
                    case 1:
                        StudentTask3 student = new StudentTask3();
                        student.readFromConsole(scanner);
                        students.add(student);
                        System.out.println("Студент добавлен.");
                        break;
                    case 2:
                        int removePosition = readInt(scanner, "Номер студента: ", 1, Integer.MAX_VALUE);
                        System.out.println(students.remove(removePosition)
                                ? "Студент удалён." : "Студент с таким номером не найден.");
                        break;
                    case 3:
                        int printPosition = readInt(scanner, "Номер студента: ", 1, Integer.MAX_VALUE);
                        if (!students.printElement(printPosition)) {
                            System.out.println("Студент с таким номером не найден.");
                        }
                        break;
                    case 4:
                        students.printAll();
                        break;
                    case 5:
                        students.clear();
                        System.out.println("Картотека очищена.");
                        break;
                    case 6:
                        System.out.println(students.isEmpty() ? "Картотека пуста." : "Картотека не пуста.");
                        break;
                    case 0:
                        return;
                    default:
                        throw new IllegalStateException("Неизвестный пункт меню");
                }
            }
        } catch (java.util.NoSuchElementException e) {
            System.out.println("\nВвод завершён. Программа закрыта.");
        }
    }
}
