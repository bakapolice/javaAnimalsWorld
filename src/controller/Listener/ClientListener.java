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
    final String log = "[LOG] ";
    final String error = "[ERROR] ";

    public ClientForm getClientForm(){
        return clientForm;
    }

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
                NetListener.exitClient();
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String message;

        if (e.getSource() == clientForm.getButtonCreate()) {
            message = log + Resources.rb.getString("MESSAGE_BUTTON_CLICK") + clientForm.getButtonCreate().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            createPerform();
        }

        if (e.getSource() == clientForm.getButtonKill()) {
            message = log + Resources.rb.getString("MESSAGE_BUTTON_CLICK") + clientForm.getButtonKill().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            killPerform();
        }

        if (e.getSource() == clientForm.getButtonFeed()) {
            message = log + Resources.rb.getString("MESSAGE_BUTTON_CLICK") + clientForm.getButtonFeed().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            feedPerform();
        }

        if (e.getSource() == clientForm.getButtonPrint()) {
            message = log + Resources.rb.getString("MESSAGE_BUTTON_CLICK") + clientForm.getButtonPrint().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            printPerform();
        }

        if (e.getSource() == clientForm.getButtonStart()) {
            message = log + Resources.rb.getString("MESSAGE_BUTTON_CLICK") + clientForm.getButtonStart().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            try {
                startPerform();
                loadData();
            } catch (Exception ex) {
                clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MESSAGE_ERROR_CONNECTING")+'\n');
            }
        }

        if (e.getSource() == clientForm.getButtonStop()) {
            message = log + Resources.rb.getString("MESSAGE_BUTTON_CLICK") + clientForm.getButtonStop().getActionCommand();
            clientForm.getTextAreaLogs().append(message + '\n');
            NetListener.disconnectClient();
            clientForm.disableComponents();
        }
    }

    private void loadData(){
        clientForm.getChoiceAllAliveHerbivoresKill().removeAll();
        clientForm.getChoiceAllAlivePredatorsKill().removeAll();
        clientForm.getChoiceAllAliveHerbivores().removeAll();
        clientForm.getListAllAliveHerbivoresToFeed().removeAll();
        clientForm.getListAllAlivePredators().removeAll();
        clientForm.getChoiceAllFood().removeAll();
        clientForm.getTextAreaPrint().setText(null);

        GeneralController.loadData(GeneralController.ALL_ALIVE_HERBIVORES);
        GeneralController.loadData(GeneralController.ALL_ALIVE_PREDATORS);
        GeneralController.loadData(GeneralController.ALL_FOOD);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == clientForm.getCbHerbivores()) {
            clientForm.getTextAreaLogs().append(log + Resources.rb.getString("MESSAGE_CHECKBOX_CHECKED") + e.getItem() + '\n');
            clientForm.getListAllAliveHerbivoresToFeed().setVisible(true);
            clientForm.getListAllAlivePredators().setVisible(false);

            clientForm.getChoiceAllFood().setVisible(true);
            clientForm.getChoiceAllAliveHerbivores().setVisible(false);
        }
        if (e.getSource() == clientForm.getCbPredators()) {
            clientForm.getTextAreaLogs().append(log + Resources.rb.getString("MESSAGE_CHECKBOX_CHECKED") + e.getItem() + '\n');
            clientForm.getListAllAlivePredators().setVisible(true);
            clientForm.getListAllAliveHerbivoresToFeed().setVisible(false);

            clientForm.getChoiceAllFood().setVisible(false);
            clientForm.getChoiceAllAliveHerbivores().setVisible(true);
        }

        if (e.getSource() == clientForm.getChoiceLanguage()) {
            clientForm.getTextAreaLogs().append(log + Resources.rb.getString("MESSAGE_LANGUAGE_CHANGED")+ "\n");
            if (!Objects.equals(clientForm.getChoiceLanguage().getSelectedItem(), Resources.locale.getLanguage())) {
                Resources.setLocale(clientForm.getChoiceLanguage().getSelectedItem());
                clientForm.refresh();
            }
        }

        if (e.getSource() == clientForm.getCbPredatorsKill())
        {
            clientForm.getTextAreaLogs().append(log + Resources.rb.getString("MESSAGE_CHECKBOX_CHECKED") + e.getItem() + '\n');
            clientForm.getChoiceAllAlivePredatorsKill().setVisible(true);
            clientForm.getChoiceAllAliveHerbivoresKill().setVisible(false);
        }

        if (e.getSource() == clientForm.getCbHerbivoresKill())
        {
            clientForm.getTextAreaLogs().append(log + Resources.rb.getString("MESSAGE_CHECKBOX_CHECKED") + e.getItem() + '\n');
            clientForm.getChoiceAllAliveHerbivoresKill().setVisible(true);
            clientForm.getChoiceAllAlivePredatorsKill().setVisible(false);
        }
    }

    private void createPerform(){
        String selected = clientForm.getListWhatToCreate().getSelectedItem();
        String name = clientForm.getTextFieldName().getText();
        String weigh = clientForm.getTextFieldWeight().getText();

        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_CREATE"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_CREATE")+"\n");
            return;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_FILL_NAME_FIELD"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_FILL_NAME_FIELD") + '\n');
            return;
        }
        if (weigh.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_FILL_WEIGH_FIELD"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_FILL_WEIGH_FIELD") + '\n');
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
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_WRONG_WEIGH_VALUE"), Resources.rb.getString("DATA_ERROR"), JOptionPane.ERROR_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_WRONG_WEIGH_VALUE") + "\n");
            return;
        }
        loadData();
    }


    private void killPerform(){
        if (clientForm.getCbgKill().getSelectedCheckbox() == null) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_KILL"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_KILL")+ "\n");
            return;
        }

        if (clientForm.getCbHerbivoresKill().getState()) {
            String selectedHerbivore = clientForm.getChoiceAllAliveHerbivoresKill().getSelectedItem();
            if (selectedHerbivore.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_KILL_HERB"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_KILL_HERB")+"\n");
                return;
            }
            GeneralController.killHerbivore(clientForm.getChoiceAllAliveHerbivoresKill().getSelectedIndex(), true);
            loadData();
        }

        if (clientForm.getCbPredatorsKill().getState()) {
            String selectedPredator = clientForm.getChoiceAllAlivePredatorsKill().getSelectedItem();
            if (selectedPredator.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_KILL_PRED"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_KILL_PRED")+"\n");
                return;
            }
            GeneralController.killPredator(clientForm.getChoiceAllAlivePredatorsKill().getSelectedIndex(), true);
            loadData();
        }
    }

    private void feedPerform(){
        if (clientForm.getCbgFeed().getSelectedCheckbox() == null) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_FEED"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_FEED")+"\n");
            return;
        }

        if (clientForm.getCbHerbivores().getState()) {
            String selectedHerbivore = clientForm.getListAllAliveHerbivoresToFeed().getSelectedItem();
            String selectedFood = clientForm.getChoiceAllFood().getSelectedItem();
            if (selectedHerbivore.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_FEED_HERB"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_FEED_HERB")+ "\n");
                return;
            }
            if (selectedFood.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHAT_TO_FEED"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHAT_TO_FEED") + "\n");
                return;
            }
            int herbivoreID = clientForm.getListAllAliveHerbivoresToFeed().getSelectedIndex();
            int foodID = clientForm.getChoiceAllFood().getSelectedIndex();
            GeneralController.feedHerbivore(herbivoreID, foodID, true);
            loadData();
        }

        if (clientForm.getCbPredators().getState()) {
            String selectedPredator = clientForm.getListAllAlivePredators().getSelectedItem();
            String selectedFood = clientForm.getChoiceAllAliveHerbivores().getSelectedItem();
            if (selectedPredator.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_FEED_PRED"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHO_TO_FEED_PRED") + "\n");
                return;
            }
            if (selectedFood.isEmpty()) {
                JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHAT_TO_FEED"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
                clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHAT_TO_FEED") + "\n");
                return;
            }
            int predatorID = clientForm.getListAllAlivePredators().getSelectedIndex();
            int herbivoreID = clientForm.getChoiceAllAliveHerbivores().getSelectedIndex();
            GeneralController.feedPredator(predatorID, herbivoreID, true);
            loadData();
        }
    }

    private void printPerform() {
        if (clientForm.getCbgPrint().getSelectedCheckbox() == null) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_CHOOSE_WHAT_TO_PRINT"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_CHOOSE_WHAT_TO_PRINT") + "\n");
            return;
        }
        TextArea textArea = clientForm.getTextAreaPrint();
        textArea.setText(null);
        if (clientForm.getCbAllAnimals().getState()) {
            GeneralController.print(GeneralController.ALL_ANIMALS);
        }
        if (clientForm.getCbAllAliveAnimals().getState()) {
            GeneralController.print(GeneralController.ALL_ALIVE_ANIMALS);
        }
        if (clientForm.getCbAllHerbivores().getState()) {
            GeneralController.print(GeneralController.ALL_HERBIVORES);
        }
        if (clientForm.getCbAllPredators().getState()) {
            GeneralController.print(GeneralController.ALL_PREDATORS);
        }
        if (clientForm.getCbAllAliveHerbivores().getState()) {
            GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES);
        }
        if (clientForm.getCbAllAlivePredators().getState()) {
            GeneralController.print(GeneralController.ALL_ALIVE_PREDATORS);
        }
        if (clientForm.getCbAllFood().getState()) {
            GeneralController.print(GeneralController.ALL_FOOD);
        }
    }

    private void startPerform() throws Exception {
        String host = clientForm.getTextFieldHost().getText();
        String sPort = clientForm.getTextFieldPort().getText();
        int port;
        if (host.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_FILL_HOST_FIELD"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_FILL_HOST_FIELD") + "\n");
            return;
        }
        if (sPort.isEmpty()) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_FILL_PORT_FIELD"), Resources.rb.getString("WARNING"), JOptionPane.WARNING_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_FILL_PORT_FIELD")+"\n");
            return;
        }

        try {
            port = Integer.parseInt(sPort);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(clientForm, Resources.rb.getString("MSGBOX_WRONG_PORT_VALUE"), Resources.rb.getString("DATA_ERROR"), JOptionPane.ERROR_MESSAGE);
            clientForm.getTextAreaErrors().append(error + Resources.rb.getString("MSGBOX_WRONG_PORT_VALUE")+"\n");
            return;
        }

        NetListener.connectClient(host,port);
        clientForm.enableComponents();
    }
}
