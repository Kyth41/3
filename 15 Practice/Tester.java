import java.util.Scanner;

// Узел односвязного списка: данные студента и ссылка на следующий узел.
class Student {
    private String name;
    private int age;
    Student next;

    public void readFromConsole(Scanner scanner) {
        do {
            System.out.print("Имя студента: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Имя не должно быть пустым.");
            }
        } while (name.isEmpty());
        age = Tester.readInt(scanner, "Возраст: ", 1, 150);
    }

    public void print() {
        System.out.println("Имя: " + name + ", возраст: " + age);
    }
}

// Список реализован вручную, без готовых коллекций Java.
class StudentList {
    private Student head;
    private Student tail;
    private int size;

    public StudentList() {
        clear();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void add(Student student) {
        student.next = null;
        if (isEmpty()) {
            head = student;
        } else {
            tail.next = student;
        }
        tail = student;
        size++;
    }

    // Нумерация элементов для пользователя начинается с единицы.
    public boolean remove(int position) {
        if (position < 1 || position > size) {
            return false;
        }
        Student removed;
        if (position == 1) {
            removed = head;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Student previous = head;
            for (int i = 1; i < position - 1; i++) {
                previous = previous.next;
            }
            removed = previous.next;
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
        Student current = head;
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
        int position = 1;
        for (Student current = head; current != null; current = current.next) {
            System.out.print(position++ + ". ");
            current.print();
        }
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}

public class Tester {
    static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Повторяем ввод, если введено не целое число.
            }
            System.out.println("Введите целое число от " + min + " до " + max + ".");
        }
    }

    public static void main(String[] args) {
        StudentList students = new StudentList();
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
                        Student student = new Student();
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
