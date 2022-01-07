package view;

import controller.GeneralController;
import controller.Listener.ClientListener;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;


public class ClientForm extends Frame {
    private Label labelCreate;
    private Label labelFeed;
    private Label labelKill;
    private Label labelPrint;

    private JButton buttonCreate;
    private JButton buttonFeed;
    private JButton buttonKill;
    private JButton buttonPrint;
    private JButton buttonStart;
    private JButton buttonStop;

    private Choice choiceLanguage;
    private Choice listWhatToCreate;
    private Choice listAllAlivePredators; // Список живых хищников для выбора "кого кормить"
    private Choice listAllAliveHerbivoresToFeed; // Список живых травоядных для выбора "кого кормить"
    private Choice choiceAllAliveHerbivores; // Список живых травоядных для выбора "чем кормить"
    private Choice choiceAllFood; // Список еды для выбора "чем кормить"


    private TextField textFieldName;
    private TextField textFieldWeight;
    private TextField textFieldHost;
    private TextField textFieldPort;

    private TextArea textAreaPrint;
    private TextArea textAreaLogs;
    private TextArea textAreaErrors;

    //Фон для отделения блоков
    private Canvas canvasSettings;
    private Canvas canvasCreate;
    private Canvas canvasFeed;
    private Canvas canvasKill;
    private Canvas canvasPrint;

    private Label labelWhoToCreate;
    private Label labelWhoToKill;
    private Label labelWhoToFeed;
    private Label labelWhatToFeed;
    private Label labelWeight;
    private Label labelName;
    private Label labelWhatToPrint;
    private Label labelLanguage;
    private Label labelHost;
    private Label labelPort;

    private CheckboxGroup cbgPrint;
    private Checkbox cbAllAnimals;
    private Checkbox cbAllAliveAnimals;
    private Checkbox cbAllHerbivores;
    private Checkbox cbAllPredators;
    private Checkbox cbAllAliveHerbivores;
    private Checkbox cbAllAlivePredators;
    private Checkbox cbAllFood;

    private CheckboxGroup cbgFeed;
    private Checkbox cbHerbivores;
    private Checkbox cbPredators;

    private CheckboxGroup cbgKill;
    private Checkbox cbHerbivoresKill;
    private Checkbox cbPredatorsKill;
    private Choice choiceAllAlivePredatorsKill;
    private Choice choiceAllAliveHerbivoresKill;


    private ClientListener clientListener;

    public ClientForm() {
        setupGUI();
        setText();
    }

    public void refresh() {
        setText();
    }

    public void disableComponents() {
        for (Component component : this.getComponents()) {
            if (component != buttonStart && component != labelLanguage
                    && component != choiceLanguage && component != labelHost
                    && component != textFieldHost && component != labelPort
                    && component != textFieldPort)
            {
                component.setEnabled(false);
            }
        }
        buttonStart.setEnabled(true);
        buttonStart.setToolTipText("Нажмите для подключения к серверу!");
        String messageDisabled = "Кнопка неактивна. Необходимо подключение к серверу";
        buttonStop.setToolTipText(messageDisabled);
        buttonCreate.setToolTipText(messageDisabled);
        buttonKill.setToolTipText(messageDisabled);
        buttonFeed.setToolTipText(messageDisabled);
        buttonPrint.setToolTipText(messageDisabled);
    }

    public void enableComponents() {
        for (Component component : this.getComponents())
            component.setEnabled(true);
        choiceLanguage.setEnabled(false);
        buttonStart.setEnabled(false);
        buttonStart.setToolTipText("Кнопка неактивна. Вы уже подключены к серверу!");
        buttonStop.setToolTipText("Нажмите, чтобы отключиться от сервера!");
        buttonCreate.setToolTipText(null);
        buttonKill.setToolTipText(null);
        buttonFeed.setToolTipText(null);
        buttonPrint.setToolTipText(null);
    }

    private void setText() {
        labelLanguage.setText(Resources.rb.getString("LABEL_LANGUAGE"));

        labelHost.setText(Resources.rb.getString("LABEL_HOST"));
        labelPort.setText(Resources.rb.getString("LABEL_PORT"));
        buttonStart.setText(Resources.rb.getString("BUTTON_START"));
        buttonStop.setText(Resources.rb.getString("BUTTON_STOP"));

        buttonCreate.setText(Resources.rb.getString("BUTTON_CREATE"));
        labelCreate.setText(Resources.rb.getString("LABEL_CREATE").toUpperCase());
        labelWhoToCreate.setText(Resources.rb.getString("LABEL_WHO_TO_CREATE"));
        labelName.setText(Resources.rb.getString("LABEL_NAME"));
        labelWeight.setText(Resources.rb.getString("LABEL_WEIGHT"));

        listWhatToCreate.removeAll();
        listWhatToCreate.add(Resources.rb.getString("MESSAGE_HERBIVORE"));
        listWhatToCreate.add(Resources.rb.getString("MESSAGE_PREDATOR"));
        listWhatToCreate.add(Resources.rb.getString("MESSAGE_GRASS"));

        buttonFeed.setText(Resources.rb.getString("BUTTON_FEED"));
        labelFeed.setText(Resources.rb.getString("LABEL_FEED").toUpperCase());
        labelWhoToFeed.setText(Resources.rb.getString("LABEL_WHO_TO_FEED"));
        labelWhatToFeed.setText(Resources.rb.getString("LABEL_WHAT_TO_FEED"));

        cbHerbivores.setLabel(Resources.rb.getString("MESSAGE_HERBIVORE"));
        cbPredators.setLabel(Resources.rb.getString("MESSAGE_PREDATOR"));

        buttonKill.setText(Resources.rb.getString("BUTTON_KILL"));
        labelKill.setText(Resources.rb.getString("LABEL_KILL").toUpperCase());
        labelWhoToKill.setText(Resources.rb.getString("LABEL_WHO_TO_KILL"));
        cbHerbivoresKill.setLabel(Resources.rb.getString("MESSAGE_HERBIVORE"));
        cbPredatorsKill.setLabel(Resources.rb.getString("MESSAGE_PREDATOR"));

        buttonPrint.setText(Resources.rb.getString("BUTTON_PRINT"));
        labelPrint.setText(Resources.rb.getString("LABEL_PRINT").toUpperCase());
        labelWhatToPrint.setText(Resources.rb.getString("LABEL_WHAT_TO_PRINT"));
        cbAllAnimals.setLabel(Resources.rb.getString("MESSAGE_PRINT_ALL_ANIMALS"));
        cbAllAliveAnimals.setLabel(Resources.rb.getString("MESSAGE_PRINT_ALL_ALIVE_ANIMALS"));
        cbAllHerbivores.setLabel(Resources.rb.getString("MESSAGE_PRINT_ALL_HERBIVORES"));
        cbAllPredators.setLabel(Resources.rb.getString("MESSAGE_PRINT_ALL_PREDATORS"));
        cbAllAliveHerbivores.setLabel(Resources.rb.getString("MESSAGE_PRINT_ALL_ALIVE_HERBIVORES"));
        cbAllAlivePredators.setLabel(Resources.rb.getString("MESSAGE_PRINT_ALL_ALIVE_PREDATORS"));
        cbAllFood.setLabel(Resources.rb.getString("MESSAGE_PRINT_ALL_FOOD"));
    }

    private void setupGUI() {
        setTitle("Animals");

        Color custom = new Color(205, 205, 205);

        // Полоса настроек
        labelLanguage = new Label();
        labelLanguage.setBounds(59, 20 + 25, 60, 30);
        labelLanguage.setBackground(custom);
        this.add(labelLanguage);

        choiceLanguage = new Choice();
        choiceLanguage.add("ru");
        choiceLanguage.add("en");
        choiceLanguage.select(Resources.locale.getLanguage());
        choiceLanguage.setBounds(120, 20 + 25, 107, 24);
        this.add(choiceLanguage);

        labelHost = new Label();
        labelHost.setBounds(280, 20 + 25, 200, 30);
        labelHost.setBackground(custom);
        this.add(labelHost);

        textFieldHost = new TextField("localhost");
        textFieldHost.setBounds(490, 20 + 25, 233, 24);
        this.add(textFieldHost);

        labelPort = new Label();
        labelPort.setBounds(778, 20 + 25, 65, 30);
        labelPort.setBackground(custom);
        this.add(labelPort);

        textFieldPort = new TextField("5050");
        textFieldPort.setBounds(844, 20 + 25, 100, 24);
        this.add(textFieldPort);

        buttonStart = new JButton();
        buttonStart.setBounds(961, 15 + 25, 170, 35);
        buttonStart.setBackground(new Color(51, 53, 59));
        buttonStart.setForeground(Color.white);
        this.add(buttonStart);

        buttonStop = new JButton();
        buttonStop.setBounds(1136, 15 + 25, 170, 35);
        buttonStop.setBackground(new Color(51, 53, 59));
        buttonStop.setForeground(Color.white);
        this.add(buttonStop);

        canvasSettings = new Canvas();
        canvasSettings.setBounds(0, 0, 1366, 80);
        canvasSettings.setBackground(custom);
        this.add(canvasSettings);
        // ------------------------------------------------------------


        // Блок "Создать"
        textFieldName = new TextField();
        textFieldName.setBounds(136, 240, 166, 24);
        this.add(textFieldName);

        textFieldWeight = new TextField();
        textFieldWeight.setBounds(136, 286, 166, 24);
        this.add(textFieldWeight);


        buttonCreate = new JButton();
        buttonCreate.setBounds(137, 324, 166, 30);
        buttonCreate.setBackground(new Color(51, 53, 59));
        buttonCreate.setForeground(Color.white);
        this.add(buttonCreate);

        labelCreate = new Label();
        labelCreate.setBounds(175, 137, 150, 28);
        labelCreate.setBackground(custom);
        this.add(labelCreate);

        labelWhoToCreate = new Label();
        labelWhoToCreate.setBounds(135, 178, 150, 15);
        labelWhoToCreate.setBackground(custom);
        this.add(labelWhoToCreate);

        labelName = new Label();
        labelName.setBounds(135, 226, 150, 15);
        labelName.setBackground(custom);
        this.add(labelName);

        labelWeight = new Label();
        labelWeight.setBounds(135, 270, 150, 15);
        labelWeight.setBackground(custom);
        this.add(labelWeight);

        listWhatToCreate = new Choice();
        listWhatToCreate.setBounds(136, 196, 166, 24);
        this.add(listWhatToCreate);

        canvasCreate = new Canvas();
        canvasCreate.setBounds(61, 121, 320, 272);
        canvasCreate.setBackground(custom);
        this.add(canvasCreate);
        // ------------------------------------------------------------


        // Блок "Покормить"
        buttonFeed = new JButton();
        buttonFeed.setBounds(137, 655, 166, 30);
        buttonFeed.setBackground(new Color(51, 53, 59));
        buttonFeed.setForeground(Color.white);
        this.add(buttonFeed);

        labelFeed = new Label();
        labelFeed.setBounds(156, 465, 150, 28);
        labelFeed.setBackground(custom);
        this.add(labelFeed);

        labelWhoToFeed = new Label();
        labelWhoToFeed.setBounds(135, 506, 150, 15);
        labelWhoToFeed.setBackground(custom);
        this.add(labelWhoToFeed);

        labelWhatToFeed = new Label();
        labelWhatToFeed.setBounds(135, 598, 150, 15);
        labelWhatToFeed.setBackground(custom);
        this.add(labelWhatToFeed);

        cbgFeed = new CheckboxGroup();
        cbHerbivores = new Checkbox("", cbgFeed, false);
        cbHerbivores.setBounds(135, 532, 150, 15);
        cbHerbivores.setBackground(custom);
        this.add(cbHerbivores);

        cbPredators = new Checkbox("", cbgFeed, false);
        cbPredators.setBounds(135, 548, 150, 15);
        cbPredators.setBackground(custom);
        this.add(cbPredators);

        listAllAlivePredators = new Choice();
        listAllAlivePredators.setBounds(136, 565, 166, 24); // Видимость зависит от выбранного типа животного
        this.add(listAllAlivePredators);
        listAllAlivePredators.setVisible(false); // Если выбран чекбокс "Травоядное" - true, иначе - false

        listAllAliveHerbivoresToFeed = new Choice();
        listAllAliveHerbivoresToFeed.setBounds(136, 565, 166, 24); // Видимость зависит от выбранного типа животного
        this.add(listAllAliveHerbivoresToFeed);
        listAllAliveHerbivoresToFeed.setVisible(false); // Если выбран чекбокс "Хищника" - true, иначе - false

        choiceAllAliveHerbivores = new Choice();
        choiceAllAliveHerbivores.setBounds(136, 615, 166, 24);
        this.add(choiceAllAliveHerbivores);
        choiceAllAliveHerbivores.setVisible(false); // Если выбран чекбокс "Хищника" - true, иначе - false

        choiceAllFood = new Choice();
        choiceAllFood.setBounds(136, 615, 166, 24);
        this.add(choiceAllFood);
        choiceAllFood.setVisible(false); // Если выбран чекбокс "Травоядное" - true, иначе - false

        canvasFeed = new Canvas();
        canvasFeed.setBounds(61, 445, 320, 272);
        canvasFeed.setBackground(custom);
        this.add(canvasFeed);
        // ------------------------------------------------------------


        // Блок "Убить"
        buttonKill = new JButton();
        buttonKill.setBounds(497, 324, 166, 30);
        buttonKill.setBackground(new Color(51, 53, 59));
        buttonKill.setForeground(Color.white);
        this.add(buttonKill);

        labelKill = new Label();
        labelKill.setBounds(547, 137, 150, 28);
        labelKill.setBackground(custom);
        this.add(labelKill);

        labelWhoToKill = new Label();
        labelWhoToKill.setBounds(494, 178, 150, 15);
        labelWhoToKill.setBackground(custom);
        this.add(labelWhoToKill);



        // Блок "Убить" -----------------------
        cbgKill = new CheckboxGroup();
        cbHerbivoresKill = new Checkbox("", cbgKill, false);
        cbHerbivoresKill.setBounds(494, 196, 150, 15);
        cbHerbivoresKill.setBackground(custom);
        this.add(cbHerbivoresKill);

        cbPredatorsKill = new Checkbox("", cbgKill, false);
        cbPredatorsKill.setBounds(494, 212, 150, 15);
        cbPredatorsKill.setBackground(custom);
        this.add(cbPredatorsKill);

        choiceAllAlivePredatorsKill = new Choice();
        choiceAllAlivePredatorsKill.setBounds(494, 240, 166, 24); // Видимость зависит от выбранного типа животного
        this.add(choiceAllAlivePredatorsKill);
        choiceAllAlivePredatorsKill.setVisible(false); // Если выбран чекбокс "Травоядное" - true, иначе - false

        choiceAllAliveHerbivoresKill = new Choice();
        choiceAllAliveHerbivoresKill.setBounds(494, 240, 166, 24); // Видимость зависит от выбранного типа животного
        this.add(choiceAllAliveHerbivoresKill);
        choiceAllAliveHerbivoresKill.setVisible(false); // Если выбран чекбокс "Хищника" - true, иначе - false

        canvasKill = new Canvas();
        canvasKill.setBounds(420, 121, 320, 272);
        canvasKill.setBackground(custom);
        this.add(canvasKill);
        // ------------------------------------------------------------


        // Блок "Печать"
        buttonPrint = new JButton();
        buttonPrint.setBounds(497, 655, 166, 30);
        buttonPrint.setBackground(new Color(51, 53, 59));
        buttonPrint.setForeground(Color.white);
        this.add(buttonPrint);

        labelPrint = new Label();
        labelPrint.setBounds(490, 465, 150, 28);
        labelPrint.setBackground(custom);
        this.add(labelPrint);

        labelWhatToPrint = new Label();
        labelWhatToPrint.setBounds(517, 506, 150, 15);
        labelWhatToPrint.setBackground(custom);
        this.add(labelWhatToPrint);

        cbgPrint = new CheckboxGroup();

        cbAllAnimals = new Checkbox("", cbgPrint, false);
        cbAllAnimals.setBounds(510, 533, 150, 15);
        cbAllAnimals.setBackground(custom);
        this.add(cbAllAnimals);

        cbAllAliveAnimals = new Checkbox("", cbgPrint, false);
        cbAllAliveAnimals.setBounds(510, 546, 150, 15);
        cbAllAliveAnimals.setBackground(custom);
        this.add(cbAllAliveAnimals);

        cbAllHerbivores = new Checkbox("", cbgPrint, false);
        cbAllHerbivores.setBounds(510, 559, 150, 15);
        cbAllHerbivores.setBackground(custom);
        this.add(cbAllHerbivores);

        cbAllPredators = new Checkbox("", cbgPrint, false);
        cbAllPredators.setBounds(510, 572, 150, 15);
        cbAllPredators.setBackground(custom);
        this.add(cbAllPredators);

        cbAllAliveHerbivores = new Checkbox("", cbgPrint, false);
        cbAllAliveHerbivores.setBounds(510, 586, 150, 15);
        cbAllAliveHerbivores.setBackground(custom);
        this.add(cbAllAliveHerbivores);

        cbAllAlivePredators = new Checkbox("", cbgPrint, false);
        cbAllAlivePredators.setBounds(510, 599, 150, 15);
        cbAllAlivePredators.setBackground(custom);
        this.add(cbAllAlivePredators);

        cbAllFood = new Checkbox("", cbgPrint, false);
        cbAllFood.setBounds(510, 612, 150, 15);
        cbAllFood.setBackground(custom);
        this.add(cbAllFood);

        canvasPrint = new Canvas();
        canvasPrint.setBounds(420, 445, 320, 272);
        canvasPrint.setBackground(custom);
        this.add(canvasPrint);
        // ------------------------------------------------------------


        // Поле вывода
        textAreaPrint = new TextArea(null, 999, 20, TextArea.SCROLLBARS_VERTICAL_ONLY);
        textAreaPrint.setBounds(800, 120, 504, 272);
        textAreaPrint.setEditable(false);
        textAreaPrint.setBackground(new Color(214, 214, 214));
        this.add(textAreaPrint);

        textAreaLogs = new TextArea(null, 999, 20, TextArea.SCROLLBARS_VERTICAL_ONLY);
        textAreaLogs.setBounds(800, 445, 504, 133);
        textAreaLogs.setEditable(false);
        textAreaLogs.setBackground(new Color(214, 214, 214));
        this.add(textAreaLogs);

        textAreaErrors = new TextArea(null, 999, 20, TextArea.SCROLLBARS_VERTICAL_ONLY);
        textAreaErrors.setBounds(800, 584, 504, 133);
        textAreaErrors.setEditable(false);
        textAreaErrors.setBackground(new Color(214, 214, 214));
        textAreaErrors.setForeground(Color.red);
        this.add(textAreaErrors);

        setLayout(null);
        this.setSize(1366, 768);
        this.setBackground(new Color(228, 228, 228));

        this.setLocationRelativeTo(null); //поставит форму в центр экрана после размещения компонентов
        setVisible(true);

        clientListener = new ClientListener(this);
        disableComponents();
    }


    public TextArea getTextAreaPrint() {
        return textAreaPrint;
    }

    public TextArea getTextAreaErrors() {return textAreaErrors;}

    public TextArea getTextAreaLogs() {
        return textAreaLogs;
    }

    public TextField getTextFieldName() {
        return textFieldName;
    }

    public TextField getTextFieldWeight() {
        return textFieldWeight;
    }

    public Choice getListWhatToCreate() {
        return listWhatToCreate;
    }

    public Choice getListAllAliveHerbivoresToFeed() {
        return listAllAliveHerbivoresToFeed;
    }

    public Choice getChoiceAllAliveHerbivores() {
        return choiceAllAliveHerbivores;
    }

    public Choice getListAllAlivePredators() {
        return listAllAlivePredators;
    }

    public Choice getChoiceAllFood() {
        return choiceAllFood;
    }

    public Choice getChoiceAllAlivePredatorsKill() {
        return choiceAllAlivePredatorsKill;
    }

    public Choice getChoiceAllAliveHerbivoresKill(){
        return choiceAllAliveHerbivoresKill;
    }

    public CheckboxGroup getCbgKill(){
        return cbgKill;
    }

    public Checkbox getCbHerbivoresKill() {
        return cbHerbivoresKill;
    }

    public Checkbox getCbPredatorsKill() {
        return cbPredatorsKill;
    }

    public Checkbox getCbHerbivores() {
        return cbHerbivores;
    }

    public Checkbox getCbPredators() {
        return cbPredators;
    }

    public Checkbox getCbAllAnimals() {
        return cbAllAnimals;
    }

    public Checkbox getCbAllAliveAnimals() {
        return cbAllAliveAnimals;
    }

    public Checkbox getCbAllHerbivores() {
        return cbAllHerbivores;
    }

    public Checkbox getCbAllPredators() {
        return cbAllPredators;
    }

    public Checkbox getCbAllAliveHerbivores() {
        return cbAllAliveHerbivores;
    }

    public Checkbox getCbAllAlivePredators() {
        return cbAllAlivePredators;
    }

    public Checkbox getCbAllFood() {
        return cbAllFood;
    }

    public JButton getButtonCreate() {
        return buttonCreate;
    }

    public JButton getButtonFeed() {
        return buttonFeed;
    }

    public JButton getButtonKill() {
        return buttonKill;
    }

    public JButton getButtonPrint() {
        return buttonPrint;
    }

    public JButton getButtonStart() {
        return buttonStart;
    }

    public TextField getTextFieldHost() {
        return textFieldHost;
    }

    public TextField getTextFieldPort() {
        return textFieldPort;
    }

    public Choice getChoiceLanguage() {
        return choiceLanguage;
    }

    public CheckboxGroup getCbgPrint() {
        return cbgPrint;
    }

    public CheckboxGroup getCbgFeed() {
        return cbgFeed;
    }

    public JButton getButtonStop() {
        return buttonStop;
    }
}
