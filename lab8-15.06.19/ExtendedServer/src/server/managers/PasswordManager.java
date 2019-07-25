package server.managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Класс {@code PasswordManager} содержит статические методы для работы с паролями.
 * @author Артемий Кульбако
 * @version 1.2
 * @since 02.06.19
 */
public class PasswordManager {

    /**
     * Генерирует новый 8-ми значный пароль, содержащий прописные и строчные символы латинского алфавита и цифры.
     * За генерацию символов отвечает {@link java.security.SecureRandom}.
     * @return новый пароль.
     */
    public static String generateNewPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) password.append(chars.charAt(random.nextInt(chars.length())));
        System.out.println("Новый пароль сгенерирован.");
        return password.toString();
    }


    /**
     * Получает хэш-последовательность для переданной строки, согласно указанному алгоритму (соль не добавляется).
     * @param nudeString строка, для которой необходимо получить хэш.
     * @param algorithm алгоритм хэширования.
     * @return хэш-последовательность.
     * @throws NoSuchAlgorithmException если алгоритм не найден.
     */
    public static String getHash(String nudeString, String algorithm) throws NoSuchAlgorithmException {
            MessageDigest mDigest = MessageDigest.getInstance(algorithm);
            byte[] hash = mDigest.digest(nudeString.getBytes());
            StringBuilder strongPassword = new StringBuilder();
            for (byte b : hash) strongPassword.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            System.out.println("Получена hash-последовательность.");
            return strongPassword.toString();
    }
}
