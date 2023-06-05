import java.io.*;

public class ExpressionCalculator implements Serializable {
    private double x;
    private double y;

    public ExpressionCalculator(double x) {
        this.x = x;
        this.y = calculate();
    }

    public double calculate() {
        return x - Math.sin(x);
    }

    public void saveStateToFile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
        fos.close();
    }

    public static ExpressionCalculator loadStateFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ExpressionCalculator calculator = (ExpressionCalculator) ois.readObject();
        ois.close();
        fis.close();
        return calculator;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ExpressionCalculator calculator = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Введите команду (\"calculate\", \"save\", \"upload\", \"exit\"): ");
            String command = reader.readLine();
            if (command.equals("calculate")) {
                System.out.print("Введите x: ");
                double x = Double.parseDouble(reader.readLine());
                calculator = new ExpressionCalculator(x);
                System.out.println(String.format("Значение выражения y=%f", calculator.y));
            } else if (command.equals("save")) {
                if (calculator != null) {
                    System.out.print("Введите имя файла: ");
                    String filename = reader.readLine();
                    calculator.saveStateToFile(filename);
                    System.out.println(String.format("Сохранено состояние объекта в файл %s", filename));
                } else {
                    System.out.println("Калькулятор не инициализирован");
                }
            } else if (command.equals("upload")) {
                System.out.print("Введите имя файла: ");
                String filename = reader.readLine();
                calculator = ExpressionCalculator.loadStateFromFile(filename);
                System.out.println(String.format("Восстановлено состояние объекта из файла %s", filename));
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Неизвестная команда");
            }
        }
    }
}