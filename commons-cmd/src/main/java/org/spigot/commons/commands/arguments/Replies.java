package org.spigot.commons.commands.arguments;

import lombok.experimental.UtilityClass;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.exceptions.ParseException;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@UtilityClass
public class Replies {
	public Consumer<ExecutionContext> senderMessage(String message) {
		return ctx -> ctx.sender().sendMessage(message);
	}

	public BiConsumer<ExecutionContext, ParseException> senderMessage(Function<ParseException, String> message) {
		return (ctx, ex) -> Replies.senderMessage(message.apply(ex)).accept(ctx);
	}
}
