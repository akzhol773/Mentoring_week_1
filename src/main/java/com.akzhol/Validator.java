package com.akzhol;

public class Validator {

    public void validate(String userInput){

        if(userInput == null || userInput.equals("")){
            throw new InvalidCommandException("Invalid command: " + userInput);
        }

        String[] parts = userInput.split(" ");

        switch (parts[0].toUpperCase()){
            case "GET":
                validateGet(parts);
                break;
            case "CREATE":
                validateCreate(parts);
                break;
            case "UPDATE":
                validateUpdate(parts);
                break;
            case "DELETE":
                validateDelete(parts);
                break;
            default:
                throwIllegalCommandException();

        }


    }

    private void validateDelete(String[] parts) {
        if(!"DELETE".equals(parts[0].toUpperCase()) || parts.length > 2 || isNotInt(parts[1])){
            throwIllegalCommandException();
        }
    }

    private void validateUpdate(String[] parts) {
        if(!"UPDATE".equals(parts[0].toUpperCase()) || parts.length < 3 || isNotInt(parts[1])){
            throwIllegalCommandException();
        }
    }

    private void validateCreate(String[] parts) {
        if (parts.length <= 1) {
            throwIllegalCommandException();
        } else if (!"CREATE".equals(parts[0].toUpperCase())) {
            throwIllegalCommandException();
        }
    }

    private void validateGet(String[] parts){
        if (parts.length > 2) {
            throwIllegalCommandException();
        }

        if (!"GET".equals(parts[0].toUpperCase())) {
            throwIllegalCommandException();
        }

        if (parts.length > 1 && isNotInt(parts[1])) {
            throwIllegalCommandException();
        }

    }

    private void throwIllegalCommandException() {
        throw new InvalidCommandException("Invalid command");
    }

    private boolean isNotInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public void validateArray(String[] array, int start, int end) {
        if (start < 0 || end > array.length || start > end) {
            throwIllegalCommandException();
        }
    }


}
