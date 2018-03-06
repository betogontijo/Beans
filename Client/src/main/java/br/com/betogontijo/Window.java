package br.com.betogontijo;

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
import javax.swing.JOptionPane;
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

	JComboBox<String> key = new JComboBox<String>();
	static JComboBox<String> value = new JComboBox<String>();
	static DistributedService service;

	public Window() {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(ClientApplication.class,
				new String[] {});
		service = applicationContext.getBean(DistributedService.class);
		setTitle("Client");
		JPanel jPanel = new JPanel();
		jPanel.add(key);
		jPanel.add(value);
		JButton jButton = new JButton("Retrieve");
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Ticker convert = service.convert(key.getSelectedItem().toString(), value.getSelectedItem().toString());
				JOptionPane.showMessageDialog(Window.this, convert.toString());
			}
		});
		jPanel.add(jButton);
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
				for (String value : map.get(key.getSelectedItem())) {
					Window.value.addItem(value);
					Window.value.setEnabled(true);
				}
			}
		});
		// Ticker ticker = service.convert("BTC", "USD");
		// System.out.println(ticker);
	}
}
