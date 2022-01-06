package controller.Listener;

import controller.GeneralController;
import controller.NetController;
import resources.Resources;
import view.ClientForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class ClientListener implements ActionListener, ItemListener {
    private final ClientForm clientForm;
    final String log = "[LOG] ";
    final String error = "[ERROR] ";

    public ClientListener(ClientForm clientForm) {
        this.clientForm = clientForm;
        for (Component component : clientForm.getComponents()) {
            if (component instanceof Checkbox) {
                ((Checkbox) component).addItemListener(this);
            }
            if (component instanceof JButton) {
                ((JButton) component).addActionListener(this);
            }
            if (component instanceof Choice) {
                ((Choice) component).addItemListener(this);
            }
        }
        this.clientForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GeneralController.save();
                NetController.exitClient();
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
            try {
                startPerform();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

//        System.out.println(e.getSource());
//        System.out.println(e.getActionCommand());
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
                clientForm.refresh();
            }
        }

        if (e.getSource() == clientForm.getCbPredatorsKill())
        {
            clientForm.getTextAreaLogs().append(log + "Выбран чекбокс:" + e.getItem() + '\n');
            clientForm.getChoiceAllAlivePredatorsKill().setVisible(true);
            clientForm.getChoiceAllAliveHerbivoresKill().setVisible(false);
        }

        if (e.getSource() == clientForm.getCbHerbivoresKill())
        {
            clientForm.getTextAreaLogs().append(log + "Выбран чекбокс:" + e.getItem() + '\n');
            clientForm.getChoiceAllAliveHerbivoresKill().setVisible(true);
            clientForm.getChoiceAllAlivePredatorsKill().setVisible(false);
        }


//        System.out.println(e.getItem());
    }

    private void createPerform() {
        String message;
        String selected = clientForm.getListWhatToCreate().getSelectedItem();
        String name = clientForm.getTextFieldName().getText();
        String weigh = clientForm.getTextFieldWeight().getText();

        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Выберите кого создать!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Выберите кого создать!\n");
            return;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Заполните поле 'Имя'", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Заполните поле 'Имя'\n");
            return;
        }
        if (weigh.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Заполните поле 'Вес'", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Заполните поле 'Вес'\n");
            return;
        }

        try {
            float fWeigh = Float.parseFloat(weigh);
                            switch (clientForm.getListWhatToCreate().getSelectedIndex()){
                                case 0 -> GeneralController.createHerbivore(name, fWeigh);
                                case 1 -> GeneralController.createPredator(name, fWeigh);
                                case 2 -> GeneralController.createGrass(name, fWeigh);
                            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(clientForm, "Значение поля 'Вес' введено некорректно", "Ошибка данных", JOptionPane.ERROR_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Значение поля 'Вес' введено некорректно\n");
            return;
        }
        clientForm.loadData();
        message = log +
                "Создать: " + selected + "; " +
                "Имя: " + name + "; " +
                "Вес: " + weigh;
        clientForm.getTextAreaLogs().append(message + '\n');
    }


    private void killPerform() {
        String message;
        if (clientForm.getCbgKill().getSelectedCheckbox() == null) {
            JOptionPane.showMessageDialog(clientForm, "Выберите кого убить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Выберите кого убить!\n");
            return;
        }

        if (clientForm.getCbHerbivoresKill().getState()) {
            String selectedHerbivore = clientForm.getChoiceAllAliveHerbivoresKill().getSelectedItem();
            if (selectedHerbivore.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите какого травоядного убить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + "Выберите какого травоядного убить!\n");
                return;
            }
            GeneralController.killHerbivore(clientForm.getChoiceAllAliveHerbivoresKill().getSelectedIndex(), true);
            clientForm.loadData();
            message = log +
                    "Убить травоядное " + selectedHerbivore;
            clientForm.getTextAreaLogs().append(message + '\n');
        }

        if (clientForm.getCbPredatorsKill().getState()) {
            String selectedPredator = clientForm.getChoiceAllAlivePredatorsKill().getSelectedItem();
            if (selectedPredator.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите какого хищника убить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + "Выберите какого хищника убить!\n");
                return;
            }
            GeneralController.killPredator(clientForm.getChoiceAllAlivePredatorsKill().getSelectedIndex(), true);
            clientForm.loadData();
            message = log +
                    "Убить хищника " + selectedPredator;
            clientForm.getTextAreaLogs().append(message + '\n');
        }
    }

    private void feedPerform() {
        String message;
        if (clientForm.getCbgFeed().getSelectedCheckbox() == null) {
            JOptionPane.showMessageDialog(clientForm, "Выберите кого покормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Выберите кого покормить!\n");
            return;
        }

        if (clientForm.getCbHerbivores().getState()) {
            String selectedHerbivore = clientForm.getListAllAliveHerbivoresToFeed().getSelectedItem();
            String selectedFood = clientForm.getChoiceAllFood().getSelectedItem();
            if (selectedHerbivore.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите какого травоядного кормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + "Выберите какого травоядного кормить!\n");
                return;
            }
            if (selectedFood.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите чем кормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + "Выберите чем кормить!\n");
                return;
            }
            int herbivoreID = clientForm.getListAllAliveHerbivoresToFeed().getSelectedIndex();
            int foodID = clientForm.getChoiceAllFood().getSelectedIndex();
            GeneralController.feedHerbivore(herbivoreID, foodID, true);
            clientForm.loadData();
            message = log +
                    "Покормить травоядное " + selectedHerbivore +
                    "; Еда: " + selectedFood;
            clientForm.getTextAreaLogs().append(message + '\n');
        }

        if (clientForm.getCbPredators().getState()) {
            String selectedPredator = clientForm.getListAllAlivePredators().getSelectedItem();
            String selectedFood = clientForm.getChoiceAllAliveHerbivores().getSelectedItem();
            if (selectedPredator.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите какого хищника кормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + "Выберите какого хищника кормить!\n");
                return;
            }
            if (selectedFood.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, "Выберите чем кормить!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + "Выберите чем кормить!\n");
                return;
            }
            int predatorID = clientForm.getListAllAlivePredators().getSelectedIndex();
            int herbivoreID = clientForm.getChoiceAllAliveHerbivores().getSelectedIndex();
            GeneralController.feedPredator(predatorID, herbivoreID, true);
            clientForm.loadData();
            message = log +
                    "Покормить хищника " + selectedPredator +
                    "; Еда: " + selectedFood;
            clientForm.getTextAreaLogs().append(message + '\n');
        }
    }

    private void printPerform() {
        if (clientForm.getCbgPrint().getSelectedCheckbox() == null) {
            JOptionPane.showMessageDialog(clientForm, "Выберите что вывести на экран!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Выберите что вывести на экран!\n");
            return;
        }
        TextArea textArea = clientForm.getTextAreaPrint();

        if (clientForm.getCbAllAnimals().getState()) {
            textArea.setText(GeneralController.print(GeneralController.ALL_ANIMALS));
            textArea.append("Вывод на экран списка всех животных\n");
        }
        if (clientForm.getCbAllAliveAnimals().getState()) {
            textArea.setText(GeneralController.print(GeneralController.ALL_ALIVE_ANIMALS));
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех живых животных\n");
        }
        if (clientForm.getCbAllHerbivores().getState()) {
            textArea.setText(GeneralController.print(GeneralController.ALL_HERBIVORES));
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех травоядных\n");
        }
        if (clientForm.getCbAllPredators().getState()) {
            textArea.setText(GeneralController.print(GeneralController.ALL_PREDATORS));
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех хищников\n");
        }
        if (clientForm.getCbAllAliveHerbivores().getState()) {
            textArea.setText(GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES));
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех живых травоядных\n");
        }
        if (clientForm.getCbAllAlivePredators().getState()) {
            textArea.setText(GeneralController.print(GeneralController.ALL_ALIVE_PREDATORS));
            clientForm.getTextAreaPrint().append("Вывод на экран списка всех живых хищников\n");
        }
        if (clientForm.getCbAllFood().getState()) {
            textArea.setText(GeneralController.print(GeneralController.ALL_FOOD));
            clientForm.getTextAreaPrint().append("Вывод на экран списка всей еды\n");
        }
    }


    private void startPerform() throws Exception {
        String message;
        String host = clientForm.getTextFieldHost().getText();
        String sPort = clientForm.getTextFieldPort().getText();
        int port = 0;
        if (host.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Введите адрес хоста!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Введите адрес хоста!\n");
            return;
        }
        if (sPort.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, "Введите значение порта!", "Внимание!", JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Введите значение порта!\n");
            return;
        }

        try {
            port = Integer.parseInt(sPort);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(clientForm, "Значение порта введено некорректно!", "Ошибка данных", JOptionPane.ERROR_MESSAGE);
            clientForm.getTextAreaErrors().append(error + "Значение порта введено некорректно!\n");
            return;
        }

        message = log +
                "Подключение к серверу... " +
                host + ":" + port;
        NetController.connectClient(host,port);
        clientForm.enableComponents();
        clientForm.getTextAreaLogs().append(message + '\n');
    }
}
