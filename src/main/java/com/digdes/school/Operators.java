package com.digdes.school;

import java.util.regex.Pattern;

public enum Operators {

    EQUALS("=") {
        @Override
        boolean operation(Object val1, Object val2) {
            return val1.equals(val2);
        }
    },
    NOT_EQUALS("!=") {
        @Override
        boolean operation(Object val1, Object val2) {
            return !val1.equals(val2);
        }
    },
    LIKE("LIKE") {
        @Override
        boolean operation(Object val1, Object val2) {
            if (val1 instanceof String && val2 instanceof String) {
                String str1 = val1.toString();
                String str2 = val2.toString();
                return like(str1, str2);
            }
            throw new IllegalArgumentException("operator LIKE is working only with Strings");
        }

        private boolean like(String str1, String str2) {
            return Pattern.compile(str2.replaceAll("%", ".*")).matcher(str1).matches();
        }
    },
    ILIKE("ILIKE") {
        @Override
        boolean operation(Object val1, Object val2) {
            if (val1 instanceof String && val2 instanceof String) {
                String str1 = val1.toString();
                String str2 = val2.toString();

                return iLike(str1, str2);
            }
            throw new IllegalArgumentException("operator ILIKE is working only with Strings");
        }

        private boolean iLike(String str1, String str2) {
            return Pattern.compile(str2.replaceAll("%", ".*"), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(str1).matches();
        }
    },
    HIGHER_OR_EQUALS(">=") {
        @Override
        boolean operation(Object val1, Object val2) {

            if (val1 instanceof Double  && (val2 instanceof Double) ) {
                return (Double) val1 >= (Double) val2;

            } else if (val1 instanceof Long  && (val2 instanceof Long)) {
                return (Long) val1 >= (Long) val2;
            } else {
                throw new IllegalArgumentException("Comparing >= problem ");
            }
        }
    },
    LESS_OR_EQUALS("<=") {
        @Override
        boolean operation(Object val1, Object val2) {
            if (val1 instanceof Double  && (val2 instanceof Double) ) {
                return (Double) val1 <= (Double) val2;

            } else if (val1 instanceof Long  && (val2 instanceof Long)) {
                return (Long) val1 <= (Long) val2;
            } else {
                throw new IllegalArgumentException("Comparing <= problem ");
            }
        }
    },
    HIGHER(">") {
        @Override
        boolean operation(Object val1, Object val2) {
            if (val1 instanceof Double  && (val2 instanceof Double) ) {
                return (Double) val1 > (Double) val2;

            } else if (val1 instanceof Long  && (val2 instanceof Long)) {
                return (Long) val1 > (Long) val2;
            } else {
                throw new IllegalArgumentException("Comparing >= problem ");
            }
        }
    },
    LESS("<") {
        @Override
        boolean operation(Object val1, Object val2) {
            if (val1 instanceof Double  && (val2 instanceof Double) ) {
                return (Double) val1 < (Double) val2;

            } else if (val1 instanceof Long  && (val2 instanceof Long)) {
                return (Long) val1 < (Long) val2;
            } else {
                throw new IllegalArgumentException("Comparing >= problem ");
            }
        }
    };

    private final String operator;

    Operators(String operator) {
        this.operator = operator;
    }


    public static Operators getByOperator(String str) {
        for (Operators i : Operators.values()) {
            if (i.getNameOperator().equals(str.toUpperCase())) {
                return i;
            }
        }
        throw new IllegalArgumentException("no constant");
    }

    public String getNameOperator() {
        return operator;
    }

    abstract boolean operation(Object val1, Object val2);
}