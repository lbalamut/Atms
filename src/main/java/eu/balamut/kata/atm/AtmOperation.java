package eu.balamut.kata.atm;

/**
 */
public class AtmOperation {

    private final Type type;
    private final Integer argument;

    public enum Type {

        BALANCE("B", false),
        WITHDRAWAL("W", true);

        private final String logSymbol;
        private final boolean argumentMandatory;

        Type(String logSymbol, boolean argumentMandatory) {
            this.logSymbol = logSymbol;
            this.argumentMandatory = argumentMandatory;
        }

        @Override
        public String toString() {
            return logSymbol;
        }

        public static Type fromString(String typeString) {
            StringBuilder possibleValues = new StringBuilder();

            for (Type type : values()) {
                if (type.logSymbol.equals(typeString)) {
                    return type;
                }
                possibleValues.append(type).append(",");
            }

            throw new IllegalArgumentException("no such type: " + typeString
                    + ", possible values: " + possibleValues.substring(0, possibleValues.length() - 1));

        }

        public boolean isArgumentMandatory() {
            return argumentMandatory;
        }
    }

    /**
     * TODO consider multiple arguments for future implementations
     */
    public AtmOperation(Type type, Integer argument) {
        if (type == null) {
            throw new IllegalArgumentException("operation type cannot be null");
        }

        if (type.isArgumentMandatory() && argument == null) {
            throw new IllegalArgumentException("this operation type requires argument");
        }

        this.type = type;
        this.argument = argument;
    }

    public AtmOperation(Type type) {
        this(type, null);
    }

    public Type getType() {
        return type;
    }

    public Integer getArgument() {
        return argument;
    }

}
