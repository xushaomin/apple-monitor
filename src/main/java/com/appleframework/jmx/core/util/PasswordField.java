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
package com.appleframework.jmx.core.util;

import java.io.*;
import java.util.Arrays;

/**
 * This class prompts the user for a password and attempts to mask input with ""
 */

public class PasswordField {

    /**
     *@param prompt The prompt to display to the user.
     *@return The password as entered by the user.
     */
    public static char[] getPassword(String prompt) throws IOException {
        /* password holder */
        char[] password = new char[1000];
        MaskingThread maskingthread = new MaskingThread(prompt);
        /* block until enter is pressed */
        Thread thread = new Thread(maskingthread);
        thread.start();
        int index = 0;
        for(; true; index++) {
            char c = (char) System.in.read();
            // assume enter pressed, stop masking
            maskingthread.stopMasking();

            if (c == '\r') {
                c = (char) System.in.read();
                if (c == '\n') {
                    break;
                } else {
                    continue;
                }
            } else if (c == '\n') {
                break;
            } else {
                /* store the password */
                password[index] = c;
            }
        }
        char[] passwordEntered = new char[index];
        for(int i=0; i<passwordEntered.length; i++){
            passwordEntered[i] = password[i];
        }
        /* clear password */
        Arrays.fill(password, '\0');

        return passwordEntered;
    }

    /**
     * This class attempts to erase characters echoed to the console.
     */
    private static class MaskingThread extends Thread {
        private boolean stop = false;
        private String prompt;

        /**
         *@param prompt The prompt displayed to the user
         */
        public MaskingThread(String prompt) {
            this.prompt = prompt;
        }


        /**
         * Begin masking until asked to stop.
         */
        public void run() {
            while (!stop) {
                try {
                    // attempt masking at this rate
                    Thread.sleep(1);
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }
                if (!stop) {
                    System.out.print("\r" + prompt + " \r" + prompt);
                }
                System.out.flush();
            }
        }


        /**
         * Instruct the thread to stop masking.
         */
        public void stopMasking() {
            this.stop = true;
        }
    }
}

