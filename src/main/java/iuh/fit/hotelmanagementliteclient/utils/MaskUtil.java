package iuh.fit.hotelmanagementliteclient.utils;

/**
 * @author Le Tran Gia Huy
 * @created 18/04/2025 - 2:31 PM
 * @project Hotel-Management-Lite-Client
 * @package iuh.fit.hotelmanagementliteclient.utils
 */
public class MaskUtil {

    public static String mask(String input, String flag) {
        if (input == null || input.isEmpty()) return input;

        switch (flag.toLowerCase()) {
            case "name":
                return maskName(input);
            case "phone":
                return maskPhone(input);
            case "id":
                return maskID(input);
            default:
                return input; // Không biết loại gì thì trả về nguyên
        }
    }

    private static String maskName(String name) {
        String[] parts = name.trim().split("\\s+");
        if (parts.length <= 2) return name; // Không đủ để che

        StringBuilder masked = new StringBuilder();
        masked.append(parts[0]).append(" ");
        for (int i = 1; i < parts.length - 1; i++) {
            masked.append("XXX ");
        }
        masked.append(parts[parts.length - 1]);
        return masked.toString();
    }

    private static String maskPhone(String phone) {
        if (phone.length() < 4) return phone;
        return phone.substring(0, 2) + "XXXXXX" + phone.substring(phone.length() - 2);
    }

    private static String maskID(String id) {
        if (id.length() < 4) return id;
        return id.substring(0, 2) + "XXXXXXXX" + id.substring(id.length() - 2);
    }
}
