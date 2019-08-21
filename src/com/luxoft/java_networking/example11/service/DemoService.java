package com.luxoft.java_networking.example11.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DemoService {
	public String getEcho(String text);

	public int getSum(Integer x, Integer y);

	public Date getDate();

	public void sendMessage(String msg);

	public List<String> getMessages(String file) throws IOException;
}