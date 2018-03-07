package br.com.betogontijo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

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

	public Window() {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(ClientApplication.class,
				new String[] {});
		service = applicationContext.getBean(DistributedService.class);
		setTitle("Client");
		JPanel jPanel = new JPanel(new GridLayout(10, 1));
		JPanel keyValue = new JPanel(new GridLayout(1, 2));
		keyValue.add(key);
		keyValue.add(value);
		jPanel.add(keyValue);
		JButton jButton = new JButton("Retrieve");
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ticker convert = service.convert(key.getSelectedItem().toString(), value.getSelectedItem().toString());

				curValue.setText(convert.getLast() == null ? NULL_VALUE : convert.getLast() + "");
				lowValue.setText(convert.getLow() == null ? NULL_VALUE : convert.getLow() + "");
				highValue.setText(convert.getHigh() == null ? NULL_VALUE : convert.getHigh() + "");
				avgValue.setText(convert.getVwap() == null ? NULL_VALUE : convert.getVwap() + "");
				timestampValue.setText(convert.getTimestamp() == null ? NULL_VALUE : convert.getTimestamp() + "");
				volumeValue.setText(convert.getVolume() == null ? NULL_VALUE : convert.getVolume() + "");
				// JOptionPane.showMessageDialog(Window.this, convert.toString());
			}
		});
		keyValue.add(jButton);
		jPanel.add(keyValue);
		JPanel curPanel = new JPanel(new GridLayout(1, 2));
		curPanel.add(curLabel);
		curPanel.add(curValue);
		JPanel lowPanel = new JPanel(new GridLayout(1, 2));
		lowPanel.add(lowLabel);
		lowPanel.add(lowValue);
		JPanel highPanel = new JPanel(new GridLayout(1, 2));
		highPanel.add(highLabel);
		highPanel.add(highValue);
		JPanel avgPanel = new JPanel(new GridLayout(1, 2));
		avgPanel.add(avgLabel);
		avgPanel.add(avgValue);
		JPanel timestampPanel = new JPanel(new GridLayout(1, 2));
		timestampPanel.add(timestampLabel);
		timestampPanel.add(timestampValue);
		JPanel volumePanel = new JPanel(new GridLayout(1, 2));
		volumePanel.add(volumeLabel);
		volumePanel.add(volumeValue);
		jPanel.add(curPanel);
		jPanel.add(lowPanel);
		jPanel.add(highPanel);
		jPanel.add(avgPanel);
		jPanel.add(timestampPanel);
		jPanel.add(volumePanel);

		add(jPanel);
		setSize(500, 500);
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
					Window.value.setEnabled(true);
				}
			}
		});
		for (String value : map.get(key.getSelectedItem())) {
			Window.value.addItem(value);
			Window.value.setEnabled(true);
		}
		// Ticker ticker = service.convert("BTC", "USD");
		// System.out.println(ticker);
	}
}
