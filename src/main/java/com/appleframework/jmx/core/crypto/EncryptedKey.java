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

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.DESedeKeySpec;

/**
 *
 * date:  Jul 22, 2004
 * @author	Rakesh Kalra
 */
public class EncryptedKey {

    /* encryption/decryption algorigthm */
    public static final String CRYPTO_ALGORITHM = "DESede";

    private static final String PBE_ALGORITHM = "PBEWithMD5AndDES";

    /* TripleDES key size */
    private static final int KEY_SIZE = 112; /* 128 bits (112 effective bits) */



    /* Salt */
    private static final byte[] salt = {
        (byte) 0xd7, (byte) 0x73, (byte) 0x31, (byte) 0x8c,
        (byte) 0x8e, (byte) 0xb7, (byte) 0xee, (byte) 0x91
    };

    /* Iteration count */
    private static final int iteration_count = 1000;

    private SecretKey secretKey;
    private byte[] encryptedKey;

    public EncryptedKey(char[] password)
            throws Exception {

        KeyGenerator keyGen = KeyGenerator.getInstance(CRYPTO_ALGORITHM);
        keyGen.init(KEY_SIZE);
        SecretKey key = keyGen.generateKey();
        this.secretKey = key;
        this.encryptedKey = getEncrypted(secretKey.getEncoded(), password);
    }

    public EncryptedKey(byte[] encryptedKey, char[] password) {

        this.encryptedKey = encryptedKey;
        /* get SecretKey from encryptedKey */
        byte[] key = getDecrypted(encryptedKey, password);
        try {
            SecretKeyFactory keyFac = SecretKeyFactory.getInstance(CRYPTO_ALGORITHM);
            this.secretKey = keyFac.generateSecret(new DESedeKeySpec(key));;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Re-generates the encryptedKey based on the given password.
     *
     * @param password
     */
    public void setPassword(char[] password){
        assert password != null;
        this.encryptedKey = getEncrypted(secretKey.getEncoded(), password);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public byte[] get() {
        return encryptedKey;
    }

    private static byte[] getEncrypted(byte[] plaintext, char[] password) {

        try {
            /* Create PBE Cipher */
            Cipher pbeCipher = getCipher(Cipher.ENCRYPT_MODE, password);
            /* Encrypt the plaintext */
            return pbeCipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] getDecrypted(byte[] cyphertext, char[] password){

        try {
            /* Create PBE Cipher */
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE, password);
            /* get the plaintext */
            return cipher.doFinal(cyphertext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher getCipher(int mode, char[] password)
            throws Exception {

        PBEKeySpec pbeKeySpec;
        PBEParameterSpec pbeParamSpec;
        SecretKeyFactory keyFac;

        /* Create PBE parameter set */
        pbeParamSpec = new PBEParameterSpec(salt, iteration_count);

        pbeKeySpec = new PBEKeySpec(password);
        keyFac = SecretKeyFactory.getInstance(PBE_ALGORITHM);
        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

        /* Create PBE Cipher */
        Cipher pbeCipher = Cipher.getInstance(PBE_ALGORITHM);

        /* Initialize PBE Cipher with key and parameters */
        pbeCipher.init(mode, pbeKey, pbeParamSpec);
        return pbeCipher;
    }
}
