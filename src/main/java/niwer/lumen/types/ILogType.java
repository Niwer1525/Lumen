package niwer.lumen.types;

import niwer.lumen.EnumLogColor;

/**
 * This interface represents a log type, which is used to categorize logs and assign them a specific color in the console.
 * E.G : INFO logs will be blue, while 'NETWORK' will be yellow, and so on. This allows for better organization and readability of logs in the console/file.
 * 
 * @note We recommend using BasicLogType to create your own types insted of implementing this directly.
 * @author Niwer
 * @see BasicLogType
 */
public interface ILogType {
    String name();
    
    EnumLogColor color();
}
