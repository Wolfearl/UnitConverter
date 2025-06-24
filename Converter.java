package com.UnitConverter;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class Converter extends JFrame {

    private Map<String, String[]> unitsMap;
    private GridBagConstraints constraints;
    private JComboBox<String> cbFirst;
    private JComboBox<String> cbSecond;
    private DefaultComboBoxModel<String> cbModel1;
    private DefaultComboBoxModel<String> cbModel2;
    private JTextField text;
    private JLabel toWhat;

    public Converter() {

        super("Конвертер единиц");
        super.setBounds(600, 120, 500, 200);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setResizable(false);

        unitsMap = new HashMap<>();

        unitsMap.put("килограмм", new String[] {"грамм", "миллиграмм", "тонна"});
        unitsMap.put("грамм", new String[] {"килограмм", "миллиграмм"});
        unitsMap.put("миллиграмм", new String[] {"грамм", "килограмм"});
        unitsMap.put("тонна", new String[] {"килограмм"});

        unitsMap.put("секунда", new String[] {"минута", "час"});
        unitsMap.put("минута", new String[] {"секунда", "час"});
        unitsMap.put("час", new String[] {"минута", "секунда"});

        unitsMap.put("ампер", new String[] {"миллиампер", "килоампер"});
        unitsMap.put("миллиампер", new String[] {"ампер", "килоампер"});
        unitsMap.put("килоампер", new String[] {"ампер", "миллиампер"});

        unitsMap.put("кельвин", new String[] {"градусы цельсия"});
        unitsMap.put("градусы цельсия", new String[] {"кельвин"});

        unitsMap.put("литр", new String[] {"миллилитр"});
        unitsMap.put("миллилитр", new String[] {"литр"});

        unitsMap.put("ватт", new String[] {"киловатт", "лошадиная сила"});
        unitsMap.put("киловатт", new String[] {"ватт"});
        unitsMap.put("лошадиная сила", new String[] {"ватт"});

        unitsMap.put("ньютон", new String[] {"килограмм-сила"});
        unitsMap.put("килограмм-сила", new String[] {"ньютон"});

        unitsMap.put("паскаль", new String[] {"бар"});
        unitsMap.put("бар", new String[] {"паскаль"});

        unitsMap.put("джоуль", new String[] {"килоджоуль", "ватт-секунда"});
        unitsMap.put("килоджоуль", new String[] {"джоуль"});
        unitsMap.put("ватт-секунда", new String[] {"джоуль"});

        unitsMap.put("вольт", new String[] {"милливольт", "киловольт"});
        unitsMap.put("милливольт", new String[] {"вольт", "киловольт"});
        unitsMap.put("киловольт", new String[] {"вольт", "милливольт"});

        unitsMap.put("ом", new String[] {"миллиом", "килоом"});
        unitsMap.put("миллиом", new String[] {"ом", "килоом"});
        unitsMap.put("килоом", new String[] {"ом", "миллиом"});

        unitsMap.put("герц", new String[] {"килогерц", "мегагерц"});
        unitsMap.put("килогерц", new String[] {"герц", "мегагерц"});
        unitsMap.put("мегагерц", new String[] {"герц", "килогерц"});

        Container container = super.getContentPane();
        container.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weighty = 0.5;

        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.ipady = 20;
        JLabel label = new JLabel("Конвертер");
        container.add(label, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.BOTH;


        text = new JTextField();
        container.add(text, constraints);

        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;

        cbModel1 = new DefaultComboBoxModel<>();
        for (String s : unitsMap.keySet()) {
            cbModel1.addElement(s);
        }
        cbFirst = new JComboBox<>(cbModel1);
        container.add(cbFirst, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        toWhat = new JLabel("Result");
        container.add(toWhat, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        cbModel2 = new DefaultComboBoxModel<>();
        for (String s : unitsMap.get((String) cbFirst.getSelectedItem())) {
            cbModel2.addElement(s);
        }
        cbSecond = new JComboBox<>(cbModel2);
        container.add(cbSecond, constraints);
        cbFirst.addActionListener(e -> {
            cbModel2.removeAllElements();
            for (String s : unitsMap.get((String) cbFirst.getSelectedItem())) {
                cbModel2.addElement(s);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.PAGE_END;
        JButton button = new JButton("Ок");
        container.add(button, constraints);

        button.addActionListener(new ButtonManager());

    }

    class ButtonManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double number = Double.parseDouble(text.getText());
                doCalc(number);

            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(null, "Введите корректное число!",
                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            }
        }

        public void doCalc (double number) {

            String first = (String) Objects.requireNonNull(cbFirst.getSelectedItem());
            String second = (String) Objects.requireNonNull(cbSecond.getSelectedItem());
            switch (first) {
                case "килограмм":
                    switch (second) {
                        case "грамм":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "миллиграмм":
                            toWhat.setText(String.valueOf(number / 1000000));
                            break;
                        case "тонна":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                    }
                    break;
                case "грамм":
                    switch (second) {
                        case "килограмм":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "миллиграмм":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                    }
                    break;

                case "миллиграмм":
                    switch (second) {
                        case "грамм":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "килограмм":
                            toWhat.setText(String.valueOf(number / 1000000));
                            break;
                    }
                    break;

                case "тонна":
                    if (second.equals("килограмм")) {
                        toWhat.setText(String.valueOf(number * 1000));
                    }
                    break;

                case "секунда":
                    switch (second) {
                        case "минута":
                            toWhat.setText(String.valueOf(number / 60));
                            break;
                        case "час":
                            toWhat.setText(String.valueOf(number / 3600));
                            break;
                    }
                    break;

                case "минута":
                    switch (second) {
                        case "секунда":
                            toWhat.setText(String.valueOf(number * 60));
                            break;
                        case "час":
                            toWhat.setText(String.valueOf(number / 60));
                            break;
                    }
                    break;

                case "час":
                    switch (second) {
                        case "минута":
                            toWhat.setText(String.valueOf(number * 60));
                            break;
                        case "секунда":
                            toWhat.setText(String.valueOf(number * 3600));
                            break;
                    }
                    break;

                case "ампер":
                    switch (second) {
                        case "миллиампер":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                        case "килоампер":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                    }
                    break;

                case "миллиампер":
                    switch (second) {
                        case "ампер":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "килоампер":
                            toWhat.setText(String.valueOf(number / 1000000));
                            break;
                    }
                    break;

                case "килоампер":
                    switch (second) {
                        case "ампер":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                        case "миллиампер":
                            toWhat.setText(String.valueOf(number * 1000000));
                            break;
                    }
                    break;

                case "кельвин":
                    if (second.equals("градусы цельсия")) {
                        toWhat.setText(String.valueOf(number - 273.15));
                    }
                    break;

                case "градусы цельсия":
                    if (second.equals("кельвин")) {
                        toWhat.setText(String.valueOf(number + 273.15));
                    }
                    break;

                case "литр":
                    if (second.equals("миллилитр")) {
                        toWhat.setText(String.valueOf(number * 1000));
                    }
                    break;

                case "миллилитр":
                    if (second.equals("литр")) {
                        toWhat.setText(String.valueOf(number * 1000));
                    }
                    break;

                case "ватт":
                    switch (second) {
                        case "киловатт":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "лошадиная сила":
                            toWhat.setText(String.valueOf(number / 735.5));
                            break;
                    }
                    break;

                case "киловатт":
                    if (second.equals("ватт")) {
                        toWhat.setText(String.valueOf(number * 1000));
                    }
                    break;

                case "лошадиная сила":
                    if (second.equals("ватт")) {
                        toWhat.setText(String.valueOf(number * 735.5));
                    }
                    break;

                case "ньютон":
                    if (second.equals("килограмм-сила")) {
                        toWhat.setText(String.valueOf(number * 0.10197162));
                    }
                    break;

                case "килограмм-сила":
                    if (second.equals("ньютон")) {
                        toWhat.setText(String.valueOf(number / 0.10197162));
                    }
                    break;

                case "паскаль":
                    if (second.equals("бар")) {
                        toWhat.setText(String.valueOf(number / 100000));
                    }
                    break;

                case "бар":
                    if (second.equals("паскаль")) {
                        toWhat.setText(String.valueOf(number * 100000));
                    }
                    break;

                case "джоуль":
                    switch (second) {
                        case "килоджоуль":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "ватт-секунда":
                            toWhat.setText(String.valueOf(number));
                            break;
                    }
                    break;

                case "килоджоуль":
                    if (second.equals("джоуль")) {
                        toWhat.setText(String.valueOf(number * 1000));
                    }
                    break;

                case "ватт-секунда":
                    if (second.equals("джоуль")) {
                        toWhat.setText(String.valueOf(number));
                    }
                    break;

                case "вольт":
                    switch (second) {
                        case "милливольт":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                        case "киловольт":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                    }
                    break;

                case "милливольт":
                    switch (second) {
                        case "вольт":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "киловольт":
                            toWhat.setText(String.valueOf(number / 1000000));
                            break;
                    }
                    break;

                case "киловольт":
                    switch (second) {
                        case "вольт":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                        case "милливольт":
                            toWhat.setText(String.valueOf(number * 1000000));
                            break;
                    }
                    break;

                case "ом":
                    switch (second) {
                        case "миллиом":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                        case "килоом":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                    }
                    break;

                case "миллиом":
                    switch (second) {
                        case "ом":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "килоом":
                            toWhat.setText(String.valueOf(number / 1000000));
                            break;
                    }
                    break;

                case "килоом":
                    switch (second) {
                        case "ом":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                        case "миллиом":
                            toWhat.setText(String.valueOf(number * 1000000));
                            break;
                    }
                    break;

                case "герц":
                    switch (second) {
                        case "килогерц":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                        case "мегагерц":
                            toWhat.setText(String.valueOf(number / 1000000));
                            break;
                    }
                    break;

                case "килогерц":
                    switch (second) {
                        case "герц":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                        case "мегагерц":
                            toWhat.setText(String.valueOf(number / 1000));
                            break;
                    }
                    break;

                case "мегагерц":
                    switch (second) {
                        case "герц":
                            toWhat.setText(String.valueOf(number * 1000000));
                            break;
                        case "килогерц":
                            toWhat.setText(String.valueOf(number * 1000));
                            break;
                    }
                    break;
            }
        }
    }

}
