package com.luxoft.java_networking.example11.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DemoServiceImpl implements DemoService {
	public String getEcho(String text) {
		return text;
	}

	public int getSum(Integer x, Integer y) {
		return x + y;
	}

	public Date getDate() {
		return Calendar.getInstance().getTime();
	}

	public void sendMessage(String msg) {
		System.out.println(msg);
	}

	public List<String> getMessages(String file) throws IOException {
		List<String> lines = new ArrayList<String>();
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
			return lines;
		}
	}
}
