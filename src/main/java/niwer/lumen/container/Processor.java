package niwer.lumen.container;

import niwer.lumen.Console;

@FunctionalInterface
public interface Processor {

    public void process(Console data, long time, String formattedMessage);
}
