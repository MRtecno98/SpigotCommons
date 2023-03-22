package org.spigot.commons.commands.builtin;

import org.bukkit.command.CommandSender;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.annotations.Help;

import lombok.Builder;
import lombok.Getter;

@Getter
@Help("Sends this help message")
public class HelpCommand extends Command {
	public static final String ARGUMENT_SEPARATOR = " ";
	public static final String DEFAULT_FORMAT = "%s %s";
	
	private Command command;
	private String format;

	private String header;
	
	@Builder
	public HelpCommand(String label, Command command, String format, String header) {
		super(label, 0);
		this.command = command;
		this.format = format;
		this.header = header;
		
		getCommand().getSubcommands().forEach((sub) 
				-> registerSubcommand(new HelpSubcommand(sub.getLabel(), sub)));
	}

	public HelpCommand(String label, Command command) {
		this(label, command, null, null);
	}

	@Override
	public boolean execute(CommandSender sender, ExecutionContext context) {
		if(!context.isLastCommand()) return false;
		
		if(getHeader() != null) sender.sendMessage(getHeader());
		sender.sendMessage(getHelpString(getCommand(), false));
		getCommand().getSubcommands().forEach((sub) 
				-> sender.sendMessage(getHelpString(sub, true)));
		
		return false;
	}
	
	public String getHelpString(Command command, boolean sub) {
		String format = getFormat() != null ? getFormat() : DEFAULT_FORMAT;
		String description = "";
		
		if(command.getClass().isAnnotationPresent(Help.class)) {
			Help annot = command.getClass().getAnnotation(Help.class);
			
			if(!annot.format().equals("")) 
				format = annot.format();
			description = annot.value();
		}
		
		/* if(format.contains("\0")) {
			String[] splitted = format.split("\0", 2);
			format = sub ? String.join("", splitted) : splitted[0];
		} */
		
		return String.format(format, "/" + getHelpLabel() + (sub ? " " + command.getLabel() : ""), description).trim();
	}
	
	protected String getHelpLabel() {
		return getCommand().getLabel();
	}
	
	class HelpSubcommand extends HelpCommand {
		public HelpSubcommand(String label, Command command) {
			super(label, command);
		}
		
		@Override
		protected String getHelpLabel() {
			return String.join(ARGUMENT_SEPARATOR, HelpCommand.this.getCommand().getLabel(), super.getHelpLabel());
		}
	}
}
