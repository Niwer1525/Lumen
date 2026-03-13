package niwer.lumen.types;

import niwer.lumen.EnumLogColor;

/**
 * This interface represents a log type, which is used to categorize logs and assign them a specific color in the console.
 * E.G : INFO logs will be blue, while 'NETWORK' will be yellow, and so on. This allows for better organization and readability of logs in the console/file.
 * 
 * @author Niwer
 */
public class BasicLogType implements  ILogType {

    private final String name;
    private final EnumLogColor color;

    public BasicLogType(String name, EnumLogColor color) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Log type name cannot be null or empty");
        if(color == null) throw new IllegalArgumentException("Log type color cannot be null");

        this.name = name;
        this.color = color;
    }

    @Override
    public String name() { return this.name; }

    @Override
    public EnumLogColor color() { return this.color; }
}
