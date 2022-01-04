package controller.Listener;

import controller.GeneralController;
import resources.Resources;
import view.ClientForm;

import java.awt.*;
import java.awt.event.*;

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
        if (e.getSource() == clientForm.getButtonCreate()) {
            message = log + "Нажата кнопка " + clientForm.getButtonCreate().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            String selected = clientForm.getListWhatToCreate().getSelectedItem();
            String name = clientForm.getTextFieldName().getText();
            String weigh = clientForm.getTextFieldWeight().getText();

            if (!selected.isEmpty()) {
                if (!name.isEmpty()) {
                    if (!weigh.isEmpty()) {
                        try {
                            float fWeigh = Float.parseFloat(weigh);
//                            switch (clientForm.getListWhatToCreate().getSelectedIndex()){
//                                case 0 -> GeneralController.createHerbivore(name, fWeigh);
//                                case 1 -> GeneralController.createPredator(name, fWeigh);
//                                case 2 -> GeneralController.createGrass(name, fWeigh);
//                            }
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                            clientForm.getTextAreaLogs().append(error + "Значение поля 'Вес' введено некорректно\n");
                            
                            throw new IllegalArgumentException("Значение веса введено некорректно");
                        }
                    } else {
                        clientForm.getTextAreaLogs().append(error + "Заполните поле 'Вес'\n");
                        return;
                    }
                } else {
                    clientForm.getTextAreaLogs().append(error + "Заполните поле 'Имя'\n");
                    return;
                }
            } else {
                clientForm.getTextAreaLogs().append(error + "Выберите кого создать!\n");
                return;
            }
            message = log +
                    "Создать: " + selected + "; " +
                    "Имя: " + name + "; " +
                    "Вес: " + weigh;
            clientForm.getTextAreaLogs().append(message + '\n');
        }


        if (e.getSource() == clientForm.getButtonKill()) {
            message = log + "Нажата кнопка " + clientForm.getButtonKill().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');

            String selected = clientForm.getChoiceAllAliveAnimals().getSelectedItem();
            if (selected.isEmpty()) {
                clientForm.getTextAreaLogs().append(error + "Выберите кого убить!\n");
                return;
            }

            message = log + "Убить: " + selected;
            clientForm.getTextAreaLogs().append(message + '\n');
        }
        if (e.getSource() == clientForm.getButtonFeed()) {
            message = log + "Нажата кнопка " + clientForm.getButtonFeed().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');

            if (clientForm.getCbgFeed().getSelectedCheckbox() != null) {
                if (clientForm.getCbHerbivores().getState()) {
                    String selectedHerbivore = clientForm.getListAllAliveHerbivoresToFeed().getSelectedItem();
                    String selectedFood = clientForm.getChoiceAllFood().getSelectedItem();
                    if (!selectedHerbivore.isEmpty()) {
                        if (!selectedFood.isEmpty()) {
                            message = log +
                                    "Покормить травоядное " + selectedHerbivore +
                                    "; Еда: " + selectedFood;
                            clientForm.getTextAreaLogs().append(message + '\n');
                        } else clientForm.getTextAreaLogs().append(error + "Выберите чем кормить!\n");
                    } else clientForm.getTextAreaLogs().append(error + "Выберите кого кормить!\n");
                }
                if (clientForm.getCbPredators().getState()) {
                    String selectedPredator = clientForm.getListAllAlivePredators().getSelectedItem();
                    String selectedFood = clientForm.getChoiceAllAliveHerbivores().getSelectedItem();
                    if (!selectedPredator.isEmpty()) {
                        if (!selectedFood.isEmpty()) {
                            message = log +
                                    "Покормить хищника " + selectedPredator +
                                    "; Еда: " + selectedFood;
                            clientForm.getTextAreaLogs().append(message + '\n');
                        } else clientForm.getTextAreaLogs().append(error + "Выберите чем кормить!\n");
                    } else clientForm.getTextAreaLogs().append(error + "Выберите кого кормить!\n");
                }
            } else clientForm.getTextAreaLogs().append(error + "Выберите кого покормить!\n");
        }
        if (e.getSource() == clientForm.getButtonPrint()) {
            message = log + "Нажата кнопка " + clientForm.getButtonPrint().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            if(clientForm.getCbgPrint().getSelectedCheckbox() == null)
            {
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

        if (e.getSource() == clientForm.getButtonStart()) {
            message = log + "Нажата кнопка " + clientForm.getButtonStart().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            String host = clientForm.getTextFieldHost().getText();
            String sPort = clientForm.getTextFieldPort().getText();
            int port;
            if(!host.isEmpty())
            {
                if(!sPort.isEmpty()){
                    try{
                        port = Integer.parseInt(sPort);
                    }
                    catch (NumberFormatException ex){
                        ex.printStackTrace();
                        clientForm.getTextAreaLogs().append(error + "Значение порта введено некорректно!\n");
                        throw new IllegalArgumentException("Значение порта введено некорректно");
                    }
                }
                else {
                    clientForm.getTextAreaLogs().append(error + "Введите значение порта!\n");
                    return;
                }
            }
            else
            {
                clientForm.getTextAreaLogs().append(error + "Введите адрес хоста!\n");
                return;
            }
            message = log +
                    "Подключение к серверу... " +
                    host + ":" + port;
            clientForm.enableComponents();
            clientForm.getTextAreaLogs().append(message + '\n');
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
            if (clientForm.getChoiceLanguage().getSelectedItem() != Resources.locale.getLanguage()) {
                Resources.setLocale(clientForm.getChoiceLanguage().getSelectedItem());
                System.out.println(Resources.locale.getLanguage());
                clientForm.refresh();
            }
        }
        System.out.println(e.getItem());
    }
}
