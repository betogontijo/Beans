package br.com.betogontijo;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.betogontijo.Beans.CustomTicker;
import br.com.betogontijo.Beans.DistributedService;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7321835299704305072L;

	final static String NULL_VALUE = "-";

	JComboBox<String> key = new JComboBox<String>();
	static JComboBox<String> value = new JComboBox<String>();
	static DistributedService service;
	final static JLabel marketLabel = new JLabel("Servidor consultado: ");
	JLabel marketValue = new JLabel(NULL_VALUE);
	final static JLabel curLabel = new JLabel("Valor Atual: ");
	JLabel curValue = new JLabel(NULL_VALUE);
	final static JLabel lowLabel = new JLabel("Menor Valor: ");
	JLabel lowValue = new JLabel(NULL_VALUE);
	final static JLabel highLabel = new JLabel("Maior Valor: ");
	JLabel highValue = new JLabel(NULL_VALUE);
	final static JLabel avgLabel = new JLabel("Valor Médio: ");
	JLabel avgValue = new JLabel(NULL_VALUE);
	final static JLabel timestampLabel = new JLabel("Horário: ");
	JLabel timestampValue = new JLabel(NULL_VALUE);
	final static JLabel volumeLabel = new JLabel("Volume: ");
	JLabel volumeValue = new JLabel(NULL_VALUE);

	Properties properties;

	public Window() {
		ConfigurableApplicationContext applicationContext = null;
		try {
			applicationContext = SpringApplication.run(ClientApplication.class, new String[] {});
		} catch (BeanCreationException e) {
			JOptionPane.showMessageDialog(this, "Não foi possivel conectar ao servidor.");
		}

		try {
			InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
			properties = new Properties();
			properties.load(fileInputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		key.setRenderer(new ListCellRenderer<String>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
					boolean isSelected, boolean cellHasFocus) {
				if (value == null) {
					return new JLabel();
				}
				String text = properties.getProperty(value);
				if (text == null) {
					text = value;
				}
				return new JLabel(text);
			}
		});
		value.setRenderer(new ListCellRenderer<String>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
					boolean isSelected, boolean cellHasFocus) {
				if (value == null) {
					return new JLabel();
				}
				String text = properties.getProperty(value);
				if (text == null) {
					text = value;
				}
				return new JLabel(text);
			}
		});
		service = applicationContext.getBean(DistributedService.class);
		setTitle("Cliente");
		JPanel jPanel = new JPanel(new GridLayout(8, 1));
		JPanel keyValue = new JPanel(new GridLayout(1, 2));
		keyValue.add(key);
		keyValue.add(value);
		jPanel.add(keyValue);
		JButton jButton = new JButton("Consultar");

		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomTicker customTicker = service.convert(key.getSelectedItem().toString(),
						value.getSelectedItem().toString());
				Ticker convert = customTicker.getTicker();
				String market = customTicker.getMarket();

				marketValue.setText(market == null ? NULL_VALUE : market);
				curValue.setText(convert.getLast() == null ? NULL_VALUE : convert.getLast() + "");
				lowValue.setText(convert.getLow() == null ? NULL_VALUE : convert.getLow() + "");
				highValue.setText(convert.getHigh() == null ? NULL_VALUE : convert.getHigh() + "");
				avgValue.setText(convert.getVwap() == null ? NULL_VALUE : convert.getVwap() + "");
				timestampValue.setText(convert.getTimestamp() == null ? NULL_VALUE : convert.getTimestamp() + "");
				volumeValue.setText(convert.getVolume() == null ? NULL_VALUE : convert.getVolume() + "");
			}
		});
		keyValue.add(jButton);
		jPanel.add(keyValue);
		JPanel marketPanel = new JPanel(new GridLayout(1, 2));
		marketPanel.setBackground(new Color(255, 255, 255));
		marketPanel.add(marketLabel);
		marketPanel.add(marketValue);
		JPanel curPanel = new JPanel(new GridLayout(1, 2));
		curPanel.setBackground(new Color(255, 255, 255));
		curPanel.add(curLabel);
		curPanel.add(curValue);
		JPanel lowPanel = new JPanel(new GridLayout(1, 2));
		lowPanel.setBackground(new Color(255, 255, 255));
		lowPanel.add(lowLabel);
		lowPanel.add(lowValue);
		JPanel highPanel = new JPanel(new GridLayout(1, 2));
		highPanel.setBackground(new Color(255, 255, 255));
		highPanel.add(highLabel);
		highPanel.add(highValue);
		JPanel avgPanel = new JPanel(new GridLayout(1, 2));
		avgPanel.setBackground(new Color(255, 255, 255));
		avgPanel.add(avgLabel);
		avgPanel.add(avgValue);
		JPanel timestampPanel = new JPanel(new GridLayout(1, 2));
		timestampPanel.setBackground(new Color(255, 255, 255));
		timestampPanel.add(timestampLabel);
		timestampPanel.add(timestampValue);
		JPanel volumePanel = new JPanel(new GridLayout(1, 2));
		volumePanel.setBackground(new Color(255, 255, 255));
		volumePanel.add(volumeLabel);
		volumePanel.add(volumeValue);
		jPanel.add(marketPanel);
		jPanel.add(curPanel);
		jPanel.add(lowPanel);
		jPanel.add(highPanel);
		jPanel.add(avgPanel);
		jPanel.add(timestampPanel);
		jPanel.add(volumePanel);

		add(jPanel);
		setSize(500, 250);
		setVisible(true);
	}

	public void execute() {
		final Map<String, Set<String>> map = service.getMap();
		List<String> keySet = new ArrayList<String>(map.keySet());
		Collections.sort(keySet);
		for (String string : keySet) {
			key.addItem(string);
		}
		value.setEnabled(false);
		key.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Window.value.removeAllItems();
				for (String value : map.get(key.getSelectedItem())) {
					Window.value.addItem(value);
				}
				Window.value.setEnabled(true);
			}
		});
		for (String value : map.get(key.getSelectedItem())) {
			Window.value.addItem(value);
		}
		Window.value.setEnabled(true);
		// Ticker ticker = service.convert("BTC", "USD");
		// System.out.println(ticker);
	}
}
