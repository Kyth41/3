import java.util.Scanner;

// Узел двусвязного списка: данные и ссылки в обоих направлениях.
class StudentTask2 {
    private String name;
    private int age;
    StudentTask2 next;
    StudentTask2 previous;

    public void readFromConsole(Scanner scanner) {
        do {
            System.out.print("Имя студента: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Имя не должно быть пустым.");
            }
        } while (name.isEmpty());
        age = Practice17Task2.readInt(scanner, "Возраст: ", 1, 150);
    }

    public void print() {
        System.out.println("Имя: " + name + ", возраст: " + age);
    }
}

// Двусвязный список: каждый узел связан с предыдущим и следующим.
class StudentListTask2 {
    private StudentTask2 head;
    private StudentTask2 tail;
    private int size;

    public StudentListTask2() {
        clear();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void add(StudentTask2 student) {
        student.next = null;
        student.previous = tail;
        if (isEmpty()) {
            head = student;
        } else {
            tail.next = student;
        }
        tail = student;
        size++;
    }

    // Ищем с ближайшего конца списка. Нумерация начинается с 1.
    private StudentTask2 get(int position) {
        if (position < 1 || position > size) {
            return null;
        }
        if (position <= size / 2) {
            StudentTask2 current = head;
            for (int i = 1; i < position; i++) {
                current = current.next;
            }
            return current;
        }
        StudentTask2 current = tail;
        for (int i = size; i > position; i--) {
            current = current.previous;
        }
        return current;
    }

    public boolean remove(int position) {
        StudentTask2 removed = get(position);
        if (removed == null) {
            return false;
        }
        if (removed.previous == null) {
            head = removed.next;
        } else {
            removed.previous.next = removed.next;
        }
        if (removed.next == null) {
            tail = removed.previous;
        } else {
            removed.next.previous = removed.previous;
        }
        removed.next = null;
        removed.previous = null;
        size--;
        return true;
    }

    public boolean printElement(int position) {
        StudentTask2 student = get(position);
        if (student == null) {
            return false;
        }
        student.print();
        return true;
    }

    public void printAll() {
        if (isEmpty()) {
            System.out.println("Картотека пуста.");
            return;
        }
        int position = 1;
        for (StudentTask2 current = head; current != null; current = current.next) {
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

public class Practice17Task2 {
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
        StudentListTask2 students = new StudentListTask2();
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
                        StudentTask2 student = new StudentTask2();
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
