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

import com.appleframework.jmx.core.util.CoreUtils;

import java.io.*;

/**
 *
 * date:  Jul 23, 2004
 * @author	Rakesh Kalra
 */
public class KeyManager {

    public static final String KEY_FILE_NAME = "jmanage-key";

    private static final String KEY_FILE_PATH =
                CoreUtils.getConfigDir() + File.separatorChar + KEY_FILE_NAME;

    public static void writeKey(EncryptedKey encryptedKey)
        throws FileNotFoundException, IOException {

        File file = new File(KEY_FILE_PATH);
        if(file.exists()){
            file.renameTo(new File(KEY_FILE_PATH + ".bak"));
        }
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(encryptedKey.get());
        fos.flush();
        fos.close();
    }
    public static EncryptedKey readKey(char[] password) {

        try {
            byte[] encryptedKey = readKey();
            return new EncryptedKey(encryptedKey, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readKey()
        throws FileNotFoundException, IOException {

        File file = new File(KEY_FILE_PATH);
        BufferedInputStream is =
                new BufferedInputStream(new FileInputStream(file));
        byte[] encryptedKey = new byte[1000];
        int data = is.read();
        int index = 0;
        while(data != -1){
            encryptedKey[index] = (byte)data;
            index ++;
            data = is.read();
        }
        byte[] actualLengthKey = new byte[index];
        for(int i=0; i<index; i++){
            actualLengthKey[i] = encryptedKey[i];
        }
        is.close();
        return actualLengthKey;
    }
}
