package org.spigot.commons.tests;

import java.util.ArrayList;
import java.util.List;

import org.spigot.commons.util.AbstractCommandSender;

import lombok.Getter;

@Getter
public class TrackSender extends AbstractCommandSender {
	private List<String> messages = new ArrayList<>();
	
	@Override
	public void sendMessage(String message) {
		getMessages().add(message);
	}

	@Override
	public void sendMessage(String[] messages) {
		for(String s : messages) sendMessage(s);
	}
}
