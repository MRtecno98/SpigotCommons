package org.spigot.commons.commands;

import java.util.List;

import lombok.Value;

@Value
public class ExecutionContext {
	private String aliasLabel;
	private org.bukkit.command.Command bukkitCommand;
	private List<String> callArguments;
	private boolean isLastCommand;
}
