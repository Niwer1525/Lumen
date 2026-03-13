package niwer.lumen.types;

import niwer.lumen.EnumLogColor;

public class DefaultLogTypes {

    public static final ILogType NONE = new BasicLogType("NONE", EnumLogColor.RESET);
    public static final ILogType INFO = new BasicLogType("INFO", EnumLogColor.BLUE);
}
