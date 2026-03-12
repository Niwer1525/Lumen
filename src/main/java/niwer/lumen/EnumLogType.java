package niwer.lumen;

/**
 * @author Niwer
 */
public enum EnumLogType {
    NONE(EnumLogColor.RESET),
    INFO(EnumLogColor.BLUE),
    WEB_SERVER(EnumLogColor.CYAN),
    DATABASE(EnumLogColor.GREEN),
    SQL(EnumLogColor.PURPLE);
    
    final EnumLogColor color;

    EnumLogType(EnumLogColor color) { this.color = color; }
}
