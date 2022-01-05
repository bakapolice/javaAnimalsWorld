package controller.Listener;

import controller.GeneralController;
import resources.Resources;
import view.ClientForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class ClientListener implements ActionListener, ItemListener {
    private final ClientForm clientForm;
    private Button button;
    private Choice choice;
    private Checkbox checkbox;
    final String log = "[LOG] ";
    final String error = "[ERROR] ";

    public ClientListener(ClientForm clientForm) {
        this.clientForm = clientForm;
        for (Component component : clientForm.getComponents()) {
            if (component instanceof Checkbox) {
                checkbox = (Checkbox) component;
                checkbox.addItemListener(this);
            }
            if (component instanceof Button) {
                button = (Button) component;
                button.addActionListener(this);
            }
            if (component instanceof Choice) {
                choice = (Choice) component;
                choice.addItemListener(this);
            }
        }
        this.clientForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GeneralController.save();
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String message;

        // Обработка события "Создать"
        if (e.getSource() == clientForm.getButtonCreate()) {
            message = log + "Нажата кнопка " + clientForm.getButtonCreate().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            createPerform();
        }


        // Обработка события "Убить"
        if (e.getSource() == clientForm.getButtonKill()) {
            message = log + "Нажата кнопка " + clientForm.getButtonKill().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            killPerform();
        }

        // Обработка события "Покормить"
        if (e.getSource() == clientForm.getButtonFeed()) {
            message = log + "Нажата кнопка " + clientForm.getButtonFeed().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            feedPerform();
        }

        // Обработка события "Вывести на экран"
        if (e.getSource() == clientForm.getButtonPrint()) {
            message = log + "Нажата кнопка " + clientForm.getButtonPrint().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            printPerform();
        }

        // Обработка события "Начать работу"
        if (e.getSource() == clientForm.getButtonStart()) {
            message = log + "Нажата кнопка " + clientForm.getButtonStart().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            startPerform();
        }

        System.out.println(e.getSource());
        System.out.println(e.getActionCommand());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == clientForm.getCbHerbivores()) {
            clientForm.getTextAreaLogs().append(log + "Выбран чекбокс:" + e.getItem() + '\n');
            clientForm.getListAllAliveHerbivoresToFeed().setVisible(true);
            clientForm.getListAllAlivePredators().setVisible(false);

            clientForm.getChoiceAllFood().setVisible(true);
            clientForm.getChoiceAllAliveHerbivores().setVisible(false);
        }
        if (e.getSource() == clientForm.getCbPredators()) {
            clientForm.getTextAreaLogs().append(log + "Выбран чекбокс:" + e.getItem() + '\n');
            clientForm.getListAllAlivePredators().setVisible(true);
            clientForm.getListAllAliveHerbivoresToFeed().setVisible(false);

            clientForm.getChoiceAllFood().setVisible(false);
            clientForm.getChoiceAllAliveHerbivores().setVisible(true);
        }

        if (e.getSource() == clientForm.getChoiceLanguage()) {
            clientForm.getTextAreaLogs().append(log + "Язык формы изменен\n");
            if (!Objects.equals(clientForm.getChoiceLanguage().getSelectedItem(), Resources.locale.getLanguage())) {
                Resources.setLocale(clientForm.getChoiceLanguage().getSelectedItem());
                //System.out.println(Resources.locale.getLanguage());
                clientForm.refresh();
            }
        }
        System.out.println(e.getItem());
    }

    private void createPerform() {
        String message;
        String selected = clientForm.getListWhatToCreate().getSelectedItem();
        String name = clientForm.getTextFieldName().getText();
        String weigh = clientForm.getTextFieldWeight().getText();

        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Выберите кого создать!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Выберите кого создать!\n");
            return;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Заполните поле 'Имя'", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Заполните поле 'Имя'\n");
            return;
        }
        if (weigh.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Заполните поле 'Вес'", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Заполните поле 'Вес'\n");
            return;
        }

        try {
            float fWeigh = Float.parseFloat(weigh);
//                            switch (clientForm.getListWhatToCreate().getSelectedIndex()){
//                                case 0 -> GeneralController.createHerbivore(name, fWeigh);
//                                case 1 -> GeneralController.createPredator(name, fWeigh);
//                                case 2 -> GeneralController.createGrass(name, fWeigh);
//                            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(clientForm, "Значение поля 'Вес' введено некорректно", "Ошибка данных", JOptionPane.ERROR_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Значение поля 'Вес' введено некорректно\n");
            return;
        }
        message = log +
                "Создать: " + selected + "; " +
                "Имя: " + name + "; " +
                "Вес: " + weigh;
        clientForm.getTextAreaLogs().append(message + '\n');
    }


    private void killPerform() {
        String message;
        String selected = clientForm.getChoiceAllAliveAnimals().getSelectedItem();
        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Выберите кого убить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Выберите кого убить!\n");
            return;
        }
        message = log + "Убить: " + selected;
        clientForm.getTextAreaLogs().append(message + '\n');
    }

    private void feedPerform() {
        String message;
        if (clientForm.getCbgFeed().getSelectedCheckbox() == null) {
            JOptionPane.showMessageDialog(clientForm, "Выберите кого покормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Выберите кого покормить!\n");
            return;
        }

        if (clientForm.getCbHerbivores().getState()) {
            String selectedHerbivore = clientForm.getListAllAliveHerbivoresToFeed().getSelectedItem();
            String selectedFood = clientForm.getChoiceAllFood().getSelectedItem();
            if (selectedHerbivore.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите кого кормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaLogs().append(error + "Выберите кого кормить!\n");
                return;
            }
            if (selectedFood.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите чем кормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaLogs().append(error + "Выберите чем кормить!\n");
                return;
            }
            message = log +
                    "Покормить травоядное " + selectedHerbivore +
                    "; Еда: " + selectedFood;
            clientForm.getTextAreaLogs().append(message + '\n');
        }

        if (clientForm.getCbPredators().getState()) {
            String selectedPredator = clientForm.getListAllAlivePredators().getSelectedItem();
            String selectedFood = clientForm.getChoiceAllAliveHerbivores().getSelectedItem();
            if (selectedPredator.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите кого кормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaLogs().append(error + "Выберите кого кормить!\n");
                return;
            }
            if (selectedFood.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите чем кормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaLogs().append(error + "Выберите чем кормить!\n");
                return;
            }
            message = log +
                    "Покормить хищника " + selectedPredator +
                    "; Еда: " + selectedFood;
            clientForm.getTextAreaLogs().append(message + '\n');
        }
    }

    private void printPerform() {
        if (clientForm.getCbgPrint().getSelectedCheckbox() == null) {
            JOptionPane.showMessageDialog(clientForm, "Выберите что вывести на экран!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Выберите что вывести на экран!\n");
            return;
        }
        if (clientForm.getCbAllAnimals().getState()) {
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех животных\n");
        }
        if (clientForm.getCbAllAliveAnimals().getState()) {
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех живых животных\n");
        }
        if (clientForm.getCbAllHerbivores().getState()) {
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех травоядных\n");
        }
        if (clientForm.getCbAllPredators().getState()) {
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех хищников\n");
        }
        if (clientForm.getCbAllAliveHerbivores().getState()) {
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех живых травоядных\n");
        }
        if (clientForm.getCbAllAlivePredators().getState()) {
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех живых хищников\n");
        }
        if (clientForm.getCbAllFood().getState()) {
            clientForm.getTextAreaPrint().append("Вывод на экран списка всей еды\n");
        }
    }


    private void startPerform() {
        String message;
        String host = clientForm.getTextFieldHost().getText();
        String sPort = clientForm.getTextFieldPort().getText();
        int port = 0;
        if (host.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Введите адрес хоста!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Введите адрес хоста!\n");
            return;
        }
        if (sPort.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Введите значение порта!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Введите значение порта!\n");
            return;
        }

        try {
            port = Integer.parseInt(sPort);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(clientForm, "Значение порта введено некорректно!", "Ошибка данных", JOptionPane.ERROR_MESSAGE);
            clientForm.getTextAreaLogs().append(error + "Значение порта введено некорректно!\n");
            return;
        }

        message = log +
                "Подключение к серверу... " +
                host + ":" + port;
        clientForm.enableComponents();
        clientForm.getTextAreaLogs().append(message + '\n');
    }
}
