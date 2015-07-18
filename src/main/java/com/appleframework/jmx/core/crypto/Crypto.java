/**
 * Copyright 2004-2005 jManage.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appleframework.jmx.core.crypto;

import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.core.util.JManageProperties;

import javax.crypto.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

/**
 * Crypto acts as a facade layer for the crypto classes.
 *
 * Note that the crypto functions must not be called directly from CLI. This
 * is because server has the crypto keys and the crypto configuration.
 *
 * date:  Jul 23, 2004
 * @author	Rakesh Kalra
 */
public class Crypto {

    private static final Logger logger = Loggers.getLogger(Crypto.class);

    /* get the hashing algorithm from jmanage.properties */
    private static final String hashAlgorithm =
            JManageProperties.getHashAlgorithm();

    private static Cipher encrypter;
    private static Cipher decrypter;

    public static void init(char[] password){
        EncryptedKey encryptedKey = KeyManager.readKey(password);
        SecretKey secretKey = encryptedKey.getSecretKey();
        encrypter = getCipher(Cipher.ENCRYPT_MODE, secretKey);
        assert encrypter != null;
        decrypter = getCipher(Cipher.DECRYPT_MODE, secretKey);
        assert decrypter != null;
        logger.info("Crypto initialized");
    }

    public static synchronized byte[] encrypt(String plaintext){
        try {
            return encrypter.doFinal(plaintext.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptToString(String plaintext){
        byte[] ciphertext = encrypt(plaintext);
        return byteArrayToHexString(ciphertext);
    }

    public static synchronized byte[] decrypt(byte[] ciphertext){
        try {
            return decrypter.doFinal(ciphertext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String ciphertext){
        byte[] plaintext = decrypt(hexStringToByteArray(ciphertext));
        return new String(plaintext);
    }

    /**
     * hash method can be used without calling init() method on Crypto.
     *
     * @param plaintext
     * @return
     */
    public static String hash(String plaintext){
        return hash(plaintext.toCharArray());
    }

    public static String hash(char[] plaintext){
        try {
            MessageDigest sha = MessageDigest.getInstance(hashAlgorithm);
            byte[] hash = sha.digest(charArrayToByteArray(plaintext));
            return byteArrayToHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher getCipher(int mode, SecretKey secretKey){

        try {
            Cipher cipher = Cipher.getInstance(EncryptedKey.CRYPTO_ALGORITHM);
            cipher.init(mode, secretKey);
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] charArrayToByteArray(char[] array){
        final byte[] output = new byte[array.length];
        for(int i=0; i<output.length; i++){
            output[i] = (byte) array[i];
        }
        return output;
    }

    private static String byteArrayToHexString(byte[] ba)
    {
        StringBuffer buffer = new StringBuffer(ba.length * 2);
        int n = ba.length;
        for (int i = 0; i < n; i++) {
            byte b = ba[i];
            appendASCII(b, buffer);
        }
        return buffer.toString();
    }

    private static byte[] hexStringToByteArray(String s) {
        char[] ca = s.toCharArray();
        int n_chars = ca.length;
        byte[] ba = new byte[n_chars / 2];
        int ci = 0;
        int bi = 0;
        while (ci < n_chars) {
            char c = ca[ci++];
            byte hi = hexCharToByte(c);
            c = ca[ci++];
            byte lo = hexCharToByte(c);
            ba[bi++] = (byte) ((hi << 4) | lo);
        }
        return ba;
    }

    private static void appendASCII(byte b, StringBuffer buffer) {
        byte hi = (byte) ((b >> 4) & 0xf);
        byte lo = (byte) (b & 0xf);
        buffer.append(hexByteToChar(hi));
        buffer.append(hexByteToChar(lo));
    }

    private static char hexByteToChar(byte b) {
        char c = 0;
        switch (b)
        {
        case 0x0: c = '0'; break;
        case 0x1: c = '1'; break;
        case 0x2: c = '2'; break;
        case 0x3: c = '3'; break;
        case 0x4: c = '4'; break;
        case 0x5: c = '5'; break;
        case 0x6: c = '6'; break;
        case 0x7: c = '7'; break;
        case 0x8: c = '8'; break;
        case 0x9: c = '9'; break;
        case 0xa: c = 'a'; break;
        case 0xb: c = 'b'; break;
        case 0xc: c = 'c'; break;
        case 0xd: c = 'd'; break;
        case 0xe: c = 'e'; break;
        case 0xf: c = 'f'; break;
        default: assert false:"Bad byte digit of '" + c + "' received"; break;
        }
        return c;
    }

    private static byte hexCharToByte(char c) {
        byte b = 0;
        switch (c)
        {
        case '0': b = 0x0; break;
        case '1': b = 0x1; break;
        case '2': b = 0x2; break;
        case '3': b = 0x3; break;
        case '4': b = 0x4; break;
        case '5': b = 0x5; break;
        case '6': b = 0x6; break;
        case '7': b = 0x7; break;
        case '8': b = 0x8; break;
        case '9': b = 0x9; break;
        case 'a': case 'A': b = 0xa; break;
        case 'b': case 'B': b = 0xb; break;
        case 'c': case 'C': b = 0xc; break;
        case 'd': case 'D': b = 0xd; break;
        case 'e': case 'E': b = 0xe; break;
        case 'f': case 'F': b = 0xf; break;
        default: assert false:"Bad hex digit of '" + c + "' received"; break;
        }
        return b;
    }
}
